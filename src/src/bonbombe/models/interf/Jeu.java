package bonbombe.models.interf;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Jeu extends Remote, Serializable{
	
	/**
	 * Ajoute un joueur au jeu
	 * @param  _joueur         Login du joueur a ajouter
	 * @throws RemoteException
	 */
	public void ajouterJoueur(Joueur _joueur) throws RemoteException, JeuPleinException;


	/**
	 * Supprime un joueur du jeu
	 * @param  _joueur         Login du joueur a supprimer
	 * @throws RemoteException
	 */
	public void supprimerJoueur(Joueur _joueur) throws RemoteException, JoueurInexistantException;


	/* Setters */
	/**
	 * Setter de l'attribut nom
	 * @param  _nom            Nom du jeu
	 * @throws RemoteException
	 */
	public void setNom(String _nom) throws RemoteException;
	/**
	 * Setter de l'attribut statut
	 * @param  _statut         Statut du jeu
	 * @throws RemoteException
	 */
	public void setStatut(Statut _statut) throws RemoteException;

	/**
	 * Setter de l'attribut listeJoueurs
	 * @param _listeJoueurs La liste des joueurs
	 */
	public void setListeJoueurs(HashMap<String, Joueur> _listeJoueurs) throws RemoteException;
	/**
	 * Setter de l'attribut nbJoueurs
	 * @param  _nbJoueurs      Nombre de joueurs dans la partie
	 * @throws RemoteException
	 */
	public void setNbJoueurs(int _nbJoueurs) throws RemoteException;
	/**
	 * Setter de l'attribut nbJoueursMax
	 * @param  _nbJoueursMax   Nombre de joueurs max dans le jeu
	 * @throws RemoteException
	 */
	public void setNbJoueursMax(int _nbJoueursMax) throws RemoteException;
	/**
	 * Setter de l'attribut administrateur
	 * @param  _administrateur Administrateur du jeu
	 * @throws RemoteException
	 */
	public void setAdmin(Admin _administrateur) throws RemoteException;
	/**
	 * Setter de l'attribut taillePlateau
	 * @param  _taillePlateau taille du plateau
	 * @throws RemoteException
	 */
	public void setTaillePlateau(String _taillePlateau) throws RemoteException;



	/* Getters */
	/**
	 * Getter de l'attribut nom
	 * @return Nom du jeu
	 * @throws RemoteException
	 */
	public String getNom() throws RemoteException;
	/**
	 * Getter de l'attribut statut
	 * @return Statut du jeu
	 * @throws RemoteException
	 */
	public Statut getStatut() throws RemoteException;
	/**
	 * Getter de l'attribut listeJoueurs
	 * @return La liste des joueurs
	 */
	public HashMap<String, Joueur> getListeJoueurs() throws RemoteException;
	/**
	 * Getter de l'attribut nbJoueurs
	 * @return Nombre de joueurs actuel
	 * @throws RemoteException
	 */
	public int getNbJoueurs() throws RemoteException;
	/**
	 * Getter de l'attribut nbJoueursMax
	 * @return Nombre de joueurs max dans le jeu
	 * @throws RemoteException
	 */
	public int getNbJoueursMax() throws RemoteException;
	/**
	 * Getter de l'attribut administrateur
	 * @return Administrateur du jeu
	 * @throws RemoteException
	 */
	public Admin getAdmin() throws RemoteException;
	/**
	 * Getter de l'attribut taillePlateau
	 * @return taille du plateau
	 * @throws RemoteException
	 */
	public String getTaillePlateau() throws RemoteException;

}
