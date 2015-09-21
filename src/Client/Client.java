package Client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.util.Scanner;
import java.util.Enumeration;
import bonbombe.controllers.impl.ClientController;
import bonbombe.enums.*;
import bonbombe.controllers.interf.ServerController;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Client {
	public static void main(String[] args) {
			/* Récupération de l'ip */
			Scanner sc = new Scanner(System.in);

			String ip = "127.0.0.1";
		    int port = 1099;

			boolean ok = true;

			String response;
			// try{
			// 	Enumeration e = NetworkInterface.getNetworkInterfaces();
			// 	while(e.hasMoreElements() && ok)
			// 	{
			// 	    NetworkInterface n = (NetworkInterface) e.nextElement();
			// 	    Enumeration ee = n.getInetAddresses();
			// 	    while (ee.hasMoreElements() && ok)
			// 	    {
			// 	        InetAddress i = (InetAddress) ee.nextElement();
			// 	        System.out.println("Votre adresse IP : "+i.getHostAddress()+"\nConfirmer ? (y/n)");
			// 	        response = sc.next();
			// 	        switch (response) {
			// 	        	case "y" :
			// 	        		ok = false;
			// 	        		ip = i.getHostAddress();
			// 	        	break;
			// 	        	case "n" :
			// 	        		System.out.println("Suivante...\n");
			// 	        	break;
			// 	        	default :
			// 					System.out.println("Suivante...\n");
			// 	        	break;
			// 	        }
			// 	    }
			// 	}
			
			// }catch(Exception e){

			// 	System.out.println("Erreur, saisissez votre adresse IP :");
			// 	ip = sc.next();

			// }

			System.out.println("Port 1099 ? (y/n)");
			response = sc.next();
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

			/* On récupère maintenant le controlleur serveur auprès du rmiregistry */
			System.out.println("IP serveur : 127.0.0.1 ? (y/n)");
			response = sc.next();
			String ip_serveur;
			switch (response) {
				case "y" :
					ip_serveur = "127.0.0.1";
				break;

				case "n" :
					System.out.println("Rentrez l'IP serveur :");
					ip_serveur = sc.next();
				break;
				default :
					System.out.println("IP fixée à 127.0.0.1");
					ip_serveur = "127.0.0.1";
				break;
			}

			try{
				Registry registry = LocateRegistry.getRegistry(ip_serveur, port);
				ServerController serv_controller = (ServerController)registry.lookup("ServerController");
				/* Création du controlleur client a partir des arguments IP et PORT */
				ClientController controller = new ClientController(ip_serveur,port,serv_controller);
				System.out.println(controller.toString());
				/* On affiche la page de menu et on laisse la main au controlleur Client à partir de maintenant */
				controller.creerPage(NomPage.WELCOME);
			}catch(Exception e){
				System.out.println("Erreur : "+e);
			}
		}	
}