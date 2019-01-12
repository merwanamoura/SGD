import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import java.util.*;
import org.bson.Document;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hc047736
 */
public class MongoDBConnection {
    
    static MongoDatabase db;
     
    static void connect()
    {
        char[] pass = new char[10];
        String s = "ma522501";
        pass = s.toCharArray();
        MongoCredential credential = MongoCredential.createCredential("ma522501","ma522501",pass);
        MongoClient client = new MongoClient(new ServerAddress("mongo",27017),Arrays.asList(credential));
        db = client.getDatabase("ma522501");
     
    }  /*

    static void connect()
    {
        MongoClient client = new MongoClient("localhost");
        db = client.getDatabase("ma522501");
    }*/ 

    public static MongoDatabase getDb() {
        return db;
    }
    
}
