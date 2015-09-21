package bonbombe.views;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
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
import java.rmi.RemoteException;

/**
 * Page Welcome : Permet de se connecter à l'application
 */
public class WelcomePage extends Page {
	
	/* Panels */
	private JPanel middlePane;

	/* Labels */
	private JLabel lbLogin;
	private JLabel lbErreur;

	/* TextFields */
	private JTextField tfLogin;


	/* JButtons */
	private JButton bConnexion;

	/**
	 * Contructeur de WelcomePage
	 * @param  _controller Controlleur Client de la page
	 * @return             WelcomePage
	 */
	public WelcomePage(ClientController _controller){
		super(_controller);
		
		/* Paramétrage des labels, textfields, buttons... */
		lbLogin = new JLabel("Choisissez un login : ", SwingConstants.CENTER);

		lbErreur= new JLabel("",SwingConstants.CENTER);
		lbErreur.setPreferredSize(new Dimension(110, 20));
		lbErreur.setForeground(Color.RED);
		
		tfLogin = new JTextField();
		tfLogin.setPreferredSize(new Dimension(150, 30));
		tfLogin.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					connexion(tfLogin.getText());
				}
			}
			public void keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){}
		});

		bConnexion= new JButton("Connexion");
		bConnexion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				connexion(tfLogin.getText());
			}
		});

		/* On positionne le tout sur le Panel middle */
		middlePane = new JPanel(new GridBagLayout());
		middlePane.setBackground(this.getCouleurFond());
		middlePane.add(lbLogin, new GBC(0,0,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(tfLogin, new GBC(1,0,1,1).setFill(GridBagConstraints.BOTH).setInsets(0,1,0,1));
		middlePane.add(lbErreur, new GBC(0,1,2,1).setFill(GridBagConstraints.BOTH).setIpad(50,0));
		middlePane.add(bConnexion, new GBC(0,2,2,1).setFill(GridBagConstraints.BOTH).setInsets(5,0,0,0));


		/* On positionne les panels sur le Panel principal */
		getPrincipalPane().add(middlePane,new GBC(0,1,1,1).setFill(GridBagConstraints.BOTH).setInsets(5,0,5,0).setIpad(20,20));


		this.setVisible(true);
		this.setMinimumSize(this.getSize());
	}


	/**
	 * Connexion à l'application
	 * @param _login Login de l'utilisateur
	 */
	private void connexion(String _login){
		try{
			getController().connexion(_login);
			lbErreur.setText("");
		}catch(LoginExistantException lEE){
			lbErreur.setText(lEE.getMessage());
			lbErreur.updateUI();
		}catch(LoginVideException lVE){
			lbErreur.setText(lVE.getMessage());
			lbErreur.updateUI();
		}catch(RemoteException rE){
			lbErreur.setText("Erreur de connexion");
		}
	}
}