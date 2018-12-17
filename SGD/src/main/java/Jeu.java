
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
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

   

    public Jeu(int idJeu, String nom, String nomEditeur, long dateSortie, String Categorie, String  image) {
        this.idJeu = idJeu;
        this.nom = nom;
        this.nomEditeur = nomEditeur;
        this.dateSortie = dateSortie;
        this.Categorie = Categorie;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
    
    public Jeu(int id)
    {
        this.idJeu=id;
        
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("jeux");

        it = jeux.find(eq("idJeu" , id)).iterator();
        
        Document doc = it.next();

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

    public String getCategorie() {
        return Categorie;
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
