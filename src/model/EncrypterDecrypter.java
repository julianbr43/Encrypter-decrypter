package model;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;


/**
 * This class defines a program that encrypts and decrypts files
 * @author: Julián Andrés Brito
 * @version: 5/06/2021
 */
public class EncrypterDecrypter {
	public final static String AES_ALGORITHM = "AES";
	public final static String PBK_ALGORITHM = "PBKDF2WithHmacSHA256";
	public final static int ITERATIONS = 10000;
	public final static int KEY_LENGTH = 128;
	public final static String HASH_SHA = "SHA-1";

	/**
	 * Method to obtain the 128-bit key, using the PBKDF2 algorithm
	 * 
	 * @param password is the password of the file to be encrypted
	 * @return 
	 */
	public static SecretKey getKey(String password) {
		byte[] salt = new byte[16];
		SecretKeyFactory factory;
		try {
			factory = SecretKeyFactory.getInstance(PBK_ALGORITHM);
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
			SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), AES_ALGORITHM);
			return secret;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("The selected algorithm is not available");
		} catch (InvalidKeySpecException e) {
			System.out.println("Invalid key");
		}
		return null;
	}

	/**
	 * Method that allows you to encrypt a file
	 * 
	 * @param file is the file to encrypt
	 * @param sk is the 128-bit key that will be used for encryption
	 */
	public static void encryptFile(File file, SecretKey sk) {
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, sk);

			File encryptedFile = new File("./resources/files/Encryption_file.txt");
			File shaFile = new File("./resources/files/sha1.txt");

			FileInputStream inputStream = new FileInputStream(file);
			FileOutputStream outputStream = new FileOutputStream(encryptedFile);
			FileOutputStream shaStream = new FileOutputStream(shaFile);

			byte[] buffer = new byte[128];
			int bytesRead;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] output = cipher.update(buffer, 0, bytesRead);
				if (output != null) {
					outputStream.write(output);
				}
			}

			byte[] cifrado = cipher.doFinal();
			if (cifrado != null) {
				outputStream.write(cifrado);
			}

			byte[] sha1 = generateHashSha1(file);
			shaStream.write(sha1);

			inputStream.close();
			outputStream.close();
			shaStream.close();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.out.println("The selected algorithm is not available");
		} catch (InvalidKeyException e) {
			System.out.println("The entered key is invalid (invalid encoding, wrong length, etc");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("The length of the data supplied to a block cipher is incorrect");
		} catch (IOException e) {
			System.out.println("An error occurred while writing / reading files");
		}
	}

	/**
	 * Method to decrypt a file
	 * 
	 * @param file is the file to decrypt
	 * @param sk is the 128 bit key to be used for decryption
	 */
	public static void decryptFile(File file, SecretKey sk) {
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, sk);

			File decryptedFile = new File("./resources/files/Decryption_file.txt");
			File shaFile = new File("./resources/files/sha2.txt");

			FileInputStream inputStream = new FileInputStream(file);
			FileOutputStream outputStream = new FileOutputStream(decryptedFile);
			FileOutputStream shaStream = new FileOutputStream(shaFile);

			byte[] buffer = new byte[128];
			int bytesRead;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] output = cipher.update(buffer, 0, bytesRead);
				if (output != null) {
					outputStream.write(output);
				}
			}

			byte[] descifrado = cipher.doFinal();
			if (descifrado != null) {
				outputStream.write(descifrado);
			}

			byte[] sha1 = generateHashSha1(decryptedFile);
			shaStream.write(sha1);

			inputStream.close();
			outputStream.close();
			shaStream.close();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.out.println("The selected algorithm is not available");
		} catch (InvalidKeyException e) {
			System.out.println("The entered key is invalid (invalid encoding, wrong length, etc");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("The length of the data supplied to a block cipher is incorrect");
		} catch (IOException e) {
			System.out.println("An error occurred while writing / reading files");
		}

	}

	/**
	 * Method that allows generating the SHA-1 hash of a file
	 * 
	 * @param file is the file to which you want to generate the SHA-1 hash
	 * @return hash SHA-1  of the file
	 */
	public static byte[] generateHashSha1(File file) {
		try {
			InputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			MessageDigest digest = MessageDigest.getInstance(HASH_SHA);
			int numRead;

			do {
				numRead = fis.read(buffer);
				if (numRead > 0) {
					digest.update(buffer, 0, numRead);
				}
			} while (numRead != -1);
			fis.close();
			return digest.digest();
		} catch (FileNotFoundException e) {
			System.out.println("The indicated file could not be opened");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("The selected algorithm is not available");
		} catch (IOException e) {
			System.out.println("An error occurred while writing / reading files");
		}
		return null;
	}

	/**
	 * Method to validate if the SHA1 hash of the encrypted and decrypted file match
	 * 
	 * @return a boolean indicating whether the SHA-1 hashes of the encrypted and decrypted files match
	 */
	public static boolean validateSHA() {
		File shaOriginal = new File("./resources/files/sha1.txt");
		File shaDecrypted = new File("./resources/files/sha2.txt");

		String sha1;
		sha1 = getSha(shaOriginal);
		String sha2 = getSha(shaDecrypted);
		return sha1.equals(sha2);
	}

	/**
	 * Method that allows to obtain the SHA-1 hash written in a file
	 * 
	 * @param file is where the SHA-1 hash is written
	 * @return the hash SHA-1 of the file
	 */
	public static String getSha(File file) {
		String result = "";

		try {
			DataInputStream reader = new DataInputStream(new FileInputStream(file));
			int nBytesToRead;
			nBytesToRead = reader.available();
			if (nBytesToRead > 0) {
				byte[] bytes = new byte[nBytesToRead];
				reader.read(bytes);
				result = new String(bytes);
			}
			reader.close();
			return result;
		} catch (IOException e) {
			System.out.println("An error occurred while writing / reading files");
		}
		return result;

	}
	
	public static void main(String[] args) {

	}

}
