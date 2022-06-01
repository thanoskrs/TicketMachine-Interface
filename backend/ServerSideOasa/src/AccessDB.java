

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class AccessDB {

    private MongoDatabase database;
    private MongoClient mongo;


    public AccessDB(){
        String ConnectionString = "mongodb://nikos:nikosaueboasa13@ac-kbc2inh-shard-00-00.esl1qsr.mongodb.net:27017,ac-kbc2inh-shard-00-01.esl1qsr.mongodb.net:27017,ac-kbc2inh-shard-00-02.esl1qsr.mongodb.net:27017/?ssl=true&replicaSet=atlas-a8gza1-shard-0&authSource=admin&retryWrites=true&w=majority&ssl=true";

        mongo = new MongoClient(new MongoClientURI(ConnectionString));

        // Accessing the database
        database = mongo.getDatabase("Oasa_Users");

        InsertDocuments();
    }

    public void InsertDocuments(){
        MongoCollection<Document> collection = database.getCollection("Ticket");
        System.out.println("Collection "+ collection.getNamespace()+" selected successfully");

        try{
            Document document = new Document();
            document.append("TicketID", "box1_card");
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
            document.append("TicketID", "box2_card");
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
            document.append("TicketID", "box6_card");
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
            document.append("TicketID", "box5_card");
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
            document.append("TicketID", "box7_card");
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
            document.append("TicketID", "box8_card");
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
            document.append("TicketID", "box9_card");
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
            document.append("TicketID", "box10_card");
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
            document.append("TicketID", "box1_card");
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
            document.append("TicketID", "box2_card");
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
            document.append("TicketID", "box3_card");
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
            document.append("TicketID", "box4_card");
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
            document.append("TicketID", "box5_card");
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
            document.append("TicketID", "box6_card");
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

    public void CheckCode(int code){
        // Creating Credentials
        MongoCredential credential;

        MongoCollection<Document> collection = database.getCollection("User");

//        for (Document document: collection.)
//        if (code == )


    }
}
