/** Classe representant le fou */
public class Fou extends Piece {

  public Fou(int couleur) {
 super(couleur);

    this.type = 1;
    if ((couleur != BLANC) && (couleur != NOIR))
      throw new RuntimeException("couleur invalide.");
    
    this.couleur = couleur;
    if (couleur == BLANC) nom = "FoB";
    else nom = "FoN";
  }
  
  public boolean deplacer (Echiquier e, int[][] coup, boolean silent) {
    int[] src = coup[0];
    int[] dst = coup[1];
    
    boolean coupExiste = (src[X] - src[Y] == dst[X] - dst[Y])  /* 1er sens, diag ou x-y cste */
      || (src[X] + src[Y] == dst[X] + dst[Y]); /* 2eme sens, diag ou x+y cste  */
    
    if (coupExiste && e.estVide(src, dst))
      return verifEchecDepl(e, coup, silent);

    return false;
  }

}