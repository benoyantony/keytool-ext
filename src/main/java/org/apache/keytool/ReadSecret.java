package org.apache.keytool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class ReadSecret {

  /**
   * @param args
   */
  public static void main(String[] args) {
    if (args.length <1){
      System.out.println ("Need to provide a keystore file. ");
      System.exit(1);
    }
    
    File ksFile = new File (args[0]);
    FileInputStream fs = null;
    if (ksFile.exists()){
       try {
        fs = new FileInputStream (args[0]);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
    else{
      System.out.println ("The keystore file does not exist.");
      System.exit(1);
    }
    try {
      System.out.println ("Enter KeyStore Password: ");
      char[] keyStorePassword = System.console().readPassword();

      KeyStore ks = KeyStore.getInstance("JCEKS");
      ks.load(fs, // InputStrweam to keystore
        keyStorePassword);
      
      System.out.println ("Enter alias for the secret : ");
      String key = System.console().readLine();
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
  
      PasswordProtection keyStorePP = new PasswordProtection(keyStorePassword);
      System.out.println ("Enter secret password for <"+key+">  (RETURN if same as keystore password)");
      char[] storePassword = System.console().readPassword();
      
      if (storePassword.length > 0){
        keyStorePP = new PasswordProtection(storePassword);
      }

      SecretKeyEntry ske =
        (SecretKeyEntry)ks.getEntry(key, keyStorePP);
      PBEKeySpec keySpec = (PBEKeySpec)factory.getKeySpec(
          ske.getSecretKey(), 
          PBEKeySpec.class);
  
      char[] password = keySpec.getPassword();
      System.out.println (password);
    }
    catch (FileNotFoundException e){
      
    } catch (KeyStoreException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (CertificateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    } catch (UnrecoverableEntryException e) {
      e.printStackTrace();
    }
  }

}
