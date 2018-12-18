
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ai265149
 */
public class Client extends Users {

    public Client(int idU, String nom, String pseudo, String passWord, List<String> listeJeuFavori, List<String> listeLike, List<String> listeDislike) {
        super(idU, nom, pseudo, passWord, listeJeuFavori, listeLike, listeDislike);
    }

    public Client(int id) {
        super(id);
    }

    
    
}
