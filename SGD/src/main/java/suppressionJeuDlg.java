
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import static javax.swing.JOptionPane.showMessageDialog;
import org.bson.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author at560075
 */
public class suppressionJeuDlg extends javax.swing.JDialog {

    /**
     * Creates new form suppressionJeuDlg
     */
    MongoDatabase db;
    JList list;
    
    public suppressionJeuDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        db = MongoDBConnection.getDb();
        fillListGames();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        
    }
    
    public void fillListGames()
    {
        DefaultListModel dlm = new DefaultListModel();
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("jeux");
        
        String saisie = "";
        saisie = nomJeuText.getText();
        it = jeux.find(regex("nom",saisie)).iterator();
        
        
        while (it.hasNext()) 
        {
            Document doc = it.next();
            File f = new File((String) doc.get("image"));
            
            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("image"))));
            else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));
            

        } 
        list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());
     
        listeJeux.add(list); 
        listeJeux.setViewportView(list);
        
        
    }
    
    public void deleteGame()
    {
        if(list.getSelectedValue() != null)
        {
            //on supprime le jeu 
            
            MongoCollection<Document> jeux = db.getCollection("jeux");
            String nameGame = list.getSelectedValue().toString(); 
            System.out.println("--->" + nameGame);
 
            MongoCursor<Document> cursor;
            cursor = (MongoCursor<Document>) jeux.find(eq("nom",nameGame)).iterator();
            Document document = cursor.next();
            Jeu jeu = new Jeu((int) document.get("idJeu"));
            
            jeux.deleteOne(eq("nom",nameGame));
            fillListGames();
            
            //on supprime le jeu des favoris, like et dislike de chaque utilisateur
            
            MongoCursor<Document> it;
            MongoCollection<Document> user = db.getCollection("user");
            it = (MongoCursor<Document>) user.find().iterator();
            
            while(it.hasNext()){
                Document doc = it.next();
                int idJeu;
            if( doc.get("idA") instanceof Double )
            {
                idJeu = ((Double)doc.get("idA")).intValue();
            }
            else 
            {
                idJeu = (int) doc.get("idA");
            }
            
                Users us = new Users(idJeu );
                
                if(us.isLike(nameGame)) us.removeJeuLike(nameGame);
                if(us.isDislike(nameGame)) us.removeJeuDislike(nameGame);
                if(us.isFavori(nameGame)) us.removeFavori(nameGame);
                
            }
            
            //on supprime le jeu si il appartient a une série de jeu
            
            MongoCursor<Document> it3;
            MongoCollection<Document> sj = db.getCollection("SerieJeux");
            it3 = (MongoCursor<Document>) sj.find().iterator();
            
            while(it3.hasNext()){
                Document doc = it3.next();
                SerieJeux serie = new SerieJeux((String) doc.get("nomSerie"));
                
                if(serie.isInSerie(jeu.getIdJeu())) serie.supprimerJeu(jeu.getIdJeu());
                
                
            }
            
            //on supprime tous les avis du jeu
            
            jeu.removeAllAvis();
            
            //on supprime la note
            
            jeu.removeNote();
            
            //on supprime la note
            
            jeu.removeDescription();
        }
        else
        {
            showMessageDialog(null, "Veuillez bien sélectionner un jeu à supprimer !!!");
        }
  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        general = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botPanel = new javax.swing.JPanel();
        leftSpacePanel = new javax.swing.JPanel();
        buttonPannel = new javax.swing.JPanel();
        supprimerButton = new javax.swing.JButton();
        rightSpacePanel = new javax.swing.JPanel();
        midPanel = new javax.swing.JPanel();
        listeJeuPanel = new javax.swing.JPanel();
        listeJeux = new javax.swing.JScrollPane();
        recherchePanel = new javax.swing.JPanel();
        leftRecherchePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rightRecherchePanel = new javax.swing.JPanel();
        nomJeuText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        general.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sélectionnez votre jeu à supprimer");

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        general.add(topPanel, java.awt.BorderLayout.PAGE_START);

        botPanel.setPreferredSize(new java.awt.Dimension(553, 60));
        botPanel.setLayout(new java.awt.GridLayout(1, 3));

        javax.swing.GroupLayout leftSpacePanelLayout = new javax.swing.GroupLayout(leftSpacePanel);
        leftSpacePanel.setLayout(leftSpacePanelLayout);
        leftSpacePanelLayout.setHorizontalGroup(
            leftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );
        leftSpacePanelLayout.setVerticalGroup(
            leftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        botPanel.add(leftSpacePanel);

        supprimerButton.setText("Supprimer");
        supprimerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPannelLayout = new javax.swing.GroupLayout(buttonPannel);
        buttonPannel.setLayout(buttonPannelLayout);
        buttonPannelLayout.setHorizontalGroup(
            buttonPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(supprimerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );
        buttonPannelLayout.setVerticalGroup(
            buttonPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(supprimerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        botPanel.add(buttonPannel);

        javax.swing.GroupLayout rightSpacePanelLayout = new javax.swing.GroupLayout(rightSpacePanel);
        rightSpacePanel.setLayout(rightSpacePanelLayout);
        rightSpacePanelLayout.setHorizontalGroup(
            rightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );
        rightSpacePanelLayout.setVerticalGroup(
            rightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        botPanel.add(rightSpacePanel);

        general.add(botPanel, java.awt.BorderLayout.PAGE_END);

        midPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout listeJeuPanelLayout = new javax.swing.GroupLayout(listeJeuPanel);
        listeJeuPanel.setLayout(listeJeuPanelLayout);
        listeJeuPanelLayout.setHorizontalGroup(
            listeJeuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listeJeuPanelLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(listeJeux, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        listeJeuPanelLayout.setVerticalGroup(
            listeJeuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listeJeux, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
        );

        midPanel.add(listeJeuPanel, java.awt.BorderLayout.CENTER);

        recherchePanel.setPreferredSize(new java.awt.Dimension(553, 60));
        recherchePanel.setLayout(new java.awt.BorderLayout());

        leftRecherchePanel.setPreferredSize(new java.awt.Dimension(160, 60));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Entrez le nom du jeu :");
        jLabel2.setMinimumSize(new java.awt.Dimension(170, 15));

        javax.swing.GroupLayout leftRecherchePanelLayout = new javax.swing.GroupLayout(leftRecherchePanel);
        leftRecherchePanel.setLayout(leftRecherchePanelLayout);
        leftRecherchePanelLayout.setHorizontalGroup(
            leftRecherchePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );
        leftRecherchePanelLayout.setVerticalGroup(
            leftRecherchePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        recherchePanel.add(leftRecherchePanel, java.awt.BorderLayout.WEST);

        nomJeuText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomJeuTextActionPerformed(evt);
            }
        });
        nomJeuText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nomJeuTextKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout rightRecherchePanelLayout = new javax.swing.GroupLayout(rightRecherchePanel);
        rightRecherchePanel.setLayout(rightRecherchePanelLayout);
        rightRecherchePanelLayout.setHorizontalGroup(
            rightRecherchePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightRecherchePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nomJeuText, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        rightRecherchePanelLayout.setVerticalGroup(
            rightRecherchePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightRecherchePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nomJeuText)
                .addContainerGap())
        );

        recherchePanel.add(rightRecherchePanel, java.awt.BorderLayout.CENTER);

        midPanel.add(recherchePanel, java.awt.BorderLayout.NORTH);

        general.add(midPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(general, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(general, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nomJeuTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomJeuTextActionPerformed

       
    
        // TODO add your handling code here:
    }//GEN-LAST:event_nomJeuTextActionPerformed

    private void supprimerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimerButtonActionPerformed
   
     deleteGame();   
        
  /*  this.setVisible(false);
    dispose();
   */ 
// TODO add your handling code here:
    }//GEN-LAST:event_supprimerButtonActionPerformed

    private void nomJeuTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomJeuTextKeyPressed

    fillListGames();

        // TODO add your handling code here:
    }//GEN-LAST:event_nomJeuTextKeyPressed

    
    
    
    
    
    /**
     * @param args the command line arguments
     */
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel botPanel;
    private javax.swing.JPanel buttonPannel;
    private javax.swing.JPanel general;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel leftRecherchePanel;
    private javax.swing.JPanel leftSpacePanel;
    private javax.swing.JPanel listeJeuPanel;
    private javax.swing.JScrollPane listeJeux;
    private javax.swing.JPanel midPanel;
    private javax.swing.JTextField nomJeuText;
    private javax.swing.JPanel recherchePanel;
    private javax.swing.JPanel rightRecherchePanel;
    private javax.swing.JPanel rightSpacePanel;
    private javax.swing.JButton supprimerButton;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
