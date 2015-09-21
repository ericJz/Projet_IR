package bonbombe.models.interf;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Plateau extends Remote, Serializable{
	/**
	 * décrémente temps de bombe     
	 * @throws RemoteException
	 */ 	
	public void decrementerTpsBombes() throws RemoteException;
	/**
	 * Ajoute un joueur au jeu
	 * @param  joueur       
	 * @throws RemoteException
	 */   	
	public void ajouterJoueur(Joueur joueur) throws RemoteException;
	/**
	 * Supprime un joueur du jeu
	 * @param  joueur       
	 * @throws RemoteException JoueurInexistantException
	 */      		
	public void supprimerJoueur(Joueur joueur) throws RemoteException, JoueurInexistantException;	
	//public void verifier(Joueur joueur,Direction direction,) throws RemoteException;	
	/**
	 * ajoute une bombe au jeu
	 * @param  positionX
	 * @param  positionY    
	 * @throws RemoteException
	 */      	
	public void ajoutUnBombe(int positionX, int positionY) throws RemoteException;
	
}
