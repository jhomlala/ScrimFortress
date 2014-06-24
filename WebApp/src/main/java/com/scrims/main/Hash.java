package com.scrims.main;
import java.util.UUID;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.math.BigInteger;
public class Hash {
	private static final int ITERATIONS = 1000;
	private static final int KEY_LENGTH = 192; // bits

	public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
	      char[] passwordChars = password.toCharArray();
	      byte[] saltBytes = salt.getBytes();
	  
	      PBEKeySpec spec = new PBEKeySpec(
	          passwordChars,
	          saltBytes,
	          ITERATIONS,
	          KEY_LENGTH
	      );
	      SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	      byte[] hashedPassword = key.generateSecret(spec).getEncoded();
	      return String.format("%x", new BigInteger(hashedPassword));
	    }
	  
	 
	  
}
