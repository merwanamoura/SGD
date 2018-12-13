/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ai265149
 */
public class Note {
    
    private int idJeu;
    private int val;
    private int nbLikes;
    private int nbDislikes;

      public Note(int idJeu, int val, int nbLikes, int nbDislikes) {
        this.idJeu = idJeu;
        this.val = val;
        this.nbLikes = nbLikes;
        this.nbDislikes = nbDislikes;
    }
      
    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public int getIdJeu() {
        return idJeu;
    }
    
    public int getVal() {
        return val;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public int getNbDislikes() {
        return nbDislikes;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public void setNbDislikes(int nbDislikes) {
        this.nbDislikes = nbDislikes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.val;
        hash = 83 * hash + this.nbLikes;
        hash = 83 * hash + this.nbDislikes;
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
        final Note other = (Note) obj;
        if (this.val != other.val) {
            return false;
        }
        if (this.nbLikes != other.nbLikes) {
            return false;
        }
        if (this.nbDislikes != other.nbDislikes) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Note{" + "val=" + val + ", nbLikes=" + nbLikes + ", nbDislikes=" + nbDislikes + '}';
    }
    
    

    
}
