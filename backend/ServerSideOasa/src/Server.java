
import com.mongodb.ConnectionString;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

public class Server extends Thread{

    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;
    private boolean HasAccess;

    public Server(Socket connectionSocket) {
        try {
            objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Socket connectionSocket = null;
        ServerSocket myServerSocket = null;

        myServerSocket = new ServerSocket(8080);

        System.out.println("Server starts with port 3000");


        while (true){
            connectionSocket = myServerSocket.accept();

            Thread server = new Server(connectionSocket);
            server.start();
        }

    }

    public void run(){
        try {

            while (true){
                String task = objectInputStream.readUTF();
                if (task.equals("check")){

                    String ID = objectInputStream.readUTF();
                    new AccessDB().CheckCode(ID);
                }
                if (task.equals("getTicket")){
                    String ID = objectInputStream.readUTF();
                    new AccessDB().getTicket(ID);
                }

                if (task.equals("Card")){

                    new AccessDB().getCardsorTickets(task);
                }
                if (task.equals("Ticket")){

                    new AccessDB().getCardsorTickets(task);
                }
                if (task.equals("Insert User")){

                    new AccessDB().insertUser();
                }
                if (task.equals("Update User")){

                    new AccessDB().updateUser();
                }
                if (task.equals("LastProductScreen")){
                    String id = objectInputStream.readUTF();
                    new AccessDB().checkLastProduct(id);
                }
                if (task.equals("GetTicketName")){
                    String ticket_id = objectInputStream.readUTF();
                    new AccessDB().getTicketName(ticket_id);
                }
                if (task.equals("UpdateWallet")){
                    new AccessDB().WalletAccess();
                }

            }



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}