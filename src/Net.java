import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/** Classe pour le jeu en reseau tcp/ip */

public class Net {
 /** Constante de la couleur BLANC. */
 final static int BLANC = 0;
 /** Constante de la couleur NOIR. */
 final static int NOIR = 1;
 /** Constante du serveur */
 final static int SERVER = 0;
 /** Constante du client */
 final static int CLIENT = 1; 
 /** Port des connections serveur / client */
 final static int PORT = 667; 
 
 /** surnom pour le reseau */
 public static String nick = "Joueur";
 /** ----------------- rezo -----------------------*/
 /** Couleur locale pour le jeu en reseau */
 public static int couleur;
 /** reseau: c a moi de jouer ? */
 public static boolean monTour;
 /** reseau: socket avec qui on bavarde */
 public static Socket socket;


 /**
  * Cree un serveur de partie.
  * @param jeu La partie associee.
  **/
 public static void createServer(Jeu jeu) {
  
  ServerSocket serverSocket;
  PrintWriter out;
  BufferedReader in;

  try { 
   serverSocket = new ServerSocket(PORT);
  } catch (IOException e) {
   out("Listen failed: " + e.getMessage());
   return;
  }
  out("Attente d'une connection sur le port " + PORT + "...");
  try {
   Socket socket = serverSocket.accept();
   Net.socket = socket;
   new ChessSocketThread(jeu, socket, SERVER).start();
  } catch (IOException e) {
      out("Accept failed: " + e.getMessage());
      return;
  }
  out("Connection acceptee.");
 
  try { serverSocket.close(); }
  catch (IOException e) { out("Close failed: " + e.getMessage()); }
 } 
 
 /**
  * Cree un client de connection.
  * @param jeu La partie associee.
  * @param host La cible a laquelle on desire se connecter.
  **/
 public static void createClient (Jeu jeu, String host) {
  
  try {
   Socket socket = new Socket(host, PORT);
   Net.socket = socket;
   new ChessSocketThread(jeu, socket, CLIENT).start();
  } catch (IOException e) {
   out("Connect failed: " + e.getMessage()); 
   return;
  }
  out("Connection effectuee.");
 }

 /**
  * Procedure 'outil' qui permet d'envoyer du texte sur une connection.
  * @param socket La socket sur laquelle on desire envoyer le texte.
  * @param text Texte a envoyer.
  **/ 
 public static void SendText(Socket socket, String text) {
  try {
   PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
   out.println(text);
  } catch (java.io.IOException e) {
   e.printStackTrace();
  }
 }
 
 /**
  * Raccourci pour l'affichage de texte.
  * @param str Chaine a afficher.
  **/
 public static void out (String str) { System.out.println(str); }
 
}
