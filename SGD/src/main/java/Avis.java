
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import java.util.List;
import java.util.Objects;
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
public class Avis {
    
    private int idUser;
    private int idJeu; 
    private String avis;
    private long dateCom;
    
    public static String nomCollection = "Avis";
    public static String idUserCollection = "idUser";
    public static String idJeuCollection = "idJeu";
    public static String avisCollection = "avis";
    public static String dateCollection = "dateCollection";

    public Avis(int idUser, int idJeu, String avis, long dateCom) {
        this.idUser = idUser;
        this.idJeu = idJeu;
        this.avis = avis;
        this.dateCom = dateCom;
    }
    
    public Avis(int idUser, int idJeu){
        this.idUser = idUser;
        this.idJeu = idJeu;
        MongoDBConnection.connect(); 
        
        MongoDatabase db = MongoDBConnection.getDb();
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("Avis");

        it = user.find(and(eq("idUser" , idUser),eq("idJeu" , idJeu))).iterator();
        
        Document doc = it.next();

        setAvis((String) doc.get("avis"));
    }
    
    public boolean exist() 
    {
        boolean bol = false;
        MongoDatabase db = MongoDBConnection.getDb();

        MongoCursor<Document> it;
        MongoCollection<Document> avis = db.getCollection(Avis.nomCollection);

        it = avis.find(and(eq(Avis.idUserCollection , this.idUser),eq(Avis.idJeuCollection , this.idJeu))).iterator();
        
        while(it.hasNext()){
            bol = true;
        }
        return bol;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public String getAvis() {
        return avis;
    }

    public long getDateCom() {
        return dateCom;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public void setDateCom(long dateCom) {
        this.dateCom = dateCom;
    }

    
    
    
    
}
