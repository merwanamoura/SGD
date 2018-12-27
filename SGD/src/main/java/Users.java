
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.bson.BsonString;
import org.bson.Document;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ma522501
 */
public class Users {
    
    private int idU;
    private String nom;

    private String pseudo; 
    private String passWord;
    
    private List<String> listeJeuFavori;
    private List<String> listeLike;
    private List<String> listeDislike;
    
    
    public Users(int idU, String nom, String pseudo, String passWord, List<String> listeJeuFavori, List<String> listeLike, List<String> listeDislike) {
        this.idU = idU;
        this.nom = nom;
        this.pseudo = pseudo;
        this.passWord = passWord;
        this.listeJeuFavori = listeJeuFavori;
        this.listeLike = listeLike;
        this.listeDislike = listeDislike;
    }

    public Users(int id){
        idU=id;
        MongoDBConnection.connect(); 
        
        MongoDatabase db = MongoDBConnection.getDb();
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("user");

        it = user.find(eq("idA" , id)).iterator();
        
        Document doc = it.next();

        setNom((String) doc.get("name.last"));
        setPseudo((String) doc.get("pseudo"));
        setPassWord((String) doc.get("passWord")); 
        setListeJeuFavori((List<String>) doc.get("jeuxFavoris"));
        setListeLike((List<String>) doc.get("jeuLike"));
        setListeDislike((List<String>) doc.get("jeuDislike"));
               
    } 
    
    public boolean hasComment(int id){
        boolean bol = false;
        
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> avis = db.getCollection(Avis.nomCollection);
        
        it = avis.find(eq(Avis.idUserCollection , this.idU)).iterator();
        
        while(it.hasNext()){
            Document doc = it.next();
                        
            if( (int)doc.get(Avis.idJeuCollection ) == id){
                bol = true;
            }
        }
 
        return bol;
    }
    
    public String getAvis(int idJeu){
        String str = "";
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> avis = db.getCollection(Avis.nomCollection);
        
        it = avis.find(eq(Avis.idUserCollection  , this.idU)).iterator();
        
        while(it.hasNext()){
            Document doc = it.next();
            
            if( (int)doc.get(Avis.idJeuCollection ) == idJeu  ){
                str = ((String) doc.get(Avis.avisCollection ));
            }
        }
        
        return str;
    }
    
    public boolean isFavori(String nomJeu)
    {
        boolean bol = false;
        for(int i = 0 ; i < listeJeuFavori.size(); i++){
            if(listeJeuFavori.get(i).equals(nomJeu)) bol = true;
        }
        return bol;     
    }
    
    public boolean isLike(String nomJeu)
    {
        boolean bol = false;
        for(int i = 0 ; i < listeLike.size(); i++){
            if(listeLike.get(i).equals(nomJeu)) bol = true;
        }
        return bol;     
    }
    
    public boolean isDislike(String nomJeu)
    {
        boolean bol = false;
        for(int i = 0 ; i < listeDislike.size(); i++){
            if(listeDislike.get(i).equals(nomJeu)) bol = true;
        }
        return bol;     
    }
    
    public void addFavori(String jeu){
        this.listeJeuFavori.add(jeu);
        
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("user");
        
        Document updatedDocument = user.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$push",  
             new BasicDBObject("jeuxFavoris", new BsonString(jeu)))
        );
    }
    
    public void removeFavori(String jeu){
        this.listeJeuFavori.remove(jeu);
                                
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("user");
        
        Document updatedDocument = user.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$pull",  
             new BasicDBObject("jeuxFavoris", new BsonString(jeu)))
        );
    }
    
    public void addJeuLike(String jeu)
    {
        this.listeLike.add(jeu);
        
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("user");
        
        Document updatedDocument = user.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$push",  
             new BasicDBObject("jeuLike", new BsonString(jeu)))
        );
        
    }
    
    public void updateAvis(String str, int idJeu){
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCollection<Document> avis = db.getCollection(Avis.nomCollection);
        avis.updateOne(and(eq(Avis.idJeuCollection , idJeu),eq(Avis.idUserCollection , idU)), new Document("$set", new Document(Avis.avisCollection , str)));
    }
    
    public void createAvis(String str, int idJeu){
        
        MongoDatabase db = MongoDBConnection.getDb();
        MongoCollection<Document> collection = db.getCollection(Avis.nomCollection);
        
        Document doc = new Document(Avis.idUserCollection , idU);
        doc.append(Avis.idJeuCollection  , idJeu);
        doc.append(Avis.avisCollection  , str);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        
        Date d = new Date();
        Date dateS = new Date();
        try {
            dateS = dateFormat.parse(d.toString());
        } catch (ParseException ex) {
        }
 
        
        doc.append(Avis.dateCollection , dateS);

        collection.insertOne(doc);
    }
    
    public void addJeuDislike(String jeu)
    {
        this.listeDislike.add(jeu);
                
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("user");
        
        Document updatedDocument = user.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$push",  
             new BasicDBObject("jeuDislike", new BsonString(jeu)))
        );
        

    }
    
    public void removeJeuLike(String jeu)
    {
        this.listeLike.remove(jeu);
                                
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("user");
        
        Document updatedDocument = user.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$pull",  
             new BasicDBObject("jeuLike", new BsonString(jeu)))
        );
    }
    
    public void removeJeuDislike(String jeu)
    {
        this.listeDislike.remove(jeu);
                        
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> user = db.getCollection("user");
        
        Document updatedDocument = user.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$pull",  
             new BasicDBObject("jeuDislike", new BsonString(jeu)))
        );
        
    }

    public List<String> getListeJeuFavori() {
        return listeJeuFavori;
    }

    public void setListeJeuFavori(List<String> listeJeuFavori) {
        this.listeJeuFavori = listeJeuFavori;
    }

    public List<String> getListeLike() {
        return listeLike;
    }

    public void setListeLike(List<String> listeLike) {
        this.listeLike = listeLike;
    }

    public List<String> getListeDislike() {
        return listeDislike;
    }

    public void setListeDislike(List<String> listeDislike) {
        this.listeDislike = listeDislike;
    }


    
    

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getIdU() {
        return idU;
    }
    
    public String getNom() {
        return nom;
    }



    public String getPseudo() {
        return pseudo;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Users{" + "nom=" + nom + ", pseudo=" + pseudo + ", passWord=" + passWord + '}';
    }

  
    
    

}
