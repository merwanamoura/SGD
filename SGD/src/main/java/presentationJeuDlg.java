
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Image;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
public class presentationJeuDlg extends javax.swing.JDialog {
    Jeu jeu;
    Users us;
    static int idJeu;
    static int idUser;
    JFrame previousFrame;
    /**
     * Creates new form presentationJeuDlg
     */
    public presentationJeuDlg(java.awt.Frame parent, boolean modal,int idJ, int idU) {
        super(parent, modal);
        initComponents();
        
        previousFrame=(JFrame) parent;
        
        
        this.setSize(800,600);
        
        this.idUser = idU;
        this.idJeu = idJ;
       
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
        setComment();
        setCote();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
    
    public String map(int nb){
        String map = "function() {\n" +
"if(this.idJeu == "+ nb +") emit(this.idJeu, this.nbLikes - this.nbDislikes);\n" +
"};";
        return map;
    }
    
    public String reduce(){
        String reduce = "function(key, value) {\n" +
        "    return Array.sum(value);\n" +
        "};";
        return reduce;
    }
    
    public void mapReduce(int idJeu){
        
        char[] pass = new char[10];
        String s = "ma522501";
        pass = s.toCharArray();
        MongoCredential credential = MongoCredential.createCredential("ma522501","ma522501",pass);
        MongoClient client = new MongoClient(new ServerAddress("mongo",27017),Arrays.asList(credential));
        
	DB db;
        db = client.getDB("ma522501");
	DBCollection note = db.getCollection("Note");
/*
        MongoClient client = new MongoClient("localhost");
        DB db = client.getDB("ma522501");
        DBCollection note = db.getCollection("Note");
        */
	MapReduceCommand cmd = new MapReduceCommand(note, map(idJeu), reduce(), null, MapReduceCommand.OutputType.INLINE,null);
	MapReduceOutput out = note.mapReduce(cmd);

	for (DBObject o : out.results()) {  
            labelCote.setText("Cote : "+ o.get("value"));     
	}

    }
    
    public void setCote(){
        mapReduce(this.idJeu);
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
                buttonDislike.setBackground(Color.red);
            } 
        }
    }
    
    public void setLike(){
        List<String> listeJeuLike = us.getListeLike();

        for(int i = 0; i< listeJeuLike.size() ; i++){
            
            if(listeJeuLike.get(i).equals(jeu.getNom())) {
                buttonLike.setBackground(Color.green);
            }  
        }
    }
    
    public void setPresentation(){
        labelNomJeu.setText(labelNomJeu.getText() + " " + jeu.getNom());
    }
    
    public void description()
    {

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
        

        File f = new File(jeu.getImage());
         
        if(f.exists() && !f.isDirectory())img = jeu.getImage();    
        else img="imageJeux/default.png";
        JLabel label = new JLabel();
        ImageIcon ic = new ImageIcon(img);
        ImageIcon imageIcon = new ImageIcon(ic.getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
       
        label.setIcon(imageIcon);
        
        panelImage.add(label, BorderLayout.CENTER);
   
    }
    public void jeuSimilaire()
    {

        MongoDatabase db = MongoDBConnection.getDb();
        DefaultListModel dlm = new DefaultListModel();
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("jeux");

        it = jeux.find(eq("categorie" , jeu.getCategorie())).iterator();
        
        
        while (it.hasNext()) 
        {
            Document doc = it.next();
            if(!((String) doc.get("nom")).equals(jeu.getNom())){
                File f = new File((String) doc.get("image"));
            
                if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon((String) doc.get("image"))));
                else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));
    
            }

        } 
        JList list = new JList(dlm);
        list.setCellRenderer(new ListEntryCellRenderer());
        
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

                        setVisible(false);
                        dispose();

                        presentationJeuDlg pj = new presentationJeuDlg(previousFrame,true,idNewjeu,idUser);
                        pj.setVisible(true);
                }
              }
                
                
        };
        list.addMouseListener(mouseListener);
        
        jScrollPane1.add(list); 
        jScrollPane1.setViewportView(list);


    }
  
    
    public void setComment(){
        JLabel jb;
        JTextArea jt;
        JButton but;
        
        JPanel panel = new JPanel(new BorderLayout());
              
        if( us.hasComment(idJeu) ){   
            jb = new JLabel(us.getPseudo() + " : ");
            
            jt = new JTextArea(us.getAvis(this.idJeu) );
            jt.setLineWrap(true);
            jt.setEnabled(false);
            
            but = new JButton("Modifier");
        }
        else
        {
            jb = new JLabel(us.getPseudo() + " : ");
 
            jt = new JTextArea("Laisser un commentaire ..." );
            jt.setLineWrap(true);

            but = new JButton("Commenter");
        }
        
        panel.add(jb,BorderLayout.WEST);
        panel.add(jt,BorderLayout.CENTER);
        panel.add(but,BorderLayout.EAST);  
        
        but.addActionListener(new ActionListener() 
            { 
                public void actionPerformed(ActionEvent e) 
                { 
                    
                    if( !us.hasComment(idJeu) ){
                        jt.setEnabled(false);
                        but.setText("Modifier");
                        String str = jt.getText();
                        us.createAvis(str,idJeu);
                        panelAvis.removeAll();
                        setAvis();
                        setComment();
                    }else{
                        if(but.getText().equals("Modifier"))
                        {
                            jt.setEnabled(true);
                            but.setText("Commenter");
                        }else{
                            jt.setEnabled(false);
                            but.setText("Modifier");
                            String str = jt.getText();
                            us.updateAvis(str,idJeu);
                            panelAvis.removeAll();
                            setAvis();
                            setComment();
                        }
                    }  
                } 
            } );
        
        panelAvis.add(panel,BorderLayout.NORTH);
        
    }
    
    
    public void setAvis()
    {

        MongoDatabase db = MongoDBConnection.getDb();
        MongoCursor<Document> it;
        MongoCollection<Document> avis = db.getCollection("Avis");

        it = avis.find(eq("idJeu" , this.idJeu)).iterator();  
        
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel);

        while (it.hasNext()) 
        {
            Document doc = it.next();
            int idUtilisateur = (int) doc.get("idUser");
            Users us = new Users(idUtilisateur);
            
            JPanel jp = new JPanel(new BorderLayout());

            JTextArea jt = new JTextArea((String) doc.get("avis"));
            jt.setLineWrap(true);
            jt.setEnabled(false);
            jt.setBackground(Color.WHITE);
            jp.add(jt,BorderLayout.CENTER);

            JLabel jl = new JLabel(us.getPseudo());
            jp.add(jl,BorderLayout.WEST);
            panel.add(jp);
            
 
        } 
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        panelAvis.add(scrollPane,BorderLayout.CENTER);
        
        
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        labelCote = new javax.swing.JLabel();
        panelAvis = new javax.swing.JPanel();
        panelJeuSimilaire = new javax.swing.JPanel();
        labelJeuSimilaire = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        labelNomJeu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        buttonFavori.setPreferredSize(new java.awt.Dimension(800, 600));
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

        labelCote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCote.setText("Cote :");
        labelCote.setPreferredSize(new java.awt.Dimension(51, 25));
        panelDesc.add(labelCote, java.awt.BorderLayout.SOUTH);

        jPanel1.add(panelDesc);

        panelJeu.add(jPanel1);

        panelAvis.setLayout(new java.awt.BorderLayout());
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

        labelNomJeu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNomJeu.setText("Présentation du jeu");
        labelNomJeu.setPreferredSize(new java.awt.Dimension(141, 35));
        getContentPane().add(labelNomJeu, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLikeActionPerformed
        // TODO add your handling code here:
        String nomJeu = this.jeu.getNom();
        Note no = new Note(idJeu);

        //pour le like
        if(us.isLike(jeu.getNom()))
        {
            buttonLike.setBackground(Color.white);
            this.us.removeJeuLike(nomJeu);
            no.removeLike();
        }
        else
        {
            buttonLike.setBackground(Color.green);
            this.us.addJeuLike(nomJeu);
            no.addLike();
        }

        //pour le dislike
        if(us.isDislike(jeu.getNom()))
        {
            buttonDislike.setBackground(Color.white);
            this.us.removeJeuDislike(nomJeu);
            no.removeDislike();
        }
        setCote();
    }//GEN-LAST:event_buttonLikeActionPerformed

    private void buttonDislikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDislikeActionPerformed
        // TODO add your handling code here:
        String nomJeu = this.jeu.getNom();
        Note no = new Note(idJeu);
        //pour le dislike
        if(us.isDislike(jeu.getNom()))
        {
            buttonDislike.setBackground(Color.white);
            this.us.removeJeuDislike(nomJeu);
            no.removeDislike();
        }else{
            buttonDislike.setBackground(Color.red);
            this.us.addJeuDislike(nomJeu);
            no.addDislike();
        }

        //pour le like
        if(us.isLike(jeu.getNom()))
        {
            buttonLike.setBackground(Color.white);
            this.us.removeJeuLike(nomJeu);
            no.removeLike();
        }
        setCote();
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



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDislike;
    private javax.swing.JButton buttonFavori;
    private javax.swing.JButton buttonLike;
    private javax.swing.JPanel corps;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCote;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelJeuSimilaire;
    private javax.swing.JLabel labelNomJeu;
    private javax.swing.JPanel panelAvis;
    private javax.swing.JPanel panelDesc;
    private javax.swing.JPanel panelImage;
    private javax.swing.JPanel panelJeu;
    private javax.swing.JPanel panelJeuSimilaire;
    private javax.swing.JPanel panelLikeDislike;
    // End of variables declaration//GEN-END:variables
}
