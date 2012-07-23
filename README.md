keytool-ext
===========

Extensions for Java Keytool

This utility jar provides the feature to store and read  secret keys in a Java Keystore.
This is a missing functionality in the command line tool - keytool utility. 

Usage : 

Store a secret
=============

java -cp keytool-ext-0.1.jar org.apache.keytool.SaveSecret passwords.jceks
Enter KeyStore Password: 

Enter alias for the secret: 
password
Enter secret to store: 

Enter secret password for <password>  (RETURN if same as keystore password)

Note: The file is created if it doesn't exist.

Read a secret
============

java -cp keytool-ext-0.1.jar org.apache.keytool.ReadSecret passwords.jceks
Enter KeyStore Password: 

Enter alias for the secret : 
password
Enter secret password for <pwd>  (RETURN if same as keystore password)

pass
 
