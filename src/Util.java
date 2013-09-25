import java.awt.Frame;

import javax.swing.JOptionPane;

/** 
 * Classe statique contenant toutes les fonctions utiles 
 * qui ne rentrent pas vraiment autre part. 
 **/

public class Util {
  /** Constante des abcisses. */
  final static int X = 0 ; 
  /** Constante des ordonees. */
  final static int Y = 1 ; 
  /** Constante de la couleur BLANC. */
  final static int BLANC = 0; 
  /** Constante de la couleur NOIR. */
  final static int NOIR = 1; 

  /**
   * Affiche un dialogue avec les boutons "Oui" et "Non".
   * @param frame fenetre 'mere' a qui appartient ce dialogue.
   * @param title Titre du dialogue.
   * @param text Texte de la question.
   * @result Entier qui represente le bouon sur lequel on a clique.
   * @ante Frame un une fenetre valide.
   * @cons Un dialogue s'affiche.
   **/
  public static int showYesNoDialog (Frame frame, String title, String text) {
    return JOptionPane.showConfirmDialog(
    frame, text, title, JOptionPane.YES_NO_OPTION);
  }

  /**
   * Dialogue qui demande d'entrer du texte.
   * @param prompt Texte a afficher dans le dialogue qui precise quoi entrer.
   * @result Chaine entree dans le dialogue.
   * @cons Le dialogue s'affiche.
   **/
  public static String readLine(String prompt) {
 return JOptionPane.showInputDialog(prompt);
  }

  /** 
   * Renvoie la couleur oppose a celle passee en argument.
   * @param couleur dont on cherche l'oppose.
   * @result La couleur opposee.
   * @ante couleur est 0 ou 1
   * @cons Si couleur = 1, on renvoie 0 et 1 sinon.
   */
  public static int coulOpp(int couleur) {
    return (couleur + 1) % 2;
  }
  
  /**
   * Sert a inverser un coup. 
   * @param coup Coup dont on cherche l'inverse.
   * @result Le coup oppose.
   * @ante Coup est un coup de la forme {{x1,y1},{x2,y2}}
   * @cons si coup = {{x1,y1},{x2,y2}}, on renvoie {{x2,y2},{x1,y1}}.
   */
  public static int[][] inverserCoup(int[][] coup) {
    return new int[][] { coup[1] , coup[0] };
  }
  
  /**
   * Verifie si la 'case', ou 'position' passee en argument est bien sur 
   * l'echiquier d'apres nos standards de notation.
   * @param pos Position a verifier.
   * @result Vrai si la position est correcte, faux sinon.
   */
  public static boolean estPosValide(int[] pos) {
    if ( (pos.length != 2) || (pos[X] < 0) || (pos[X] > 7)
          || (pos[Y] < 0) || (pos[Y] > 7) )
      return false;
    return true;
  }

  /**
   * Converti un coup de la forme tableau d'entier vers la forme d'une chaine
   * de caracteres (ex: { {4, 6} , {4, 4} } --> "e2-e4" ).
   * @param coup Coup a convertir.
   * @result Chaine de caracteres representant le coup.
   **/
  public static String convCoup(int[][] coup) {
 return "" + (char)('a' + coup[0][0]) + (8 - coup[0][1]) + "-"
    + (char)('a' + coup[1][0]) + (8 - coup[1][1]);
  }
  
  /**
   * Converti un coup de la forme chaine de caracteres vers un tableau d'entier
   * en verifiant la syntaxe (ex: "e2-e4" --> { {4, 6} , {4, 4} } ).
   * @param coup Coup a convertir.
   * @result Si la syntaxe est fausse, le tableau suivant : 
   * { { -1 , 0 } , { 0, 0 } }, sinon un tableau representant le coup.
   */ 
  public static int[][] convCoup(String coup) {
    coup = coup.toLowerCase();
    if ((coup.length() != 5) || (coup.charAt(2) != '-') 
          || (!estCharEchec(coup.charAt(0))) || (!estEntierEchec(coup.charAt(1)))
          || (!estCharEchec(coup.charAt(3))) || (!estEntierEchec(coup.charAt(4))) )
      return new int[][] { { -1 , 0 } , { 0, 0 } };
    
    int x1 = coup.charAt(0) - 'a';
    int y1 = 8 - ( coup.charAt(1) - 48 );
    int x2 = coup.charAt(3) - 'a';
    int y2 = 8 - ( coup.charAt(4) - 48 );
    
    return new int[][] { { x1, y1} , { x2, y2} };
  }
  
  /**
   * Verifie si un charactere est un charactere de colonnes d'echec ('a'-'h').
   * @param c Charactere a verifier.
   * @result Vrai si le charactere est bon, Faux sinon.
   */ 
  public static boolean estCharEchec(char c) {
    return ((c >= 'a') && (c <= 'h'));
  }

  /**
   * Verifie si un charactere est un charactere de lignes d'echec ('1'-'8').
   * @param c Charactere a verifier.
   * @result Vrai si le charactere est bon, Faux sinon.
   */ 
  public static boolean estEntierEchec(char c) {
    return ((c >= '1') && (c <= '8'));
  }
  
  /**
   * Effectue une translation d'une case. 
   * @param pos Case a translater.
   * @param x Valeur horizontale de la translation a effectuer.
   * @param y Valeur verticale de la translation a effectuer.
   * @result Nouvelle case translatee.
   */ 
  public static int[] translation(int[] pos, int x, int y) {
    return new int[] { pos[0] + x , pos[1] + y };
  }
  
  /**
   * Compare deux tableaux d'entiers.
   * @param array1 Premier tableau.
   * @param array2 Second tableau.
   * @result Vrai si les tableaux sont egaux, faux sinon.
   */ 
  public static boolean compIntArrays (int[] array1, int[] array2) {
    boolean res = true;
    if (array1.length != array2.length)
      return false;
    for (int i = 0; i < array1.length; i++) {
      if (array1[i] != array2[i])
        return false;
    }
    
    return res;
  }
  
  /**
   * Calcule la distance separant deux cases. 
   * @param array1 Premier case.
   * @param array2 Seconde case.
   * @result La distance arrondie au prochain entier.
   */ 
  public static int intDist(int[] array1, int[] array2) {
    int dx = Math.abs(array1[X] - array2[X]);
    int dy = Math.abs(array1[Y] - array2[Y]);
      
    return (int)Math.round( Math.sqrt( dx*dx + dy*dy )  );
  }

  /**
   * Calcule la distance separant deux cases. 
   * @param array1 Premier case.
   * @param array2 Seconde case.
   * @result La distance separant les deux cases.
   */ 
  public static double dblDist(int[] array1, int[] array2) {
    int dx = Math.abs(array1[X] - array2[X]);
    int dy = Math.abs(array1[Y] - array2[Y]);
      
    return Math.sqrt( dx*dx + dy*dy );
  }
  
}