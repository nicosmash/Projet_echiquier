/** Classe representant le roi */
public class Roi extends Piece {

  public Roi(int couleur) {
    super(couleur);
    
    this.type = 4;
    
    if ((couleur != BLANC) && (couleur != NOIR))
      throw new RuntimeException("couleur invalide.");

    this.couleur = couleur;    
    if (couleur == BLANC) nom = "RoB";
    else nom = "RoN";
  }
  
  /* antecedent:  - this est sur src 
   *              - dst ne peut contenir de piece de notre couleur
   * 
   */
   public boolean deplacer (Echiquier e, int[][] coup, boolean silent) {
    int[] src = coup[0];
    int[] dst = coup[1];

    boolean coup1Case = Util.intDist(src, dst) == 1;

 boolean coupRoque = false;
 
 for (int i = 0; i <= 1; i++) {
  if (e.roques[couleur][i] 
   && Util.compIntArrays(dst, Util.translation(src, 2 + (-4 * i), 0))) {
    
       int y = (e.getPiece(src).getCouleur() == BLANC ? 7 : 0);
       int x = (dst[X] == 2 ? 0 : 7); 
       int[] posTour = new int[] {x, y};
           
    if (e.estEnEchec(couleur)[0] != -1 
     || e.influence(dst, Util.coulOpp(couleur), true)[0] != -1) {
     System.out.println("** Roque interdit, roi en echec."); 
    
    } else if (!e.estVide(src, posTour)) {
     System.out.println("** Roque interdit, obstruction de piece(s)."); 
    
    } else if (e.influence(src, dst, Util.coulOpp(couleur), true)[0] != -1) {
     System.out.println("** Roque interdit, trajet sous echec."); 
    }
    else {
     coupRoque = true; 
    }   
  }
 }
 
    if ( coup1Case || coupRoque )
      return verifEchecDepl(e, coup, silent);
      
    return false;
  }

  
}