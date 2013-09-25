/** Classe representant la reine */
public class Reine extends Piece {
  
  public Reine(int couleur) {
    super(couleur);
    
    this.type = 3;
    
    if ((couleur != BLANC) && (couleur != NOIR))
      throw new RuntimeException("couleur invalide.");
    
    this.couleur = couleur;    
    if (couleur == BLANC) nom = "ReB";
    else nom = "ReN";
  }
  
  public boolean deplacer (Echiquier e, int[][] coup, boolean silent) {
    int[] src = coup[0];
    int[] dst = coup[1];
    
    boolean coupExiste = (src[X] - src[Y] == dst[X] - dst[Y])  /* 1er sens, diag ou x-y cste */
      || (src[X] + src[Y] == dst[X] + dst[Y]) /* 2eme sens, diag ou x+y cste  */
      || (dst[X] == src[X])  /* depl. vertical */
      || (dst[Y] == src[Y]); /* depl. horizontal */
    
    if (coupExiste && e.estVide(src, dst))
    return verifEchecDepl(e, coup, silent);

    return false;
  }
  
}