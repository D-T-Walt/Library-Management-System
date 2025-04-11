/*
Diwani Walters - 2303848
Olivia McFarlane - 2301555
Javone-Anthony Gordon - 2206126
Kemone Laws - 2109446
*/

package lms;

import java.io.FileWriter;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Password {
	private String defaultP;
	private String hashPassword;
	
	Password(String def, String hashPassword){
		defaultP= def;
		this.hashPassword= hashPassword;
	}
	
	Password(){
		defaultP= "1234Abcd";
		this.hashPassword= "1234Abc";
	}
	
	Password(Password obj){
		defaultP= obj.defaultP;
		this.hashPassword= obj.hashPassword;
	}
	
	public String getDefaultP() {
		return defaultP;
	}

	public void setDefaultP(String defaultP) {
		this.defaultP = defaultP;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	//Hash a given password using SHA-256
	public static String hashPassword(String password) {
        try {
        	//MessageDigest provides cryptographic hash functions.
        	//"SHA-256" is passed as an argument to specify that we are using the SHA-256 hashing algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            //password.getBytes() converts the string password into a byte array.
            //md.digest() processes this byte array through the SHA-256 algorithm and produces a 32-byte (256-bit) hash.
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert bytes to hexadecimal string
            StringBuilder hexaString = new StringBuilder();
            for (byte b : hashedBytes) { //The method iterates through each byte in the hashedBytes array.
                hexaString.append(String.format("%02x", b)); //String.format("%02x", b) converts a byte into a two-character hexadecimal string.
            }
            
            return hexaString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
    // Generates a random 8-character password containing letters, numbers, and special characters
	public static String generateDefaultPassword() {
        String allChars = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM!@#$%&";
        Random random = new Random();
        
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) { // Generate 8-character password
            password.append(allChars.charAt(random.nextInt(allChars.length()))); 
            //Using the length of allChars to generate a random integer, which is then used to append the character at the random number's index to the password
        }

        return password.toString();
    }

	//Write/ Saves the arraylist of passwords to the Passwords file
	public static void storePasswords(ArrayList<Password> pass){
		FileWriter outFStream = null;
		try {
			outFStream= new FileWriter(new File("PasswordCollection.txt"), false);
			
			for (Password pwd : pass) {
				outFStream.write(pwd.toString()+ "\n");
	        }
			
			System.out.println("Passwords successfully saved to file.");
			outFStream.close();
		} catch (IOException e) {
            Toolkit.getDefaultToolkit().beep();
			System.err.println(e.getMessage());
		}catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
			System.err.println(e.getMessage());
		}
	}
	
	//Read/ Retrives all the Passwords from the file into arraylist
	public static void loadPasswords(ArrayList<Password> pass, Scanner fileScanner) {
        try {
            while (fileScanner.hasNext()) {
                String defaultP = fileScanner.next();
                String hashPassword = fileScanner.next();
                pass.add(new Password(defaultP, hashPassword));
            }
			System.out.println("Passwords successfully loaded from file.");
        } catch (InputMismatchException e) {
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Error: Invalid data format encountered. " + e.getMessage());
        } catch (NoSuchElementException e) {
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Error: Missing data elements. " + e.getMessage());
        } catch (IllegalStateException e) {
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Error: Scanner is closed. " + e.getMessage());
        }
    }
	
	@Override
	public String toString() {
		return defaultP + "\t" + hashPassword;
	}	
}
