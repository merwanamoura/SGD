
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.List;
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
public class Administrateur extends Users{

    public Administrateur(int idU, String nom, String pseudo, String passWord, List<String> listeJeuFavori, List<String> listeLike, List<String> listeDislike) {
        super(idU, nom, pseudo, passWord, listeJeuFavori, listeLike, listeDislike);
    }

 
    public Administrateur(int id) {
        super(id);
    }
    

   
    
    


    
    
}
