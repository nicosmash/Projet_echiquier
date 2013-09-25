import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** 
 * Processus fils du programme qui gere la connection tcp/ip.
 * Petit defaut : cette classe devrait inclure l'attente de connection afin
 * que le programme ne 'freeze' pas pendant qu'il ecoute sur le port.
 **/
public class ChessSocketThread extends Thread {
 /** Constante 'Serveur' */	
 final static int SERVER = 0;
 /** Constante 'Client' */	 
 final static int CLIENT = 1; 
 /** Socket de la connection */	
 private Socket socket = null;
 /** Partie en cours */	
 private Jeu jeu = null;
 /** Type de la connection, client ou serveur ? */	 
 private int type;

  /**
   * Constructeur du processus. Initialise tout.
   * @param jeu Partie corespondante.
   * @param socket Connection qui nous est passee de Net normalement.
   * @param type Type de connection.
   **/
    public ChessSocketThread(Jeu jeu, Socket socket, int type) {
  super("ChessSocketThread");
  this.socket = socket;
  this.type = type;
  this.jeu = jeu;
    }

 /**
  * Procedure principale du processus.
  * Recoit les donnees de la connection et les passe au ChessProtocol qui
  * renvoie les reponses adequates, qui sont renvoyes sur la socket par ce
  * processus.
  **/
 public void run() {

  try {
   PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
   BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   String input, output;

   if (type == CLIENT) out.println("jC10");
   
   ChessProtocol cP = new ChessProtocol(jeu, type);
   while ((input = in.readLine()) != null) { 
     output = cP.processInput(input);
//     out("in:" + input);
//     out("out:" + output);
     if (output == null) continue;
     out.println(output);
     if (output.equals("bye.")) break;
 }
         
         in.close();
         out.close();
         socket.close();
         
  } catch (IOException e) {
  	  // ugly hack :'(
      //e.printStackTrace();
  }
 }

 /**
  * Raccourci pour l'affichage de texte.
  * @param str Chaine a afficher.
  **/
 public static void out (String str) { System.out.println(str); } 
}
