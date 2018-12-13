
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
public class Avis {
    
    private int idUser;
    private int idJeu; 
    private String avis;
    private long dateCom;

    public Avis(int idUser, int idJeu, String avis, long dateCom) {
        this.idUser = idUser;
        this.idJeu = idJeu;
        this.avis = avis;
        this.dateCom = dateCom;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public String getAvis() {
        return avis;
    }

    public long getDateCom() {
        return dateCom;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public void setDateCom(long dateCom) {
        this.dateCom = dateCom;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idUser;
        hash = 97 * hash + this.idJeu;
        hash = 97 * hash + Objects.hashCode(this.avis);
        hash = 97 * hash + (int) (this.dateCom ^ (this.dateCom >>> 32));
        return hash;
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
        final Avis other = (Avis) obj;
        if (this.idUser != other.idUser) {
            return false;
        }
        if (this.idJeu != other.idJeu) {
            return false;
        }
        if (this.dateCom != other.dateCom) {
            return false;
        }
        if (!Objects.equals(this.avis, other.avis)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Avis{" + "idUser=" + idUser + ", idJeu=" + idJeu + ", avis=" + avis + ", dateCom=" + dateCom + '}';
    }
    
    
    
    
    
}
