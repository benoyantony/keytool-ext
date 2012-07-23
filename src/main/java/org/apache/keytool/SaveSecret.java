package org.apache.keytool;

/** Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
/**
 *  A Utility which allows user to store passwords in a keystore
 */
public class SaveSecret {

  public static void main(String[] args) {

    if (args.length <1){
      System.out.println ("Need to provide a keystore file. Will create if the file doesn't exist.");
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
    else {
      System.out.println("The keystore file : " + args[0] +" does not exist. It will be created. " );
    }
    try {
    System.out.println ("Enter KeyStore Password: ");
    char[] keyStorePassword = System.console().readPassword();
    
    KeyStore ks = KeyStore.getInstance("JCEKS");
   
    ks.load(fs, keyStorePassword);
 
    System.out.println ("Enter alias for the secret: ");
    String key = System.console().readLine();
    
    System.out.println ("Enter secret to store: ");
    char[] password = System.console().readPassword();
 
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
    SecretKey generatedSecret =
       factory.generateSecret(new PBEKeySpec(    
         password));
    
    PasswordProtection keyStorePP = new PasswordProtection(keyStorePassword);
    System.out.println ("Enter secret password for <"+key+">  (RETURN if same as keystore password)");
    char[] storePassword = System.console().readPassword();
    
    if (storePassword.length > 0){
      keyStorePP = new PasswordProtection(storePassword);
    }
   
    ks.setEntry(key, new SecretKeyEntry(
       generatedSecret), keyStorePP);
    
    FileOutputStream fos = new FileOutputStream(args[0]);

    ks.store(fos, 
       keyStorePassword);
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
    }
  }

}
