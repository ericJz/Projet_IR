package bonbombe.views.utils;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.controllers.impl.ClientController;
import bonbombe.models.interf.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

public class JeuKeyListener implements KeyListener {
	private ClientController controller;

	public JeuKeyListener(ClientController _controller){
		this.setController(_controller);
	}

	/* Getter et Setter controller */
	public ClientController getController(){
		return this.controller;
	}
	public void setController(ClientController _controller){
		this.controller = _controller;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		//do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
                try{	
		Joueur moi = controller.getJoueur();
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moi.deplacer(Direction.MONTER);			
			//move(Direction.DROITE);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			moi.deplacer(Direction.DROITE);				
			//move(Direction.DESCENDRE);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			moi.deplacer(Direction.DESCENDRE);							
			//move(Direction.GAUCHE);
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			moi.deplacer(Direction.GAUCHE);	
			//move(Direction.MONTER);
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            //moi.deposerBombe();
	        controller.deposerBombe(moi.getPosX(),moi.getPosY());        
		}		
		else {
			//do nothing
		}
	}
		catch (RemoteException ex) {
			ex.printStackTrace();
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		//do nothing		
	}
/*
	private void move(Direction d) {
		try {
			Joueur moi = controller.getJoueur();
			moi.deplacer(d);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}*/
	
	
}
