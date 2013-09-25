/**********************************************************
*
* NOM : ChessFrame.java
* SUJET : Classe mère (graphisme, souris, menus ...)
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

public class ChessFrame extends JFrame // Ancien public class ChessFrame extends JFrame : JFrame permet de créer des fenêtre
{
	/* Constantes */
	public static final int MARGE_GAUCHE = 0; // En pixel
	public static final int MARGE_HAUT = 0;
	public static final int CASE_ECHIQUIER = 64; // Taille d'une case en pixel

	/* Instanciations */
	public Echiquier e; // Instancie la classe CEchiquier : @uml.property  name="e" @uml.associationEnd  
	public Jeu jeu; // Instancie la classe CJeu_1 : @uml.property  name="jeu" @uml.associationEnd  
	private ChessPanel cPanel = new ChessPanel() ; // Instancie la classe interne CGraphique : @uml.property  name="cPanel" @uml.associationEnd 

	/* Variables */
	private int caseSelectionne[]; 
 
	// Classe dérivée de JPanel : JPanel permet de créer les menus et de gérer les couleurs
	public class ChessPanel extends JPanel 	{
	
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

	/* gère la souris */
	class Mouse extends MouseAdapter {  
		/* Gère le click de la souris : m est l'évènement de la souris */ 
		public void mouseClicked(MouseEvent m) {
			/* getClickCount et getButton méthode de mouseEvent : TODO BUTTON1 */
			if (m.getClickCount() == 1 && m.getButton() == MouseEvent.BUTTON1) {
				/* TODO ? coordonnees du click */
				int[] coordonnees = new int[] { 
					m.getX() / CASE_ECHIQUIER , m.getY() / CASE_ECHIQUIER 
				}; 

				/* Premier clic on sélectionne */
				if (caseSelectionne[0] == -1) {
					if (jeu.estPremierClickValide(coordonnees)) {  
						caseSelectionne = coordonnees;  // On enregistre les coordonnees

						int y = 8 - caseSelectionne[1];
						char x = (char)('A' + caseSelectionne[0]);
						System.out.println("Piece selectionnée en [ " + x + " ]  [ " + y + " ]");
						repaint();
					}
				} else { /* Second clic on pose la pièce si c'est valide */  
					int[][] coup = new int[][] { { caseSelectionne[0] , caseSelectionne[1] } , {coordonnees[0] , coordonnees[1]} };
					jeu.traiterCoup(coup, true);
					caseSelectionne = new int[] {-1, -1}; // On ré-initialise la caseSelectionnée
					repaint();
				}
			}
		}
	}
	
	/* Classe permettant d'intéragir avec le menu : Implémente l'interface ActionListener */
	public class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { /* Récupère les actions sur le menu : e est l'event */
			String cmd = e.getActionCommand();
			if ("Quitter".equals(cmd)) {
				System.out.println("Quitter le jeu ...");
				System.exit(0);
			}
			else if ("Nouvelle partie".equals(cmd)) {
				System.out.println("nouvelle partie..."); 
				jeu.reset();
			}
			else if ("Continuer".equals(cmd)) {
				System.out.println("Continuer..."); 
				jeu.reset();
			}
			else if ("Servir une partie".equals(cmd)) {
				System.out.println("Servir une partie ...");
				Net.createServer(jeu);
			}
			else if ("Rejoindre une partie".equals(cmd)) {
				String addr = Util.readLine("Addresse ?");
				if (addr == null) return;{
					Net.createClient(jeu, addr);
				}
			}
			else if ("Changer le nom...".equals(cmd)){
				System.out.println("Changer le nom ...");
				Net.nick = Util.readLine("Nom ?");
			}
			else if ("À propos".equals(cmd)){
				System.out.println("Ouverture de la pop-up : À propos" ); 
				new About() ;
			}
			else if ("Defaut".equals(cmd)) {
				System.out.println("Changement d'échiquier : Defaut ..."); 
				cPanel.changeEchiquier ("." + File.separator + "img" + File.separator + "echiquier" + File.separator + "defaut" + File.separator + "defaut.gif") ;
				repaint() ;
			}
			else if ("Bois".equals(cmd)) {
				System.out.println("Changement d'échiquier : Bois ..."); 
				cPanel.changeEchiquier ("." + File.separator + "img" + File.separator + "echiquier" + File.separator + "bois" + File.separator + "bois.gif") ;
				repaint() ;
			}
			else if ("Jaune".equals(cmd)) {
				System.out.println("Changement de pions : Jaune ..."); 
				cPanel.changePions ("." + File.separator + "img" + File.separator + "pions" + File.separator + "jaune" + File.separator) ;
				repaint() ;
			}
			else if ("Noir".equals(cmd)) {
				System.out.println("Changement de pions : Noir ...");
				cPanel.changePions ("." + File.separator + "img" + File.separator + "pions" + File.separator + "noir" + File.separator) ;
				repaint() ;
			}
			else if ("Français".equals(cmd)) {
				System.out.println("Changement de langue : Français ..."); 
				repaint() ;
			}
			else if ("Anglais".equals(cmd)) {
				System.out.println("Changement de langue : Anglais ...");
				repaint() ;
			}
		}
	} 

	public ChessFrame(Jeu jeu) {
		this.jeu = jeu;
		this.e = jeu.getEchiquier(); /* TODO : getEchiquier */
		caseSelectionne = new int[] { -1, -1 };

		setBackground(Color.white); /* Couleur de la fenêtre */
		setTitle("Jeu d'echec - VIEUX Nicolas - 0.0.1"); // Titre de la fenêtre
		setSize(8 * CASE_ECHIQUIER + 20, 8 * CASE_ECHIQUIER + 50); /* Dimension de la fenêtre */

		this.getContentPane().add(cPanel); /* TODO getContentPane, add, cPanel */ 
		this.getContentPane().addMouseListener(new Mouse()); /* TODO : addMouseListener, Mouse */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /* TODO setDefaultCloseOperation, EXIT_ON_CLOSE */

		//Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//setLocation((screen.width - getSize().width)/2, (screen.height - getSize().height)/2); /* TODO : avant */
		setLocationRelativeTo(null); /* TODO : maintenant */
		creerMenu();

		this.setResizable(false);
		setVisible(true);
	} 

	public void onQuit() {
		// 	Net.socket();
		System.exit(0);	
	}

	public void creerMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		/* ----- FICHIER ----- */     
		JMenu menu1 = new JMenu("Fichier");
		menu1.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu1);
		MenuListener mL = new MenuListener();

		JMenuItem menuItem11 = new JMenuItem("Nouvelle partie", KeyEvent.VK_N);
		menuItem11.addActionListener(mL);
		menu1.add(menuItem11);
		menu1.addSeparator();
		
		JMenuItem menuItem12 = new JMenuItem("Continuer", KeyEvent.VK_Q);
		menuItem12.addActionListener(mL);
		menu1.add(menuItem12);
		menu1.addSeparator();
		
		JMenuItem menuItem13 = new JMenuItem("Quitter", KeyEvent.VK_Q);
		menuItem13.addActionListener(mL);
		menu1.add(menuItem13);
		
		/* ----- RESEAU ----- */    
		 JMenu menu2 = new JMenu("Reseau");
		menu2.setMnemonic(KeyEvent.VK_R);
		menuBar.add(menu2);

		JMenuItem menuItem21 = new JMenuItem("Changer le nom...", KeyEvent.VK_S);
		menuItem21.addActionListener(mL);
		menu2.add(menuItem21);
		menu2.addSeparator();

		JMenuItem menuItem22 = new JMenuItem("Servir une partie", KeyEvent.VK_S);
		menuItem22.addActionListener(mL);
		menu2.add(menuItem22);
		menu2.addSeparator();

		JMenuItem menuItem23 = new JMenuItem("Rejoindre une partie", KeyEvent.VK_R);
		menuItem23.addActionListener(mL);
		menu2.add(menuItem23);
		
		/* ----- PION ----- */    
		JMenu menu3 = new JMenu("Pion");
		menu3.setMnemonic(KeyEvent.VK_P);
		menuBar.add(menu3);

		JMenuItem menuItem31 = new JMenuItem("Jaune");
		menuItem31.addActionListener(mL);
		menu3.add(menuItem31);
		menu3.addSeparator();

		JMenuItem menuItem32 = new JMenuItem("Noir");
		menuItem32.addActionListener(mL);
		menu3.add(menuItem32);

		/* ----- ECHIQUIER ----- */    
		JMenu menu4 = new JMenu("Echiquier") ;
		menu4.setMnemonic(KeyEvent.VK_E);
		menuBar.add(menu4);
		
		JMenuItem menuItem41 = new JMenuItem("Defaut");
		menuItem41.addActionListener(mL);
		menu4.add(menuItem41);
		menu4.addSeparator();

		JMenuItem menuItem43 = new JMenuItem("Bois");
		menuItem43.addActionListener(mL);
		menu4.add(menuItem43);
		
		/* ----- LANGUE ----- */   
		JMenu menu5 = new JMenu("Langue");
		menu5.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu5);

		JMenuItem menuItem51 = new JMenuItem("Français", KeyEvent.VK_A);
		menuItem51.addActionListener(mL);
		menu5.add(menuItem51);
		menu5.addSeparator();

		JMenuItem menuItem52 = new JMenuItem("Anglais");
		menuItem52.addActionListener(mL);
		menu5.add(menuItem52);

		/* ----- AIDE ----- */    
		JMenu menu6 = new JMenu("Aide");
		menu6.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu6);

		JMenuItem menuItem61 = new JMenuItem("Règles du jeu", KeyEvent.VK_A);
		menuItem61.addActionListener(mL);
		menu6.add(menuItem61);
		
		/* ----- ? ----- */    
		JMenu menu7 = new JMenu("?");
		menu7.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu7);

		JMenuItem menuItem71 = new JMenuItem("À propos", KeyEvent.VK_A);
		menuItem71.addActionListener(mL);
		menu7.add(menuItem71);
	}
}