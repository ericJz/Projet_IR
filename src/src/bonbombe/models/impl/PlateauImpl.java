package bonbombe.models.impl;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.models.interf.*;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;


public class PlateauImpl implements Plateau{
	/* Atributs */
    private int tailleX;	
    private int tailleY;	
    private ArrayList<Bombe> listeBombes;        
    private HashMap<String,Joueur> listeJoueurs;

	/**
	 * Decremente le temps des bombes sur le plateau
	 * @throws RemoteException
	 */
	public void decrementerTpsBombes() throws RemoteException{
		for (Bombe b : listeBombes){
			b.decrementerTimer();
		}
	}

	/**
	 * Ajoute un joueur au jeu
	 * @param  joueur       
	 * @throws RemoteException
	 */        
    public void ajouterJoueur(Joueur joueur) throws RemoteException{
		listeJoueurs.put(joueur.getLogin(), joueur);        
    }

	/**
	 * Supprime un joueur du jeu
	 * @param  joueur       
	 * @throws RemoteException JoueurInexistantException
	 */      
	public void supprimerJoueur(Joueur joueur) throws RemoteException, JoueurInexistantException{
		if(listeJoueurs.containsKey(joueur.getLogin())){
			listeJoueurs.remove(joueur.getLogin());
		}else{
			throw new JoueurInexistantException("Joueur non présent dans le jeu");
		}
	}
	/**
	 * ajoute une bombe au jeu
	 * @param  positionX
	 * @param  positionY    
	 * @throws RemoteException
	 */  
	public void ajoutUnBombe(int positionX, int positionY) throws RemoteException{
		listeBombes.add(new BombeImpl(positionX,positionY));
	}
}


