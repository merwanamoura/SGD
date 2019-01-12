
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
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
public class SerieJeux {
    
    private int idSerie;
    private String nomSerie;
    private List<Integer> idsJeux;
    private String description;
    
    MongoDatabase db;
      
    public SerieJeux(String nom){
        this.nomSerie = nom;
        MongoDBConnection.connect(); 
        
        db = MongoDBConnection.getDb();
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("SerieJeux");

        it = user.find(eq("nomSerie" , nom)).iterator();
        
        Document doc = it.next();

        setIdSerie((int)doc.get("idSerie"));
        setIdsJeux((List<Integer>) doc.get("idsJeux"));
        setDescription((String) doc.get("description"));
    }
    
    public boolean isInSerie(int idJeu)
    {
        boolean bol = false;
        for(int i = 0; i < this.idsJeux.size() ; i++) if(idsJeux.get(i) == idJeu) bol = true;
        return bol;
    }
    
    public void supprimerJeu(int idJeu)
    {  
        try{
            this.idsJeux.remove(idJeu);

            MongoCursor<Document> it;
            MongoCollection<Document> SJ = db.getCollection("SerieJeux");

            Document updatedDocument = SJ.findOneAndUpdate(
                 Filters.eq("idSerie", this.idSerie),
                 new Document("$pull",  
                 new BasicDBObject("idsJeux", idJeu))
            );
        }catch(IllegalArgumentException e){}
        
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public String getNomSerie() {
        return nomSerie;
    }

    public void setNomSerie(String nomSerie) {
        this.nomSerie = nomSerie;
    }

    public List<Integer> getIdsJeux() {
        return idsJeux;
    }

    public void setIdsJeux(List<Integer> idsJeux) {
        this.idsJeux = idsJeux;
    }

    
 

    
    
    
    
}
