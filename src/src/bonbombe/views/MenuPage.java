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

/**
 * Page Welcome : Permet de se connecter à l'application
 */
public class MenuPage extends Page {

	/* Tableau de Jeux */
	// tableau
	private JTable tJeux;
	// model du tableau, pour insert et remove des lignes
	private DefaultTableModel tMJeux;

	/* Tableau de Joueurs */
	// tableau
	private JTable tJoueurs;
	// model du tableau, pour insert et remove des lignes
	private DefaultTableModel tMJoueurs;
	
	/* Panels */
	private JPanel middlePane;
	private JPanel tJeuxPane;
	private JPanel tJoueursPane;

	/* Labels */
	private JLabel lbLogin;
	private JLabel lbTJoueurs;
	private JLabel lbTJeux;
	private JLabel lbErreur;

	/* Buttons */
	private JButton bCreerJeu;
	private JButton bDeconnexion;

	/* Timer pour rafraichir les listes */
	private javax.swing.Timer timerListes;

	/**
	 * Contructeur de MenuPage
	 * @param  _controller Controlleur Client de la page
	 */
	public MenuPage(ClientController _controller){
		super(_controller);
		
		/* Paramétrage des labels, textfields, buttons... */
		try{
			lbLogin = new JLabel("Connecté en tant que : "+getController().getJoueur().getLogin(), SwingConstants.RIGHT);
			lbLogin.setPreferredSize(new Dimension(300, 40));
		}catch(RemoteException rE){
			System.out.println(rE);
		}
		bDeconnexion = new JButton("Déconnexion");
		bDeconnexion.setHorizontalAlignment(SwingConstants.RIGHT);
		bDeconnexion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deconnexion();
			}
		});

		bCreerJeu = new JButton("Créer un jeu");
		bCreerJeu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				creerJeu();
			}
		});

		lbTJeux = new JLabel("Liste des Jeux", SwingConstants.CENTER);
		lbTJeux.setPreferredSize(new Dimension(300, 40));

		lbTJoueurs = new JLabel("Liste des Joueurs", SwingConstants.CENTER);
		lbTJoueurs.setPreferredSize(new Dimension(300, 40));

		lbErreur= new JLabel("",SwingConstants.CENTER);
		lbErreur.setPreferredSize(new Dimension(110, 20));
		lbErreur.setForeground(Color.RED);

		/* Initialisation des tableaux */
		initTableauJoueurs();
		initTableauJeux();

		/* On positionne le tout sur le Panel middle */
		middlePane = new JPanel(new GridBagLayout());
		middlePane.setBackground(getCouleurFond());

		middlePane.add(lbLogin, new GBC(8,0,2,1).setFill(GridBagConstraints.NONE).setInsets(0,1,0,5).setAnchor(GridBagConstraints.NORTHEAST));
		middlePane.add(bDeconnexion, new GBC(9,1,1,1).setFill(GridBagConstraints.NONE).setInsets(0,1,20,5).setAnchor(GridBagConstraints.NORTHEAST));

		
		middlePane.add(lbErreur, new GBC(4,2,2,1).setFill(GridBagConstraints.BOTH).setIpad(50,0));

		tJeuxPane.setPreferredSize(new Dimension(600,250));
		tJeuxPane.setBorder(new LineBorder(new Color(200,200,200),1,true));
		middlePane.add(tJeuxPane, new GBC(0,3,8,6).setFill(GridBagConstraints.BOTH).setInsets(0,5,0,1).setWeight(1.0,1.0));

		tJoueursPane.setPreferredSize(new Dimension(300,250));
		tJoueursPane.setBorder(new LineBorder(new Color(200,200,200),1,true));
		middlePane.add(tJoueursPane,new GBC(8,3,2,6).setFill(GridBagConstraints.BOTH).setInsets(0,20,0,5).setWeight(1.0,1.0));


		middlePane.add(bCreerJeu, new GBC(0,10,2,1).setFill(GridBagConstraints.NONE).setInsets(10,5,10,1));

		middlePane.setBorder(getPrincipalBorder());
		// /* On positionne les panels sur le Panel principal */
		getPrincipalPane().add(middlePane,new GBC(0,1,1,1).setFill(GridBagConstraints.NONE).setInsets(5,0,5,0).setIpad(20,20));

		this.setVisible(true);
		this.setMinimumSize(this.getSize());

		/* Demarrage du Timer pour le rafraichissement des tableaux */
		startTimer();
	}


	/**
	 * Initialise le tableau des joueurs
	 */
	private void initTableauJoueurs(){
		tJoueursPane = new JPanel();
		tMJoueurs = new DefaultTableModel();
		tJoueurs = new JTable(tMJoueurs);

		tMJoueurs.addColumn("Login");
		tJoueurs.getColumn("Login").setPreferredWidth(100);
		tMJoueurs.addColumn("Statut");
		tJoueurs.getColumn("Statut").setPreferredWidth(70);

		for(Joueur joueur : getController().getListeJoueurs()) {
			try{
				tMJoueurs.addRow(new Object[]{joueur.getLogin(), joueur.getStatut()});
			}catch(RemoteException e){
				tMJoueurs.addRow(new Object[]{"ERREUR", "ERREUR"});
			}
		}
		tJoueurs.setPreferredSize(new Dimension(170,200));
		JScrollPane jSPJoueurs = new JScrollPane(tJoueurs);
		jSPJoueurs.setPreferredSize(new Dimension(250,200));
		tJoueursPane.add(lbTJoueurs,new GBC(0,0,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setAnchor(GridBagConstraints.CENTER));
		tJoueursPane.add(tJoueurs.getTableHeader(),new GBC(0,1,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setWeight(1.0,1.0));
		tJoueursPane.add(jSPJoueurs,new GBC(0,2,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setWeight(1.0,1.0));
	}

	/**
	 * Rafraichissement du tableau des joueurs
	 */
	private void refreshTableauJoueurs(){
		tMJoueurs.setRowCount(0);
		for(Joueur joueur : getController().getListeJoueurs()) {
			try{
				tMJoueurs.addRow(new Object[]{joueur.getLogin(), joueur.getStatut()});
			}catch(RemoteException e){
				tMJoueurs.addRow(new Object[]{"ERREUR", "ERREUR"});
			}
		}
	}


	/**
	 * Initialise le tableau des jeux
	 */
	private void initTableauJeux(){
		tJeuxPane = new JPanel();
		tMJeux = new DefaultTableModel();
		tJeux = new JTable(tMJeux);

		tMJeux.addColumn("Nom");
		tJeux.getColumn("Nom").setPreferredWidth(100);
		tMJeux.addColumn("Joueurs");
		tJeux.getColumn("Joueurs").setPreferredWidth(70);
		tMJeux.addColumn("Admin");
		tJeux.getColumn("Admin").setPreferredWidth(100);
		tMJeux.addColumn("Statut");
		tJeux.getColumn("Statut").setPreferredWidth(70);
		tMJeux.addColumn("");
		tJeux.getColumn("").setPreferredWidth(70);
		/*tMJeux.addColumn("");
		tJeux.getColumn("").setPreferredWidth(70);*/		

		for(Jeu jeu : getController().getListeJeux()) {
			try{
				tMJeux.addRow(new Object[]{jeu.getNom(), jeu.getNbJoueurs()+"/"+jeu.getNbJoueursMax(), jeu.getAdmin().getLogin(), jeu.getStatut(), "Rejoindre","Lancer"});
			}catch(RemoteException e){
				tMJeux.addRow(new Object[]{"ERREUR", "ERREUR", "ERREUR", "ERREUR"});
			}
		}

		/* Ajouter un bouton rejoindre sur le tableau */
		Action rejoindre = new AbstractAction(){
		    public void actionPerformed(ActionEvent e){
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        String nomJeu = (String)((DefaultTableModel)table.getModel()).getValueAt(modelRow,0) ;
		        rejoindre(nomJeu);
		    }
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(tJeux, rejoindre, 4);
		
		/* Ajouter un bouton lancer sur le tableau */
		/*Action lancer = new AbstractAction(){
		    public void actionPerformed(ActionEvent e){
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        String nomJeu = (String)((DefaultTableModel)table.getModel()).getValueAt(modelRow,0) ;
		        lancer(nomJeu);
		    }
		};
		
		ButtonColumn buttonColumn2 = new ButtonColumn(tJeux, lancer, 5);	*/	

		tJeux.setPreferredSize(new Dimension(410,200));
		JScrollPane jSPJeux = new JScrollPane(tJeux);
		jSPJeux.setPreferredSize(new Dimension(500,200));
		tJeuxPane.add(lbTJeux,new GBC(0,0,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setAnchor(GridBagConstraints.CENTER));
		tJeuxPane.add(tJeux.getTableHeader(),new GBC(0,1,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setWeight(1.0,1.0));
		tJeuxPane.add(jSPJeux,new GBC(0,2,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1).setWeight(1.0,1.0));
	}

	/**
	 * Rafraichissement du tableau des jeux
	 */
	private void refreshTableauJeux(){
		tMJeux.setRowCount(0);
		for(Jeu jeu : getController().getListeJeux()) {
			try{
				tMJeux.addRow(new Object[]{jeu.getNom(), jeu.getNbJoueurs()+"/"+jeu.getNbJoueursMax(), jeu.getAdmin().getLogin(), jeu.getStatut(), "Rejoindre", "Lancer"});
			}catch(RemoteException e){
				tMJeux.addRow(new Object[]{"ERREUR", "ERREUR", "ERREUR", "ERREUR"});
			}
		}
	}


	/**
	 * Rejoindre un jeu
	 * @param _row Index du jeu dans le tableau
	 */
	private void rejoindre(String _nomJeu){
		try{
			getController().rejoindreJeu(_nomJeu);
			System.out.println("Je rejoins le jeu "+_nomJeu);
			timerListes.stop();
		}catch(JeuPleinException jPE){
			lbErreur.setText(jPE.getMessage());
			lbErreur.updateUI();
		}
	}

	/**
	 * Lancer un jeu
	 * @param _row Index du jeu dans le tableau
	 */
	private void lancer(String _nomJeu){
			getController().lancerJeu(_nomJeu);
			System.out.println("Je lance le jeu "+_nomJeu);
			timerListes.stop();
	}

	/**
	 * Creer un jeu
	 */
	private void creerJeu(){
		timerListes.stop();
		getController().creerPage(NomPage.CREERJEU);
	}
	
	/**
	 * lanver un jeu
	 */
	private void lancerJeu(String _nomJeu){
		timerListes.stop();		
		System.out.println("Je lances le jeu "+_nomJeu);
		getController().creerPage(NomPage.JEU);
	}

	/**
	 * Deconnexion de l'utilisateur
	 */
	private void deconnexion(){
		timerListes.stop();
		getController().deconnexion();
	}

	/**
	 * Demarrer le Timer pour le rafraichissement des tables
	 */
	private void startTimer(){
		timerListes = new javax.swing.Timer(1000, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	refreshTableauJoueurs();
	        	refreshTableauJeux();
	        }
    	});
    	timerListes.start();
    }
}
