/**
 * @author Arash Tashakori
 */

import java.io.*;
import java.net.*;
public class VigenereCipherClient{
	public static void main(String[] args) throws IOException{
	Socket link = null;
	PrintWriter output = null;
	BufferedReader input = null;

	try{

	link= new Socket("127.0.0.1", 51000);
	//Create a new PrintWriter to write the client's output into the socket's outgoing stream for the server
	output = new PrintWriter(link.getOutputStream(), true);
	//Create a new BufferedReader object to get the incoming stream from the client's socket's incoming stream
	input = new BufferedReader(new InputStreamReader(link.getInputStream()));
	}
	//If the host at the given address does not exist, catch the exception and abort the program with code 1
	catch(UnknownHostException e)
	{
		System.out.println("Unknown Host");
		System.exit(1);
	}
	//If cannot get the input/output from the server, catch IOException and abort the program with code 1
	catch (IOException ie) {
		System.out.println("Cannot connect to host");
		System.exit(1);
	}
	//Create a BufferedReader to get the user's stream of inputs (directly entered)
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

	String key = "A";
	boolean encryptionBegan = false;

	String usrInput;

	//While user still enters new non-blank lines,
	while ((usrInput = stdIn.readLine())!=null) {
		//A boolean control var to prevent the server from sending back the message to the client if it's the req message
		boolean usrInputIsKeyReq = false;

		if (usrInput.equals("PLEASE SEND THE KEY")){
			output.println(usrInput);
			key = input.readLine();	//Getting the keyword from the server
			System.out.println("Echo from Server: The key is " + key);
			encryptionBegan = true;
			usrInputIsKeyReq = true;
		}
		//If encryption has began (after receiving the key), encrypt before sending
		if (encryptionBegan) {
			usrInput = encrypt(usrInput, key);

			//If the usrInput is the request to get the key, we don't want to send the message again
			if (!usrInputIsKeyReq){
				output.println(usrInput);
			}
		}
		//If the encryption hasn't begun send the plain text
		else {
			//send the usrInput to the server
			output.println(usrInput);

			System.out.println("Echo from Server: " + input.readLine());
		}

		if (usrInput.equals("BYE")){
			break;
		}
	}

	//Close the BufferedReaders, PrintWriter and the connection socket
	output.close();
	input.close();
	stdIn.close();
	link.close();
	}


	/**
	 * This method encrypts a String given a plaintext and a secret keyword using the Vigenere encryption
	 * @param plaintext	The plaintext that needs to be encrypted
	 * @param keyword The secret keyword of any length
	 * @return	The encrypted text
	 */
	public static String encrypt (String plaintext, String keyword){
		String ciphertext = "";

		//Getting each character of the string, encrypting them and reassembling them into ciphertext String
		for (int i = 0; i < plaintext.length(); i++){
			char plainChar = plaintext.charAt(i);
			//keyword should get repeated to become the same size with the plaintext
			char keyChar = keyword.charAt(i % keyword.length());
			char encryptedChar = findVigenereTableVal(plainChar, keyChar);

			ciphertext = ciphertext + encryptedChar;
		}

		return ciphertext;
	}

	/**
	 * This method received two chars - a plain char and a secret keyword's char and sends back the char corresponding
	 * to those two chars in the Vigenere Table
	 * @param plainChar the char from the plaintext
	 * @param keyChar the char from the keyword
	 * @return The corresponding char from the Vigenere Table
	 */
	public static char findVigenereTableVal (char plainChar, char keyChar) {
		//finding row and col by subtracting the ASCII value from base A
		int row = plainChar - 'A';
		int col = keyChar - 'A';
		int encodedChar = (row + col) % 26 + 'A';
		return (char) encodedChar;
	}
}
