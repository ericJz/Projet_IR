package bonbombe.controllers.interf;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.models.impl.*;
import bonbombe.models.interf.*;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

/**
 * Interface ServerController
 *
 * @see Remote
 */
public interface ServerController extends Remote{
	/* Les methodes doivent toutes propager l'exception RemoteException */

	/**
	 * Ajout d'un utilisateur au controller
	 * @param  _login          Login de l'utilisateur
	 * @throws RemoteException
	 */
	public void ajouterJoueur(String _login) throws LoginExistantException, RemoteException, LoginVideException;
	
	public void ajouterBombe(int x, int y) throws RemoteException, BombeExistantException;
	
	public HashMap<String, Joueur> trouverBlesse(int posX, int posY) throws RemoteException;

	/**
	 * Supprime un joueur de l'application
	 * @param  _login                    Login du joueur a supprimer
	 * @throws JoueurInexistantException [description]
	 * @throws RemoteException           [description]
	 */
	public void supprimerJoueur(String _login) throws JoueurInexistantException, RemoteException, NotBoundException;

	public void supprimerBombe(int x, int y) throws RemoteException;
	/**
	 * Creation d'un nouveau jeu
	 * @param  _nom                    nom du jeu
	 * @param  _nbJoueursMax           nombre maximum de joueurs
	 * @param  _taille                 taille du plateau
	 * @param  _login                  login du createur
	 * @return Login de l'administrateur
	 * @throws RemoteException         
	 * @throws NomJeuExistantException 
	 */
	public String creerJeu(String _nom, int _nbJoueursMax, String _taille, String _login) throws RemoteException, NomJeuExistantException;

	/**
	 * Supprime le jeu d'un utilisateur
	 * @param _login login de l'utilisateur
	 */
	public String supprimerJeu(String _login) throws RemoteException;

	/**
	 * Ajoute un joueur dans le jeu en question
	 * @param _login  Login du joueur a rajouter
	 * @param _nomJeu Nom du jeu dans lequel rajouter le joueur
	 */
	public void rejoindreJeu(String _login, String _nomJeu) throws RemoteException, JeuPleinException;

	/**
	 * Retire un joueur d'un jeu
	 * @param  _login          Login du joueur
	 * @param  _nomJeu         Nom du jeu du joueur
	 * @throws RemoteException
	 */
	public void quitterJeu(String _login, String _nomJeu) throws RemoteException, JoueurInexistantException;

	/**
	 * Renvoi la liste de tous les joueurs de l'application
	 * @return Liste des joueurs de l'application
	 * @throws RemoteException
	 */
	public HashMap<String, Joueur> getListeJoueurs() throws RemoteException;

        public HashMap<String, Bombe> getListeBombes() throws RemoteException;
	/**
	 * Renvoi la liste de tous les Jeux de l'application
	 * @return Liste des Jeux de l'application
	 * @throws RemoteException
	 */
	public HashMap<String, Jeu> getListeJeux() throws RemoteException;
}
