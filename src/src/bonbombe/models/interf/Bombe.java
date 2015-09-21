package bonbombe.models.interf;

import bonbombe.enums.*;
import bonbombe.exceptions.*;
import bonbombe.views.*;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Bombe extends Remote, Serializable{

	/* decrementerTimer */
	/**
	 * decrementer le temps de bombe
	 * @throws RemoteException
	 */
	public void decrementerTimer() throws RemoteException;

	/* Setters */
	/**
	 * Setter de l'attribut positionX
	 * @param  positionX            positionX de la bombe
	 * @throws RemoteException
	 */
	public void setPositionX(int positionX) throws RemoteException;
	/**
	 * Setter de l'attribut positionY
	 * @param  positionY            positionY de la bombe
	 * @throws RemoteException
	 */	
	public void setPositionY(int positionY) throws RemoteException;	
	/**
	 * Setter de l'attribut timer
	 * @param  timer            timer de la bombe
	 * @throws RemoteException
	 */
	public void setTimer(int timer) throws RemoteException;

	/**
	 * Getter de l'attribut positionX
	 * @return positionX de la bombe
	 * @throws RemoteException
	 */
	public int getPositionX() throws RemoteException;
	/**
	 * Getter de l'attribut positionY
	 * @return positionY de la bombe
	 * @throws RemoteException
	 */	
	public int getPositionY() throws RemoteException;	

	/**
	 * Getter de l'attribut timer
	 * @return timer de la bombe
	 * @throws RemoteException
	 */
	public int getTimer() throws RemoteException;		
}
