
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import java.util.ArrayList;
import java.util.List;
import org.bson.BsonString;
import org.bson.Document;
import java.util.Objects;


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
        MongoCollection<Document> Admin = db.getCollection("Admin");

        it = Admin.find(eq("idA" , id)).iterator();
        
        Document doc = it.next();

        setNom((String) doc.get("name.last"));
        setPseudo((String) doc.get("pseudo"));
        setPassWord((String) doc.get("passWord")); 
        setListeJeuFavori((List<String>) doc.get("jeuFavori"));
        setListeLike((List<String>) doc.get("jeuLike"));
        setListeDislike((List<String>) doc.get("jeuDislike"));
               
    } 
    
    public boolean hasComment(int idJeu){
        boolean bol = false;
        
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> avis = db.getCollection("Avis");
        
        it = avis.find(eq("idUser" , this.idU)).iterator();
        
        while(it.hasNext()){
            Document doc = it.next();
            
            if( (double)doc.get("idJeu") == (double)idJeu  ){
                bol = true;
            }
        }
 
        return bol;
    }
    
    public String getAvis(int idJeu){
        String str = "";
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> avis = db.getCollection("Avis");
        
        it = avis.find(eq("idUser" , this.idU)).iterator();
        
        while(it.hasNext()){
            Document doc = it.next();
            
            if( (double)doc.get("idJeu") == (double)idJeu  ){
                str = (String) doc.get("synopsis");
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
        
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> admin = db.getCollection("Admin");
        
        Document updatedDocument = admin.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$push",  
             new BasicDBObject("jeuFavori", new BsonString(jeu)))
        );
    }
    
    public void removeFavori(String jeu){
        this.listeJeuFavori.remove(jeu);
                                
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> admin = db.getCollection("Admin");
        
        Document updatedDocument = admin.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$pull",  
             new BasicDBObject("jeuFavori", new BsonString(jeu)))
        );
    }
    
    public void addJeuLike(String jeu)
    {
        this.listeLike.add(jeu);
        
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> admin = db.getCollection("Admin");
        
        Document updatedDocument = admin.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$push",  
             new BasicDBObject("jeuLike", new BsonString(jeu)))
        );
        
    }
    
    public void addJeuDislike(String jeu)
    {
        this.listeDislike.add(jeu);
                
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> admin = db.getCollection("Admin");
        
        Document updatedDocument = admin.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$push",  
             new BasicDBObject("jeuDislike", new BsonString(jeu)))
        );
        

    }
    
    public void removeJeuLike(String jeu)
    {
        this.listeLike.remove(jeu);
                                
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> admin = db.getCollection("Admin");
        
        Document updatedDocument = admin.findOneAndUpdate(
             Filters.eq("idA", this.idU),
             new Document("$pull",  
             new BasicDBObject("jeuLike", new BsonString(jeu)))
        );
    }
    
    public void removeJeuDislike(String jeu)
    {
        this.listeDislike.remove(jeu);
                        
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> admin = db.getCollection("Admin");
        
        Document updatedDocument = admin.findOneAndUpdate(
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
