package bonbombe.views;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.controllers.impl.ClientController;
import bonbombe.models.interf.*;
import bonbombe.views.utils.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.color.*;
import java.awt.Graphics;
import java.util.*;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.util.Random;
import java.io.*;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
/**
 * Page Jeu : Permet de jouer BonBombe
 */
public class JeuPage extends Page{
	/* Attributs */

	private ClientController controller;
	// Plateau du jeu
	private Plateau plateau;

	/* Tableau de Joueurs */
	// tableau
	private JTable tJoueurs;
	// model du tableau, pour insert et remove des lignes
	private DefaultTableModel tMJoueurs;


	/* Panels */
	private JPanel infoPane;
	private JPanel jeuPane;
	private JPanel middlePane;
	private JPanel lbPanneau;

	/* Labels */
	private JLabel lbLogin;
	private JLabel lbTempsRestant;
	private JLabel lbListeJoueurs;


  	/* Buttons */
	private JButton bQuitter;
	private JButton bRejouer;

	/* Timer pour rafraichir les listes */
	private javax.swing.Timer timerListes;

	/* Parameters du jeuPane */
	private HashMap<String, Carre> map;
	private int taillePlateau;

	/**
	 * Contructeur de JeuPage
	 * @param  _controller Controlleur Client de la page
	 */
	public JeuPage(ClientController _controller){
		super(_controller);
		try{
			lbLogin = new JLabel("Connecté en tant que : "+getController().getJoueur().getLogin(), SwingConstants.RIGHT);
			lbLogin.setPreferredSize(new Dimension(300, 40));		
			String taille = getController().getTaillePlateau();
			taillePlateau = Integer.parseInt(taille.substring(0, taille.indexOf('x')));
		}catch(RemoteException rE){
			System.out.println(rE);
		}
		//Label Temps restant
		lbTempsRestant = new JLabel("Temps restant", SwingConstants.RIGHT);
		lbTempsRestant.setPreferredSize(new Dimension(300, 40));

		//Bouton Quitter
		bQuitter = new JButton("Quitter");
		bQuitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				quitter();
			}
		});
		//Bouton Rejouer
		bRejouer = new JButton("Rejouer");
		bRejouer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				rejouer();
			}
		});

		//Label Panneau du jeu
		//lbPanneau = new JLabel("Panneau du jeu", SwingConstants.CENTER);
		//lbPanneau.setPreferredSize(new Dimension(300, 40));
		// Label Liste des joueurs
		lbListeJoueurs = new JLabel("Liste des Joueurs", SwingConstants.CENTER);
		lbListeJoueurs.setPreferredSize(new Dimension(300, 40));

		/* Initialisation des tableaux */
		initInfosJoueurs();
		initPanneauJeu();
         
        /* Donner le focus à le pane */

		/* On positionne le tout sur le Panel middle */
		middlePane = new JPanel(new GridBagLayout());
		middlePane.setBackground(getCouleurFond());

		//ajout label 
		middlePane.add(lbLogin, new GBC(8,0,2,1).setFill(GridBagConstraints.NONE).setInsets(0,1,0,5).setAnchor(GridBagConstraints.NORTHEAST));
		middlePane.add(lbTempsRestant, new GBC(9,1,1,1).setFill(GridBagConstraints.NONE).setInsets(0,1,20,5).setAnchor(GridBagConstraints.NORTHEAST));
		middlePane.add(bQuitter, new GBC(0,10,2,1).setFill(GridBagConstraints.NONE).setInsets(10,5,10,1));
		try{
			if(getController().getJoueur().getLogin().equals(getController().getJeuEnCours().getAdmin().getLogin())){
				middlePane.add(bRejouer, new GBC(0,1,2,1).setFill(GridBagConstraints.NONE).setInsets(10,5,10,1));
			}
		}catch(RemoteException rE){
			System.out.println(rE);
		}catch(NotBoundException nBE){
			System.out.println(nBE);
		}
		//jeuPane
		jeuPane.setPreferredSize(new Dimension(600,600));
		jeuPane.setLayout(new GridLayout(taillePlateau, taillePlateau));
		jeuPane.addKeyListener(new JeuKeyListener(_controller));
		
		map = new HashMap<String, Carre>();
		
		for(int i = 1; i <= taillePlateau; i++) {
			for(int j = 1; j <= taillePlateau; j++) {
				Carre c = new Carre(i, j);
				c.setColour(Color.gray);
				c.addKeyListener(new JeuKeyListener(_controller));
				map.put(c.getName(), c);
				jeuPane.add(c);
			}
		}
		middlePane.add(jeuPane, new GBC(0,3,8,6).setFill(GridBagConstraints.BOTH).setInsets(0,5,0,1).setWeight(1.0,1.0));

		//infoPane
		infoPane.setPreferredSize(new Dimension(300,600));
		infoPane.setBorder(new LineBorder(new Color(200,200,200),1,true));
		infoPane.add(lbListeJoueurs,new GBC(0,0,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setAnchor(GridBagConstraints.CENTER));
		middlePane.add(infoPane, new GBC(8,3,2,6).setFill(GridBagConstraints.BOTH).setInsets(0,20,0,5).setWeight(1.0,1.0));
		middlePane.setBorder(getPrincipalBorder());

		/* On positionne les panels sur le Panel principal */
		getPrincipalPane().add(middlePane,new GBC(0,1,1,1).setFill(GridBagConstraints.NONE).setInsets(5,0,5,0).setIpad(20,20));

		this.setVisible(true);
		this.setMinimumSize(this.getSize());

		/* Demarrage du Timer pour le rafraichissement des tableaux */
		startTimer();	
	}

	/**
	 * Initialise le panneau jeu
	 */
	private void initPanneauJeu(){
			jeuPane = new JPanel();
			jeuPane.setFocusable(true);
	}

	/**
	 * Initialise le tableau d'infos jeueurs
	 */
	private void initInfosJoueurs(){
		infoPane = new JPanel();
		infoPane.setFocusable(false);
		tMJoueurs = new DefaultTableModel();
		tJoueurs = new JTable(tMJoueurs);
		tJoueurs.setFocusable(false);


		tMJoueurs.addColumn("Login");
		tJoueurs.getColumn("Login").setPreferredWidth(100);
		tMJoueurs.addColumn("Nb de Bombes");
		tJoueurs.getColumn("Nb de Bombes").setPreferredWidth(100);
		tMJoueurs.addColumn("PV");
		tJoueurs.getColumn("PV").setPreferredWidth(100);
		for(Joueur joueur : getController().getListeJoueursJeu()) {
			try{
				// initialiser les positions des joueurs
				Random randomGenerator = new Random();
				joueur.setPosX(randomGenerator.nextInt(taillePlateau+1)+1);
				joueur.setPosY(randomGenerator.nextInt(taillePlateau+1)+1);

				tMJoueurs.addRow(new Object[]{joueur.getLogin(), joueur.getNbBombes(), joueur.getPv()});
			}catch(RemoteException e){
				tMJoueurs.addRow(new Object[]{"ERREUR", "ERREUR", "ERREUR"});
			}
		}
		tJoueurs.setPreferredSize(new Dimension(240,600));
		JScrollPane jSPJoueurs = new JScrollPane(tJoueurs);
		jSPJoueurs.setPreferredSize(new Dimension(250,600));
		infoPane.add(tJoueurs.getTableHeader(),new GBC(0,1,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setWeight(1.0,1.0));
		infoPane.add(jSPJoueurs,new GBC(0,2,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setWeight(1.0,1.0));
	}

	/**
	 * Met a jour les informations du tableau des joueurs
	 */
	private void updateInfosJoueurs(){
		tMJoueurs.setRowCount(0);
		for(Joueur joueur : getController().getListeJoueursJeu()) {
			try{
				tMJoueurs.addRow(new Object[]{joueur.getLogin(), joueur.getNbBombes(), joueur.getPv()});
			}catch(RemoteException e){
				tMJoueurs.addRow(new Object[]{"ERREUR", "ERREUR", "ERREUR"});
			}
		}
	}



	/**
	 * Deconnexion de l'utilisateur
	 */
	private void quitter(){
		timerListes.stop();
		System.out.println("Je quitte le jeu.");
		getController().quitterJeu();
	}

	private void rejouer(){
		getController().rejouer();
		for(Joueur joueur : getController().getListeJoueursJeu()) {
			try{
				// initialiser les positions des joueurs
				Random randomGenerator = new Random();
				joueur.setPosX(randomGenerator.nextInt(taillePlateau+1)+1);
				joueur.setPosY(randomGenerator.nextInt(taillePlateau+1)+1);
			}catch(RemoteException e){
				System.out.println(e);
			}
		}
	}
	
	/**
	 * Demarrer le Timer pour le rafraichissement des tables
	 */
	private void startTimer(){
		timerListes = new javax.swing.Timer(100, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        		updateInfosJoueurs();
					refresh();
	        }
    	});
    	timerListes.start();
    }

    /**
     * Getter de l'attribut plateau
     * @return Plateau Le plateau du jeu 
     */
    public Plateau getPlateau(){
    	return plateau;
    }
    
    /**
     * Setter de l'attribut plateau
     * @param _plateau Le plateau du jeu
     */
    public void setPlateau(Plateau _plateau){
    	this.plateau = _plateau;
    }

    /**
     * Refresh le jeuPane
     */
    private void refresh(){
		for(Carre c : map.values()){
			c.setColour(Color.gray);
			c.setIcon(null);
		}
		for(Bombe bombe : getController().getListeBombes()){
			try{     
			    bombe.decrementerTimer();
				int x = bombe.getPositionX();
				int y = bombe.getPositionY();                     		
				if(bombe.getTimer()>0){	
					drawBombe(bombe);
				}else if(bombe.getTimer()>-105){
					HashMap<String, Joueur> listeBlesses = getController().getServerController().trouverBlesse(x,y);
					Joueur j = getController().getJoueur();
			        if(listeBlesses.containsKey(j.getLogin())){
			            if(j.getPv()>0){
			            	j.decrementerPtVie();
			            	if(j.getPv()<=0){
			                	getController().getJoueur().setEtat(Etat.MORT);
			                }
			            }
			        }
			        drawExplosion(bombe);
				}else{
					getController().getServerController().supprimerBombe(x,y);
				}
			}catch(RemoteException e){
		    	e.printStackTrace();
            }
        }
		for(Joueur joueur : getController().getListeJoueursJeu()){
			try{
				if(joueur.getEtat().equals(Etat.VIVANT)){
					drawJoueur(joueur);
				}
			}catch(RemoteException rE){
				System.out.println(rE);
			}
		}
    }

    /**
     * Dessinner les joueurs
     */
    private void drawJoueur(Joueur j){
    	try{
    		Color c;
    		if(j.getLogin().equals(getController().getJoueur().getLogin())) {
				c = Color.green;
			}else{
				c = Color.blue;
			}
			int x = j.getPosX();
			int y = j.getPosY();
                        if(x>=taillePlateau){
                                x = taillePlateau;                        
                        }
                        if(y>=taillePlateau){
                                y = taillePlateau;
                        } 
                        if(x<1){
                                x = 1;                        
                        }
                        if(y<1){
                                y = 1;
                        }   
                        j.setPosX(x);
                        j.setPosY(y);                                              
			map.get(""+x+","+y+"").setColour(c);
                      //  Image img = ImageIO.read(getClass().getResource("./ressources/bombe_mini.png"));
         		map.get(""+x+","+y+"").setIcon(new ImageIcon("./ressources/bombeur.png"));               				
			//map.get(""+x+","+y+"").setVisible(true);
		}catch(RemoteException e){
		}
    }

	/**
	 * Dessine une bombe
	 * @param b une bombe
	 */
	private void drawBombe(Bombe b){
        try{
 		int x = b.getPositionX();
		int y = b.getPositionY();
         	map.get(""+x+","+y+"").setIcon(new ImageIcon("./ressources/bombe_mini.png"));              		       
        }catch(RemoteException e){
		}
	}

	/**
	 * Dessine l'explosion
	 * @param b une bombe qui explose
	 */
	private void drawExplosion(Bombe b){
	    try{
	        	Color c;
	        	c = Color.red;
			int x = b.getPositionX();
		int y = b.getPositionY();
		for(int i = 1;i<=taillePlateau;i++){
	     	map.get(""+i+","+y+"").setColour(c);
	     	map.get(""+x+","+i+"").setColour(c);         	
	     	}        		                	   		       
	    }catch(RemoteException e){
	    	e.printStackTrace();
		}	
	}          
}
