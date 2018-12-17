
import com.mongodb.client.MongoDatabase;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ai265149
 */
public class creationCompte extends javax.swing.JFrame {

    /**
     * Creates new form creationCompte
     */
    public creationCompte() {
        initComponents();
        
        MongoDBConnection.connect();
        MongoDatabase db = MongoDBConnection.getDb();
        
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

        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nomField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        prenomField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pseudoField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pwField1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        pwdField2 = new javax.swing.JPasswordField();
        cancelButton = new javax.swing.JButton();
        createAccountButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 400));

        jPanel2.setFont(new java.awt.Font("GFS Artemisia", 1, 14)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 50));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Veuillez renseigner les champs pour commencer");
        jLabel6.setToolTipText("");
        jLabel6.setPreferredSize(new java.awt.Dimension(600, 15));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.GridLayout(6, 2));

        jLabel1.setText("Nom ?");
        jPanel4.add(jLabel1);
        jPanel4.add(nomField);

        jLabel2.setText("Prénom ?");
        jPanel4.add(jLabel2);
        jPanel4.add(prenomField);

        jLabel3.setText("Pseudo ?");
        jPanel4.add(jLabel3);
        jPanel4.add(pseudoField);

        jLabel4.setText("Mot de passe");
        jPanel4.add(jLabel4);
        jPanel4.add(pwField1);

        jLabel5.setText("Retapez le mot de passe");
        jPanel4.add(jLabel5);

        pwdField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwdField2ActionPerformed(evt);
            }
        });
        jPanel4.add(pwdField2);

        cancelButton.setText("Annuler");
        jPanel4.add(cancelButton);

        createAccountButton.setText("Créer Compte");
        createAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAccountButtonActionPerformed(evt);
            }
        });
        jPanel4.add(createAccountButton);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pwdField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwdField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwdField2ActionPerformed

    private void createAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAccountButtonActionPerformed
        // TODO add your handling code here:
                String nom = nomField.getText();
                String prenom = nomField.getText();
                String pseudo = nomField.getText();
                String pwd= pwField1.getText();
                String pwd2= pwdField2.getText();
                
                // Champ(s) vide(s)
                if (nom.length()==0 || prenom.length()==0 || pseudo.length()==0 || pwd.length()==0 || pwd2.length()==0 ){
                    
                         JOptionPane.showMessageDialog(this,
                        "Vous devez remplir les champs pour créer votre compte",
                        "Champs vides",
                        JOptionPane.WARNING_MESSAGE);

                }
                
                // Mots de passe différents 
                else if (!pwd.equals(pwd2))
                {
                    JOptionPane.showMessageDialog(this,
                        "Les mots de passe doivent être identiques pour créer votre compte",
                        "Champs vides",
                        JOptionPane.ERROR_MESSAGE);

                }
                
                else {
                    
                }
        
        
    }//GEN-LAST:event_createAccountButtonActionPerformed

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
            java.util.logging.Logger.getLogger(creationCompte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(creationCompte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(creationCompte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(creationCompte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new creationCompte().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createAccountButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField nomField;
    private javax.swing.JTextField prenomField;
    private javax.swing.JTextField pseudoField;
    private javax.swing.JPasswordField pwField1;
    private javax.swing.JPasswordField pwdField2;
    // End of variables declaration//GEN-END:variables
}