package bonbombe.views;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.views.utils.*;
import bonbombe.controllers.impl.ClientController;
import bonbombe.models.interf.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.color.*;
import java.awt.Graphics;
import java.util.*;

public abstract class Page extends JPanel{
	/* Attributs */

	private ClientController controller;

		/* Panels */
	private JPanel principalPane;
	private JPanel titlePane;


		/* Labels */
	private JLabel lbIcone1;
	private JLabel lbIcone2;
	private JLabel lbTitre;


		/* Colors */
	private Color couleurFond;

		/* Borders */
	private LineBorder principalBorder;


	/* Constructeur */
	public Page(ClientController _controller){
		this.setController(_controller);

		/* Initialisation de la couleur du fond */
		this.setCouleurFond(187,187,187);

		/* Initialisation du border */
		this.setPrincipalBorder(new LineBorder(new Color(60,60,60),1,true));

		/* Initialisation du Panel principal */
		this.setPrincipalPane(new JPanel(new GridBagLayout()));
		this.add(this.principalPane, BorderLayout.CENTER);
		// this.principalPane.setBackground(this.getCouleurFond());

		/* Création du Panel Title présent sur toutes les pages */
		this.lbIcone1 = new JLabel(new ImageIcon("./ressources/bombe_mini.png"));
		this.lbIcone1.setPreferredSize(new Dimension(25, 26));
		this.lbIcone2 = new JLabel(new ImageIcon("./ressources/bombe_mini.png"));
		this.lbIcone2.setPreferredSize(new Dimension(25, 26));

		this.lbTitre = new JLabel("BonBomb ", SwingConstants.CENTER);

		this.titlePane = new JPanel(new GridBagLayout());
		this.titlePane.add(lbIcone1, new GBC(0,0).setFill(GridBagConstraints.BOTH));
		this.titlePane.add(lbTitre, new GBC(1,0).setFill(GridBagConstraints.BOTH).setInsets(0,5,0,5));
		this.titlePane.add(lbIcone2, new GBC(2,0).setFill(GridBagConstraints.BOTH));
		this.titlePane.setBorder(getPrincipalBorder());


		/* Ajout du Panel title au Panel principal */
		this.principalPane.add(this.titlePane, new GBC(0,0,1,1).setFill(GridBagConstraints.BOTH).setIpad(20,80));
	}



	/* Getter et Setter controller */
	public ClientController getController(){
		return this.controller;
	}
	public void setController(ClientController _controller){
		this.controller = _controller;
	}



	/* Getter et Setter principalPane */
	protected JPanel getPrincipalPane(){
		return this.principalPane;
	}
	protected void setPrincipalPane(JPanel _p){
		this.principalPane = _p;
	}

	
	/* Getter et Setter colorFond */
	protected Color getCouleurFond(){
		return this.couleurFond;
	}
	protected void setCouleurFond(int _r, int _g, int _b){
		this.couleurFond = new Color(_r,_g,_b);
	}

	/* Getter et Setter principalBorder */
	/**
	 * Getter de l'attribut principalBorder
	 * @return LineBorder Bord type ligne 
	 */
	public LineBorder getPrincipalBorder(){
		return principalBorder;
	}
	
	/**
	 * Setter de l'attribut principalBorder
	 * @param _principalBorder Bord type ligne
	 */
	public void setPrincipalBorder(LineBorder _principalBorder){
		this.principalBorder = _principalBorder;
	}

}