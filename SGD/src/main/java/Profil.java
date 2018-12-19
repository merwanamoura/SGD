
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
public class Profil extends javax.swing.JFrame {

private JLabel mesJeux;
private JLabel mesLikes;
private JLabel mesDislikes;
private JScrollPane mesJeuxScrollPane; 
private JScrollPane mesLikesScrollPane; 
private JScrollPane mesDislikesScrollPane; 
private JPanel mesLikesPanel;
private JPanel mesDislikesPanel;

    /**
     * Creates new form Profil
     */
    public Profil(boolean admin, int idUser) {
        
         mesJeux= new JLabel ("Mes Jeux");
         mesLikes= new JLabel ("Mes Likes");
         mesDislikes= new JLabel ("Mes Dislikes");
        mesLikesPanel= new JPanel();
        mesDislikesPanel= new JPanel();
         
         
         mesJeuxScrollPane = new JScrollPane();
         mesLikesScrollPane = new JScrollPane();
         mesDislikesScrollPane = new JScrollPane();
        
        initComponents();
        
         MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        DefaultListModel dlm = new DefaultListModel();
        DefaultListModel dlmfav = new DefaultListModel();
        DefaultListModel dlmlikes = new DefaultListModel();        
        DefaultListModel dlmdislikes = new DefaultListModel();

        MongoCollection<Document> jeux = db.getCollection("jeux");
        MongoCollection<Document> jeuxFavoris =db.getCollection("Users");
        
        // Mes Favoris 
          try (MongoCursor<Document> cursor = jeuxFavoris.find(new Document().append("idA", idUser)).iterator()) {
                      
                      while (cursor.hasNext()){
                          
                          Document doc = cursor.next();
                          
                          pseudoLabel.setText(""+doc.get("pseudo"));
                          // TODO modifier
                          Document nomPrenom  = (Document)doc.get("name");
                          nameLabel.setText(""+nomPrenom.get("first")+" "+nomPrenom.get("last"));

                          ArrayList<String> listeJeux = (ArrayList<String>)doc.get("jeuxFavoris");
                          
                          for (String jf : listeJeux ){
                       //   System.out.println("Jeux favoris: "+ jf);
                          
                     try (MongoCursor<Document> cursorfav = jeux.find(new Document().append("nom", jf)).iterator()) {
                      
                      while (cursorfav.hasNext()){
                          
                          Document docfav = cursorfav.next();
                          
                          
                           File f = new File((String) docfav.get("image"));
    

                            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon((String) docfav.get("pathImage"))));
                            else dlmfav.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon("imageJeux/default.png")));

                            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("pathImage"))));
                            else dlmfav.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                      }
                      
                       JList list = new JList(dlmfav);
                        list.setCellRenderer(new ListEntryCellRenderer());

                        jScrollPane1.add(list); 
                        jScrollPane1.setViewportView(list);
                      
                  }
                          
                          
                   
                          }
                        
                      }
                   
                  }
                     
         
        
        // Affichage panneau mes jeux  
        if (admin) {
            
            
            mesJeux.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            mesJeux.setText("Mes Jeux");
            jPanel4.add(mesJeux, java.awt.BorderLayout.PAGE_START);
            jPanel4.add(mesJeuxScrollPane, java.awt.BorderLayout.CENTER);
            
                     // Mes Jeux
                     
                     try (MongoCursor<Document> cursor = jeux.find(new Document().append("idA", idUser)).iterator()) {
                      
                      while (cursor.hasNext()){
                          
                          Document doc = cursor.next();
                          
                      //    System.out.println("Jeux : "+ (String)doc.get("nom"));
                          
                           File f = new File((String) doc.get("image"));
            

                            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nomJeu"), new ImageIcon((String) doc.get("pathImage"))));
                            else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));

                            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("pathImage"))));
                            else dlm.addElement(new ListEntry((String) doc.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                      }
                      
                       JList list = new JList(dlm);
                        list.setCellRenderer(new ListEntryCellRenderer());

                        mesJeuxScrollPane.add(list); 
                        mesJeuxScrollPane.setViewportView(list);
                      
                  }
                    
                     

        }
        else {
            
             jPanel4.setLayout(new java.awt.GridLayout(2, 1));
             
             mesLikes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
             mesDislikes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
             
             mesLikesPanel.setLayout(new java.awt.BorderLayout());
             mesLikesPanel.add(mesLikes, java.awt.BorderLayout.PAGE_START);
             mesLikesPanel.add(mesLikesScrollPane, java.awt.BorderLayout.CENTER);
             
             mesDislikesPanel.setLayout(new java.awt.BorderLayout());
             mesDislikesPanel.add(mesDislikes, java.awt.BorderLayout.PAGE_START);
             mesDislikesPanel.add(mesDislikesScrollPane, java.awt.BorderLayout.CENTER);
             
            jPanel4.add(mesLikesPanel);
            jPanel4.add(mesDislikesPanel);
            
            
                    // Mes Likes
          try (MongoCursor<Document> cursor = jeuxFavoris.find(new Document().append("idA", idUser)).iterator()) {
                      
                                        while (cursor.hasNext()){

                                            Document doc = cursor.next();

                                            ArrayList<String> listeJeux = (ArrayList<String>)doc.get("jeuLike");

                                            for (String jf : listeJeux ){
                                            //System.out.println("Jeux like: "+ jf);

                                                try (MongoCursor<Document> cursorfav = jeux.find(new Document().append("nom", jf)).iterator()) {

                                                                    while (cursorfav.hasNext()){

                                                                        Document docfav = cursorfav.next();


                                                                         File f = new File((String) docfav.get("image"));


                                                                          if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon((String) docfav.get("pathImage"))));
                                                                          else dlmlikes.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon("imageJeux/default.png")));

                                                                          if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("pathImage"))));
                                                                          else dlmlikes.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                                                                    }

                                                                     JList list = new JList(dlmlikes);
                                                                      list.setCellRenderer(new ListEntryCellRenderer());

                                                                      mesLikesScrollPane.add(list); 
                                                                      mesLikesScrollPane.setViewportView(list);

                                                }



                                            }

                                        }
                   
                  }
          
        // Mes Dislikes
          try (MongoCursor<Document> cursor = jeuxFavoris.find(new Document().append("idA", idUser)).iterator()) {
                      
                                        while (cursor.hasNext()){

                                            Document doc = cursor.next();

                                            ArrayList<String> listeJeux = (ArrayList<String>)doc.get("jeuDislike");

                                            for (String jf : listeJeux ){
                                            System.out.println("Jeux dislike: "+ jf);

                                                try (MongoCursor<Document> cursorfav = jeux.find(new Document().append("nom", jf)).iterator()) {

                                                                    while (cursorfav.hasNext()){

                                                                        Document docfav = cursorfav.next();


                                                                         File f = new File((String) docfav.get("image"));


                                                                          if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon((String) docfav.get("pathImage"))));
                                                                          else dlmdislikes.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon("imageJeux/default.png")));

                                                                          if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("pathImage"))));
                                                                          else dlmdislikes.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                                                                    }

                                                                     JList list = new JList(dlmdislikes);
                                                                      list.setCellRenderer(new ListEntryCellRenderer());

                                                                      mesDislikesScrollPane.add(list); 
                                                                      mesDislikesScrollPane.setViewportView(list);

                                                }



                                            }

                                        }
                   
                  }
            
            
            
             
        }
        
        
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pseudoLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));
        jPanel1.setLayout(new java.awt.GridLayout(1, 4));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pseudo :");
        jPanel1.add(jLabel1);
        jPanel1.add(pseudoLabel);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nom & Prénom :");
        jPanel1.add(jLabel3);
        jPanel1.add(nameLabel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Jeux Favoris");
        jPanel3.add(jLabel2, java.awt.BorderLayout.PAGE_START);
        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPanel4);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Profil(false,22).setVisible(true);
            
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel pseudoLabel;
    // End of variables declaration//GEN-END:variables
}
