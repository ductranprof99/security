import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.util.Base64;
import java.util.Arrays;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidKeyException;
import java.security.Signature;
import java.io.File;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.File;
import java.util.Scanner;
public class Lab03_3 {
    static public byte[] sign(String data, String pvtKeyFile) 
	throws InvalidKeyException, Exception { 
	
		byte[] bytes = Files.readAllBytes(Paths.get(pvtKeyFile));
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pvt = kf.generatePrivate(ks);
		
		Signature dsa = Signature.getInstance("SHA1withRSA"); 
		dsa.initSign(pvt);
		dsa.update(data.getBytes());
		return dsa.sign();
	}

	static public boolean verifySignature(byte[] data, byte[] signature, String pubKeyFile) 
	throws Exception {
	
		byte[] bytes = Files.readAllBytes(Paths.get(pubKeyFile));
		X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pub = kf.generatePublic(ks);
		
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initVerify(pub);
		sig.update(data);	
		return sig.verify(signature);	
	}

	static private void signAndEncrypt(String[] args)
	throws InvalidKeyException, Exception {
		File encryptFile = new File("output.enc");
		if ( args.length != 2 ) {
			System.err.println("signAndEncrypt pvtKeyFile message");
			System.exit(1);
		}
		
		int index = 0;
		String pvtKeyFile = args[index++];
		String message = args[index++];
		
		//Sign
		byte[] signStr = sign(message, pvtKeyFile);
		String str = new String(signStr, "UTF-8");
		System.out.println("Message: " + message);
		System.out.println("Sign: " + str);

		// Create a Secret key
		byte key[] = "abcdEFGH".getBytes(); 
		SecretKeySpec secretKey = new SecretKeySpec(key,"DES");

		// Create Cipher object
		Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
		encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
		
		ByteArrayInputStream signStrInputStream = new ByteArrayInputStream(signStr);
		CipherInputStream cis = new CipherInputStream(signStrInputStream, encrypt);

		FileOutputStream fos = new FileOutputStream(encryptFile);
		byte[] b = new byte[8];
		int i = cis.read(b);
		while (i != -1) {
			fos.write(b, 0, i);
			i = cis.read(b);
		}
		cis.close();
		fos.close();

	}

	private static void decryptAndVerify(String[] args) 
	throws Exception{
		File desFile = new File("output.enc");
		if ( args.length != 3 ) {
			System.err.println("decryptAndVerify pubKeyFile encryptFile message");
			System.exit(1);
		}

		// Create a Secret key
		byte key[] = "abcdEFGH".getBytes(); 
		SecretKeySpec secretKey = new SecretKeySpec(key,"DES");

		// Create Cipher object
		Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
		decrypt.init(Cipher.DECRYPT_MODE, secretKey);

		// Open the Encrypted file
		FileInputStream fis = new FileInputStream(desFile);
		CipherInputStream cis = new CipherInputStream(fis, decrypt); 
	
		// Write to the Decrypted file
		byte[] signStr = new byte[256];
		cis.read(signStr);
		cis.close();
		boolean result = verifySignature(args[2].getBytes(), signStr, args[0]);
		System.out.println("Result: " + result);
    }

	static private void doSignAndVerify(String[] args)
	throws InvalidKeyException, Exception
	{
		if ( args.length != 3 ) {
			System.err.println("signAndVerify pvtKeyFile pubKeyFile message");
			System.exit(1);
		}
		
		int index = 0;
		String pvtKeyFile = args[index++];
		String pubKeyFile = args[index++];
		String message = args[index++];
		
		//Sign
		byte[] signStr = sign(message, pvtKeyFile);
		String str = new String(signStr, "UTF-8");
		System.out.println("Message: " + message);
		System.out.println("Sign: " + signStr);
		
		//Verify
		boolean result = verifySignature(message.getBytes(), signStr, pubKeyFile);
		System.out.println("Result: " + result);
    }

    static public void main(String[] args) throws Exception
    {
	if ( args.length == 0 ) {
	    System.err.print("usage: java RSA command params..\n" +
			     "where commands are:\n" +
				 "genkey nameFile\n"+
				 "signAndEncrypt pvtKeyFile message\n" + 
				 "decryptAndVerify pubKeyFile encryptFile message\n" 
				 );
	    System.exit(1);
		}
	int index = 0;
	String command = args[index++];
	String[] params = Arrays.copyOfRange(args, index, args.length);
	if ( command.equals("signAndEncrypt") ) signAndEncrypt(params);
	else if ( command.equals("decryptAndVerify") ) decryptAndVerify(params);
	else if (command.equals("test")) doSignAndVerify(params);
	else if (command.equals("genkey")){
		doGenkey(args);
	}
	else throw new Exception("Unknown command: " + command);
    }

    static private void doGenkey(String[] args)
	throws java.security.NoSuchAlgorithmException,
	       java.io.IOException
    {
        if ( args.length == 1 ) {
            System.err.println("genkey -- need fileBase");
            return;
        }

        int index = 1;
        String fileBase = args[index++];
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        FileOutputStream out1 = new FileOutputStream(fileBase + ".key");
        out1.write(kp.getPrivate().getEncoded());
        
        FileOutputStream out2 = new FileOutputStream(fileBase + ".pub");
        out2.write(kp.getPublic().getEncoded());
    }


}
