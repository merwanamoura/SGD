import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ai265149
 */
public class Description {
    
    private int idJeu;
    private String synopsis;

    public Description(int idJeu, String synopsis) {
        this.idJeu = idJeu;
        this.synopsis = synopsis;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    
    
}
