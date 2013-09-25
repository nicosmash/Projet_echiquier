/** Classe mere qui sert a representer les pieces du jeu d'echec. */

public class Piece {
 /** Constante de la couleur BLANC. */
 final static int BLANC = 0;
 /** Constante de la couleur NOIR. */
 final static int NOIR = 1;
 final static String[] strCoul = new String[] {"blanc", "noir"};
 
 /** Constante du petit roque */
 final static int PETIT = 0;
 /** Constante du grand roque */
 final static int GRAND = 1; 

 /** Constante des abcisses. */
 final static int X = 0 ;
 /** Constante des ordonees. */
 final static int Y = 1 ;

 /** Constante du type cavalier. */
 final static int CAVALIER = 0 ;
 /** Constante du type fou. */
 final static int FOU = 1;
 /** Constante du type pion. */
 final static int PION = 2;
 /** Constante du type reine. */
 final static int REINE = 3;
 /** Constante du type roi. */
 final static int ROI = 4;
 /** Constante du type tour. */
 final static int TOUR = 5;
 /** Constante des chaines represantant les six pieces */
 final static String[] strPieces = new String[] 
      { "cavalier","fou","pion","reine","roi","tour" };
  
 /** Type de la piece. */  
 protected int type;
 /** Nom comme il apparait sur l'echiquier texte. */
 protected String nom;
 /** Couleur de la piece. */
 protected int couleur;

 /** 
  * Constructeur de la piece.
  * @param couleur Couleur a attribuer a la piece.
  */  
 public Piece(int couleur) {
  couleur = couleur;
     nom = "Piece";
     type = -1;
   }
 /** 
  * Accesseur pour la couleur de la piece.
  * @result La couleur.
  */  
 public int getCouleur() { return couleur; }

 /** 
  * Accesseur pour le type de la piece.
  * @result Le type.
  */  
 public int getType() { return type; }
 
    /** 
  * Accesseur de l'objet.
  * @result Une chaine de caracteres representant la piece.
  */  
 public String toString() { return nom; }
   
   /** 
    * Essaie de deplacer la piece this.
    * @param e L'echiquier sur lequel on ve deplacer this.
    * @param coup Le coup selon lequel on veut deplacer this.
    * @result Vrai si le coup est possible, faux sinon.
    * @ante Le coup est sur l'echiquier
    * @cons
    */
   public boolean deplacer (Echiquier e, int[][] coup, boolean silent) {
  return false;
   }
   
   
   /** 
    * Verifie si deplacer cette piece mettrait le roi en echec.
    * On stocke la piece sur la case de destination dans une variable (si il
    * y'en a une, sinon c'est 'null' et c'est pas grave non plus), on efectue 
    * le coup, on verifie pour l'echec. Si il y a echec, on refait le coup 
    * inverse et on replace la piece qu'on avait eventuellement prise.
    * @param e Echiquier sur lequel on doit deplacer la piece.
    * @param coup Coup a effectuer.
    * @param silent Est-ce qu'on rale si le coup est incorrect ?
    * @result Vrai si le coup est bon, Faux si il y a risque d'echec.
    * @ante ?
    * @cons ?
    */
   public boolean verifEchecDepl(Echiquier e, int[][] coup, boolean silent) {

     Piece piece = e.getPiece(coup[1]);
     e.deplacerPiece(coup);

     int[] pos = e.estEnEchec(this.couleur);
     if (pos[0] != -1) {
         if (!silent)
          System.out.println("** Coup incorrect, le roi " + strCoul[this.couleur] +
            " serai en echec de: " + strPieces[e.getPiece(pos).getType()]);
         e.deplacerPiece(Util.inverserCoup(coup));
         e.setPiece(piece, coup[1]);
         return false;
     }
      
     return true;
 } 

}