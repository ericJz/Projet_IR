package bonbombe.models.impl;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.models.interf.*;

import java.io.Serializable;
import java.rmi.RemoteException;


public class JoueurImpl implements Joueur{
	/* Atributs */
	private String login;
	private Statut statut;
	private Etat etat;
	private int pv;
	private int nbBombes;
	private int posX;
	private int posY;
	private Plateau plateau;

	/**
	 * Contructeur de Joueur 
	 * @return Joueur
	 */
	public JoueurImpl(String _login) throws RemoteException{
		setLogin(_login);
		setStatut(Statut.PASENJEU);
		setEtat(Etat.VIVANT);
		this.pv = 3;
	}
	/**
	 * déposer une bombe
	 * @throws RemoteException
	 */
	public void deposerBombe() throws RemoteException{
		this.getPlateau().ajoutUnBombe(this.getPosX(),this.getPosY());
	}
	/**
	 * décrémenter le point de vie
	 * @throws RemoteException
	 */
	public void decrementerPtVie() throws RemoteException{
		this.pv-=1;
	}
	/**
	 * deplacer le joueur
	 * @param  direction         Direction du deplacement	 
	 * @throws RemoteException
	 */	
	public void deplacer(Direction direction) throws RemoteException{
        switch(direction){
                case MONTER :
                        this.monter();
                        break;
                case DESCENDRE :
                        this.descendre();
                        break;
                case GAUCHE :
                        this.gauche();
                        break;
				case DROITE :
                        this.droite();
                        break;	                        
        }
	}	
	/**
	 * monter le joueur
	 * @throws RemoteException
	 */	
	public void monter() throws RemoteException{
	        this.posY+=1;
	}
	/**
	 * descendre le joueur
	 * @throws RemoteException
	 */	
	public void descendre() throws RemoteException{
	        this.posY-=1;
	}	
	/**
	 * déplacer à gauche le joueur
	 * @throws RemoteException
	 */	
	public void gauche() throws RemoteException{
	        this.posX-=1;
	}			
	/**
	 * déplacer à droite le joueur
	 * @throws RemoteException
	 */	
	public void droite() throws RemoteException{
	        this.posX+=1;
	}	
	/* Setters */
	/**
	 * Setter de login
	 * @param _login Le login du joueur
	 * @throws RemoteException
	 */
	public void setLogin(String _login) throws RemoteException{
		this.login = _login;
	}
	/**
	 * Setter de statut
	 * @param  _statut         Statut du joueur
	 * @throws RemoteException
	 */
	public void setStatut(Statut _statut) throws RemoteException{
		this.statut = _statut;
	}
	/**
	 * Setter de pv
	 * @param  _pv             Points de vies du joueur
	 * @throws RemoteException
	 */
	public void setPv(int _pv) throws RemoteException{
		this.pv = _pv;
	}
	/**
	 * Setter de nbBombes
	 * @param  _nbBombes       Nombre de bombes du joueur
	 * @throws RemoteException
	 */
	public void setNbBombes(int _nbBombes) throws RemoteException{
		this.nbBombes = _nbBombes;
	}
	/**
	 * Setter de posX
	 * @param  _posX           Position en X du joueur
	 * @throws RemoteException
	 */
	public void setPosX(int _posX) throws RemoteException{
		this.posX = _posX;
	}
	/**
	 * Setter de posY
	 * @param  _posY           Position en Y du joueur
	 * @throws RemoteException
	 */
	public void setPosY(int _posY) throws RemoteException{
		this.posY = _posY;
	}
	/**
	 * Setter de plateau
	 * @param  plateau         Plateau du joueur
	 * @throws RemoteException
	 */
	public void setPlateau(Plateau plateau) throws RemoteException{
		this.plateau = plateau;
	}	
	/**
	 * Getter de l'attribut etat
	 * @return Etat Etat du Joueur 
	 */
	public Etat getEtat() throws RemoteException{
		return etat;
	}

	/* Getters */
	/**
	 * Setter de l'attribut etat
	 * @param _etat Etat du Joueur
	 */
	public void setEtat(Etat _etat) throws RemoteException{
		this.etat = _etat;
	}
	/**
	 * Getter de login
	 * @return Le login du joueur
	 * @throws RemoteException	 
	 */
	public String getLogin() throws RemoteException{
		return this.login;
	}
	/**
	 * Getter de statut
	 * @return Statut du joueur
	 * @throws RemoteException
	 */
	public Statut getStatut() throws RemoteException{
		return this.statut;
	}
	/**
	 * Getter de pv
	 * @return Points de vies du joueur
	 * @throws RemoteException
	 */
	public int getPv() throws RemoteException{
		return this.pv;
	}
	/**
	 * Getter de nbBombes
	 * @return Nombre de bombes du joueur
	 * @throws RemoteException
	 */
	public int getNbBombes() throws RemoteException{
		return this.nbBombes;
	}
	/**
	 * Getter de posX
	 * @return Poisition en X du joueur
	 * @throws RemoteException
	 */
	public int getPosX() throws RemoteException{
		return this.posX;
	}
	/**
	 * Getter de posY
	 * @return Position en Y du joueur
	 * @throws RemoteException
	 */
	public int getPosY()  throws RemoteException{
		return this.posY;
	}
	/**
	 * Getter de plateau
	 * @return plateau du joueur
	 * @throws RemoteException
	 */
	public Plateau getPlateau()  throws RemoteException{
		return this.plateau;
	}
	
}
