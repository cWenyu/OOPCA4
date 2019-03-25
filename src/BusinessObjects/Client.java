package BusinessObjects;

/**
 * CLIENT February 2019 DL 08/03/19
 *
 * This Client program asks the user to input commands to be sent to the server.
 *
 * There are only two valid commands in the protocol: "Time" and "Echo"
 *
 * If user types "Time" the server should reply with the current server time.
 *
 * If the user types "Echo" followed by a message, the server will echo back the
 * message. e.g. "Echo Nice to meet you"
 *
 * If the user enters any other input, the server will not understand, and will
 * send back a message to the effect.
 *
 * NOte: You must run the server before running this the client. (Both the
 * server and the client will be running together on this computer)
 */
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket, and open new socket

            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client: This Client is running and has connected to the server");

            System.out.println("If you want to insert a new movie ,please use space to indicate table colunm is finished except last one"
                    + "and if the column is empty please use null instead");
            String message = "please enter command:";
            System.out.print(message);        
            String command;

            OutputStream os = socket.getOutputStream();

            PrintWriter socketWriter = new PrintWriter(os, true);

            Scanner socketReader = new Scanner(socket.getInputStream());
            labelB:
            while (in.hasNextLine()) {
                command = in.nextLine();  // read a command from the user

                String[] lineWords = command.split(" ");
                String subCommand = lineWords[0].toUpperCase();

                String feedback;
                socketWriter.println(command);

                switch (subCommand) {
                    case "TESTMOVIES":
                    case "FINDMOVIEBYID":
                    case "FINDMOVIEBYTITLE":
                    case "FINDMOVIEBYYEAR":
                    case "FINDMOVIEBYDIRECTOR":
                    case "TOPTENMOVIES":
                    case "FINDMOVIEWATCHEDBYUSERNAME":
                        feedback = socketReader.nextLine();
                        System.out.println("Message:\"" + feedback + "\"");
                        break;

                    case "DELETEMOVIE":
                    case "UPDATEMOVIE":
                        feedback = socketReader.nextLine();
                        System.out.println("Message:" + feedback);
                        break;

                    case "INSERTMOVIE":
                    case "MOVIEWATCH":
                        feedback = socketReader.nextLine();
                        System.out.println("Message:\"" + feedback + "\"");
                        break;

                    case "Q":
                        feedback = socketReader.nextLine();
                        System.out.println("Message:" + feedback);
                        break labelB;

                    default:
                        feedback = socketReader.nextLine();
                        System.out.println("Message:" + feedback);
                        break labelB;

                }
                System.out.print(message);
            }
            socketWriter.close();
            socketReader.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }
}
