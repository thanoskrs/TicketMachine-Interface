

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import javax.print.Doc;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class AccessDB {

    private MongoDatabase database;
    private MongoClient mongo;


    public AccessDB(){
        String ConnectionString = "mongodb://nikos:nikosaueboasa13@ac-kbc2inh-shard-00-00.esl1qsr.mongodb.net:27017,ac-kbc2inh-shard-00-01.esl1qsr.mongodb.net:27017,ac-kbc2inh-shard-00-02.esl1qsr.mongodb.net:27017/?ssl=true&replicaSet=atlas-a8gza1-shard-0&authSource=admin&retryWrites=true&w=majority&ssl=true";

        mongo = new MongoClient(new MongoClientURI(ConnectionString));

        // Accessing the database
        database = mongo.getDatabase("Oasa_Users");

//        InsertDocuments();
//        insert();
    }

    public boolean CheckCode(String id) throws IOException {
        // Creating Credentials
        MongoCredential credential;

        MongoCollection<Document> collection =  database.getCollection("User");


        BasicDBObject Query = new BasicDBObject();
        Query.put("UserID", id);

        FindIterable<Document> iterDoc = collection.find();
        for (Document doc : iterDoc) {
            System.out.println(doc);
            if (doc.get("userID").equals(id)) {
                Server.objectOutputStream.writeUTF("Pass");
                Server.objectOutputStream.flush();

                Server.objectOutputStream.writeObject((Document) doc);
                Server.objectOutputStream.flush();


                return true;
            }
        }
        Server.objectOutputStream.writeUTF("Not-Pass");
        Server.objectOutputStream.flush();
        return false;


    }

    public void getCardsorTickets(String selected_genre) throws IOException {
        MongoCollection<Document> collection =  database.getCollection("Ticket");

        ArrayList<Document> list = new ArrayList<>();

        FindIterable<Document> iterDoc = collection.find();
        for (Document doc : iterDoc) {
            if (doc.get("Genre").equals(selected_genre)) {
                System.out.println(doc);
                list.add(doc);
            }
        }

        Server.objectOutputStream.writeInt(list.size());
        Server.objectOutputStream.flush();

        for (Document document: list){
            Server.objectOutputStream.writeObject(document);
            Server.objectOutputStream.flush();
        }

    }

    public void getTicket(String id) throws IOException {
        MongoCollection<Document> collection =  database.getCollection("Ticket");

        ArrayList<Document> list = new ArrayList<>();

        FindIterable<Document> iterDoc = collection.find();
        for (Document doc : iterDoc) {
            if (doc.get("TicketID").equals(id)) {
                System.out.println(doc);
                Server.objectOutputStream.writeObject(doc);
                Server.objectOutputStream.flush();
                break;

            }
        }
    }

    public void getTicketName(String id) throws IOException {
        MongoCollection<Document> collection =  database.getCollection("Ticket");

        FindIterable<Document> iterDoc = collection.find();
        for (Document doc : iterDoc) {
            if (doc.get("TicketID").equals(id)) {

                Server.objectOutputStream.writeUTF((String) doc.get("Name"));
                Server.objectOutputStream.flush();
                break;
            }
        }
    }

    public void WalletAccess() throws IOException {
        MongoCollection<Document> collection = database.getCollection("User");

        String userID = Server.objectInputStream.readUTF();

        FindIterable<Document> iterDoc = collection.find();
        for (Document user : iterDoc) {
            if (user.get("userID").equals(userID)) {

                 // update
                String new_amount = Server.objectInputStream.readUTF();

                float sum = Float.parseFloat(user.get("Wallet").toString()) + Float.parseFloat(new_amount);
                System.out.println(new_amount);
                BasicDBObject query = new BasicDBObject();
                query.put("Wallet", user.get("Wallet"));

                BasicDBObject newDocument = new BasicDBObject();
                newDocument.put("Wallet", String.valueOf(sum));

                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", newDocument);

                collection.updateOne(query, updateObject);

                System.out.println("Document updated successfully");
                System.out.println(user);

                break;

            }
        }
    }

    public void insertUser() throws IOException, ClassNotFoundException {

        MongoCollection<Document> collection = database.getCollection("User");

        Document new_user = (Document) Server.objectInputStream.readObject();

        collection.insertOne(new_user);
        System.out.println("Document inserted successfully");
    }

    public void updateUser() throws IOException, ClassNotFoundException {

        MongoCollection<Document> collection = database.getCollection("User");

        String userID = Server.objectInputStream.readUTF();
        String ticketID = Server.objectInputStream.readUTF();

        FindIterable<Document> iterDoc = collection.find();
        for (Document user : iterDoc) {
            if (user.get("userID").equals(userID)) {
                BasicDBObject query = new BasicDBObject();
                query.put("LastProductId", user.get("LastProductId"));
                query.put("LastProductScreen", user.get("LastProductScreen"));

                BasicDBObject newDocument = new BasicDBObject();
                newDocument.put("LastProductId", ticketID);
                newDocument.put("LastProductScreen", true);

                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", newDocument);

                collection.updateOne(query, updateObject);

                System.out.println("Document inserted successfully");
                System.out.println(user);
                break;
            }
        }
    }

    public void checkLastProduct(String id) throws IOException, ClassNotFoundException {

        MongoCollection<Document> collection = database.getCollection("Ticket");

        FindIterable<Document> iterDoc = collection.find();
        for (Document doc : iterDoc) {
            if (doc.get("TicketID").equals(id)) {
                System.out.println(doc);
                Server.objectOutputStream.writeObject(doc);
                Server.objectOutputStream.flush();
                break;
            }
        }

    }

    public void deactivateLstProductScreen(String userId) {
        MongoCollection<Document> collection = database.getCollection("User");

        FindIterable<Document> iterDoc = collection.find();
        for (Document user : iterDoc) {
            if (user.get("userID").equals(userId)) {
                BasicDBObject query = new BasicDBObject();
                query.put("LastProductScreen", user.get("LastProductScreen"));

                BasicDBObject newDocument = new BasicDBObject();
                newDocument.put("LastProductScreen", false);

                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", newDocument);

                collection.updateOne(query, updateObject);
                break;
            }
        }
    }


    public void insert(){
        MongoCollection<Document> collection = database.getCollection("User");
        System.out.println("Collection "+ collection.getNamespace()+" selected successfully");

        try{
            Document document1 = new Document();
            document1.append("userID", "000");
            document1.append("userName", "Kouros Thanos");
            document1.append("Student", true);
            document1.append("Unemployed", false);
            document1.append("LastProductScreen", false);


            //Inserting document into the collection
            collection.insertOne(document1);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document1 = new Document();
            document1.append("userID", "111");
            document1.append("userName", "Deligiannis Nikos");
            document1.append("Student", false);
            document1.append("Unemployed", true);
            document1.append("LastProductScreen", false);


            //Inserting document into the collection
            collection.insertOne(document1);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document1 = new Document();
            document1.append("userID", "222");
            document1.append("userName", "Eleftherou Mirto");
            document1.append("Student", true);
            document1.append("Unemployed", false);
            document1.append("LastProductScreen", false);


            //Inserting document into the collection
            collection.insertOne(document1);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }
    }




    public void InsertDocuments(){
        MongoCollection<Document> collection = database.getCollection("Ticket");
        System.out.println("Collection "+ collection.getNamespace()+" selected successfully");

        // ticket - uniform
        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box1");
            document.append("Name", "90 λεπτών");
            document.append("Genre", "Ticket");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "1.20");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box2");
            document.append("Name", "2 Διαδρομές");
            document.append("Genre", "Ticket");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "2.30");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box3");
            document.append("Name", "5 Διαδρομές");
            document.append("Genre", "Ticket");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "8.20");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box4");
            document.append("Name", "24 Ωρών");
            document.append("Genre", "Ticket");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "4.10");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box5");
            document.append("Name", "5 Ημερών");
            document.append("Genre", "Ticket");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "8.20");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box6");
            document.append("Name", "10+1 Διαδρομές");
            document.append("Genre", "Ticket");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "12.00");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        // ticket - airport

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box1_ticket");
            document.append("Name", "Λεωφοριακές Γραμμές Express");
            document.append("Genre", "Ticket");
            document.append("Kind", "Airport");
            document.append("Standard Price", "5.50");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box2_ticket");
            document.append("Name", "90 Λεπτών");
            document.append("Genre", "Ticket");
            document.append("Kind", "Airport");
            document.append("Standard Price", "9.00");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box3_ticket");
            document.append("Name", "3 Ημερών Τουριστικό");
            document.append("Genre", "Ticket");
            document.append("Kind", "Airport");
            document.append("Standard Price", "20.00");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box4_ticket");
            document.append("Name", "90 Λεπτών Μετ'επιστροφής");
            document.append("Genre", "Ticket");
            document.append("Kind", "Airport");
            document.append("Standard Price", "16.00");
            document.append("Student Price", "");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        // card - uniform
        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box1_card");
            document.append("Name", "90 λεπτών");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "1.20");
            document.append("Student Price", "0.50");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }


//        try{
//            Document document1 = new Document();
//            document1.append("Card Code", "1234567892");
//            document1.append("Name", "Kouros Thanos");
//            document1.append("Last Product", "");
//
//
//            //Inserting document into the collection
//            collection.insertOne(document1);
//            System.out.println("Document inserted successfully");
//        }catch (MongoException mongoException){
//            System.err.println("Unable to insert due to an error: " + mongoException);
//        }


        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box2_card");
            document.append("Name", "2 Διαδρομές");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "2.30");
            document.append("Student Price", "1.00");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box6_card");
            document.append("Name", "5 Διαδρομές");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "5.70");
            document.append("Student Price", "2.50");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box5_card");
            document.append("Name", "10+1 Διαδρομές");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "12.00");
            document.append("Student Price", "5.00");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box7_card");
            document.append("Name", "30 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "27.00");
            document.append("Student Price", "13.50");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box8_card");
            document.append("Name", "90 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "78.00");
            document.append("Student Price", "39.00");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box9_card");
            document.append("Name", "180 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "155.00");
            document.append("Student Price", "75.50");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "uniform_box10_card");
            document.append("Name", "365 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Uniform");
            document.append("Standard Price", "300.00");
            document.append("Student Price", "150.00");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        //airport - card

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box1_card");
            document.append("Name", "Λεωφοριακές Γραμμές Express");
            document.append("Genre", "Card");
            document.append("Kind", "Airport");
            document.append("Standard Price", "5.50");
            document.append("Student Price", "2.70");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box2_card");
            document.append("Name", "90 Λεπτών");
            document.append("Genre", "Card");
            document.append("Kind", "Airport");
            document.append("Standard Price", "9.00");
            document.append("Student Price", "4.50");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box3_card");
            document.append("Name", "30 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Airport");
            document.append("Standard Price", "45.00");
            document.append("Student Price", "22.50");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box4_card");
            document.append("Name", "90 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Airport");
            document.append("Standard Price", "129.00");
            document.append("Student Price", "64.50");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box5_card");
            document.append("Name", "365 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Airport");
            document.append("Standard Price", "446.00");
            document.append("Student Price", "223.00");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

        try{
            Document document = new Document();
            document.append("TicketID", "airport_box6_card");
            document.append("Name", "180 Ημερών");
            document.append("Genre", "Card");
            document.append("Kind", "Airport");
            document.append("Standard Price", "228.00");
            document.append("Student Price", "114.00");


            //Inserting document into the collection
            collection.insertOne(document);
            System.out.println("Document inserted successfully");
        }catch (MongoException mongoException){
            System.err.println("Unable to insert due to an error: " + mongoException);
        }

    }


}