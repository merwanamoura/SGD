/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ma522501
 */
public class Users {
    
    private int idU;
    private String nom;
    private String pseudo; 
    private String passWord;
    
    public Users(){
    nom="";
    pseudo="";
    passWord="";
    }
    

       public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getIdU() {
        return idU;
    }

    public Users(int idU, String nom, String pseudo, String passWord) {
        this.idU = idU;
        this.nom = nom;
        this.pseudo = pseudo;
        this.passWord = passWord;
    }
    
    public String getNom() {
        return nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Users{" + "nom=" + nom + ", pseudo=" + pseudo + ", passWord=" + passWord + '}';
    }
    
    
    
}
