
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Accumulators.sum;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.eq;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import static java.util.Arrays.asList;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
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
public class pageAcceuil extends javax.swing.JFrame {
    

    boolean isAdmin;
    /**
     * Creates new form pageAcceuil
     */
    public pageAcceuil()
    {
        initComponents();
        isAdmin = true;
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        fillJlistRecent(db);
        fillJlistPopular(db);
        fillJlistComments(db);
        ajoutSuppressionButton();

    }
   
    /* MÉTHODE POUR REMPLIR LA JLIST DES JEUX RECENTS*/
    public void fillJlistRecent(MongoDatabase db)
    {
        DefaultListModel dlm = new DefaultListModel();
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("jeux");

        it = jeux.find().sort(new BasicDBObject("dateSortie",-1)).limit(10).iterator();
        
        
        while (it.hasNext()) 
        {
            Document doc = it.next();


        } 
        
        JList list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());
     
        jScrollPane1.add(list); 
        jScrollPane1.setViewportView(list);


    }

    public void fillJlistPopular(MongoDatabase db)
    {
        DefaultListModel dlm = new DefaultListModel();
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("jeux");

        it = jeux.aggregate( Arrays.asList(   Aggregates.lookup("Note","idJeu", "idJeu", "joinJeuxNote") ,  Aggregates.sort( eq("joinJeuxNote.nbLikes",-1) ) ) ).iterator();
        
        
        
        while (it.hasNext()) 
        {
            Document doc = it.next();
            Jeu jeu = new Jeu(doc);
            File f = new File(jeu.getImage());

            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry(jeu.getNom(), new ImageIcon(jeu.getImage())));
            else dlm.addElement(new ListEntry(jeu.getNom(), new ImageIcon("imageJeux/default.png")));

        } 
        
        JList list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());
     
        jScrollPane2.add(list); 
        jScrollPane2.setViewportView(list);


    }
    
        public void fillJlistComments(MongoDatabase db)
    {
        DefaultListModel dlm = new DefaultListModel();            

        MongoCursor<Document> it,it2;
        MongoCollection<Document> jeux = db.getCollection("jeux");

        it = jeux.aggregate( Arrays.asList( Aggregates.unwind("$idJeu"),  Aggregates.lookup("Avis","idJeu", "idJeu", "joinJeuxAvis") ,  Aggregates.unwind("$joinJeuxAvis"),
                Aggregates.group(eq("_id","$idJeu"),sum("total",1)) , Aggregates.sort(eq("total",-1)) )) .iterator();
        

        while (it.hasNext()) 
        {
            Document doc = it.next();
          
            Document idJ = (Document) doc.get("_id");
  

            Jeu jeu = new Jeu((int) idJ.get("_id"));
            
            File f = new File(jeu.getImage());
            

            if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry(jeu.getNom(), new ImageIcon(jeu.getImage())));
            else dlm.addElement(new ListEntry(jeu.getNom(), new ImageIcon("imageJeux/default.png")));

        } 
        
        JList list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());
     
        jScrollPane3.add(list); 
        jScrollPane3.setViewportView(list);


    }
        
        
        public void ajoutSuppressionButton()
        {
            if(!isAdmin)
            {
                ajoutButton.setVisible(false);
                SuppressionButton.setVisible(false);
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

        barrehaut = new javax.swing.JPanel();
        fonctionnalitepanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        ajoutButton = new javax.swing.JButton();
        SuppressionButton = new javax.swing.JButton();
        jpanelrecherche = new javax.swing.JPanel();
        backtoacceuil = new javax.swing.JButton();
        recherche = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        corps = new javax.swing.JPanel();
        nouveaujeupanel = new javax.swing.JPanel();
        panneauprincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelTop = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelmarge = new javax.swing.JPanel();
        panelmarge2 = new javax.swing.JPanel();
        panelmarge3 = new javax.swing.JPanel();
        nouveaujeupanel1 = new javax.swing.JPanel();
        panneauprincipal1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelTop1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelmarge1 = new javax.swing.JPanel();
        panelmarge4 = new javax.swing.JPanel();
        panelmarge5 = new javax.swing.JPanel();
        nouveaujeupanel2 = new javax.swing.JPanel();
        panneauprincipal2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelTop2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelmarge6 = new javax.swing.JPanel();
        panelmarge7 = new javax.swing.JPanel();
        panelmarge8 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        barrehaut.setName("barrehaut"); // NOI18N
        barrehaut.setLayout(new java.awt.BorderLayout());

        fonctionnalitepanel.setLayout(new java.awt.GridLayout(1, 3));

        jPanel1.setPreferredSize(new java.awt.Dimension(164, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 164, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        fonctionnalitepanel.add(jPanel1);

        ajoutButton.setText("Ajout Jeu");
        fonctionnalitepanel.add(ajoutButton);

        SuppressionButton.setText("Suppression");
        SuppressionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuppressionButtonActionPerformed(evt);
            }
        });
        fonctionnalitepanel.add(SuppressionButton);

        barrehaut.add(fonctionnalitepanel, java.awt.BorderLayout.EAST);

        jpanelrecherche.setLayout(new java.awt.BorderLayout());

        backtoacceuil.setText("Accueil");
        backtoacceuil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backtoacceuilActionPerformed(evt);
            }
        });
        jpanelrecherche.add(backtoacceuil, java.awt.BorderLayout.WEST);

        recherche.setText("Recherche");
        jpanelrecherche.add(recherche, java.awt.BorderLayout.EAST);

        barrehaut.add(jpanelrecherche, java.awt.BorderLayout.WEST);

        jButton1.setText("Profil");
        jButton1.setMinimumSize(new java.awt.Dimension(60, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(60, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        barrehaut.add(jButton1, java.awt.BorderLayout.CENTER);

        getContentPane().add(barrehaut, java.awt.BorderLayout.NORTH);

        corps.setLayout(new java.awt.GridLayout(1, 3));

        nouveaujeupanel.setLayout(new java.awt.BorderLayout());

        panneauprincipal.setMinimumSize(new java.awt.Dimension(36, 0));
        panneauprincipal.setLayout(new java.awt.BorderLayout());
        panneauprincipal.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        nouveaujeupanel.add(panneauprincipal, java.awt.BorderLayout.CENTER);

        panelTop.setPreferredSize(new java.awt.Dimension(298, 100));
        panelTop.setLayout(new java.awt.GridLayout(2, 1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelTop.add(jPanel5);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Jeux récents");
        panelTop.add(jLabel1);

        nouveaujeupanel.add(panelTop, java.awt.BorderLayout.PAGE_START);

        panelmarge.setPreferredSize(new java.awt.Dimension(50, 235));

        javax.swing.GroupLayout panelmargeLayout = new javax.swing.GroupLayout(panelmarge);
        panelmarge.setLayout(panelmargeLayout);
        panelmargeLayout.setHorizontalGroup(
            panelmargeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        panelmargeLayout.setVerticalGroup(
            panelmargeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        nouveaujeupanel.add(panelmarge, java.awt.BorderLayout.EAST);

        panelmarge2.setPreferredSize(new java.awt.Dimension(50, 335));

        javax.swing.GroupLayout panelmarge2Layout = new javax.swing.GroupLayout(panelmarge2);
        panelmarge2.setLayout(panelmarge2Layout);
        panelmarge2Layout.setHorizontalGroup(
            panelmarge2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        panelmarge2Layout.setVerticalGroup(
            panelmarge2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        nouveaujeupanel.add(panelmarge2, java.awt.BorderLayout.LINE_START);

        panelmarge3.setPreferredSize(new java.awt.Dimension(298, 50));

        javax.swing.GroupLayout panelmarge3Layout = new javax.swing.GroupLayout(panelmarge3);
        panelmarge3.setLayout(panelmarge3Layout);
        panelmarge3Layout.setHorizontalGroup(
            panelmarge3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );
        panelmarge3Layout.setVerticalGroup(
            panelmarge3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        nouveaujeupanel.add(panelmarge3, java.awt.BorderLayout.PAGE_END);

        corps.add(nouveaujeupanel);

        nouveaujeupanel1.setLayout(new java.awt.BorderLayout());

        panneauprincipal1.setMinimumSize(new java.awt.Dimension(36, 0));
        panneauprincipal1.setLayout(new java.awt.BorderLayout());
        panneauprincipal1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        nouveaujeupanel1.add(panneauprincipal1, java.awt.BorderLayout.CENTER);

        panelTop1.setPreferredSize(new java.awt.Dimension(298, 100));
        panelTop1.setLayout(new java.awt.GridLayout(2, 1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelTop1.add(jPanel6);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Jeux populaire");
        panelTop1.add(jLabel2);

        nouveaujeupanel1.add(panelTop1, java.awt.BorderLayout.PAGE_START);

        panelmarge1.setPreferredSize(new java.awt.Dimension(50, 235));

        javax.swing.GroupLayout panelmarge1Layout = new javax.swing.GroupLayout(panelmarge1);
        panelmarge1.setLayout(panelmarge1Layout);
        panelmarge1Layout.setHorizontalGroup(
            panelmarge1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        panelmarge1Layout.setVerticalGroup(
            panelmarge1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        nouveaujeupanel1.add(panelmarge1, java.awt.BorderLayout.EAST);

        panelmarge4.setPreferredSize(new java.awt.Dimension(50, 335));

        javax.swing.GroupLayout panelmarge4Layout = new javax.swing.GroupLayout(panelmarge4);
        panelmarge4.setLayout(panelmarge4Layout);
        panelmarge4Layout.setHorizontalGroup(
            panelmarge4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        panelmarge4Layout.setVerticalGroup(
            panelmarge4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        nouveaujeupanel1.add(panelmarge4, java.awt.BorderLayout.LINE_START);

        panelmarge5.setPreferredSize(new java.awt.Dimension(298, 50));

        javax.swing.GroupLayout panelmarge5Layout = new javax.swing.GroupLayout(panelmarge5);
        panelmarge5.setLayout(panelmarge5Layout);
        panelmarge5Layout.setHorizontalGroup(
            panelmarge5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );
        panelmarge5Layout.setVerticalGroup(
            panelmarge5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        nouveaujeupanel1.add(panelmarge5, java.awt.BorderLayout.PAGE_END);

        corps.add(nouveaujeupanel1);

        nouveaujeupanel2.setLayout(new java.awt.BorderLayout());

        panneauprincipal2.setMinimumSize(new java.awt.Dimension(36, 0));
        panneauprincipal2.setLayout(new java.awt.BorderLayout());
        panneauprincipal2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        nouveaujeupanel2.add(panneauprincipal2, java.awt.BorderLayout.CENTER);

        panelTop2.setPreferredSize(new java.awt.Dimension(298, 100));
        panelTop2.setLayout(new java.awt.GridLayout(2, 1));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelTop2.add(jPanel7);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Jeux commentés");
        panelTop2.add(jLabel3);

        nouveaujeupanel2.add(panelTop2, java.awt.BorderLayout.PAGE_START);

        panelmarge6.setPreferredSize(new java.awt.Dimension(50, 235));

        javax.swing.GroupLayout panelmarge6Layout = new javax.swing.GroupLayout(panelmarge6);
        panelmarge6.setLayout(panelmarge6Layout);
        panelmarge6Layout.setHorizontalGroup(
            panelmarge6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        panelmarge6Layout.setVerticalGroup(
            panelmarge6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        nouveaujeupanel2.add(panelmarge6, java.awt.BorderLayout.EAST);

        panelmarge7.setPreferredSize(new java.awt.Dimension(50, 335));

        javax.swing.GroupLayout panelmarge7Layout = new javax.swing.GroupLayout(panelmarge7);
        panelmarge7.setLayout(panelmarge7Layout);
        panelmarge7Layout.setHorizontalGroup(
            panelmarge7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        panelmarge7Layout.setVerticalGroup(
            panelmarge7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        nouveaujeupanel2.add(panelmarge7, java.awt.BorderLayout.LINE_START);

        panelmarge8.setPreferredSize(new java.awt.Dimension(298, 50));

        javax.swing.GroupLayout panelmarge8Layout = new javax.swing.GroupLayout(panelmarge8);
        panelmarge8.setLayout(panelmarge8Layout);
        panelmarge8Layout.setHorizontalGroup(
            panelmarge8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );
        panelmarge8Layout.setVerticalGroup(
            panelmarge8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        nouveaujeupanel2.add(panelmarge8, java.awt.BorderLayout.PAGE_END);

        corps.add(nouveaujeupanel2);

        getContentPane().add(corps, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backtoacceuilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backtoacceuilActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_backtoacceuilActionPerformed

    private void SuppressionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuppressionButtonActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_SuppressionButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(pageAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pageAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pageAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pageAcceuil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pageAcceuil().setVisible(true);
               
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SuppressionButton;
    private javax.swing.JButton ajoutButton;
    private javax.swing.JButton backtoacceuil;
    private javax.swing.JPanel barrehaut;
    private javax.swing.JPanel corps;
    private javax.swing.JPanel fonctionnalitepanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpanelrecherche;
    private javax.swing.JPanel nouveaujeupanel;
    private javax.swing.JPanel nouveaujeupanel1;
    private javax.swing.JPanel nouveaujeupanel2;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPanel panelTop1;
    private javax.swing.JPanel panelTop2;
    private javax.swing.JPanel panelmarge;
    private javax.swing.JPanel panelmarge1;
    private javax.swing.JPanel panelmarge2;
    private javax.swing.JPanel panelmarge3;
    private javax.swing.JPanel panelmarge4;
    private javax.swing.JPanel panelmarge5;
    private javax.swing.JPanel panelmarge6;
    private javax.swing.JPanel panelmarge7;
    private javax.swing.JPanel panelmarge8;
    private javax.swing.JPanel panneauprincipal;
    private javax.swing.JPanel panneauprincipal1;
    private javax.swing.JPanel panneauprincipal2;
    private javax.swing.JButton recherche;
    // End of variables declaration//GEN-END:variables
}
