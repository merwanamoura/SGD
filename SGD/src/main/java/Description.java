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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Description other = (Description) obj;
        if (this.idJeu != other.idJeu) {
            return false;
        }
        if (!Objects.equals(this.synopsis, other.synopsis)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Description{" + "idJeu=" + idJeu + ", synopsis=" + synopsis + '}';
    }
    
    
    
    
}
