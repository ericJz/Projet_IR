package bonbombe.models.impl;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;
import bonbombe.models.interf.*;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;


public class BombeImpl implements Bombe{
	/* Atributs */
	private int timer;
    private int positionX;
    private int positionY;
        
	/**
	 * Contructeur de BombeImpl
	 * @return BombeImpl
	 */
	public BombeImpl(int positionX, int positionY){
		this.positionX = positionX;
		this.positionY = positionY;		
	}

	/* decrementerTimer */
	/**
	 * decrementer le temps de bombe
	 * @throws RemoteException
	 */
	public void decrementerTimer() throws RemoteException{
		this.timer-=100;
	}

	/* Setters */
	/**
	 * Setter de l'attribut positionX
	 * @param  positionX            positionX de la bombe
	 * @throws RemoteException
	 */
	public void setPositionX(int positionX) throws RemoteException{
	        this.positionX = positionX;
	}
	/**
	 * Setter de l'attribut positionY
	 * @param  positionY            positionY de la bombe
	 * @throws RemoteException
	 */
	public void setPositionY(int positionY) throws RemoteException{
	        this.positionY = positionY;
	}
	/**
	 * Setter de l'attribut timer
	 * @param  timer            timer de la bombe
	 * @throws RemoteException
	 */
	public void setTimer(int timer) throws RemoteException{
	        this.timer = timer;
	}

	/* Getters */
	/**
	 * Getter de l'attribut positionX
	 * @return positionX de la bombe
	 * @throws RemoteException
	 */
	public int getPositionX() throws RemoteException{
	        return this.positionX;
	}
	/**
	 * Getter de l'attribut positionY
	 * @return positionY de la bombe
	 * @throws RemoteException
	 */
	public int getPositionY() throws RemoteException{
	        return this.positionY;
	}	
	/**
	 * Getter de l'attribut timer
	 * @return timer de la bombe
	 * @throws RemoteException
	 */
	public int getTimer() throws RemoteException{
	        return this.timer;
	}	
}




