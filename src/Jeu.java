
/** Classe represantant une partie d'echec. */

public class Jeu {

  /** L'echiquier. */
  private Echiquier echiquier;
  /** L'echiquier graphique. */
  private ChessFrame eG; 
  /** Les joueurs. */
  private Joueur[] joueurs;
  /** Couleur qui doit jouer. */
  private int couleur;
 
  /**
   * Initialise tout pour une partie.
   **/
  public Jeu() {
  this( new String[] { "joueur1", "joueur2" } );
  }

  /**
   * Initialise tout pour une partie.
   * @param noms Noms des joueurs.
   **/
  public Jeu(String[] noms) {
    
    if (noms.length != 2)
      noms = new String[] { "joueur1", "joueur2" };
    joueurs = new Joueur[] { new Joueur(noms[0]), new Joueur(noms[1]) };

    System.out.println(joueurs[0] + " joue blanc, " + joueurs[1] + " joue noir.\n");
    //System.out.println(e); // afficher l'echiquier texte

  this.reset();
  
    
/*  
 
 	// Partie de code qui gere la partie en mode texte, et qui est devenu 
 	// obsolete.
 
    int[][] coup;

    while (true) {
      do {
        couleur = nbCoups % 2; // si nbCoups est pair c'est blanc qui joue, sinon noir
        eG.toFront();
        eG.repaint();
        coup = joueurs[couleur].demanderCoup();

        boolean coupValide = !e.estVide(coup[0])  // case source non vide ?
        && (e.getPiece(coup[0]).getCouleur() == couleur) // bonne couleur ?
        && !(!e.estVide(coup[1]) && (e.getPiece(coup[1]).getCouleur() == couleur))
        // on essaie de prendre sa propre piece ?
        && e.getPiece(coup[0]).deplacer(e, coup); // coup autorise ?

      } while( ! coupValide );

      System.out.println(e);

      nbCoups++;
    }
    */
  }
  
  /**
   * Accesseur pour l'echiquier graphique
   * @result L'attribut echiquier graphique de la classe jeu.
   */  
  public ChessFrame getFrame() { return eG; }
    /**
   * Accesseur pour les joueurs
   * @param n Quel joueur ? (blanc / noir);
   * @result Le joueur en question.
   */
  public Joueur getJoueur(int n) { return joueurs[n]; }  

  /**
   * Accesseur pour l'echiquier
   * @result L'attribut echiquier de la classe jeu.
   */
  public Echiquier getEchiquier() { return this.echiquier; }

  /** 
   * Verifie si le premier click fait sur l'echiquier est valide, c.a.d. n'est
   * pas sur une case vide ou sur une piece du joueur adverse.
   * @param coord coordonees du click transmises par la classe de la souris.
   * @result vrai si le click est correct
   */
  public boolean estPremierClickValide(int[] coord) {
 if (Net.couleur != -1 && !Net.monTour) return false;

    return !echiquier.estVide(coord) 
     && (echiquier.getPiece(coord).getCouleur() == couleur);
  }

  /**
   * Traite un coup (souris), verifie si il y a mat.
   * @param coup Coup a traiter.
   * @param local si on joue en reseau, est-ce un coup local ou un coup distant ?
   */
  public boolean traiterCoup(int[][] coup, boolean local) {
    int coulPieceSrc = echiquier.getPiece(coup[0]).getCouleur();
    
    boolean coupValide = !echiquier.estVide(coup[0])  // case source non vide ?
      && (coulPieceSrc == couleur) // bonne couleur ?
      && !(!echiquier.estVide(coup[1]) 
        && (echiquier.getPiece(coup[1]).getCouleur() == couleur))
      // on essaie pas de prendre sa propre piece ?
      && (Net.couleur == -1 || (local == (Net.couleur == coulPieceSrc) ) ) 
      // si on joue en reseau, est-ce la bonne couleur ?
      && echiquier.getPiece(coup[0]).deplacer(echiquier, coup, false); // coup autorise ?

    if (coupValide) {
    
    if (Net.couleur != -1 && Net.monTour) {
      String c = "" + coup[0][0] + coup[0][1] + coup[1][0] + coup[1][1];
      Net.SendText(Net.socket, "MOVE " + c);
      Net.monTour = false;
      
      System.out.println("Vous ("+joueurs[couleur]+") jouez: " 
           + Util.convCoup(coup) + ".");
    } else {
     System.out.println(joueurs[couleur] 
          + " joue: " + Util.convCoup(coup) + ".");
    }
    
    if (echiquier.estEnEchec(Util.coulOpp(couleur))[0] != -1) {
     System.out.println("** Echec au roi " 
        + Piece.strCoul[Util.coulOpp(couleur)] + ".");
    
    if (echiquier.estEchecMat(Util.coulOpp(couleur))) {
       System.out.println("** Echec et mat. '" + joueurs[couleur]
           + "' gagne la partie." );
     }       
    }
      
       //System.out.println(e);
       eG.repaint();
       couleur = Util.coulOpp(couleur);

      return true;
    }
    return false;
  }

  /**
   * 'Resette' la partie.
   */
  public void reset() {
    echiquier = new Echiquier();
    if (eG != null) eG.dispose();
 eG = new ChessFrame(this);

  Net.monTour = false;
  Net.couleur = -1; // partie locale      
    couleur = Piece.BLANC;

    System.out.println("C'est a " + joueurs[couleur] + " de commencer." );
  }
  
  /**
   * Point d'entree du programme.
   * @param args Arguments passes au programme (Noms des joueurs).
   */
  public static void main(String[] args) {
    System.out.println("Jeu d'échec - VIEUX Nicolas - 0.0.1\n");
    Jeu j = new Jeu(args);

  }

}