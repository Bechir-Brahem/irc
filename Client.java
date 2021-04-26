import java.net.*;
import java.io.*;
import java.util.*;

class server extends Thread {

    private DataInputStream in   = null;
    public server(Socket socket) {
        try {

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
        }

    }
    public void run() {
        try {
            while (true) {
                //print the server's outcoming data
                String s = in.readUTF();
                System.out.println(s);
                Client.f.convo.append("\n"+s);
            }
        } catch (Exception e) {

        }

    }
}
class reader_client extends Thread {
    String line = "";
    Scanner sc = new Scanner(System.in);
    public void run() {
        while (true) {
            line = sc.nextLine();
            try {
                //sends the input from the user's stdin to the server
                Client.out.writeUTF(line);
            } catch (Exception e) {
                ;
            }
        }
    }
}

public class Client {
    public static Socket socket        = null;
    public static Scanner input = null;
    public static DataOutputStream out     = null;
    public static DataInputStream in   = null;
    public static MyFrameAWT f;
    public Client(String address, int port) {
        try {
            //opens a connection to the server
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            f = new MyFrameAWT(out,false);
            //opens a thread to read standard input of the client without blocking
            //the rest of the program
            reader_client r = new reader_client();
            r.start();
            //opens a thread to connect to a server and handles the input that comes from it
            server s = new server(socket);
            s.start();

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        } finally {
            try {
                // input.close();
                // out.close();
                // socket.close();


            } catch (Exception e) {}

        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
    }
}
