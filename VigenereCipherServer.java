/**
 * @author Arash Tashakori
 */

import java.net.*;
import java.io.*;
import java.util.Random;
public class VigenereCipherServer{
	public static void main(String[] args) throws IOException{
		ServerSocket serverSock = null;
		try{
			serverSock = new ServerSocket(51000);
		}
		catch (IOException ie){
			System.out.println("Can't listen on 51000");
			System.exit(1);
		}
		Socket link = null;
		System.out.println("Listening for connection ...");
		try {
			link = serverSock.accept();	//Listen and accepts a client connection
		}
		catch (IOException ie){	//Catches the error if accepting the client connection fails
			System.out.println("Accept failed");
			System.exit(1);
		}
		System.out.println("Connection successful");
		System.out.println("Listening for input ...");

		//Create a PrintWriter object to send the string stream to the client through the output stream of the socket
		PrintWriter output = new PrintWriter(link.getOutputStream(), true);

		/*Create a BufferedReader object to read the string stream outputted by the client from the input stream of
		the socket. */
		BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));

		Random random = new Random();
		//Make a random keyword of length 1-10
		String key = makeRandomKeyword(random, random.nextInt(10) + 1);


		boolean encryptionBegan = false;


		String inputLine;
		//While there is still any lines remained in the input provided in the input stream of the socket
		while ((inputLine = input.readLine())!=null) {


			//If the encryption has begun, send the message to be decrypted
			if (encryptionBegan) {
				//Print out the encrypted message
				System.out.println("Message from the client: " + inputLine);
				System.out.println("Decrypted: " + decrypt(inputLine, key));
				//If a decrypted inputLine is "BYE" abort this while loop
				if (decrypt(inputLine, key).equals("BYE")) {
					System.out.println("Closing connection");
					break;
				}
			}
			//If encryption has not begun yet, just print the message and send it back
			else {
				//Print out the client message
				System.out.println("Message from the client: " + inputLine);
				//Send back the input line to the client
				if (!inputLine.equals("PLEASE SEND THE KEY")){
					output.println(inputLine);
				}

				//If an inputLine is "BYE" abort this while loop
				if (inputLine.equals("BYE")){
					System.out.println("Closing connection");
					break;
				}
			}

			if (inputLine.equals("PLEASE SEND THE KEY")){
				output.println(key);
				encryptionBegan = true;
			}

		}

		//closing PrintWriter, BufferedfReader, Socket and ServerSocket
		output.close();
		input.close();
		link.close();
		serverSock.close();
	}

	/**
	 * This method creates a random keyword of a given length
	 * @param random a Random object
	 * @param length length of the keyword
	 * @return	the keyword of the given length
	 */
	public static String makeRandomKeyword (Random random, int length) {
		String key = "";
		for (int i = 0; i < length; i++) {
			char randomChar = (char) (random.nextInt(26) + 'A');
			key = key + randomChar;
		}
		return key;
	}


	/**
	 * This method decrypts a ciphertext generated through Vigenere encryption given the keyword.
	 * @param encodedText The ciphertext that should be decoded
	 * @param keyword The secret keyword used encryption
	 * @return	The original plaintext
	 */
	public static String decrypt (String encodedText, String keyword){
		String decodedText = "";

		//Getting each character of the string, decrypting them and reassembling them into plaintext String
		for (int i = 0; i < encodedText.length(); i++){
			char cipherChar = encodedText.charAt(i);
			//keyword should get repeated to become the same size with the plaintext
			char keyChar = keyword.charAt(i % keyword.length());
			char encryptedChar = findVigenereTableDecryption(cipherChar, keyChar);

			decodedText = decodedText + encryptedChar;
		}

		return decodedText;
	}

	/**
	 * This method receives a character encoded with Vigenere encryption and the corresponding key character and
	 * returns the corresponding character from the encrypted plaintext
	 * @param ciphertext encoded character
	 * @param key key character corresponding to the encoded character
	 * @return plaintext character
	 */
	public static char findVigenereTableDecryption (char ciphertext, char key) {
		//Getting the int value (distance from base 'A')
		int row = ciphertext - 'A';
		int col = key - 'A';
		int decryptedChar = (row - col + 26) % 26 + 'A';
		return (char) decryptedChar;
	}


}

