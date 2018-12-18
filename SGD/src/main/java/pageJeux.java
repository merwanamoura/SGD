
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.regex;
import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author at560075
 */
public class pageJeux extends javax.swing.JFrame {

    MongoDatabase db;
    CheckboxGroup cbEditeur;
    CheckboxGroup bg;
    
    ArrayList<String> listEditeurs;
    ArrayList<String> listCategorie;
    ArrayList<String> listPrix;
    ArrayList<String> listDates;
    /**
     * 
     * Creates new form pageJeux
     */
    public pageJeux() {
        
        initComponents();
        MongoDBConnection.connect();
        db = MongoDBConnection.getDb();
        cbEditeur = new CheckboxGroup();
        bg = new CheckboxGroup();
        
        listEditeurs = new ArrayList<String>();
        listCategorie = new ArrayList<String>();
        listPrix = new ArrayList<String>();
        listDates = new ArrayList<String>();
        
        fillAll();
        rechercheJeu();
    }
    
    public void actionPerformedEditeur(ActionEvent event) {
        
        JCheckBox checkbox = (JCheckBox) event.getSource();
        
        for(int i=0;i<listEditeurs.size();i++) listEditeurs.remove(i);
        
        for(int i=0;i<LesEditeurs.getComponentCount();i++)
        {
            if(((JCheckBox)LesEditeurs.getComponent(i)).isSelected())
            {
                listEditeurs.add( ((JCheckBox)LesEditeurs.getComponent(i)).getText());
            }
        }

        
    }
    
    public void actionPerformedCategorie(ActionEvent event) {
        
        JCheckBox checkbox = (JCheckBox) event.getSource();
        
        for(int i=0;i<listCategorie.size();i++) listCategorie.remove(i);
        
        for(int i=0;i<lesCategories.getComponentCount();i++)
        {
            if(((JCheckBox)lesCategories.getComponent(i)).isSelected())
            {
                listCategorie.add( ((JCheckBox)lesCategories.getComponent(i)).getText());
            }
        }

        
    }
    

    public void actionPerformedPrix(ActionEvent event) {
        
        JRadioButton jrb = (JRadioButton) event.getSource();
        
        for(int i=0;i<listPrix.size();i++) listPrix.remove(i);
        
        for(int i=0;i<lesPrix.getComponentCount();i++)
        {
            if(((JRadioButton)lesPrix.getComponent(i)).isSelected())
            {
                listPrix.add( ((JRadioButton)lesPrix.getComponent(i)).getText());
            }
        }

        
    }
    
    public void actionPerformedDates(ActionEvent event) {
        
        JRadioButton jrb = (JRadioButton) event.getSource();
        
        for(int i=0;i<listDates.size();i++) listDates.remove(i);
        
        for(int i=0;i<lesDates.getComponentCount();i++)
        {
            if(((JRadioButton)lesDates.getComponent(i)).isSelected())
            {
                listDates.add( ((JRadioButton)lesDates.getComponent(i)).getText());
            }
        }

        
    }
    
    public void fillAll()
    {
        fillLesEditeurs();
        fillLesCategories();
        fillLesprix();
        fillLesDates();
        
    }
    
    public void fillLesEditeurs()
    {
        
       // LesEditeurs.removeAll();
        MongoCollection<Document> jeux = db.getCollection("jeux");
              
        MongoCursor<String> c =  db.getCollection("jeux").distinct("nomEditeur", String.class).iterator();
        
        while (c.hasNext()) 
        {
            String nomEditeur = c.next();
            JCheckBox checkbox = new JCheckBox(nomEditeur);
    
            checkbox.addActionListener(new ActionListener() 
            { 
                public void actionPerformed(ActionEvent e) 
                { 
                  actionPerformedEditeur(e);
                 // fillAll();
                   rechercheJeu();
                } 
            } );
            
            LesEditeurs.add(checkbox);
            LesEditeurs.setVisible(true);

        } 
       
       
    }
    
    public void fillLesCategories()
    {
       // lesCategories.removeAll();
        MongoCollection <Document> jeux = db.getCollection("jeux");
              
        MongoCursor<String> c =  db.getCollection("jeux").distinct("categorie", String.class).iterator();
        
        while (c.hasNext()) 
        {
            String categorie = c.next();
            JCheckBox checkbox = new JCheckBox(categorie);
            checkbox.addActionListener(new ActionListener() 
            { 
                public void actionPerformed(ActionEvent e) 
                { 
                  actionPerformedCategorie(e);
                //  fillAll();
                   rechercheJeu();
                } 
            } );
            
            lesCategories.add(checkbox);
            lesCategories.setVisible(true);

        } 

    }
    
    public void fillLesprix()
    {
        
       // lesPrix.removeAll();
        ButtonGroup bg = new ButtonGroup();
        
        JRadioButton b1 = new JRadioButton("0 à 20 EUR");
        bg.add(b1);
        b1.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
            //  fillAll();
               rechercheJeu();
            } 
        } );
        lesPrix.add(b1);
        lesPrix.setVisible(true);
        
        
        
        JRadioButton b2 = new JRadioButton("20 à 50 EUR");
        bg.add(b2);
        b2.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
           //   fillAll();
               rechercheJeu();
            } 
        } );
        lesPrix.add(b2);
        lesPrix.setVisible(true);
        
        
        
        JRadioButton b3 = new JRadioButton("50 à 100 EUR");
        bg.add(b3);

        b3.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
            //  fillAll();
               rechercheJeu();
            } 
        } );
        lesPrix.add(b3);
        lesPrix.setVisible(true);
        
        
        
        JRadioButton b4 = new JRadioButton("Plus de 100 EUR");
        bg.add(b4);
        b4.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
             // fillAll();
               rechercheJeu();
            } 
        } );
        lesPrix.add(b4);
        lesPrix.setVisible(true);
        
        JRadioButton b5 = new JRadioButton("Tout prix");
        bg.add(b5);
        b5.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
             // fillAll();
               rechercheJeu();
            } 
        } );
        lesPrix.add(b5);
        lesPrix.setVisible(true);
    }
    
    public void fillLesDates()
    {
      //  lesDates.removeAll();
        
        ButtonGroup bg = new ButtonGroup();
        
        JRadioButton b1 = new JRadioButton("Avant 1990");
        bg.add(b1);
        b1.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedDates(e);
             // fillAll();
               rechercheJeu();
            } 
        } );
        lesDates.add(b1);
        lesDates.setVisible(true);
        
        
        
        JRadioButton b2 = new JRadioButton("1990 - 2000");
        bg.add(b2);
        b2.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedDates(e);
             // fillAll();
               rechercheJeu();
            } 
        } );
        lesDates.add(b2);
        lesDates.setVisible(true);
        
        
        
        JRadioButton b3 = new JRadioButton("2000 - 2005");
        bg.add(b3);

        b3.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedDates(e);
             // fillAll();
               rechercheJeu();
            } 
        } );
        lesDates.add(b3);
        lesDates.setVisible(true);
        
        JRadioButton b31 = new JRadioButton("2005 - 2010");
        bg.add(b31);

        b31.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedDates(e);
            //  fillAll();
               rechercheJeu();
            } 
        } );
        lesDates.add(b31);
        lesDates.setVisible(true);
        
        
        
        JRadioButton b4 = new JRadioButton("2010 - 2018");
        bg.add(b4);
        b4.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
             // fillAll();
               rechercheJeu();
            } 
        } );
        lesDates.add(b4);
        lesDates.setVisible(true);
        
        JRadioButton b41 = new JRadioButton("Cette année");
        bg.add(b41);
        b41.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
             // fillAll();
               rechercheJeu();
            } 
        } );
        lesDates.add(b41);
        lesDates.setVisible(true);
        
        JRadioButton b5 = new JRadioButton("Toutes les dates");
        bg.add(b5);
        b5.addActionListener(new ActionListener() 
        { 
            public void actionPerformed(ActionEvent e) 
            { 
              actionPerformedPrix(e);
            //  fillAll();
               rechercheJeu();
            } 
        } );
        lesDates.add(b5);
        lesDates.setVisible(true);
    }
    
    
    
    void rechercheJeu()
    {
        MongoCursor<Document> it;
        MongoCollection<Document> jeux = db.getCollection("jeux");

        
        for(int i=0;i<listEditeurs.size();i++) listEditeurs.remove(i);
        
        for(int i=0;i<LesEditeurs.getComponentCount()-1;i++)
        {
            if(((JCheckBox)LesEditeurs.getComponent(i)).isSelected())
            {
                listEditeurs.add( ((JCheckBox)LesEditeurs.getComponent(i)).getText());
            }
        }
        
        for(int i=0;i<listCategorie.size();i++) listCategorie.remove(i);
        listCategorie.clear();
        for(int i=0;i<lesCategories.getComponentCount()-1;i++)
        {
            if(((JCheckBox)lesCategories.getComponent(i)).isSelected())
            {
                listCategorie.add( ((JCheckBox)lesCategories.getComponent(i)).getText());
            }
        }
        
        for(int i=0;i<listPrix.size();i++) listPrix.remove(i);
        listPrix.clear();
        for(int i=0;i<lesPrix.getComponentCount();i++)
        {
            if(((JRadioButton)lesPrix.getComponent(i)).isSelected())
            {
                listPrix.add( ((JRadioButton)lesPrix.getComponent(i)).getText());
            }
        }
        
        
        for(int i=0;i<listDates.size();i++) listDates.remove(i);
        listDates.clear();
        for(int i=0;i<lesDates.getComponentCount();i++)
        {
            if(((JRadioButton)lesDates.getComponent(i)).isSelected())
            {
                listDates.add( ((JRadioButton)lesDates.getComponent(i)).getText());
            }
        }
        
        
    
    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
     
        BasicDBObject andQuery = new BasicDBObject();
        BasicDBObject andQueryEditeur = new BasicDBObject();
        BasicDBObject andQueryCategorie = new BasicDBObject();
        BasicDBObject orQueryPrix = new BasicDBObject();
        BasicDBObject andQueryRegex = new BasicDBObject();
        BasicDBObject andQueryDate = new BasicDBObject();
     
        
	if(listEditeurs.size() == 0 && listCategorie.size() ==0 && listPrix.size() == 0 && listDates.size() ==0)
        {
         
            String saisie = "";
            
            saisie = barrerecherche.getText();
            DefaultListModel dlm = new DefaultListModel();

            MongoCursor<Document> cursor = jeux.find(regex("nom",saisie)).iterator();

             while (cursor.hasNext()) 
            {
                Document doc = cursor.next();
                File f = new File((String) doc.get("image"));

                if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nomJeu"), new ImageIcon((String) doc.get("pathImage"))));
                else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));

            }

            JList list = new JList(dlm);
            list.setCellRenderer(new ListEntryCellRenderer());

            jScrollPane2.add(list); 
            jScrollPane2.setViewportView(list);   


        }
        else
        {
            
        if(listEditeurs.size() != 0)
        {
           andQueryEditeur.put("nomEditeur", new BasicDBObject("$in",listEditeurs));       
           obj.add(andQueryEditeur);
        }
	
     
        
        if(listCategorie.size() != 0)
        {
             andQueryCategorie.put("categorie",  new BasicDBObject("$in",listCategorie));
             obj.add(andQueryCategorie);
        }
       
        
        if(listPrix.size() !=0)
        {
            for(int i = 0 ; i < listPrix.size() ; i++)
            {
                if (listPrix.get(i).equals("0 à 20 EUR"))
                {
                    orQueryPrix.put("prix", new BasicDBObject("$gt", 0).append("$lt", 20));
                    obj.add(orQueryPrix);                      
                }
                
                  if (listPrix.get(i).equals("20 à 50 EUR"))
                {
                    orQueryPrix.put("prix", new BasicDBObject("$gt", 20).append("$lt", 50));
                    obj.add(orQueryPrix);
                }
                  
                   if (listPrix.get(i).equals("50 à 100 EUR"))
                {
                    orQueryPrix.put("prix", new BasicDBObject("$gt", 50).append("$lt", 100));
                    obj.add(orQueryPrix);                      
                }
                
                  if (listPrix.get(i).equals("Plus de 100 EUR"))
                {
                    orQueryPrix.put("prix", new BasicDBObject("$gt", 100));
                    obj.add(orQueryPrix);
                }
                  
                    if (listPrix.get(i).equals("Tout prix"))
                {
                    orQueryPrix.put("prix", new BasicDBObject("$gt", 0));
                    obj.add(orQueryPrix);
                }
            }
        }
        
        
        if(listDates.size() !=0)
        {
            for(int i = 0 ; i < listDates.size() ; i++)
            {
                if (listDates.get(i).equals("Avant 1990"))
                {
                    
                    
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                    Date dated = new Date();
                    try {
                        dated = dateFormat.parse("31-12-1990"+"T23:59:00");
                    } catch (ParseException ex) {
                        Logger.getLogger(pageJeux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    andQueryDate.put("dateSortie", new BasicDBObject("$lt", dated));
                 
                   
                    obj.add(andQueryDate);                      
                }
                
                  if (listDates.get(i).equals("1990 - 2000"))
                {
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                    Date dated = new Date();
                    Date datef = new Date();
                    try {
                        dated = dateFormat.parse("01-01-1990"+"T00:00:00");
                        datef = dateFormat.parse("01-01-2000"+"T00:00:00");
                    } catch (ParseException ex) {
                        Logger.getLogger(pageJeux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    andQueryDate.put("dateSortie", new BasicDBObject("$gte", dated).append("$lt", datef));
                    obj.add(andQueryDate);
                }
                  
                   if (listDates.get(i).equals("2000 - 2005"))
                {
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                    Date dated = new Date();
                    Date datef = new Date();
                    try {
                        dated = dateFormat.parse("01-01-2000"+"T00:00:00");
                        datef = dateFormat.parse("01-01-2005"+"T00:00:00");
                    } catch (ParseException ex) {
                        Logger.getLogger(pageJeux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    andQueryDate.put("dateSortie", new BasicDBObject("$gte", dated).append("$lt", datef));
                    obj.add(andQueryDate);   
                    
                }
                
                  if (listDates.get(i).equals("2005 - 2010"))
                {
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                    Date dated = new Date();
                    Date datef = new Date();
                    try {
                        dated = dateFormat.parse("01-01-2005"+"T00:00:00");
                        datef = dateFormat.parse("01-01-2010"+"T00:00:00");
                    } catch (ParseException ex) {
                        Logger.getLogger(pageJeux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    andQueryDate.put("dateSortie", new BasicDBObject("$gte", dated).append("$lt", datef));
                    obj.add(andQueryDate);    
                    
                }
                  
                    if (listDates.get(i).equals("2010 - 2018"))
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                    Date dated = new Date();
                    Date datef = new Date();
                    try {
                        dated = dateFormat.parse("01-01-2010"+"T00:00:00");
                        datef = dateFormat.parse("31-12-2018"+"T23:59:00");
                    } catch (ParseException ex) {
                        Logger.getLogger(pageJeux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    andQueryDate.put("dateSortie", new BasicDBObject("$gte", dated).append("$lt", datef));
                    obj.add(andQueryDate);
                }
                    
                    if (listDates.get(i).equals("Cette année"))
                {
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                    Date dated = new Date();
                    Date datef = new Date();
                    try {
                        dated = dateFormat.parse("01-01-2019"+"T00:01:00");
                        datef = dateFormat.parse("31-12-2019"+"T23:59:00");
                    } catch (ParseException ex) {
                        Logger.getLogger(pageJeux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    andQueryDate.put("dateSortie", new BasicDBObject("$gte", dated).append("$lt", datef));
                    obj.add(andQueryDate);
                  
                }
                         
                    if (listDates.get(i).equals("Toutes les dates"))
                {
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                    Date dated = new Date();
                    try {
                        dated = dateFormat.parse("01-01-1200"+"T00:01:00");
                    } catch (ParseException ex) {
                        Logger.getLogger(pageJeux.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    andQueryDate.put("dateSortie", new BasicDBObject("$gt", dated));
                    obj.add(andQueryDate);
                }
            }
        }
        
        

        

            String saisie = "";
            saisie = barrerecherche.getText();
            if(!saisie.equals(""))
            {
                andQueryRegex.put("nom", new BasicDBObject("$regex", saisie));
                obj.add(andQueryRegex);
            }

            andQuery.put("$and",obj);


            System.out.println(andQuery.toString());

            DefaultListModel dlm = new DefaultListModel();

            MongoCursor<Document> cursor = jeux.find(andQuery).iterator();

            while (cursor.hasNext()) 
            {
                Document doc = cursor.next();
                File f = new File((String) doc.get("image"));

                if(f.exists() && !f.isDirectory())dlm.addElement(new ListEntry((String) doc.get("nomJeu"), new ImageIcon((String) doc.get("pathImage"))));
                else dlm.addElement(new ListEntry((String) doc.get("nom"), new ImageIcon("imageJeux/default.png")));


            } 
            JList list = new JList(dlm);
            list.setCellRenderer(new ListEntryCellRenderer());

            jScrollPane2.add(list); 
            jScrollPane2.setViewportView(list); 
        
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
        jpanelrecherche = new javax.swing.JPanel();
        backtoacceuil = new javax.swing.JButton();
        barrerecherche = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        corps = new javax.swing.JPanel();
        panelfiltre = new javax.swing.JPanel();
        labelfiltre = new java.awt.Label();
        filtres = new javax.swing.JPanel();
        filtreEditeur = new javax.swing.JPanel();
        labelEditeur = new javax.swing.JLabel();
        scrollEditeur = new javax.swing.JScrollPane();
        LesEditeurs = new javax.swing.JPanel();
        filtrePrix = new javax.swing.JPanel();
        labelPrix = new javax.swing.JLabel();
        scrollPrix = new javax.swing.JScrollPane();
        lesPrix = new javax.swing.JPanel();
        filtreCategorie = new javax.swing.JPanel();
        labelCategorie = new javax.swing.JLabel();
        scrollCategorie = new javax.swing.JScrollPane();
        lesCategories = new javax.swing.JPanel();
        datePanel = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        scrollDate = new javax.swing.JScrollPane();
        lesDates = new javax.swing.JPanel();
        corpspanelrecherche = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        barrehaut.setName("barrehaut"); // NOI18N
        barrehaut.setLayout(new java.awt.BorderLayout());

        fonctionnalitepanel.setLayout(new java.awt.GridLayout(1, 3));
        barrehaut.add(fonctionnalitepanel, java.awt.BorderLayout.EAST);

        jpanelrecherche.setLayout(new java.awt.BorderLayout());

        backtoacceuil.setText("Acceuil");
        backtoacceuil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jpanelrecherche.add(backtoacceuil, java.awt.BorderLayout.WEST);

        barrerecherche.setPreferredSize(new java.awt.Dimension(260, 19));
        barrerecherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrerechercheActionPerformed(evt);
            }
        });
        barrerecherche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barrerechercheKeyPressed(evt);
            }
        });
        jpanelrecherche.add(barrerecherche, java.awt.BorderLayout.EAST);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ENTREZ LE NOM DU JEU ->");
        jLabel1.setMinimumSize(new java.awt.Dimension(71, 15));
        jLabel1.setPreferredSize(new java.awt.Dimension(260, 15));
        jpanelrecherche.add(jLabel1, java.awt.BorderLayout.CENTER);

        barrehaut.add(jpanelrecherche, java.awt.BorderLayout.WEST);

        getContentPane().add(barrehaut, java.awt.BorderLayout.NORTH);

        corps.setLayout(new java.awt.BorderLayout());

        panelfiltre.setMinimumSize(new java.awt.Dimension(70, 270));
        panelfiltre.setPreferredSize(new java.awt.Dimension(220, 600));
        panelfiltre.setLayout(new java.awt.BorderLayout());

        labelfiltre.setAlignment(java.awt.Label.CENTER);
        labelfiltre.setPreferredSize(new java.awt.Dimension(35, 40));
        labelfiltre.setText("Filtre");
        panelfiltre.add(labelfiltre, java.awt.BorderLayout.NORTH);

        filtres.setLayout(new java.awt.GridLayout(4, 1));

        filtreEditeur.setLayout(new java.awt.BorderLayout());

        labelEditeur.setForeground(new java.awt.Color(0, 0, 0));
        labelEditeur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEditeur.setText("Editeur");
        labelEditeur.setPreferredSize(new java.awt.Dimension(51, 30));
        filtreEditeur.add(labelEditeur, java.awt.BorderLayout.NORTH);

        LesEditeurs.setLayout(new java.awt.GridLayout(0, 1));
        scrollEditeur.setViewportView(LesEditeurs);

        filtreEditeur.add(scrollEditeur, java.awt.BorderLayout.CENTER);

        filtres.add(filtreEditeur);

        filtrePrix.setLayout(new java.awt.BorderLayout());

        labelPrix.setForeground(new java.awt.Color(0, 0, 0));
        labelPrix.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPrix.setText("Prix");
        labelPrix.setPreferredSize(new java.awt.Dimension(26, 30));
        filtrePrix.add(labelPrix, java.awt.BorderLayout.NORTH);

        lesPrix.setLayout(new java.awt.GridLayout(0, 1));
        scrollPrix.setViewportView(lesPrix);

        filtrePrix.add(scrollPrix, java.awt.BorderLayout.CENTER);

        filtres.add(filtrePrix);

        filtreCategorie.setLayout(new java.awt.BorderLayout());

        labelCategorie.setForeground(new java.awt.Color(0, 0, 0));
        labelCategorie.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCategorie.setText("Catégorie");
        labelCategorie.setPreferredSize(new java.awt.Dimension(70, 30));
        filtreCategorie.add(labelCategorie, java.awt.BorderLayout.NORTH);

        lesCategories.setLayout(new java.awt.GridLayout(0, 1));
        scrollCategorie.setViewportView(lesCategories);

        filtreCategorie.add(scrollCategorie, java.awt.BorderLayout.CENTER);

        filtres.add(filtreCategorie);

        datePanel.setLayout(new java.awt.BorderLayout());

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label1.setPreferredSize(new java.awt.Dimension(106, 30));
        label1.setText("Date de sortie");
        datePanel.add(label1, java.awt.BorderLayout.PAGE_START);
        label1.getAccessibleContext().setAccessibleName("Date de sortie");

        lesDates.setLayout(new java.awt.GridLayout(0, 1));
        scrollDate.setViewportView(lesDates);

        datePanel.add(scrollDate, java.awt.BorderLayout.CENTER);

        filtres.add(datePanel);

        panelfiltre.add(filtres, java.awt.BorderLayout.CENTER);

        corps.add(panelfiltre, java.awt.BorderLayout.WEST);

        corpspanelrecherche.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Liste des jeux");
        jLabel2.setPreferredSize(new java.awt.Dimension(98, 70));
        corpspanelrecherche.add(jLabel2, java.awt.BorderLayout.NORTH);
        corpspanelrecherche.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        corps.add(corpspanelrecherche, java.awt.BorderLayout.CENTER);

        getContentPane().add(corps, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barrerechercheKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barrerechercheKeyPressed
        rechercheJeu();
    }//GEN-LAST:event_barrerechercheKeyPressed

    private void barrerechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrerechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_barrerechercheActionPerformed

   
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
            java.util.logging.Logger.getLogger(pageJeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pageJeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pageJeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pageJeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pageJeux().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LesEditeurs;
    private javax.swing.JButton backtoacceuil;
    private javax.swing.JPanel barrehaut;
    private javax.swing.JTextField barrerecherche;
    private javax.swing.JPanel corps;
    private javax.swing.JPanel corpspanelrecherche;
    private javax.swing.JPanel datePanel;
    private javax.swing.JPanel filtreCategorie;
    private javax.swing.JPanel filtreEditeur;
    private javax.swing.JPanel filtrePrix;
    private javax.swing.JPanel filtres;
    private javax.swing.JPanel fonctionnalitepanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpanelrecherche;
    private java.awt.Label label1;
    private javax.swing.JLabel labelCategorie;
    private javax.swing.JLabel labelEditeur;
    private javax.swing.JLabel labelPrix;
    private java.awt.Label labelfiltre;
    private javax.swing.JPanel lesCategories;
    private javax.swing.JPanel lesDates;
    private javax.swing.JPanel lesPrix;
    private javax.swing.JPanel panelfiltre;
    private javax.swing.JScrollPane scrollCategorie;
    private javax.swing.JScrollPane scrollDate;
    private javax.swing.JScrollPane scrollEditeur;
    private javax.swing.JScrollPane scrollPrix;
    // End of variables declaration//GEN-END:variables
}
