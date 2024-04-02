/**
 * @author Arash Tashakori
 */


import java.net.*;
import java.io.*;
import java.util.Random;
public class CaesarCipherServer{
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

		//Creating a random key and sending it back to the client
		Random random = new Random();
		int key = random.nextInt(25) + 1;


		boolean encryptionBegan = false;


		String inputLine;
		//While there is still any lines remained in the input provided in the input stream of the socket
		while ((inputLine = input.readLine())!=null) {


			//If the encryption has begun, send the message to be decrypted
			if (encryptionBegan) {
				//Print out the encrypted message
				System.out.println("Message from the client: " + inputLine);
				System.out.println("Decrypted: " + decrypt(inputLine, key));
				//If a decrypted inputLine is "Bye" abort this while loop
				if (decrypt(inputLine, key).equals("Bye")) {
					System.out.println("Closing connection");
					break;
				}
			}
			//If encryption has not begun yet, just print the message and send it back
			else {
				//Print out the client message
				System.out.println("Message from the client: " + inputLine);
				//Send back the input line to the client
				if (!inputLine.equals("Please send the key")){
					output.println(inputLine);
				}

				//If an inputLine is "Bye" abort this while loop
				if (inputLine.equals("Bye")){
					System.out.println("Closing connection");
					break;
				}
			}

			if (inputLine.equals("Please send the key")){
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
	 * This method decrypts a string (ciphertext) using Caesar cipher. It receives a string and changes each
	 * letter of it with the letter that comes before it with a specific distance (key)
	 * @param encodedText the encoded text that needs to be decrypted
	 * @param key determines the amount of letters that each letter gets shifted by
	 * @return the plaintext
	 */
	public static String decrypt (String encodedText, int key){
		String decodedText = "";
		// Make an array of chars from the plaintext to manipulate it better
		char cipherArray [] = encodedText.toCharArray();
		int keyReverse = 26 - key;	//The reversed value of the key for decryption

		//decrypting each letter
		for (int i = 0; i < cipherArray.length; i++){
			char plaintext;

			//Uppercase letters get changed with uppergets letters and lower case ones with lower case ones
			if (Character.isUpperCase(cipherArray[i])){
				char baseChar = 'A';
				plaintext = (char)((cipherArray[i] - baseChar + keyReverse) % 26 + baseChar);
			} else if (Character.isLowerCase(cipherArray[i])){
				char baseChar = 'a';
				plaintext = (char)((cipherArray[i] - baseChar + keyReverse) % 26 + baseChar);
			} else{	//If a letter is not upper case or lower case we don't change it
				plaintext = cipherArray[i];
			}

			decodedText = decodedText + plaintext;
		}

		return decodedText;
	}


}

