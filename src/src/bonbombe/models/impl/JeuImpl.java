package bonbombe.models.impl;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.views.utils.*;
import bonbombe.models.interf.*;

import java.util.*;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class JeuImpl implements Jeu{

	/* Attributs */
	private String nom;
	private Statut statut;
	private HashMap<String,Joueur> listeJoueurs;
	private int nbJoueurs;
	private int nbJoueursMax;
	private String taillePlateau;
	private Admin administrateur;

	public JeuImpl(String _nom, int _nbJoueursMax, Admin _administrateur, String _taillePlateau) throws RemoteException{
		setListeJoueurs(new HashMap<String, Joueur>());
		setNom(_nom);
		setNbJoueurs(1);
		setNbJoueursMax(_nbJoueursMax);
		setAdmin(_administrateur);
		setTaillePlateau(_taillePlateau);
		try{
			ajouterJoueur(_administrateur);
		}catch(JeuPleinException jPE){
			System.out.println("Ceci ne devrait normalement vraiment pas arriver..."+jPE);
		}
		if(nbJoueurs == nbJoueursMax){
			setStatut(Statut.PLEIN);
		}else{
			setStatut(Statut.OUVERT);
		}
	}


	/**
	 * Ajoute un joueur au jeu
	 * @param  _joueur         Login du joueur a ajouter
	 * @throws RemoteException
	 */
	public void ajouterJoueur(Joueur _joueur) throws RemoteException, JeuPleinException{
		if(nbJoueurs < nbJoueursMax){
			listeJoueurs.put(_joueur.getLogin(), _joueur);
			nbJoueurs++;
			if(nbJoueurs == nbJoueursMax){
				setStatut(Statut.PLEIN);
			}
		}else{
			throw new JeuPleinException("Jeu plein");
		}
	}


	/**
	 * Supprime un joueur du jeu
	 * @param  _joueur         Login du joueur a supprimer
	 * @throws RemoteException
	 */
	public void supprimerJoueur(Joueur _joueur) throws RemoteException, JoueurInexistantException{
		if(listeJoueurs.containsKey(_joueur.getLogin())){
			listeJoueurs.remove(_joueur.getLogin());
			nbJoueurs--;
		}else{
			throw new JoueurInexistantException("Joueur non présent dans le jeu");
		}
	}


	/* Setters */
	/**
	 * Setter de l'attribut nom
	 * @param  _nom            Nom du jeu
	 * @throws RemoteException
	 */
	public void setNom(String _nom) throws RemoteException{
		this.nom = _nom;
	}
	/**
	 * Setter de l'attribut statut
	 * @param  _statut         Statut du jeu
	 * @throws RemoteException
	 */
	public void setStatut(Statut _statut) throws RemoteException{
		this.statut = _statut;
	}
	/**
	 * Setter de l'attribut listeJoueurs
	 * @param _listeJoueurs La liste des joueurs
	 */
	public void setListeJoueurs(HashMap<String, Joueur> _listeJoueurs){
		this.listeJoueurs = _listeJoueurs;
	}
	/**
	 * Setter de l'attribut nbJoueurs
	 * @param  _nbJoueurs      Nombre de joueurs dans la partie
	 * @throws RemoteException
	 */
	public void setNbJoueurs(int _nbJoueurs) throws RemoteException{
		if(_nbJoueurs <= getNbJoueursMax()){
			this.nbJoueurs = _nbJoueurs;
		}
	}
	/**
	 * Setter de l'attribut nbJoueursMax
	 * @param  _nbJoueursMax   Nombre de joueurs max dans le jeu
	 * @throws RemoteException
	 */
	public void setNbJoueursMax(int _nbJoueursMax) throws RemoteException{
		this.nbJoueursMax = _nbJoueursMax;
	}
	/**
	 * Setter de l'attribut administrateur
	 * @param  _administrateur Administrateur du jeu
	 * @throws RemoteException
	 */
	public void setAdmin(Admin _administrateur) throws RemoteException{
		this.administrateur = _administrateur;
	}
	/**
	 * Setter de l'attribut taillePlateau
	 * @param  _taillePlateau taille du plateau
	 * @throws RemoteException
	 */
	public void setTaillePlateau(String _taillePlateau) throws RemoteException{
		this.taillePlateau = _taillePlateau;
	}


	/* Getters */
	/**
	 * Getter de l'attribut nom
	 * @return Nom du jeu
	 * @throws RemoteException
	 */
	public String getNom() throws RemoteException{
		return this.nom;
	}
	/**
	 * Getter de l'attribut statut
	 * @return Statut du jeu
	 * @throws RemoteException
	 */
	public Statut getStatut() throws RemoteException{
		return this.statut;
	}
	/**
	 * Getter de l'attribut listeJoueurs
	 * @return La liste des joueurs
	 */
	public HashMap<String, Joueur> getListeJoueurs(){
		return this.listeJoueurs;
	}
	/**
	 * Getter de l'attribut nbJoueurs
	 * @return Nombre de joueurs actuel
	 * @throws RemoteException
	 */
	public int getNbJoueurs() throws RemoteException{
		return this.nbJoueurs;
	}
	/**
	 * Getter de l'attribut nbJoueursMax
	 * @return Nombre de joueurs max dans le jeu
	 * @throws RemoteException
	 */
	public int getNbJoueursMax() throws RemoteException{
		return this.nbJoueursMax;
	}
	/**
	 * Getter de l'attribut administrateur
	 * @return Administrateur du jeu
	 * @throws RemoteException
	 */
	public Admin getAdmin() throws RemoteException{
		return this.administrateur;
	}
	/**
	 * Getter de l'attribut taillePlateau
	 * @return taille du plateau
	 * @throws RemoteException
	 */
	public String getTaillePlateau() throws RemoteException{
		return this.taillePlateau;
	}


}
