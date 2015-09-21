package Server;

import bonbombe.controllers.impl.*;
import bonbombe.controllers.interf.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;

import java.util.*;

public class Server {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

	    int port = 1099;

	    System.out.println("Port 1099 ? (y/n)");
		String response = sc.next();
		switch (response) {
			case "y" :
				System.out.println("Port fixé à 1099");
				port=1099;
			break;
			case "n" :
				System.out.println("Quel port?");
				port = Integer.parseInt(sc.next());
			break;
			default :
			break;
		}

		try {
			Registry registry = LocateRegistry.getRegistry(port);
			ServerController controller = (ServerController)UnicastRemoteObject.exportObject(new ServerControllerImpl(port,registry), 0);

			if(!Arrays.asList(registry.list()).contains("ServerController")){
				registry.bind("ServerController", controller);
				System.out.println("Controlleur serveur lié au registry");
			}else{
				registry.rebind("ServerController", controller);
				System.out.println("Controlleur serveur lié au registry");
			}
		}
    	catch (Exception e) {
      		System.out.println(e);
    	}
    	
	}
}