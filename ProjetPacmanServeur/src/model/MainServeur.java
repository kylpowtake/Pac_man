package model;

import java.net.*;
import java.util.*;
import java.io.*;

public class MainServeur {
	
	
	static Vector<Socket> clients = new Vector<>();				//vecteur contenant tout les clients (socket)
	static ServeurRecepteur test = null;
	static PrintWriter out;
	static String chemin = "layouts/capsuleClassic.lay";		//chemin envoyé au client à sa connexion 
	

	public static void main(String[] args)throws IOException{
		
		ServerSocket serverSocket;	//socket du serveur 
		Socket clientSocket;		//socket du client 
		int port;					//numero du port sur le quel le serveur demarre (> 2500)
				

		if(args.length == 1){
	
			try{
				
				port = Integer.parseInt(args[0]);
				serverSocket = new ServerSocket(port);
				System.out.println("serveur lancé");
				PacmanGame game = null;
				ControleurGame controleur = null;
				
				
				//boucle infini en attente de connexion d'un nouveau client 
				while(true){
					
					//connexion d'un nouveau client 
	                clientSocket = serverSocket.accept(); 
	                System.out.println("nouveau client connecté : " + clientSocket); 
	                
	                
	              //chargement du labyrinthe et lancement du jeu 
					try {
						Maze laby = new Maze(chemin);
						game = new PacmanGame(laby, chemin);
						game.setLabyrinthe(laby);
						controleur = new ControleurGame(game);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
	  	             
	                //envoi du chemin du labyrinthe au client
	                out = new PrintWriter(clientSocket.getOutputStream(),true);
					out.println("chemin:" + chemin);   
					out.flush();
					
	                //creation de nouveaux gestionnaires pour le client 
	                ServeurRecepteur serveurRecepteur = new ServeurRecepteur(clientSocket,controleur);
	                ServeurEmetteur serveurEmetteur = new ServeurEmetteur(clientSocket,game);
	                
	                Thread ecoute = new Thread(serveurRecepteur);
	                Thread envoi = new Thread(serveurEmetteur);
	                
	           
	                //ajout du client dans la liste
	                clients.add(clientSocket);
	  
	                //lancement des thread d'écoute et d'envoi 
	                ecoute.start(); 
	                envoi.start();
				}
			}catch (Exception e){ 
                e.printStackTrace();
			}
		}else{
			System.out.println("erreur dans la nombre d'arguments  ./Programme port");
		}
	}
}