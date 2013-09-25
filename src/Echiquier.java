/** Classe representant l'echiquier */

public class Echiquier {
  final static boolean DEBUG = false;  
 
  /** Constante de largeur de l'echiquier */
  final static int HORIZONTALE = 8 ;
  /** Constante de hauteur de l'echiquier */
  final static int VERTICALE = 8 ;
  /** Constante de la couleur BLANC. */
  final static int BLANC = 0;
  /** Constante de la couleur NOIR. */
  final static int NOIR = 1; 
  /** Constante du petit roque */
  final static int PETIT = 0;
  /** Constante du grand roque */
  final static int GRAND = 1; 
  /** Constante des abcisses. */
  final static int X = 0 ;
  /** Constante des ordonees. */
  final static int Y = 1 ;
  
  /** L'echiquier */
  private Piece[][] grid ; 
  /** La position des rois pour verifier l'echec, etc.. */
  public int[][] Roi; 
  /** Indicateurs pour les roques 0: petit roque / 1: grd roque */
  public boolean[][] roques = new boolean[][] {{true,true},{true,true}};

  /**
   * Cree le tableau et place toutes les pieces sur l'echiquier.
   */
  public Echiquier () {
    grid = new Piece[HORIZONTALE][VERTICALE] ;
    for (int i = 0; i < HORIZONTALE; i++) {
      for (int j = 0; j < VERTICALE; j++) 
        grid[i][j] = null ;        
    }
    Roi = new int[2][2];
    
    grid [0][0] = new Tour(NOIR);
    grid [1][0] = new Cavalier(NOIR) ;
    grid [2][0] = new Fou(NOIR) ;
    grid [3][0] = new Reine(NOIR) ;
    grid [4][0] = new Roi(NOIR);
    Roi[NOIR] = new int[] {4, 0};
    grid [5][0] = new Fou(NOIR) ;
    grid [6][0] = new Cavalier(NOIR) ;
    grid [7][0] = new Tour(NOIR) ;
    for (int i=0; i < HORIZONTALE; i++)
      grid [i][1] = new Pion(NOIR) ;
    grid [0][VERTICALE - 1] = new Tour(BLANC);
    grid [1][VERTICALE - 1] = new Cavalier(BLANC) ;
    grid [2][VERTICALE - 1] = new Fou(BLANC) ;
    grid [3][VERTICALE - 1] = new Reine(BLANC) ;
    grid [4][VERTICALE - 1] = new Roi(BLANC);
    Roi[BLANC] = new int[] {4, VERTICALE - 1};
    grid [5][VERTICALE - 1] = new Fou(BLANC) ;
    grid [6][VERTICALE - 1] = new Cavalier(BLANC) ;
    grid [7][VERTICALE - 1] = new Tour(BLANC) ;
    for (int i=0; i < HORIZONTALE; i++)
      grid [i][6] = new Pion(BLANC) ;
    
  }
  
  /**
   * Accesseur pour les pieces.
   * @param pos Case dont on aimerais obtenir la piece.
   * @result La piece en question.
   */
  public Piece getPiece(int[] pos) {
    return grid[ pos[X] ][ pos[Y] ];
  }
  
  public void setPiece(Piece p, int[] pos) {
    grid[ pos[X] ][ pos[Y] ] = p;
  }
  

  /** Determine si le roi d'une couleur est en echec.
   * @param couleur La couleur du roi en question
   * @result {-1,-1} si le roi n'est pas en echec, sinon la case qui contient 
   * la premiere piece trouvee qui le met en echec.
   * @ante ??
   * @cons ??
   * */
 public int[] estEnEchec(int couleur) {
  return influence(Roi[couleur], Util.coulOpp(couleur), true);
  }

  /** 
   * Determine si une des pieces d'une certaine couleur a de l'influence 
   * sur une case.
   * @param position La case a verifier.
   * @param couleur La couleur dont on veut verifier si elle a de l'influence 
   * sur cette case.
   * @param traiterAttaquePion Si vrai, on traite que l'attaque du pion sinon, 
   * on traite que les mouvements du pion. Autrement dit, si c'est vrai on
   * traite toutes les attaques et sinon tous les mouvements.
   * @result {-1,-1} si la case n'est pas sous influence, sinon la case qui contient 
   * la premiere piece trouvee qui a de l'influence sur cette case.
   * @ante ??
   * @cons ??
   * */
 public int[] influence(int position[], int couleur, boolean traiterAttaquePion) {
    
    /** vecteurs Orthogonaux (Horiz. et Vert.) et diagonales */
     final int[][] vectOrth = new int[][] { {-1,0}, {0,-1}, {1,0}, {0,1} };
     final int[][] vectDiag = new int[][] { {-1, -1}, {1,-1}, {1,1}, {-1,1} };
  

     int[] pos;
     Piece p;
     /* ---------- reine, tours et fous  -----------*/
     for (int i = 0; i < 4; i++) {
       pos = chercherPiece(position, vectOrth[i]);
       if (pos[0] == -1) continue;
       p = getPiece(pos);
       if ((pos[0] != -1) && ( p.getType() == Piece.TOUR || p.getType() == Piece.REINE ) 
            && (p.getCouleur() == couleur) )
       return pos;
     } 
    
     for (int i = 0; i < 4; i++) {
      pos = chercherPiece(position, vectDiag[i]);
      if (pos[0] == -1) continue;
       p = getPiece(pos);
       if ((pos[0] != -1) && ( p.getType() == Piece.FOU || p.getType() == Piece.REINE ) 
            && (p.getCouleur() == couleur) )
      return pos;
     }
 
    /* ---------- roi  -------------------------*/
   if (Util.intDist(position, Roi[couleur]) == 1)
     return Roi[couleur];

     /* ---------- pions  -----------------------*/
     int y = (couleur == BLANC ? 1 : -1);
    
     if (traiterAttaquePion) {
      for (int i = -1; i <= 1; i += 2) {
          int[] trsl = Util.translation(position, i, y);
          if (Util.estPosValide(trsl) && !estVide(trsl) 
           && getPiece(trsl).getCouleur() == couleur
           && getPiece(trsl).getType() == Piece.PION)
        return trsl;
      }
     } else {
         int[] trsl = Util.translation(position, 0, y);
         if (Util.estPosValide(trsl) && !estVide(trsl) 
       && getPiece(trsl).getCouleur() == couleur
       && getPiece(trsl).getType() == Piece.PION)
     return trsl;
      
         trsl = Util.translation(position, 0, 2 * y);
         if (Util.estPosValide(trsl) 
          && trsl[Y] == (couleur == BLANC ? 6 : 1)
          && !estVide(trsl) 
       && getPiece(trsl).getCouleur() == couleur
       && getPiece(trsl).getType() == Piece.PION)
     return trsl;

     }
    
     /* ---------- cavaliers  -------------------*/
 
   int[][] cavMvts = new int[][] { {-2,-1},{-1,-2},{1,-2},{2,-1},
          {2,1},{1,2},{-1,2},{-2,1} };
         
     for (int i = 0; i < 8; i++) {
       int[] trsl = Util.translation(position, cavMvts[i][0], cavMvts[i][1]);
       if ( Util.estPosValide(trsl) && !estVide(trsl) 
          && getPiece(trsl).getCouleur() == couleur
          && getPiece(trsl).getType() == Piece.CAVALIER)
         return trsl;  
   }
   
   
   return new int[] { -1, -1} ;  
}

  /** 
   * Determine si une droite sur l'echiquier est sous l'influence d'une 
   * certaine couleur.
   * @param src Le premier point de la droite.
   * @param dst Le deuxieme point de la droite
   * @param couleur La couleur en question
   * @param traiterAttaquePion Si vrai, on traite que l'attaque du pion sinon, 
   * on traite que les mouvements du pion.
   * @result {-1,-1} si la droite n'est pas sous l'influence de la couleur, 
   * sinon la case de la premiere piece trouvee qui influence cette droite.
   * @ante ??
   * @cons ??
   * */
 public int[] influence(int[] src, int[] dst, int couleur, boolean traiterAttaquePion) {

     int x = src[X], y = src[Y];
     int dx = ( dst[X] > x ? 1 : -1);
     int dy = ( dst[Y] > y ? 1 : -1);
     int[] res;
    
     while ( dx != 0 || dy != 0 )  {
        if (x == dst[X]) dx = 0;
        if (y == dst[Y]) dy = 0;      

   res = influence(new int[] {x, y}, couleur, traiterAttaquePion);
        if ( (res[0] != -1) 
             && !((x == src[X]) && (y == src[Y])) /*on ignore la 1ere case*/
             && !((dx == 0) && (dy == 0)) ) /* .. et la derniere de la droite */     
          return res;
      
        x += dx; y += dy;
     }
    
    return new int[] {-1,-1};
  }  
  
  /**
   * Determiner si couleur est en echec et mat.
   * @param couleur La couleur qui nous interesse.
   * @result Vrai si il y a echec et maths, Faux sinon.
   */
  public boolean estEchecMat(int couleur) {
   /* On procede comme cela :
    * - voir si le roi peut s'en sortir en se deplacant, sinon
    * - si il y a double echec, -> echec & mat, sinon
    * - voir si une de ses pieces peut bloquer l' echec ou prendre la piece
    * qui fait l'echec 
    * - sinon mat.
    */
 
  final int[][] vects = new int[][] { {-1,0}, {0,-1}, {1,0}, {0,1},
          {-1, -1}, {1,-1}, {1,1}, {-1,1} };

   int[] echec1 = estEnEchec(couleur);
   if (echec1[0] == -1) return false;
   
   debug("!! estEchecMat(): On tente de deplacer le roi..");
   
   for (int i = 0; i < 8; i++) {
    
     int[] depl = Util.translation(Roi[couleur], vects[i][0], vects[i][1]);
     boolean coupValide = Util.estPosValide(depl) && 
      ( estVide(depl) || getPiece(depl).getCouleur() != couleur );
    
    if (!coupValide) continue;
  
     int[][] coup = new int[][] {{Roi[couleur][0],Roi[couleur][1]},{depl[0],depl[1]}};
     
     int[] dst = coup[1];
     Piece tmp = getPiece(dst);
     
     if ( getPiece(Roi[couleur]).deplacer(this, coup, true) ) {
       deplacerPiece(Util.inverserCoup(coup)); // on evite les checks
          setPiece(tmp, dst);
          return false; // on a reussi a deplacer le roi sans qu'il reste en echec
     }
  }

 debug("!! estEchecMat(): On a pas reussi a deplacer le roi");

 // on determine si il y a echec seulement ou double echec.
 Piece tmp = getPiece(echec1);
 setPiece(null, echec1);
 int[] echec2 = estEnEchec(couleur);
 setPiece(tmp, echec1);
 
 if (echec2[0] != -1) return true; 
 // double echec et on a pas reussi a bouger le roi, donc echec et mat

 debug("!! estEchecMat(): echec simple. On essaie de prendre..");
 
 int[] p = influence(echec1, couleur, true);
 if (p[0] != -1 && getPiece(p).getType() != Piece.ROI) 
  return false;

 debug("!! estEchecMat(): On ne peut prendre la piece");
 
 int type = getPiece(echec1).getType();
 if (type == Piece.PION || type == Piece.CAVALIER) return true;
 // on risque pas de bloquer l'echec si c'est ces pieces la...
 
 debug("!! estEchecMat(): On essaie de bloquer l'echec...");
 
 p = influence(echec1, Roi[couleur], couleur, false);
 
 debug("influence(): " + p[0] + "," + p[1]);
 
 return (p[0] == -1) || (getPiece(p).getType() == Piece.ROI);
  // le roi ne risque pas de bloquer l'echec
 
  }
  
  /**
   * Determiner si une case est vide.
   * @param pos La case a examiner.
   * @result Vrai si la case est vide, Faux sinon.
   */
  public boolean estVide(int[] pos) {
    return ((grid[ pos[X] ][ pos[Y] ]) == null);
  }
  
  /**
   * Deplacer une piece sur l'echiquier.
   * @param coup Le deplacement: de la case coup[0] vers coup[1].
   */
  public void deplacerPiece(int[][] coup) {
    int[] src = coup[0];
    int[] dst = coup[1];
    
    int srcType = getPiece(src).getType();
    int srcCoul = getPiece(src).getCouleur();
    
    if ( srcType == Piece.ROI ) {
      Roi[srcCoul] = dst;  /* on met a jour la 'memoire' des rois */
    
      roques[srcCoul][PETIT] = false;  /* on met a jour la 'memoire' des roques */
      roques[srcCoul][GRAND] = false;
    }
    
    if ( srcType == Piece.TOUR ) {
      if (src[X] == 0 && src[Y] == 0) roques[NOIR][GRAND] = false;
      else if (src[X] == 7 && src[Y] == 0) roques[NOIR][PETIT] = false;
      else if (src[X] == 0 && src[Y] == 7) roques[BLANC][GRAND] = false;
      else if (src[X] == 7 && src[Y] == 7) roques[BLANC][PETIT] = false;
    }

    grid[ dst[X] ][ dst[Y] ] = grid[ src[X] ][ src[Y] ];
    grid[ src[X] ][ src[Y] ] = null;
    
    // deplacement des tours pour les roques
    if ( (getPiece(dst).getType() == Piece.ROI) && (Util.intDist(src, dst) == 2) ) {

     int y = (getPiece(dst).getCouleur() == BLANC ? 7 : 0);
     int x1 = (dst[X] == 2 ? 0 : 7); 
     int x2 = (dst[X] == 2 ? 3 : 5);
  int[][] coupTour = new int[][] { {x1 , y} , {x2 , y} };
  deplacerPiece(coupTour);
    }
    
    // promotions de pions
    if ( getPiece(dst).getType() == Piece.PION && (dst[Y] == 0 || dst[Y] == 7) )
  grid[ dst[X] ][ dst[Y] ] = new Reine(getPiece(dst).getCouleur());

  }
  
  /** Cherche une piece en partant de la case src vers 'direction'.
   * Elle ignore la premiere case.
   * @param src La case source de la recherche.
   * @param direction Un vecteur direction qui indique vers ou chercher.
   * @result La case de la premiere piece trouvee, sinon {-1, -1}.
   * @ante 'src' est sur l'echiquier, 'direction' est un vecteur valide.
   * @cons ??
   **/
  public int[] chercherPiece(int[] src, int[] direction) {
    int[] pos = new int[] { src[X], src[Y] };
    
    do {
      pos[X] += direction[X];
      pos[Y] += direction[Y];
     
    } while (Util.estPosValide(pos) && (getPiece(pos) == null)); 
       /* condition a la fin pour ignorer la 1ere case */

 if (!Util.estPosValide(pos)) return new int[] {-1,-1};    
    return pos;
  }


  /**
   * Determine si la droite en parametre est vide sur l'echiquier 
   * (extremites excluses).
   * @param src Case representant le debut de la droite.
   * @param dst Case representant la fin de la droite.
   * @result Vrai si la droite est vide, Faux sinon.
   */
  public boolean estVide(int[] src, int[] dst) {
    int x = src[X], y = src[Y];
    int dx = ( dst[X] > x ? 1 : -1);
    int dy = ( dst[Y] > y ? 1 : -1);
    
    while ( dx != 0 || dy != 0 )  {
      if (x == dst[X]) dx = 0;
      if (y == dst[Y]) dy = 0;      

      if ( !estVide( new int[] {x,y} )
            && !((x == src[X]) && (y == src[Y])) /*on ignore la 1ere case*/
            && !((dx == 0) && (dy == 0))  /* .. et la derniere de la droite */     
            ) 
        return false;
      
      x += dx; y += dy;
    }
    
    return true;
  }  
  
  /**
   * Renvoie l'echiquier sous forme de chaine de caracteres.
   * @result La chaine en question.
   */
  public String toString() {
    String str = "  --a---b---c---d---e---f---g---h--\n";
    
    for (int i = 0; i < VERTICALE; i++) {
      str += VERTICALE - i + " |";
      for (int j = 0; j < HORIZONTALE; j++) {
        if (grid[j][i] == null)
          str += "   |";
        else
          str += grid[j][i] + "|";

        if (j == HORIZONTALE - 1) str += " " + (VERTICALE - i); /* ajouter les numeros a la fin */
      }
      str += "\n";
    }
    
    str += "  --a---b---c---d---e---f---g---h--\n";
    return str;
  }
  
  /**
   * Procedure qui sert au debogage uniquement.
   * @param text Texte de debogage.
   **/
  public void debug(String text) {
    if (DEBUG) System.out.println(text);
  }
  
}