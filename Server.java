import java.net.*;
import java.util.*;
import java.io.*;
class Client_thread extends Thread {
    Socket socket;
    DataInputStream in   ;
    DataOutputStream out ;
    DataOutputStream tmp_out ;

    public Client_thread(Socket tmps) throws Exception {
        socket = tmps;
        in          = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out        = new DataOutputStream(socket.getOutputStream());


    }
    public void run() {
        try {
            System.out.println("Client accepted");
            out.writeUTF("connection established");
            while (true) {
                String line = "";
                //reads input from a client
                line = in.readUTF();
                int id = 0;
                //finds the id of the client who sent it
                for (int i = 0; i < Server.table.size(); i++) {
                    Socket tmp_socket = Server.table.get(i);
                    if (tmp_socket.equals(socket)) {
                        id = i;
                        continue;
                    }
                }
                //redirect the message to all the clients except himself
                for (int i = 0; i < Server.table.size(); i++) {
                    Socket tmp_socket = Server.table.get(i);
                    if (tmp_socket.equals(socket)) {
                        continue;
                    }
                    tmp_out = new DataOutputStream(tmp_socket.getOutputStream());
                    tmp_out.writeUTF("[message from client " + id + "] :" + line);
                }
                //prints the message in the standard output of the server
                System.out.println("[message from client " + id + "] :" + line);
                Server.f.convo.append("\n[message from client " + id + "] :" + line);

            }
        } catch ( java.io.EOFException d ) {}
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                in.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
class reader extends Thread {
    //class reader that handles standard input for the server
    //and then directs it to all the clients
    public void run() {

        Scanner sc = new Scanner(System.in);
        while (true) {
            String  line = sc.nextLine();
            for (Socket socket : Server.table) {
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("[message from server]: " + line);
                } catch (Exception e) {
                    ;
                }
            }
        }
    }
}
public class Server {
    private static Socket       socket = null;
    private ServerSocket server = null;
    String line = "";
    public static MyFrameAWT f;
    //this vector will hold the list of the sockets all clients that connected
    //to the server
    public static  Vector<Socket> table = new Vector<Socket>();

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            f = new MyFrameAWT(true);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            //initiates standard input reader in a thread so it does not block
            //the rest of the program
            reader r = new reader();
            r.start();

            while (true) {
                //create for each client a thread to read and write its input
                socket = server.accept();
                Client_thread x = new Client_thread(socket);
                x.start();
                table.add(socket);
            }
        } catch (IOException i) {
            System.out.println(i);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        Server server = new Server(5000);

    }
}
