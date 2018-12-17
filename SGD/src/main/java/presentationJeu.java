
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import org.bson.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hc047736
 */
public class presentationJeu extends javax.swing.JFrame {

    Jeu jeu;
    Users us;
    static int idJeu;
    static int idUser;
    /**
     * Creates new form presentationJeu
     */
    public presentationJeu(int idJ, int idU) {
        initComponents();
        this.setSize(800,600);
        
        this.idUser = 0;
        this.idJeu = 2;
        
        jeu = new Jeu(idJeu);
        us=new Users(idUser);
        
        setPresentation();
        description();
        afficheImg();
        setAvis();
        jeuSimilaire();
        setLike();
        setDislike();
        setFavori();
    }
    
    public void setLike(){
        List<String> listeJeuLike = us.getListeLike();

        for(int i = 0; i< listeJeuLike.size() ; i++){
            
            if(listeJeuLike.get(i).equals(jeu.getNom())) {
                buttonLike.setEnabled(false);
            }  
        }
    }
    
    public void setFavori(){
        List<String> listeFavori = us.getListeJeuFavori();

        for(int i = 0; i< listeFavori.size() ; i++){
            
            if(listeFavori.get(i).equals(jeu.getNom())) {
                buttonFavori.setBackground(Color.yellow);
            }  
        }
    }
    
    public void setDislike(){
        List<String> listeJeuDislike = us.getListeDislike();

        for(int i = 0; i< listeJeuDislike.size() ; i++){
            
            if(listeJeuDislike.get(i).equals(jeu.getNom())) {
                buttonDislike.setEnabled(false);
            } 
        }
    }
    
    public void setComment(){
        
    }
    
    public void setPresentation(){
        labelNomJeu.setText(labelNomJeu.getText() + " " + jeu.getNom());
    }
    
    public void description()
    {
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
        MongoCursor<Document> it;
        MongoCollection<Document> desc = db.getCollection("description");

        it = desc.find(eq("idJeu" , this.idJeu)).iterator();
        
        Document doc = it.next();
        
        JTextArea jt = new JTextArea((String) doc.get("synopsis"));
        jt.setLineWrap(true);
        jt.setEnabled(false);
        panelDesc.add(jt,BorderLayout.CENTER);

    }
    
    public void afficheImg()
    {
        String img = jeu.getImage();
        System.out.println(img);
        
        JLabel label = new JLabel(new ImageIcon("imageJeux/default.png"));
        
        panelImage.add(label, BorderLayout.CENTER);
        
        
    }
    public void jeuSimilaire()
    {
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        DefaultListModel dlm = new DefaultListModel();
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("jeux");

        it = jeux.find(eq("categorie" , jeu.getCategorie())).iterator();
        
        
        while (it.hasNext()) 
        {
            Document doc = it.next();
            File f = new File((String) doc.get("image"));
            
            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("image"))));
            else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));
            

        } 
        JList list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());
     
        jScrollPane1.add(list); 
        jScrollPane1.setViewportView(list);


    }
    
    
    public void setAvis()
    {
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        MongoCursor<Document> it;
        MongoCollection<Document> avis = db.getCollection("Avis");

        it = avis.find(eq("idJeu" , this.idJeu)).iterator();  
        
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel);

        while (it.hasNext()) 
        {
            
            Document doc = it.next();
            JPanel jp = new JPanel(new BorderLayout());
        
            JTextArea jt = new JTextArea((String) doc.get("avis"));
            jt.setLineWrap(true);
            jt.setEnabled(false);
            jp.add(jt,BorderLayout.CENTER);
            
            
            JLabel jl = new JLabel((String)(" " + doc.get("idUser")));
            jp.add(jl,BorderLayout.WEST);
            panel.add( jp);
 
        } 
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        panelAvis.add(scrollPane);
        
    }   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelNomJeu = new javax.swing.JLabel();
        corps = new javax.swing.JPanel();
        panelJeu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panelImage = new javax.swing.JPanel();
        panelLikeDislike = new javax.swing.JPanel();
        buttonLike = new javax.swing.JButton();
        buttonDislike = new javax.swing.JButton();
        buttonFavori = new javax.swing.JButton();
        panelDesc = new javax.swing.JPanel();
        labelDescription = new javax.swing.JLabel();
        panelAvis = new javax.swing.JPanel();
        textFieldComment = new javax.swing.JTextField();
        panelJeuSimilaire = new javax.swing.JPanel();
        labelJeuSimilaire = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelNomJeu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNomJeu.setText("Présentation du jeu");
        labelNomJeu.setPreferredSize(new java.awt.Dimension(141, 35));
        getContentPane().add(labelNomJeu, java.awt.BorderLayout.NORTH);

        corps.setLayout(new java.awt.BorderLayout());

        panelJeu.setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel4.setLayout(new java.awt.BorderLayout());

        panelImage.setLayout(new java.awt.BorderLayout());
        jPanel4.add(panelImage, java.awt.BorderLayout.CENTER);

        panelLikeDislike.setPreferredSize(new java.awt.Dimension(272, 40));
        panelLikeDislike.setLayout(new java.awt.BorderLayout());

        buttonLike.setText("Like");
        buttonLike.setPreferredSize(new java.awt.Dimension(94, 20));
        buttonLike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLikeActionPerformed(evt);
            }
        });
        panelLikeDislike.add(buttonLike, java.awt.BorderLayout.WEST);

        buttonDislike.setText("Dislike");
        buttonDislike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDislikeActionPerformed(evt);
            }
        });
        panelLikeDislike.add(buttonDislike, java.awt.BorderLayout.EAST);

        buttonFavori.setText("favori");
        buttonFavori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFavoriActionPerformed(evt);
            }
        });
        panelLikeDislike.add(buttonFavori, java.awt.BorderLayout.CENTER);

        jPanel4.add(panelLikeDislike, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel4);

        panelDesc.setLayout(new java.awt.BorderLayout());

        labelDescription.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDescription.setText("Description du jeu :");
        labelDescription.setPreferredSize(new java.awt.Dimension(138, 35));
        panelDesc.add(labelDescription, java.awt.BorderLayout.NORTH);

        jPanel1.add(panelDesc);

        panelJeu.add(jPanel1);

        panelAvis.setLayout(new java.awt.BorderLayout());

        textFieldComment.setText("Laissez un commentaire ...");
        textFieldComment.setPreferredSize(new java.awt.Dimension(170, 25));
        panelAvis.add(textFieldComment, java.awt.BorderLayout.NORTH);

        panelJeu.add(panelAvis);

        corps.add(panelJeu, java.awt.BorderLayout.CENTER);

        panelJeuSimilaire.setLayout(new java.awt.BorderLayout());

        labelJeuSimilaire.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelJeuSimilaire.setText("Jeu Similaire : ");
        labelJeuSimilaire.setPreferredSize(new java.awt.Dimension(150, 35));
        panelJeuSimilaire.add(labelJeuSimilaire, java.awt.BorderLayout.NORTH);
        panelJeuSimilaire.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        corps.add(panelJeuSimilaire, java.awt.BorderLayout.EAST);

        getContentPane().add(corps, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLikeActionPerformed
        // TODO add your handling code here:
        buttonLike.setEnabled(false);
        buttonDislike.setEnabled(true);
        
        String nomJeu = this.jeu.getNom();
        this.us.addJeuLike(nomJeu);
        this.us.removeJeuDislike(nomJeu);
    }//GEN-LAST:event_buttonLikeActionPerformed

    private void buttonDislikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDislikeActionPerformed
        // TODO add your handling code here:
        buttonDislike.setEnabled(false);
        buttonLike.setEnabled(true);
        
        String nomJeu = this.jeu.getNom();
        this.us.addJeuDislike(nomJeu);
        this.us.removeJeuLike(nomJeu);
    }//GEN-LAST:event_buttonDislikeActionPerformed

    private void buttonFavoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFavoriActionPerformed
        // TODO add your handling code here:
        if(us.isFavori(jeu.getNom())){
            buttonFavori.setBackground(Color.white);
            us.removeFavori(jeu.getNom());
        }else{
            buttonFavori.setBackground(Color.yellow);
            us.addFavori(jeu.getNom());
        }
    }//GEN-LAST:event_buttonFavoriActionPerformed

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
            java.util.logging.Logger.getLogger(presentationJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(presentationJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(presentationJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(presentationJeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new presentationJeu(idJeu,idUser).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDislike;
    private javax.swing.JButton buttonFavori;
    private javax.swing.JButton buttonLike;
    private javax.swing.JPanel corps;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelJeuSimilaire;
    private javax.swing.JLabel labelNomJeu;
    private javax.swing.JPanel panelAvis;
    private javax.swing.JPanel panelDesc;
    private javax.swing.JPanel panelImage;
    private javax.swing.JPanel panelJeu;
    private javax.swing.JPanel panelJeuSimilaire;
    private javax.swing.JPanel panelLikeDislike;
    private javax.swing.JTextField textFieldComment;
    // End of variables declaration//GEN-END:variables
}
