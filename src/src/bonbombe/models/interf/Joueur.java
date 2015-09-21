package bonbombe.models.interf;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Joueur extends Remote, Serializable{
	/**
	 * déposer une bombe
	 * @throws RemoteException
	 */
	 public void deposerBombe()	throws RemoteException;
	/**
	 * décrémenter le point de vie
	 * @throws RemoteException
	 */
	public void decrementerPtVie() throws RemoteException;
	/**
	 * deplacer le joueur
	 * @param  direction         Direction du deplacement	 
	 * @throws RemoteException
	 */	
	public void deplacer(Direction direction) throws RemoteException;		
	/**
	 * monter le joueur
	 * @throws RemoteException
	 */	
	public void monter() throws RemoteException;	
	/**
	 * descendre le joueur
	 * @throws RemoteException
	 */	
	public void descendre() throws RemoteException;	
	/**
	 * déplacer à gauche le joueur
	 * @throws RemoteException
	 */	
	public void gauche() throws RemoteException;
	/**
	 * déplacer à droite le joueur
	 * @throws RemoteException
	 */	
	public void droite() throws RemoteException;				
	/* Setters */
	/**
	 * Setter de login
	 * @param  _login          Login du joueur
	 * @throws RemoteException 
	 */
	public void setLogin(String _login) throws RemoteException;
	/**
	 * Setter de statut
	 * @param  _statut         Statut du joueur
	 * @throws RemoteException
	 */
	public void setStatut(Statut _statut) throws RemoteException;
	/**
	 * Setter de pv
	 * @param  _pv             Points de vies du joueur
	 * @throws RemoteException
	 */
	public void setPv(int _pv) throws RemoteException;
	/**
	 * Setter de nbBombes
	 * @param  _nbBombes       Nombre de bombes du joueur
	 * @throws RemoteException
	 */
	public void setNbBombes(int _nbBombes) throws RemoteException;
	/**
	 * Setter de posX
	 * @param  _posX           Position en X du joueur
	 * @throws RemoteException
	 */
	public void setPosX(int _posX) throws RemoteException;
	/**
	 * Setter de posY
	 * @param  _posY           Position en Y du joueur
	 * @throws RemoteException
	 */
	public void setPosY(int _posY) throws RemoteException;
	/**
	 * Setter de plateau
	 * @param  plateau         Plateau du joueur
	 * @throws RemoteException
	 */
	public void setPlateau(Plateau plateau) throws RemoteException;
	/**
	 * Setter de l'attribut etat
	 * @param _etat Etat du Joueur
	 * @throws RemoteException
	 */
	public void setEtat(Etat _etat) throws RemoteException;

	/* Getters */
	/**
	 * Getter de l'attribut etat
	 * @return Etat Etat du Joueur 
	 * @throws RemoteException
	 */
	public Etat getEtat() throws RemoteException;
	/**
	 * Getter de login
	 * @return Login du joueur
	 * @throws RemoteException 
	 */
	public String getLogin() throws RemoteException;
	/**
	 * Getter de statut
	 * @return Statut du joueur
	 * @throws RemoteException
	 */
	public Statut getStatut() throws RemoteException;
	/**
	 * Getter de pv
	 * @return Points de vies du joueur
	 * @throws RemoteException
	 */
	public int getPv() throws RemoteException;
	/**
	 * Getter de nbBombes
	 * @return Nombre de bombes du joueur
	 * @throws RemoteException
	 */
	public int getNbBombes() throws RemoteException;
	/**
	 * Getter de posX
	 * @return Poisition en X du joueur
	 * @throws RemoteException
	 */
	public int getPosX() throws RemoteException;
	/**
	 * Getter de posY
	 * @return Position en Y du joueur
	 * @throws RemoteException
	 */
	public int getPosY()  throws RemoteException;
	/**
	 * Getter de plateau
	 * @return plateau du joueur
	 * @throws RemoteException
	 */	
	public Plateau getPlateau()  throws RemoteException;
}
