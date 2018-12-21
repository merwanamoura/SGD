
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
public class serieJeuxDlg extends javax.swing.JDialog {

    MongoDatabase db;
    int idUser;
    boolean isAdmin;
    JFrame previousFrame;
    String nomSerie;
    
    /**
     * Creates new form serieJeuxDlg
     */
    public serieJeuxDlg(java.awt.Frame parent, boolean modal,int idUser,boolean isAdmin) {
       
        super(parent, modal);      
        initComponents();

        db = MongoDBConnection.getDb();
        idUser = idUser;
        isAdmin = isAdmin;
        this.setSize(800,600);
        fillLesSeries();
        previousFrame = (JFrame) parent;
        this.isAdmin = isAdmin;
        affichageButton();
    }
    
    public void fillLesSeries()
    {
        MongoCollection<Document> SJ = db.getCollection("seriesJeux");
         
        DefaultListModel dlm = new DefaultListModel();
        MongoCursor<Document> it;
    
        

        it = SJ.find().iterator();
        
        
        while (it.hasNext()) 
        {
            Document doc = it.next();
            System.out.println(it.toString());
            File f = new File((String) doc.get("image"));
            
            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("image"))));
            else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));
            

        } 
        
        JList list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());
     
        list.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent evt) 
            {
                jeuClicked(evt);
            }
         });
        
        scrollLesSeries.add(list); 
        scrollLesSeries.setViewportView(list);
        
    }
    
    public void fillListeJeux(String nomSerie)
    {
        
       // if(jeuxSeriePanel.getComponentCount()>0)jeuxSeriePanel.remove(0);
        for(int i=0;i<jeuxSeriePanel.getComponentCount();i++)
        {
            if(jeuxSeriePanel.getComponent(i).getClass().toString().equals("class javax.swing.JList") )
            {
                jeuxSeriePanel.remove(i);
            }
            
        }
        MongoCollection<Document> SJ = db.getCollection("seriesJeux");       
        DefaultListModel dlm = new DefaultListModel();
        MongoCursor<Document> it;
    
        

        it = SJ.find(eq("nom",nomSerie)).iterator();
        Document doc = it.next();
       
        ArrayList<Double> listeJeux = (ArrayList<Double>)doc.get("tabJeux");
         MongoCollection<Document> jeux = db.getCollection("jeux");
         
         
        for (Object idJeu : listeJeux )
        {
            int id;
            if( idJeu instanceof Double )
            {
                id = ((Double)idJeu).intValue();
            }
            else 
            {
                id = (int) idJeu;
            }
            it = jeux.find(eq("idJeu" , (int)id)).iterator();

            doc = it.next();
     
            File f = new File((String) doc.get("image"));

            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("image"))));
            else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));

        }
        
        JList list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());       
        
        jeuxSeriePanel.add(list);

        MouseListener mouseListener = new MouseAdapter() {
            
              public void mouseClicked(MouseEvent e) {
                  
                if (e.getClickCount() == 2)
                {
                        MongoDatabase db = MongoDBConnection.getDb();

                        MongoCursor<Document> it;
                        MongoCollection<Document> jeux = db.getCollection("jeux");
                        String nameGame = list.getSelectedValue().toString(); 
                        System.out.println(nameGame);
                        it = jeux.find(eq("nom" , nameGame)).iterator();

                        Document d = it.next();
                        int idNewjeu = (int) d.get("idJeu");

                       

                        presentationJeuDlg pj = new presentationJeuDlg(previousFrame,true,idNewjeu,idUser);
                        pj.setVisible(true);
                }
              }
                
                
        };
        
        list.addMouseListener(mouseListener);
        jeuxSeriePanel.setViewportView(list);
        
    }
    
    public void affichageButton()
    {
        if(isAdmin == false)
        {
            ajouterButton.setVisible(false);
            supprimerButton.setVisible(false);
        }
    }
    
    public void refresh(String nomSerieJeu)
    {      


        
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("seriesJeux");

        it = jeux.find(eq("nom",nomSerieJeu)).iterator();
        Document doc = it.next();
    //    int idJ = (int) doc.get("idJeu");

       String description = (String) doc.get("description");

        descriptionText.setText(description);

      String img = (String) doc.get("image");      
      JLabel label2 = new JLabel(new ImageIcon("imageJeux/default.png"));
      panelImage.add(label2);
      panelImage.repaint();panelImage.revalidate();

      fillListeJeux(nomSerieJeu);

      labelNomJeu.setText(nomSerieJeu);
      nomSerie = nomSerieJeu;
      System.out.println(nomSerie);
    }
    
    public void jeuClicked(MouseEvent evt) {
       
        JList list = (JList)evt.getSource();
        String nomSerieJeu = list.getSelectedValue().toString();
        if (evt.getClickCount() == 2) {
    
            refresh(nomSerieJeu);
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

        topPanel = new javax.swing.JPanel();
        labelNomJeu = new javax.swing.JLabel();
        ajouterButton = new javax.swing.JButton();
        supprimerButton = new javax.swing.JButton();
        corps = new javax.swing.JPanel();
        panelJeu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panelSerieJeux = new javax.swing.JPanel();
        lesSeriesLabel = new javax.swing.JLabel();
        scrollLesSeries = new javax.swing.JScrollPane();
        panelImage = new javax.swing.JPanel();
        panelDescription = new javax.swing.JPanel();
        labelDescription = new javax.swing.JLabel();
        descriptionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextArea();
        panelJeuSimilaire = new javax.swing.JPanel();
        labelJeuSimilaire = new javax.swing.JLabel();
        jeuxSeriePanel = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        topPanel.setLayout(new java.awt.BorderLayout());

        labelNomJeu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNomJeu.setText("PRÉSENTATION DE LA SÉRIE");
        labelNomJeu.setPreferredSize(new java.awt.Dimension(520, 35));
        topPanel.add(labelNomJeu, java.awt.BorderLayout.WEST);

        ajouterButton.setText("AJOUTER");
        ajouterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterButtonActionPerformed(evt);
            }
        });
        topPanel.add(ajouterButton, java.awt.BorderLayout.CENTER);

        supprimerButton.setText("SUPPRIMER");
        supprimerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerButtonActionPerformed(evt);
            }
        });
        topPanel.add(supprimerButton, java.awt.BorderLayout.LINE_END);

        getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);

        corps.setLayout(new java.awt.BorderLayout());

        panelJeu.setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel4.setLayout(new java.awt.BorderLayout());

        panelSerieJeux.setLayout(new java.awt.BorderLayout());

        lesSeriesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lesSeriesLabel.setText("LES SÉRIES DISPONIBLES");
        panelSerieJeux.add(lesSeriesLabel, java.awt.BorderLayout.NORTH);
        panelSerieJeux.add(scrollLesSeries, java.awt.BorderLayout.CENTER);

        jPanel4.add(panelSerieJeux, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4);

        panelImage.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panelImage);

        panelJeu.add(jPanel1);

        panelDescription.setLayout(new java.awt.BorderLayout());

        labelDescription.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDescription.setText("Description de la série");
        labelDescription.setPreferredSize(new java.awt.Dimension(138, 35));
        panelDescription.add(labelDescription, java.awt.BorderLayout.NORTH);

        descriptionText.setColumns(20);
        descriptionText.setRows(5);
        descriptionText.setEnabled(false);
        jScrollPane1.setViewportView(descriptionText);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
        );

        panelDescription.add(descriptionPanel, java.awt.BorderLayout.CENTER);

        panelJeu.add(panelDescription);

        corps.add(panelJeu, java.awt.BorderLayout.CENTER);

        panelJeuSimilaire.setLayout(new java.awt.BorderLayout());

        labelJeuSimilaire.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelJeuSimilaire.setText("Jeux de la série");
        labelJeuSimilaire.setPreferredSize(new java.awt.Dimension(150, 35));
        panelJeuSimilaire.add(labelJeuSimilaire, java.awt.BorderLayout.NORTH);
        panelJeuSimilaire.add(jeuxSeriePanel, java.awt.BorderLayout.CENTER);

        corps.add(panelJeuSimilaire, java.awt.BorderLayout.EAST);

        getContentPane().add(corps, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ajouterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajouterButtonActionPerformed

      
        AjouterJeuSerieDlg adj = new AjouterJeuSerieDlg(previousFrame,true,nomSerie);
        adj.setVisible(true);
        refresh(nomSerie);
        // TODO add your handling code here:
    }//GEN-LAST:event_ajouterButtonActionPerformed

    private void supprimerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimerButtonActionPerformed
        suppresionSerieJeuDlg adj = new suppresionSerieJeuDlg(previousFrame,true,nomSerie);
        adj.setVisible(true);
        refresh(nomSerie);
    }//GEN-LAST:event_supprimerButtonActionPerformed

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
            java.util.logging.Logger.getLogger(serieJeuxDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(serieJeuxDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(serieJeuxDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(serieJeuxDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                serieJeuxDlg dialog = new serieJeuxDlg(new javax.swing.JFrame(), true,0,true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ajouterButton;
    private javax.swing.JPanel corps;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JTextArea descriptionText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jeuxSeriePanel;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelJeuSimilaire;
    private javax.swing.JLabel labelNomJeu;
    private javax.swing.JLabel lesSeriesLabel;
    private javax.swing.JPanel panelDescription;
    private javax.swing.JPanel panelImage;
    private javax.swing.JPanel panelJeu;
    private javax.swing.JPanel panelJeuSimilaire;
    private javax.swing.JPanel panelSerieJeux;
    private javax.swing.JScrollPane scrollLesSeries;
    private javax.swing.JButton supprimerButton;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
