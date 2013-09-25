/**********************************************************
*
* NOM : ChessFrame.java
* SUJET : Classe m�re (graphisme, souris, menus ...)
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

public class ChessFrame extends JFrame // Ancien public class ChessFrame extends JFrame : JFrame permet de cr�er des fen�tre
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

	/* g�re la souris */
	class Mouse extends MouseAdapter {  
		/* G�re le click de la souris : m est l'�v�nement de la souris */ 
		public void mouseClicked(MouseEvent m) {
			/* getClickCount et getButton m�thode de mouseEvent : TODO BUTTON1 */
			if (m.getClickCount() == 1 && m.getButton() == MouseEvent.BUTTON1) {
				/* TODO ? coordonnees du click */
				int[] coordonnees = new int[] { 
					m.getX() / CASE_ECHIQUIER , m.getY() / CASE_ECHIQUIER 
				}; 

				/* Premier clic on s�lectionne */
				if (caseSelectionne[0] == -1) {
					if (jeu.estPremierClickValide(coordonnees)) {  
						caseSelectionne = coordonnees;  // On enregistre les coordonnees

						int y = 8 - caseSelectionne[1];
						char x = (char)('A' + caseSelectionne[0]);
						System.out.println("Piece selectionn�e en [ " + x + " ]  [ " + y + " ]");
						repaint();
					}
				} else { /* Second clic on pose la pi�ce si c'est valide */  
					int[][] coup = new int[][] { { caseSelectionne[0] , caseSelectionne[1] } , {coordonnees[0] , coordonnees[1]} };
					jeu.traiterCoup(coup, true);
					caseSelectionne = new int[] {-1, -1}; // On r�-initialise la caseSelectionn�e
					repaint();
				}
			}
		}
	}
	
	/* Classe permettant d'int�ragir avec le menu : Impl�mente l'interface ActionListener */
	public class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { /* R�cup�re les actions sur le menu : e est l'event */
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
			else if ("� propos".equals(cmd)){
				System.out.println("Ouverture de la pop-up : � propos" ); 
				new About() ;
			}
			else if ("Defaut".equals(cmd)) {
				System.out.println("Changement d'�chiquier : Defaut ..."); 
				cPanel.changeEchiquier ("." + File.separator + "img" + File.separator + "echiquier" + File.separator + "defaut" + File.separator + "defaut.gif") ;
				repaint() ;
			}
			else if ("Bois".equals(cmd)) {
				System.out.println("Changement d'�chiquier : Bois ..."); 
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
			else if ("Fran�ais".equals(cmd)) {
				System.out.println("Changement de langue : Fran�ais ..."); 
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

		setBackground(Color.white); /* Couleur de la fen�tre */
		setTitle("Jeu d'echec - VIEUX Nicolas - 0.0.1"); // Titre de la fen�tre
		setSize(8 * CASE_ECHIQUIER + 20, 8 * CASE_ECHIQUIER + 50); /* Dimension de la fen�tre */

		this.getContentPane().add(cPanel); /* TODO getContentPane, add, cPanel */ 
		this.getContentPane().addMouseListener(new Mouse()); /* TODO : addMouseListener, Mouse */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /* TODO setDefaultCloseOperation, EXIT_ON_CLOSE */

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
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

		JMenuItem menuItem51 = new JMenuItem("Fran�ais", KeyEvent.VK_A);
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

		JMenuItem menuItem61 = new JMenuItem("R�gles du jeu", KeyEvent.VK_A);
		menuItem61.addActionListener(mL);
		menu6.add(menuItem61);
		
		/* ----- ? ----- */    
		JMenu menu7 = new JMenu("?");
		menu7.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu7);

		JMenuItem menuItem71 = new JMenuItem("� propos", KeyEvent.VK_A);
		menuItem71.addActionListener(mL);
		menu7.add(menuItem71);
	}
}