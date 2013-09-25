/** Classe qui gere le protocole de communication tcp/ip du programme */
public class ChessProtocol {
 /** Constante 'Serveur' */	
 final static int SERVER = 0;
 /** Constante 'Client' */	 
 final static int CLIENT = 1;
 /** Instance du jeu en cours */	 
 private Jeu jeu;
 /** Type de la connection, client ou serveur ? */	 
 private int sockType;
 /** Etat de la connection (Authentification, partie en cours, etc..) */	 
 private int state;
 /** Constante pour retenir le surnom recu par le cote oppose. */
 private String tmpNick;
 
 /**
  * Constructeur de la classe, initialise l'etat de la connection a 0.
  * @param jeu Jeu en cours a associer a cette communication.
  * @param sockType Type de connection.
  */
 public ChessProtocol(Jeu jeu, int sockType) {
  this.jeu = jeu;
  this.sockType = sockType;
  this.state = 0;
 }
 
 /**
  * Fonction principale qui gere les informations recues et renvoie la reponse
  * appropriee.
  * @param input Informations recues.
  * @result Reponse adequate.
  */
 public String processInput(String input) {
  if (input.equals("bye."))
    return input;
  else if (input.startsWith("-ERR")) {
 out("socket erreur: " + input.substring(5)); 
    return null;
  }


 switch(state) {
  
  case 0:{ 

  if (sockType == SERVER) {
   if (input.equals("jC10")) {
    state++;
    return "jC10";
   }
   break;
  } else { //sockType == CLIENT
  	
  if (input.equals("jC10")) {
    state++;
    return "NICK " + Net.nick;
   }
   break; 
  }   
  
  }
   
  case 1:{
  	
   if (sockType == SERVER) {
   if (input.startsWith("NICK")) {
    if (Util.showYesNoDialog(jeu.getFrame(), "Connection acceptee", 
     "Voulez vous accepter la connection de '" 
     + input.substring(5) + "' ?") == 0) {
     tmpNick = input.substring(5); 
     state++;
       return "NICK " + Net.nick;
    } else // il a clicke non
     return "-ERR Connection refusee par le serveur.";
   }
   break; 
  } else { //sockType == CLIENT
 
    if (input.startsWith("NICK")) {
    state += 2;
    tmpNick = input.substring(5);
    out("Connection etablie a '" + tmpNick + "'.");

    out("Debut de la partie.");
       jeu.getJoueur(0).setNom(tmpNick);
       jeu.getJoueur(1).setNom(Net.nick);
       jeu.reset();
       Net.couleur = sockType;
       Net.monTour = false; // le client joue apres le serveur

    return "+OK On joue ?"; 
   }
   break; 
 
  }
 } 
  case 2: {
  	if (sockType == SERVER) {
    if (input.startsWith("+OK")) {
    state++;
    
    out("Debut de la partie.");
       jeu.getJoueur(0).setNom(Net.nick);
       jeu.getJoueur(1).setNom(tmpNick);
       jeu.reset();
       Net.couleur = sockType;
       Net.monTour = true; // le serveur joue cash
    
    return null;
   }
   break;
  
}
}
  
  case 3:{
   	
   if (input.startsWith("MOVE")) {
       if (input.length() != 9)
        return "-ERR 601 longuer de la comande incorrecte.";
   
       int src[] = new int[] { input.charAt(5) - 48, input.charAt(6) - 48 };
       int dst[] = new int[] { input.charAt(7) - 48, input.charAt(8) - 48 };
       int coup[][] = new int[][] { src, dst };
       if (jeu.traiterCoup(coup, false)) {
        Net.monTour = true;
        return "+OK";
       }
       else { // coup incorrect
        return "-ERR 602 coup invalide.";
       }
      }
      else if (input.startsWith("+OK")) return null;
       

   break;
  
     
  }
  
  }
  return "-ERR 501 non implemente.";
  
}
 
 /**
  * Raccourci pour l'affichage de texte.
  * @param str Chaine a afficher.
  **/
 public static void out (String str) { System.out.println(str); }
 
}
