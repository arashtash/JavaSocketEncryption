# JavaSocketEncryption
This Java program implement two client-server programs that exchange encrypted messages using Caesar cipher and Vigenere cipher.

# Overview
This program which was an assignment for my Network Computing course at Dalhousie University focuses on developing Client-Server applications using Java socket programming. The objective is to implement two client-server programs that exchange encrypted messages using Caesar cipher and Vigenere cipher.

# Prerequisites
Java Development Kit (JDK)
An Integrated Development Environment

# Installation and Running the Program
1- Download the source code.
2- Open the project in your IDE.
3- Compile the Java files.
4- Running the Programs
5- Run the server program (CaesarCipherServer or VigenereCipherServer):

To start the server program, you will need to open a terminal or command prompt on your computer. Navigate to the repository's directory in the terminal. Once you are in the correct directory, you can run the server program by typing the following command:

For CaesarCipherServer:

java CaesarCipherServer

For VigenereCipherServer:

java VigenereCipherServer

This command will start the server, and it will begin listening for connections from clients on the specified port (in this case, port 51000). The server will remain running and waiting for client connections until you manually stop it (usually by pressing Ctrl+C in the terminal).

6- Run the corresponding client program (CaesarCipherClient or VigenereCipherClient):

After starting the server, you can run the client program in a separate terminal or command prompt window. Navigate to the repository's directory in the terminal. Then, run the client program by typing the following command:

For CaesarCipherClient:

java CaesarCipherClient

For VigenereCipherClient:

java VigenereCipherClient

This command will start the client program, which will then attempt to connect to the server running on the same machine (localhost) and port 51000. Once the connection is established, the client can send messages to the server, which will be encrypted using the appropriate cipher (Caesar or Vigenere) based on the server program running. The server will decrypt the messages and display them. The client can continue sending messages until it sends a "Bye" message, which will terminate the connection.


# Ciphers Explained
1- Caesar Cipher
Implements a Client-Server application where the client asks the server for a key, and the server responds with a randomly generated key. All subsequent messages from the client to the server are encrypted using Caesar cipher with the provided key. The server decrypts the messages and displays them.
In Caesar Cipher each letter is replaced with the letter that is k (determined by the key) letters after it in the alphabet. E.g. 'A' will be replaced by 'D' if k = 3.

2- Vigenere Cipher
Repeats Part 1, but instead of Caesar cipher, it uses Vigenere cipher for encryption and decryption. The secret key is a string of letters known to both the client and the server.
In the Vigenere cipher, the secret key consists of a sequence of letters rather than a numerical value. The encryption process is as follows: Assume the plaintext is HELLOWORLD and the secret keyword is SECRET. The keyword is repeated to create a keystring that matches the length of the plaintext, as demonstrated below. The letter in the ciphertext is determined by the intersection of the row corresponding to the plaintext letter and the column corresponding to the keystring letter in the Vigenere table. For instance, the intersection of H and S results in Z, while the intersection of E and E results in I, and so forth.
![Screenshot 2024-04-02 at 10 55 08 AM](https://github.com/arashtash/JavaSocketEncryption/assets/140542600/7f9ae72b-8b45-4d10-9194-9d3f50d8cb82)

# Author
Arash Tashakori


[Website and Contact information](https://arashtash.github.io/)

# MIT License

Copyright (c) 2024 Arash Tashakori

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

# Acknowledgement
This program was an assignment for CSCI3171 Network Computing at Dalhousie University. While I have done all the work for the assignment, the idea and instructions for the assignment were given to us by the instructor, Dr. Srini Sampalli.
