/**
 * @author Arash Tashakori
 */

import java.io.*;
import java.net.*;
public class CaesarCipherClient{
	public static void main(String[] args) throws IOException{
	Socket link = null;
	PrintWriter output = null;
	BufferedReader input = null;

	try{
		//Create a new socket to try and establish connection with server
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

	int key = -1;
	boolean encryptionBegan = false;

	String usrInput;

	//While user still enters new non-blank lines,
	while ((usrInput = stdIn.readLine())!=null) {
		//A boolean control var to prevent the server from sending back the message to the client if it's the req message
		boolean usrInputIsKeyReq = false;

		if (usrInput.equals("Please send the key")){
			output.println(usrInput);
			key = Integer.parseInt(input.readLine());	//Getting the key from the server
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

		if (usrInput.equals("Bye")){
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
	 * This method encrypts a string (plaintext) using Caesar cipher. It receives a string and changes each letter of it
	 * with the letter that comes after it with a specific distance (key)
	 * @param plaintext the plaintext that needs to be encrypted
	 * @param key determines the amount of letters that each letter gets shifted by
	 * @return the ciphertext
	 */
	public static String encrypt (String plaintext, int key){
		String ciphertext = "";

		// Make an array of chars from the plaintext to manipulate it better
		char plainArray [] = plaintext.toCharArray();

		for (int i = 0; i < plainArray.length; i++){
			char encodedChar;

			//Uppercase letters get changed with uppergets letters and lower case ones with lower case ones
			if (Character.isUpperCase(plainArray[i])){
				char baseChar = 'A';
				encodedChar = (char)((plainArray[i] - baseChar + key) % 26 + baseChar);
			} else if (Character.isLowerCase(plainArray[i])){
				char baseChar = 'a';
				encodedChar = (char)((plainArray[i] - baseChar + key) % 26 + baseChar);
			} else{	//If a letter is not upper case or lower case we don't change it
				encodedChar = plainArray[i];
			}

			ciphertext = ciphertext + encodedChar;
		}

		return ciphertext;
	}
}
