package edu.wsu;

import java.io.*;
import java.net.*;


//This is all to test out sending packets

public class Client {
    public static void main(String[] args) throws IOException {
        String hostName = "10.159.105.186";
        int portNumber = 4444;

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            // Send a message to the server
            out.println("Client message");

            // Receive the server's response
            String response = in.readLine();
            System.out.println("Server: " + response);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
