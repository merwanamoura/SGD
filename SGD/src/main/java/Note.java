
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import java.util.List;
import org.bson.BsonString;
import org.bson.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ai265149
 */
public class Note {
    
    private int idJeu;
    private int nbLikes;
    private int nbDislikes;

    public Note(int idJeu){
        this.idJeu=idJeu;
        
        MongoDatabase db = MongoDBConnection.getDb();
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("Note");

        it = user.find(eq("idJeu" , idJeu)).iterator();
        
        Document doc = it.next();

        setIdJeu((int) doc.get("idJeu"));
        setNbLikes((int) doc.get("nbLikes"));
        setNbDislikes((int) doc.get("nbDislikes"));
    }
    
    public void addLike(){

        MongoDatabase db = MongoDBConnection.getDb();

        MongoCollection<Document> avis = db.getCollection("Note");
        avis.updateOne(eq("idJeu" , idJeu), new Document("$set", new Document("nbLikes" , getNbLikes() + 1)));
        
    }
    
    public void removeLike(){
                
       MongoDatabase db = MongoDBConnection.getDb();

        MongoCollection<Document> avis = db.getCollection("Note");
        avis.updateOne(eq("idJeu" , idJeu), new Document("$set", new Document("nbLikes" , getNbLikes() - 1)));
    }
    
    public void addDislike(){
                
        MongoDatabase db = MongoDBConnection.getDb();

        MongoCollection<Document> avis = db.getCollection("Note");
        avis.updateOne(eq("idJeu" , idJeu), new Document("$set", new Document("nbDislikes" , getNbDislikes() + 1)));
    }
    
    public void removeDislike(){                
        MongoDatabase db = MongoDBConnection.getDb();

        MongoCollection<Document> avis = db.getCollection("Note");
        avis.updateOne(eq("idJeu" , idJeu), new Document("$set", new Document("nbDislikes" , getNbDislikes() - 1)));
    }
      
    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public int getNbDislikes() {
        return nbDislikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public void setNbDislikes(int nbDislikes) {
        this.nbDislikes = nbDislikes;
    }
 
}
