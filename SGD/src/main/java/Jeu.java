
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

    @Override
    public String toString() {
        return "Jeu{" + "nom=" + nom + ", nomEditeur=" + nomEditeur + ", dateSortie=" + dateSortie + ", Categorie=" + Categorie + '}';
    }


  
    
}
