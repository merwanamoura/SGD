
import java.util.ArrayList;

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
    private ArrayList<Integer> idsJeux;

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public void setNomSerie(String nomSerie) {
        this.nomSerie = nomSerie;
    }

    public void setIdsJeux(ArrayList<Integer> idsJeux) {
        this.idsJeux = idsJeux;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public String getNomSerie() {
        return nomSerie;
    }

    public ArrayList<Integer> getIdsJeux() {
        return idsJeux;
    }

    public SerieJeux(int idSerie, String nomSerie, ArrayList<Integer> idsJeux) {
        this.idSerie = idSerie;
        this.nomSerie = nomSerie;
        this.idsJeux = idsJeux;
    }
 

    
    
    
    
}
