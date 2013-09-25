/** Classe representant la tour */
public class Tour extends Piece {
  
  public Tour(int couleur) {
    super(couleur);
    
    this.type = 5;
    
    if ((couleur != BLANC) && (couleur != NOIR))
      throw new RuntimeException("couleur invalide.");
    
    this.couleur = couleur;    
    if (couleur == BLANC) nom = "ToB";
    else nom = "ToN";
  }
  
  public boolean deplacer (Echiquier e, int[][] coup, boolean silent) {
    int[] src = coup[0];
    int[] dst = coup[1];
    
    boolean coupExiste = (dst[X] == src[X])  /* depl. vertical */
      || (dst[Y] == src[Y]); /* depl. horizontal */
    
    if (coupExiste && e.estVide(src, dst))
  return verifEchecDepl(e, coup, silent);

    return false;
  }
  
}