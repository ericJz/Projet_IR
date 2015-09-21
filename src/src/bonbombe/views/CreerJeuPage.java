package bonbombe.views;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.controllers.impl.ClientController;
import bonbombe.models.interf.*;

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
public class CreerJeuPage extends Page {

	/* Panels */
	private JPanel middlePane;
	private JPanel tJeuxPane;
	private JPanel tJoueursPane;

	/* Labels */
	private JLabel lbLogin;
	private JLabel lbNom;
	private JLabel lbNbJoueursMax;
	private JLabel lbTaille;
	private JLabel lbErreurNb;
	private JLabel lbErreurNom;

	/* TextFields */
	private JTextField tNom;
	private JTextField tNbJoueursMax;

	/* Buttons */
	private JButton bRetour;
	private JButton bDeconnexion;
	private JButton bValider;

	/* ComboBox */
	private JComboBox cbTaille;

	/**
	 * Contructeur de MenuPage
	 * @param  _controller Controlleur Client de la page
	 */
	public CreerJeuPage(ClientController _controller){
		super(_controller);
		
		/* Paramétrage des labels, textfields, buttons... */
		try{
			lbLogin = new JLabel("Connecté en tant que : "+getController().getJoueur().getLogin(), SwingConstants.RIGHT);
			lbLogin.setPreferredSize(new Dimension(300, 40));
		}catch(RemoteException rE){
			System.out.println(rE);
		}
		bDeconnexion = new JButton("Déconnexion");
		bDeconnexion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deconnexion();
			}
		});

		bRetour = new JButton("Retour");
		bRetour.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				retour();
			}
		});

		bValider = new JButton("Valider");
		bValider.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				valider();
			}
		});


		lbTaille = new JLabel("Taille du plateau :", SwingConstants.RIGHT);
		lbTaille.setPreferredSize(new Dimension(300, 40));
		
		cbTaille = new JComboBox();
	 	cbTaille.addItem("10x10");
	 	cbTaille.addItem("15x15");
	 	cbTaille.addItem("20x20");
	 	cbTaille.addItem("25x25");

	 	lbNom = new JLabel("Nom du jeu :", SwingConstants.RIGHT);
		lbNom.setPreferredSize(new Dimension(300, 40));

	 	tNom = new JTextField("");
		tNom.setPreferredSize(new Dimension(300, 40));


		lbNbJoueursMax = new JLabel("Nombre de joueurs maximum :", SwingConstants.RIGHT);
		lbNbJoueursMax.setPreferredSize(new Dimension(300, 40));

		tNbJoueursMax = new JTextField("2-4");
		tNbJoueursMax.setPreferredSize(new Dimension(300, 40));
		tNbJoueursMax.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				tNbJoueursMax.selectAll();
			}
			public void focusLost(FocusEvent e){
				// Empty
			}
		});

		lbErreurNb= new JLabel("",SwingConstants.CENTER);
		lbErreurNb.setPreferredSize(new Dimension(110, 20));
		lbErreurNb.setForeground(Color.RED);

		lbErreurNom= new JLabel("",SwingConstants.CENTER);
		lbErreurNom.setPreferredSize(new Dimension(110, 20));
		lbErreurNom.setForeground(Color.RED);

		/* On positionne le tout sur le Panel middle */
		middlePane = new JPanel(new GridBagLayout());
		middlePane.setBackground(this.getCouleurFond());

		middlePane.add(lbLogin, new GBC(5,0,2,1).setFill(GridBagConstraints.NONE).setInsets(0,1,0,5).setAnchor(GridBagConstraints.NORTHEAST));
		middlePane.add(bDeconnexion, new GBC(6,1,1,1).setFill(GridBagConstraints.NONE).setInsets(0,1,20,5).setAnchor(GridBagConstraints.NORTHEAST));

		middlePane.add(bRetour, new GBC(0,5,2,1).setFill(GridBagConstraints.BOTH).setInsets(10,5,10,1));

		middlePane.add(bValider, new GBC(6,5,1,1).setFill(GridBagConstraints.NONE).setInsets(10,1,10,5).setAnchor(GridBagConstraints.EAST));

		middlePane.add(lbNom, new GBC(1,2,2,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(tNom, new GBC(3,2,2,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(lbErreurNom, new GBC(5,2,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(lbNbJoueursMax, new GBC(1,3,2,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(tNbJoueursMax, new GBC(3,3,2,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(lbErreurNb, new GBC(5,3,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(lbTaille, new GBC(1,4,2,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(cbTaille, new GBC(3,4,2,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));


		// /* On positionne les panels sur le Panel principal */
		getPrincipalPane().add(middlePane,new GBC(0,1,1,1).setFill(GridBagConstraints.NONE).setInsets(5,0,5,0).setIpad(20,20));

		this.setVisible(true);
		this.setMinimumSize(this.getSize());
	}


	/**
	 * Retour au menu
	 */
	private void retour(){
		getController().creerPage(NomPage.MENU);
	}

	/**
	 * Deconnexion de l'utilisateur
	 */
	private void deconnexion(){
		getController().deconnexion();
	}

	/**
	 * Valide la création du Jeu
	 */
	private void valider(){
		try{
			getController().creerJeu(tNom.getText(),tNbJoueursMax.getText(),(String)cbTaille.getSelectedItem());
		}catch(NumberFormatException nFE){
			lbErreurNb.setText("Non-conforme");
			lbErreurNb.updateUI();
		}catch(NomJeuExistantException nJEE){
			lbErreurNom.setText("Nom déjà pris");
			lbErreurNom.updateUI();
		}
	}
}