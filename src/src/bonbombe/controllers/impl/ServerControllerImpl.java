package bonbombe.controllers.impl;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.controllers.interf.*;
import bonbombe.models.impl.*;
import bonbombe.models.interf.*;


import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

/**
 * Controlleur Serveur
 * 
 * @see ServerController
 */
public class ServerControllerImpl implements Serializable, ServerController {
	
	/* Attributs Controller */
	private HashMap<String, Jeu> listeJeux;
	private HashMap<String, Joueur> listeJoueurs;
	private HashMap<String, Bombe> listeBombes;
	private Registry registry;
	private int port;
        private int timer=1000;	

	/**
	 * Constructeur ServerControllerImpl
	 * @return ServerController
	 */
	public ServerControllerImpl(int _port, Registry _registry){
		setListeJoueurs(new HashMap<String, Joueur>());
		setListeBombes(new HashMap<String, Bombe>());
		setListeJeux(new HashMap<String, Jeu>());
		setRegistry(_registry);
		setPort(_port);
	}

	/**
	 * Ajout d'un utilisateur au controller
	 * @param  _login          le login de l'utilisateur
	 * @throws RemoteException
	 */
	public void ajouterJoueur(String _login) throws LoginExistantException, RemoteException, LoginVideException{
		System.out.println("Demande de connexion...");
		if (_login.equals("")) {
			throw new LoginVideException("Login Vide !");	
		}
		else{
			if(getListeJoueurs().containsKey(_login)){
				throw new LoginExistantException("Ce login existe déjà");
			}else{
				try{
					Joueur newJoueur = (Joueur)UnicastRemoteObject.exportObject(new JoueurImpl(_login), 0); 
					getListeJoueurs().put(_login,newJoueur);
					getRegistry().bind(_login,newJoueur);
					System.out.println("Joueur "+_login+" ajouté");
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}

	public void ajouterBombe(int x, int y) throws RemoteException, BombeExistantException{
		System.out.println("Ajouter un bombe...");
		String cle = ""+x+","+y+"";
			if(getListeBombes().containsKey(cle)){
				throw new BombeExistantException("Bombe existe déjà");
			}else{
			        try{
			                Bombe newBombe = (Bombe)UnicastRemoteObject.exportObject(new BombeImpl(x,y),0);
			                getListeBombes().put(cle,newBombe);
			                newBombe.setTimer(this.timer);
			                getRegistry().bind(cle,newBombe);
					System.out.println("Bombe "+cle+" ajouté");			                
			        }catch(Exception e){
					System.out.println(e);
			        }
			}	
	}
	
	public HashMap<String, Joueur> trouverBlesse(int posX, int posY) throws RemoteException{
                HashMap<String, Joueur> listeBlesses = new HashMap<String, Joueur>();
                for(Joueur joueur : getListeJoueurs().values()){
                        int x = joueur.getPosX();
                        int y = joueur.getPosY();
                        if(x == posX || y == posY){
                                listeBlesses.put(joueur.getLogin(),joueur);
                                System.out.println(joueur.getLogin()+" point de vie moins 1.\n");
                        }
                }	
                return listeBlesses;
	}

	/**
	 * Supprime un joueur du controlleur
	 * @param  _login                    Login du joueur
	 * @throws JoueurInexistantException
	 * @throws RemoteException           
	 */
	public void supprimerJoueur(String _login) throws JoueurInexistantException, RemoteException, NotBoundException{
		if(getListeJoueurs().containsKey(_login) && !getListeJoueurs().isEmpty()){
			try{
				getListeJoueurs().remove(_login);
				getRegistry().unbind(_login);
				System.out.println("Joueur "+_login+" supprimé");
			}catch(RemoteException e){
				System.out.println(e);
			}
		}else{
			throw new JoueurInexistantException("Le joueur n'existe pas");
		}
	}

	public void supprimerBombe(int x, int y) throws RemoteException{
		String cle = ""+x+","+y+"";
		if(getListeBombes().containsKey(cle) && !getListeBombes().isEmpty()){
			try{
				getListeBombes().remove(cle);
				System.out.println("Bombe "+cle+" supprimé");
			}catch(RemoteException e){
				System.out.println(e);
			}
		}
	}

	/**
	 * Creation d'un nouveau jeu
	 * @param  _nom            nom du jeu
	 * @param  _nbJoueursMax   nombre de joueurs maximum
	 * @param  _taille         taille du plateau
	 * @param  _login          login du createur
	 * @throws RemoteException
	 */
	public String creerJeu(String _nom, int _nbJoueursMax, String _taille, String _login) throws RemoteException, NomJeuExistantException{
		if(!getListeJeux().containsKey(_nom)){
			try{
				// Suppression du joueur et creation d'un admin identique au joueur
				Admin admin = (Admin)UnicastRemoteObject.exportObject(new AdminImpl(_login),0);
				getListeJoueurs().remove(_login);
				admin.setStatut(Statut.ENJEU);
				getListeJoueurs().put(_login,admin);
				getRegistry().rebind(_login,admin);
				// Maintenant creation du jeu !
				Jeu newJeu = (Jeu)UnicastRemoteObject.exportObject(new JeuImpl(_nom,_nbJoueursMax,admin,_taille), 0);
				getListeJeux().put(_nom,newJeu);
				getRegistry().bind(_nom,newJeu);
				System.out.println("Jeu "+_nom+" ajouté");
				return _login;
			}catch(Exception e){
				System.out.println(e);
				return "ERREUR";
			}
		}else{
			throw new NomJeuExistantException("Ce nom de jeu existe déjà");
		}
	}

	/**
	 * Supprime le jeu d'un utilisateur
	 * @param _login login de l'utilisateur
	 * @return login de l'utilisateur
	 */
	public String supprimerJeu(String _login) throws RemoteException{
		try{
			Jeu jeuASupprimer = null;
			for(Jeu jeu : getListeJeux().values()){
				if(((String)jeu.getAdmin().getLogin()).equals((String)_login)){
					jeuASupprimer = jeu;
					break;
				}else{
					throw new JeuInexistantException("Jeu Inexistant");
				}
			}
			getRegistry().unbind(
				getListeJeux().remove(jeuASupprimer.getNom()).getNom()
			);
			System.out.println("Jeu "+jeuASupprimer.getNom()+" supprime");
			Joueur joueur = (Joueur)UnicastRemoteObject.exportObject(new JoueurImpl(_login),0);
			getListeJoueurs().remove(_login);
			getListeJoueurs().put(_login,joueur);
			getRegistry().rebind(_login,joueur);
			return _login;
		}catch(Exception e){
			System.out.println(e);
			return "ERREUR";
		}
	}

	/**
	 * Ajoute un joueur dans le jeu en question
	 * @param _login  Login du joueur a rajouter
	 * @param _nomJeu Nom du jeu dans lequel rajouter le joueur
	 * @throws RemoteException
	 * @throws JeuPleinException
	 */
	public void rejoindreJeu(String _login, String _nomJeu) throws RemoteException, JeuPleinException{
		Jeu jeu = listeJeux.get(_nomJeu);
		Joueur joueur = listeJoueurs.get(_login);
		jeu.ajouterJoueur(joueur);
		getRegistry().rebind(_nomJeu,jeu);

		joueur.setStatut(Statut.ENJEU);
	}

	/**
	 * Retire un joueur d'un jeu
	 * @param  _login          Login du joueur
	 * @param  _nomJeu         Nom du jeu du joueur
	 * @throws RemoteException
	 */
	public void quitterJeu(String _login, String _nomJeu) throws RemoteException, JoueurInexistantException{
		Jeu jeuAQuitter = getListeJeux().get(_nomJeu);
		if(_login.equals(jeuAQuitter.getAdmin().getLogin())){
			supprimerJeu(_login);
		}else{
			jeuAQuitter.supprimerJoueur(getListeJoueurs().get(_login));
			try{
				((Joueur)getRegistry().lookup(_login)).setStatut(Statut.PASENJEU);
			}catch(NotBoundException nBE){
				System.out.println(nBE);
			}
		}
	}


	/* Getters */
	/**
	 * Getter de listeJeux
	 * @return Liste des jeux
	 */
	public HashMap<String, Jeu> getListeJeux() throws RemoteException{
		return this.listeJeux;
	}
	/**
	 * Getter de listeJoueurs
	 * @return Liste des joueurs
	 */
	public HashMap<String, Joueur> getListeJoueurs() throws RemoteException{
		return this.listeJoueurs;
	}
	/**
	 * Getter de listeBombes
	 * @return Liste des bombes
	 */
	public HashMap<String, Bombe> getListeBombes() throws RemoteException{
		return this.listeBombes;
	} 
	/**
	 * Getter de registry
	 * @return Registry lié au serveur
	 */
	public Registry getRegistry(){
		return this.registry;
	}
	/**
	 * Getter de port
	 * @return Port du serveur
	 */
	public int getPort(){
		return this.port;
	}



	/* Setters */
	/**
	 * Setter de listeJeux
	 * @param _listeJeux Liste des jeux
	 */
	public void setListeJeux(HashMap<String, Jeu> _listeJeux){
		this.listeJeux = _listeJeux;
	}
	/**
	 * Setter de listeJoueurs
	 * @param _listeJoueurs Liste des joueurs
	 */
	public void setListeJoueurs(HashMap<String, Joueur> _listeJoueurs){
		this.listeJoueurs = _listeJoueurs;
	}
	/**
	 * Setter de listeBombes
	 * @param _listeBombes Liste des bombes
	 */
	public void setListeBombes(HashMap<String, Bombe> _listeBombes){
		this.listeBombes = _listeBombes;
	}	
	/**
	 * Setter de registry
	 * @param _registry Le Registry lié au serveur
	 */
	public void setRegistry(Registry _registry){
		this.registry = _registry;
	}
	/**
	 * Setter de port
	 * @param _port Le port utilisé par le serveur
	 */
	public void setPort(int _port){
		this.port = _port;
	}

}
