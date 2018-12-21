
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
 * @author ma522501
 */
public class ProfilDlg extends javax.swing.JDialog {
    
    private JLabel mesJeux;
    private JLabel mesLikes;
    private JLabel mesDislikes;
    private JScrollPane mesJeuxScrollPane; 
    private JScrollPane mesLikesScrollPane; 
    private JScrollPane mesDislikesScrollPane; 
    private JPanel mesLikesPanel;
    private JPanel mesDislikesPanel ;
    MongoDatabase db;
    JFrame previousFrame;
    int idUser;
    boolean isAdmin;

    /**
     * Creates new form ProfilDlg
     */
    public void jeuClicked(MouseEvent evt, JFrame parent, int idUser) {
       
        JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) {
    
            String nomJeu = list.getSelectedValue().toString();
          
            
            MongoCursor<Document> it;
            MongoCollection<Document> jeux = db.getCollection("jeux");
                  
            it = jeux.find(eq("nom",nomJeu)).iterator();
            
            int idJ = (int) it.next().get("idJeu");
            
            presentationJeuDlg pj = new presentationJeuDlg(parent,true,idJ,idUser);
            pj.setVisible(true);
            
        } 
    }
    
    public ProfilDlg(java.awt.Frame parent, boolean modal,boolean admin, int idUser) {
        super(parent, modal);
        initComponents();
        setSize(600,600);
        
        MongoDBConnection.connect();
        db = MongoDBConnection.getDb();
        
        mesJeux= new JLabel ("Mes Jeux");
        mesLikes= new JLabel ("Mes Likes");
        mesDislikes= new JLabel ("Mes Dislikes");
        mesLikesPanel= new JPanel();
        mesDislikesPanel= new JPanel();
        this.idUser= idUser;
        this.isAdmin = admin;

        mesJeuxScrollPane = new JScrollPane();
        mesLikesScrollPane = new JScrollPane();
        mesDislikesScrollPane = new JScrollPane();
        
        previousFrame = (JFrame) parent;
        
     
        
        DefaultListModel dlm = new DefaultListModel();
        DefaultListModel dlmfav = new DefaultListModel();
        DefaultListModel dlmlikes = new DefaultListModel();        
        DefaultListModel dlmdislikes = new DefaultListModel();

        MongoCollection<Document> jeux = db.getCollection("jeux");
        MongoCollection<Document> jeuxFavoris =db.getCollection("user");
        
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

                          
                     try (MongoCursor<Document> cursorfav = jeux.find(new Document().append("nom", jf)).iterator()) {
                      
                      while (cursorfav.hasNext()){
                          
                          Document docfav = cursorfav.next();
                          
                          
                           File f = new File((String) docfav.get("image"));
    

                            if(f.exists() && !f.isDirectory())dlmfav.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("image"))));
                            else dlmfav.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon("imageJeux/default.png")));

                     //       if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("pathImage"))));
                       //     else dlmfav.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                      }
                      
                       JList list = new JList(dlmfav);
                        list.setCellRenderer(new ListEntryCellRenderer());

                        list.addMouseListener(new MouseAdapter() 
                        {
                            public void mouseClicked(MouseEvent evt) 
                            {
                                jeuClicked(evt,previousFrame,idUser);

                            }
                         });
                        
                        
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
                          
    
                          
                           File f = new File((String) doc.get("image"));
            

                            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("image"))));
                            else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));

                         //   if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("pathImage"))));
                           // else dlm.addElement(new ListEntry((String) doc.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                      }
                      
                       JList list = new JList(dlm);
                        list.setCellRenderer(new ListEntryCellRenderer());
                        
                        list.addMouseListener(new MouseAdapter() 
                        {
                            public void mouseClicked(MouseEvent evt) 
                            {
                                jeuClicked(evt,previousFrame,idUser);

                            }
                         });
                        
                        
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


                                                try (MongoCursor<Document> cursorfav = jeux.find(new Document().append("nom", jf)).iterator()) {

                                                                    while (cursorfav.hasNext()){

                                                                        Document docfav = cursorfav.next();


                                                                         File f = new File((String) docfav.get("image"));


                                                                          if(f.exists() && !f.isDirectory())dlmlikes.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("image"))));
                                                                          else dlmlikes.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon("imageJeux/default.png")));

                             //                                             if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("pathImage"))));
                               //                                           else dlmlikes.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                                                                    }

                                                                     JList list = new JList(dlmlikes);
                                                                      list.setCellRenderer(new ListEntryCellRenderer());
                                                                      
                                                                      list.addMouseListener(new MouseAdapter() 
                                                                    {
                                                                        public void mouseClicked(MouseEvent evt) 
                                                                        {
                                                                            jeuClicked(evt,previousFrame,idUser);
                                                                        }
                                                                     });

                                                                      
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
                                                

                                                try (MongoCursor<Document> cursorfav = jeux.find(new Document().append("nom", jf)).iterator()) {

                                                                    while (cursorfav.hasNext()){

                                                                        Document docfav = cursorfav.next();


                                                                         File f = new File((String) docfav.get("image"));


                                                                          if(f.exists() && !f.isDirectory())dlmdislikes.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("image"))));
                                                                          else dlmdislikes.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon("imageJeux/default.png")));

                                                                       //   if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) docfav.get("nom"), new ImageIcon((String) docfav.get("pathImage"))));
                                                                       //   else dlmdislikes.addElement(new ListEntry((String) docfav.get("nomJeu"), new ImageIcon("imageJeux/default.png")));

                                                                    }

                                                                     JList list = new JList(dlmdislikes);
                                                                      list.setCellRenderer(new ListEntryCellRenderer());
                                                                      
                                                                      list.addMouseListener(new MouseAdapter() 
                                                                        {
                                                                            public void mouseClicked(MouseEvent evt) 
                                                                            {
                                                                                jeuClicked(evt,previousFrame,idUser);
                                                                            }
                                                                         });
                                                                     

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
