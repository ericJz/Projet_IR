package bonbombe.controllers.impl;


import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.views.utils.*;
import bonbombe.controllers.interf.*;
import bonbombe.models.interf.*;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 * Controlleur Client
 */
public class ClientController  {
	/* Network Attributs */
	private String ip;
	private int port;
	private ServerController serverController;
	private Joueur joueur;
	private String nomJeu;
	private Registry registry;
	private String taillePlateau;

	/* Design Attributs */
	private JFrame fenetre;
	private NomPage currentPage;

	/**
	 * Constructeur de ClientController
	 * @param  _ip               Adresse IP du serveur
	 * @param  _port             Port du serveur
	 * @param  _serverController Controlleur Serveur récupéré précédemment
	 * @return                   ClientController
	 */
	public ClientController(String _ip, int _port, ServerController _serverController){
		setIp(_ip);
		setPort(_port);
		setServerController(_serverController);
		try{
			setRegistry(LocateRegistry.getRegistry(_ip, _port));
		}catch(RemoteException rE){
			System.out.println(rE);
		}
		fenetre = new JFrame("BonBombe");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setIconImage((new ImageIcon("./ressources/bombe.png")).getImage());
		/* Dans le cas ou la personne s'est connecté, ne pas oublier de la supprimer si elle quitte */
		this.fenetre.addWindowListener(new java.awt.event.WindowAdapter(){
    		public void windowClosing(java.awt.event.WindowEvent e) {
    			if(getCurrentPage()!=NomPage.WELCOME){	
					deconnexion();
    			}
    		}
        });
		fenetre.setVisible(true);
	}

	/* Getters */
	/**
	 * Getter de ip
	 * @return L'adresse IP du serveur
	 */
	public String getIp(){
		return this.ip;
	}
	/**
	 * Getter de port
	 * @return Le port utilisé par le serveur
	 */
	public int getPort(){
		return this.port;
	}
	/**
	 * Getter de serverController
	 * @return Le controlleur serveur
	 */
	public ServerController getServerController(){
		return this.serverController;
	}
	/**
	 * Getter de joueur
	 * @return Joueur correspondant au client
	 */
	public Joueur getJoueur(){
		return this.joueur;
	}
	/**
	 * Getter de registry
	 * @return Le registry distant
	 */
	public Registry getRegistry(){
		return this.registry;
	}
	/**
	 * Getter de l'attribut fenetre
	 * @return La JFrame du client
	 */
	public JFrame getFenetre(){
		return this.fenetre;
	}
	/**
	 * Getter de l'attribut currentPage
	 * @return NomPage Page courante 
	 */
	public NomPage getCurrentPage(){
		return currentPage;
	}

	/**
	 * Getter de l'attribut nomJeu
	 * @return String Nom du jeu dans lequel est le joueur 
	 */
	public String getNomJeu(){
		return nomJeu;
	}
	
	/**
	 * Getter de l'attribut taille plateau du jeu
	 * @return String taille plateau du jeu
	 */
	public String getTaillePlateau(){
		return taillePlateau;
	}
	

	/* Setters */
	/**
	 * Setter de l'attribut nomJeu
	 * @param _nomJeu Nom du jeu dans lequel est le joueur
	 */
	public void setNomJeu(String _nomJeu){
		this.nomJeu = _nomJeu;
	}
	/**
	 * Setter de l'attribut currentPage
	 * @param _currentPage Page courante
	 */
	public void setCurrentPage(NomPage _currentPage){
		this.currentPage = _currentPage;
	}
	/**
	 * Setter de ip
	 * @param _ip L'adresse IP du serveur
	 */
	public void setIp(String _ip){
		this.ip = _ip;
	}
	/**
	 * Setter de port
	 * @param _port Le port utilisé par le serveur
	 */
	public void setPort(int _port){
		this.port = _port;
	}
	/**
	 * Setter de serverController
	 * @param _serverController Le controlleur serveur
	 */
	public void setServerController(ServerController _serverController){
		this.serverController = _serverController;
	}
	/**
	 * Setter de joueur
	 * @param _joueur Le joueur correspondant au client
	 */
	public void setJoueur(Joueur _joueur){
		this.joueur = _joueur;
	}
	/**
	 * Setter de registry
	 * @param _registry Le registry distant
	 */
	public void setRegistry(Registry _registry){
		this.registry = _registry;
	}
	/**
	 * Setter de l'attribut fenetre
	 * @param _fenetre La JFrame du client
	 */
	public void setFenetre(JFrame _fenetre){
		this.fenetre = _fenetre;
	}
	/**
	 * Setter de l'attribut taille plateau du jeu
	 * @return String taille plateau du jeu
	 */
	public void setTaillePlateau(String _taille){
		this.taillePlateau = _taille;
	}



	public Jeu getJeuEnCours() throws RemoteException, NotBoundException{
		return (Jeu)getRegistry().lookup(getNomJeu());
	}
	/* Fonctions métier */
	/**
	* Connexion du client
	* @param  _login                 Login du client
	* @throws LoginExistantException En cas de login existant
	* @throws RemoteException
	*/
	public void connexion(String _login) throws LoginExistantException, RemoteException, LoginVideException {
		System.out.println("Tentative Connexion...");
		getServerController().ajouterJoueur(_login);
		try{
			setJoueur((Joueur)(getRegistry().lookup(_login)));
		}catch(Exception e){
			System.out.println(e);
		}
		creerPage(NomPage.MENU);
	}

	/**
	 * Déconnexion du client
	 * @param  _login          Login du client
	 * @throws RemoteException
	 */
	public void deconnexion(){
		try{
			if(getJoueur()!=null){
				supprimerJeu();
				getServerController().supprimerJoueur(getJoueur().getLogin());
			}
			creerPage(NomPage.WELCOME);
		}catch(JoueurInexistantException jIE){
			System.out.println("Ce joueur n'existe pas");
		}catch(RemoteException rE){
			System.out.println("Erreur Connexion");
		}catch(NotBoundException nBE){
			System.out.println("Aucun Joueur bind, deconnexion possible");
		}
	}


	/**
	 * Retourne la liste des Joueurs de l'application recuperes depuis le serveur
	 *
	 * @see Joueur
	 * 
	 * @return Liste de Joueur
	 */
	public ArrayList<Joueur> getListeJoueurs(){
		ArrayList<Joueur> listeJoueurs;
		try{
			listeJoueurs = new ArrayList(getServerController().getListeJoueurs().values());
		}catch(RemoteException e){
			System.out.println(e);
			listeJoueurs = new ArrayList();
		}
		return listeJoueurs;
	}


	/**
	 * Retourne la liste des joueurs du jeu en cours
	 * @return Liste de Joueur
	 */
	public ArrayList<Joueur> getListeJoueursJeu(){
		ArrayList<Joueur> listeJoueurs;
		try{
			listeJoueurs = new ArrayList(
				((Jeu)getRegistry().lookup(
					getNomJeu()
				)).getListeJoueurs().values()
			);
		}catch(RemoteException e){
			System.out.println(e);
			listeJoueurs = new ArrayList();
		}catch(NotBoundException nBE){
			System.out.println(nBE);
			listeJoueurs = new ArrayList();
		}
		return listeJoueurs;
	}


	/**
	 * Retourne la liste des bombes dans le jeu
	 * @return Liste de bombes
	 */
	public ArrayList<Bombe> getListeBombes(){
		ArrayList<Bombe> listeBombes;
		try{
			listeBombes = new ArrayList(getServerController().getListeBombes().values());
		}catch(RemoteException e){
			System.out.println(e);
			listeBombes = new ArrayList();
		}
		return listeBombes;
	}	

        public void deposerBombe(int pos_X, int pos_Y){
                try{
                getServerController().ajouterBombe(pos_X,pos_Y);
                }catch(RemoteException e){
                        System.out.println(e);      
                }catch(BombeExistantException e){
                        System.out.println(e);           
                }
        }

	/**
	 * Retourne la liste des Joueurs de l'application recuperes depuis le serveur
	 *
	 * @see Joueur
	 * 
	 * @return Liste de Joueur
	 */
	public ArrayList<Jeu> getListeJeux(){
		ArrayList<Jeu> listeJeux;
		try{
			listeJeux = new ArrayList(getServerController().getListeJeux().values());
		}catch(RemoteException e){
			System.out.println(e);
			listeJeux = new ArrayList();
		}
		return listeJeux;
	}

	/**
	 * Creer un jeu dont l'utilisateur courrant devient l'administrateur
	 * @param  _nom                    nom du jeu
	 * @param  _nbJoueursMax           nombre de joueurs maximum
	 * @param  _taille                 taille du plateau
	 * @throws NumberFormatException
	 * @throws NomJeuExistantException
	 */
	public void creerJeu(String _nom, String _nbJoueursMax, String _taille) throws NumberFormatException, NomJeuExistantException{
		try{
			// Tout en creant le jeu, recuperation du nouveau profil admin du joueur
			setJoueur((Admin)getRegistry().lookup(
					getServerController().creerJeu(_nom,Integer.parseInt(_nbJoueursMax),_taille, getJoueur().getLogin())
				)
			);
			setNomJeu(_nom);
			setTaillePlateau(_taille);
			creerPage(NomPage.JEU);
			// creerPage(NomPage.MENU);
		}catch(RemoteException rE){
			System.out.println(rE);
		}catch(NotBoundException nBE){
			System.out.println(nBE);
		}
	}

	/**
	 * Supprime le jeu de l'utilisateur et replace le joueur a un profil Joueur
	 */
	public void supprimerJeu(){
		try{
		// Lors de la suppression du jeu, il faut recuperer le profil Joueur car le joueur createur du jeu ne possede plus de profil Admin
			String login = getServerController().supprimerJeu(getJoueur().getLogin());
			setJoueur((Joueur)getRegistry().lookup(login));
		}catch(RemoteException e){
			System.out.println(e);
		}catch(NotBoundException ex){
			System.out.println("Aucun jeu a supprimer");
		}
	}

	/**
	 * Rejoindre un jeu
	 * @param _nomJeu Nom du jeu à rejoindre
	 */
	public void rejoindreJeu(String _nomJeu) throws JeuPleinException{
		try{
			getServerController().rejoindreJeu(getJoueur().getLogin(),_nomJeu);
			setNomJeu(_nomJeu);
			setTaillePlateau(
					((Jeu)getRegistry().lookup(_nomJeu)).getTaillePlateau()
				);
			creerPage(NomPage.JEU);
		}catch(RemoteException rE){
			System.out.println(rE);
		}catch(NotBoundException nBE){
			System.out.println(nBE);
		}
	}
	/**
	 * Lancer un jeu
	 * @param _nomJeu Nom du jeu à lancer
	 */	
	public void lancerJeu(String _nomJeu){
			setNomJeu(_nomJeu);
			creerPage(NomPage.JEU);	        
	        	        	
	}

	/**
	 * Quitter le jeu en cours
	 */
	public void quitterJeu(){
		try{
			System.out.println(getNomJeu());
			getServerController().quitterJeu(getJoueur().getLogin(),getNomJeu());
			setNomJeu("");
			creerPage(NomPage.MENU);
		}catch(RemoteException rE){
			System.out.println(rE);
			creerPage(NomPage.WELCOME);
		}catch(JoueurInexistantException jIE){
			System.out.println(jIE);
		}
	}

	public void rejouer(){
		try{
			Jeu jeuEnCours = (Jeu)getRegistry().lookup(getNomJeu());
			for(Joueur joueur : getListeJoueursJeu()){
				joueur.setPv(3);
				joueur.setEtat(Etat.VIVANT);
			}
		}catch(RemoteException rE){
			System.out.println(rE);
		}catch(NotBoundException nBE){
			System.out.println(nBE);
		}
	}



	/* Générateurs  de pages */
	/**
	 * Créer une page en fonction du nom passé
	 * @param _nomPage Nom de la page à créer
	 */
	public void creerPage(NomPage _nomPage){
		switch (_nomPage) {
			case WELCOME :
				this.creerPageWelcome();
				setCurrentPage(NomPage.WELCOME);
			break;
			case MENU :
				this.creerPageMenu();
				setCurrentPage(NomPage.MENU);
			break;
			case CREERJEU :
				this.creerPageCreerJeu();
				setCurrentPage(NomPage.CREERJEU);
			break;
			case JEU :
				this.creerPageJeu();
				setCurrentPage(NomPage.JEU);
			break;

			/* Par défaut on renvoi la page Menu */
			default :
				this.creerPageWelcome();
				setCurrentPage(NomPage.WELCOME);
			break;
		}
	}
	/**
	 * Créer la page de connexion au lancement de l'application permettant de renseigner un login
	 */
	private void creerPageWelcome(){
		System.out.println("Création page Welcome");

		/* On configure la fenetre avant de l'afficher */
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setContentPane(new WelcomePage(this));
		this.fenetre.validate();
		this.fenetre.pack();
	}
	/**
	 * Créer la page de menu après la connexion permettant de creer ou rejoindre un jeu
	 */
	private void creerPageMenu(){
		System.out.println("Création page Menu");
		/* On configure la fenetre avant de l'afficher */
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setContentPane((new MenuPage(this)));
		this.fenetre.validate();
		this.fenetre.pack();
	}
	/**
	 * Créer la page de création de jeu permettant de créer un jeu
	 */
	private void creerPageCreerJeu(){
		System.out.println("Création page CreerJeu");

		/* On configure la fenetre avant de l'afficher */
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setContentPane(new CreerJeuPage(this));
		this.fenetre.validate();
		this.fenetre.pack();
	}
	/**
	 * Créer la page de jeu permettant de jouer
	 */
	private void creerPageJeu(){
		/* On configure la fenetre avant de l'afficher */
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setContentPane(new JeuPage(this));
		this.fenetre.validate();
		this.fenetre.pack();
	}


	/**
	 * Méthode toString du controlleur client
	 * @return Informations en CdC
	 */
	public String toString(){
		return "Controlleur Client de l'host "+this.ip+" port "+port+".";
	}
}
