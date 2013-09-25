/** Classe representant un joueur. */

public class Joueur {
 /** Nom du joueur. */
   private String nom;
  
   /**
    * Assigne le parametre passe a l'attribut de la classe.
    * @param nom Nom du joueur.
    **/
   public Joueur (String nom) {
     this.nom = nom;
   }
  
   /**
    * Affiche le nom du joueur.
    * @result Chaine de caracteres representant le nom du joueur.
    **/
 public String toString() {
  return nom;
 }
 
  public void setNom(String nom) { this.nom = nom; }
   
 /**
  * Demande un coup au joueur pour le mode texte (sous la forme 'e2-e4').
  * @result Renvoie un tableau de 2 tableaux de 2 entiers { {x1 , y1} , {x2 , y2} }
  * 1er tableau = source du coup
  * 2eme tableau = destination
  */
 public int[][] demanderCoup() {
  int[][] res;
     
  do {
      res = Util.convCoup(Util.readLine(nom + ": coup (ex: e2-e4) ?"));
     } while (res[0][0] == -1);
     
     return res;
 }
}