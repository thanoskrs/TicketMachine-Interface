
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

    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

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
            String task = objectInputStream.readUTF();

            if (task.equals("check")){

               // int code = objectInputStream.readInt();

                AccessDB accessDB = new AccessDB();
//                accessDB.CheckCode(code);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
