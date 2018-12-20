
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
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
public class Jeu {
    
  private int idJeu; 
  private String nom;
  private String nomEditeur;
  private long dateSortie;
  private String Categorie;
  private String image;
  private int nbLikes;
  private int nbDislikes;

   

    public Jeu(int idJeu, String nom, String nomEditeur, long dateSortie, String Categorie, String  image) {
        this.idJeu = idJeu;
        this.nom = nom;
        this.nomEditeur = nomEditeur;
        this.dateSortie = dateSortie;
        this.Categorie = Categorie;
        this.image = image;
        this.nbLikes = 0;
        this.nbDislikes = 0;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
    
    public Jeu(int id)
    {
        MongoDBConnection.connect();
        this.idJeu=id;
        
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = MongoDBConnection.getDb().getCollection("jeux");

        it = jeux.find(eq("idJeu" , id)).iterator();
        
        Document doc = it.next();

        setNom((String) doc.get("nom"));
        setNomEditeur((String) doc.get("nomEditeur"));
        setCategorie((String) doc.get("categorie"));
        setImage((String) doc.get("image"));
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public void setNbDislikes(int nbDislikes) {
        this.nbDislikes = nbDislikes;
    }
    
    public Jeu(Document doc)
    {
        if(doc.get("idJeu") instanceof Integer)
        {
            setIdJeu((int)doc.get("idJeu"));
        }
        else 
        {
            double idJeu = (double)doc.get("idJeu");
            setIdJeu((int)idJeu);
        }
        
        setNom((String) doc.get("nom"));
        setNomEditeur((String) doc.get("nomEditeur"));
        setCategorie((String) doc.get("categorie"));
        setImage((String) doc.get("image"));
  
    }
    
    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public Jeu() {
    }

    public String getNom() {
        return nom;
    }

    public String getNomEditeur() {
        return nomEditeur;
    }

    public long getDateSortie() {
        
        return dateSortie;
    }
    
    public String getAnneeSortie() 
    {
        int annee =2000;
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = MongoDBConnection.getDb().getCollection("jeux");

        it = jeux.find(eq("idJeu" , idJeu)).iterator();
        Document doc = it.next();
        
        SimpleDateFormat fy = new SimpleDateFormat("yyyy");
        Date date = (Date)(doc.get("dateSortie"));
                
        return fy.format(date);
    }
    
    public String getCategorie() {
        return Categorie;
    }
    
    public int getNbLikes() 
    {
        
        MongoCursor<Document> it;
        MongoCollection<Document> note = MongoDBConnection.getDb().getCollection("Note");

        it = note.find(eq("idJeu" , this.idJeu)).iterator();
        
        Document doc = it.next();

        setNbLikes((int) doc.get("nbLikes"));
        
        return nbLikes;
    }
    
   

    public int getNbDislikes() {
        return nbDislikes;
    }
    

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }

    public void setDateSortie(long dateSortie) {
        this.dateSortie = dateSortie;
    }

    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }
    
    public String toString(){
       String str="nom jeu : " + this.nom;
       
       return str;
    }

    

    
}
