/** Classe representant le cavalier */
public class Cavalier extends Piece {

  public Cavalier(int couleur) {
    super(couleur);
    
    this.type = 0;
    if ((couleur != BLANC) && (couleur != NOIR))
      throw new RuntimeException("couleur invalide.");
    
    this.couleur = couleur;
    if (couleur == BLANC) nom = "CaB";
    else nom = "CaN";
  }
  
  public boolean deplacer (Echiquier e, int[][] coup, boolean silent) {
    int[] src = coup[0];
    int[] dst = coup[1];
    
    /* les coups valides sont eloignes de sqrt(2*2 + 1*1) = sqrt(5) cases. */
    boolean coupExiste = Util.dblDist(src, dst) == Math.sqrt(5);
    
    if (coupExiste)
  return verifEchecDepl(e, coup, silent);

    return false;
  }
  
}