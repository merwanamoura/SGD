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
    private String nom;
    private String pseudo; 
    private String passWord;
    
    public Users(){
    nom="";
    pseudo="";
    passWord="";
    }
    
    public Users(String n, String p, String pwd){
        nom=n;
        pseudo=p;
        passWord=pwd;
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
