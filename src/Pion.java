/** Classe representant le pion */
public class Pion extends Piece {
  
  public Pion(int couleur) {
   super(couleur);
   
    this.type = 2;
    
    if ((couleur != BLANC) && (couleur != NOIR))
      throw new RuntimeException("couleur invalide.");
    
    this.couleur = couleur;
    if (couleur == BLANC) nom = "PiB";
    else nom = "PiN";
  }
  
  /* antecedents: - 'this' est place a coup[0] 
   *              - le coup est dans l'echiquier
   *              - dst ne peut contenir une piece blanche
   * 
   */
  public boolean deplacer (Echiquier e, int[][] coup, boolean silent) {
    int[] src = coup[0];
    int[] dst = coup[1];
    int y;        
    
    if (couleur == BLANC) y = -1;
    else y = 1;

    boolean av2Cases = ( e.estVide(dst) 
        && ( src[Y] == 6 || src[Y] == 1 )  /* pion intact */
        && Util.compIntArrays(dst, Util.translation(src, 0, 2*y)) );

    /* ici, 2 rangees existent ou les pions sont autorises a sauter une case,
     * mais le cas ou un pion est sur la rangee de l'adversaire et essayerais de sauter
     * une case n'intervient pas, car dst serai alors en dehors de l'echiquier.
     */
    
    boolean av1Case = (Util.compIntArrays(dst, Util.translation(src, 0, y)) )  /* coup vers l'avant */
      && (e.getPiece(dst) == null); /* ... vers une case vide (on ne peut manger vers l'avant) */
    
    boolean avDiagD = Util.compIntArrays(dst, Util.translation(src, 1, y)) /* coup diag droite */
      && !e.estVide(dst);
                         
    boolean avDiagG = Util.compIntArrays(dst, Util.translation(src, -1, y))  /* diag gauche */
      && !e.estVide(dst);

    if (av2Cases || av1Case || avDiagD || avDiagG)
  return verifEchecDepl(e, coup, silent);
    
    return false;
  }
  
}