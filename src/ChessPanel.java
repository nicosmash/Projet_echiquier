/**********************************************************
*
* NOM : ChessPanel.java
* SUJET : 
*
* AUTEUR : VIEUX Nicolas	
* VERSION : 0.0.1
* CREATION : 23/09/2013
* DER. MODIF. : 23/09/2013
*
* ACCES SRC : C:\Users\Nico\Desktop\Master\Projet_Echec\src
* FABRICATION : Avec l'IDE Eclipse : Run
*
* CONTRAINTES : Inner class (ChessFrame - ChessPanel ...)
*
* TODO : S'occuper des TODO
*
********************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ChessPanel extends JPanel // Ancien public class ChessFrame extends JFrame : JFrame permet de créer des fenêtre
{
	/* Constantes */
	public static final int MARGE_GAUCHE = 0; // En pixel
	public static final int MARGE_HAUT = 0;
	public static final int CASE_ECHIQUIER = 64; // Taille d'une case en pixel
	
	/* Variables */
	private int caseSelectionne[]; 
 
	// Classe dérivée de JPanel : JPanel permet de créer les menus et de gérer les couleurs
	public class ChessPanel (Echiquier e) {
	
		public String urlPions = "." + File.separator + "img" + File.separator + "pions" + File.separator; // Chemin d'accès aux images pions
		public String urlEchiquier = "." + File.separator + "img" + File.separator + "echiquier" + File.separator; // Chemin d'accès à l'image échiquier
	
		/* Noms des icônes pion */
		final String[][] imgFile = new String[][] {
			{ "cheval_j.png", "fou_j.png", "pion_j.png", "reine_j.png", "roi_j.png", "tour_j.png" }, 
			{ "cheval_n.png", "fou_n.png", "pion_n.png", "reine_n.png", "roi_n.png", "tour_n.png" }
		};

		/* Mise en place des éléments : @param g Objet sur lequel on dessine @cons Tout l'echiquier est trace */
		public void paintComponent(Graphics g) {
			int[] coordonnees;
			
			super.paintComponent(g);
			creerEchiquier(g) ;
			Piece p;
			
			for (int y = 0; y < 8; y++)	{
				for (int x = 0; x < 8; x++) {
					coordonnees = new int[] {x,y};
					p = e.getPiece(coordonnees); /* TODO : getPiece */ 
					if (p != null) {
						placerPiece(g, p.getType(), p.getCouleur(), coordonnees); /* TODO : getType, getCouleur */ 
					}
				}
			
				/* Colore la case selectionnée */
				if (caseSelectionne[0] != -1) {
					g.setColor(Color.orange);
					g.drawRect(MARGE_GAUCHE + caseSelectionne[0] * CASE_ECHIQUIER, MARGE_HAUT + caseSelectionne[1] * CASE_ECHIQUIER, CASE_ECHIQUIER, CASE_ECHIQUIER);
					g.drawRect(MARGE_GAUCHE + caseSelectionne[0] * CASE_ECHIQUIER + 1, MARGE_HAUT + caseSelectionne[1] * CASE_ECHIQUIER + 1, CASE_ECHIQUIER - 2, CASE_ECHIQUIER - 2);
				}
			}
		}

		/* Permet de placer une pièce sur l'échiquier : g Objet sur lequel on dessine, type Type de piece a placer, couleur Couleur de la piece a placer, position coordonneesoonees ou placer la piece */
		public void placerPiece(Graphics g, int type, int couleur, int[] position) {
			Image img = Toolkit.getDefaultToolkit().getImage(urlPions + imgFile[couleur][type]); /* TODO : Toolkit, getDefaultToolkit, getImage */
			g.drawImage(img, position[0] * CASE_ECHIQUIER, position[1] * CASE_ECHIQUIER,this);  /* drawImage : Permet d'afficher l'image */  
		}

		/* g Objet sur lequel on dessine. */
		public void creerEchiquier (Graphics g) {
			Image img = Toolkit.getDefaultToolkit().getImage(urlEchiquier) ;
			g.drawImage(img,0,0,this) ;
		}

		/* Permet de changer d'echiquier : s est le nom du fichier de l'image de l'echiquier. */
		public void changeEchiquier (String s) {
			this.urlEchiquier = s ;
		}
		
		/* Permet de changer le chemin d'acces aux images des pions : s est le nom du chemin */
		public void changePions (String s) {
			this.urlPions = s ;    
		}
	}
}