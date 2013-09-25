import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** Classe representant le dialogue a propos */
public class About extends JFrame {
	private static final long serialVersionUID = 1L;
/** Constante de la marge gauche. */	
  public final static int MARGE_GAUCHE = 3 ;
  /** Constatne de la marge haute. */
  public final static int MARGE_HAUT = 25 ;
  /** Repertoire contenant les images. */
  public final static String url = "./images/" ;
  /** Objet pour le degrade. */
  private GradientPaint gradient = new GradientPaint(0, 0, Color.white, 175, 175, Color.lightGray,
                                                     true);
  
  /** Derivee de JPanel qui va contenir le graphique. */
  public class AboutPanel extends JPanel {
	private static final long serialVersionUID = 1L;

/**
   * Tout le tracage est effectue ici.
   * @param g Objet sur lequel on dessine.
   * @cons Une fenetre about tracee.
   */
    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      super.paintComponent(g);       
      g2.setPaint(gradient) ;
      g.fillRect(0,0,400,150);
      Image img = Toolkit.getDefaultToolkit().getImage(url + "morph.gif");
      g.drawImage(img, 290,5,this);
      g.setFont(new Font("arial black",0,12)) ;
      g.setColor(Color.black) ;
      g.drawString("javaChess",10,10) ;
      g.setFont(new Font("arial",0,10)) ;
      g.drawString("by Paul Schumacher & Sebastien Pinchard",10,25) ;
      g.setFont(new Font("arial black",0,10)) ;
      g.drawString("Em@ils:",10,45) ;
      g.setFont(new Font("arial",0,10)) ;
      g.drawString("paul@yes-f.com",10,55) ;
      g.drawString("hysteria06300@yahoo.fr",10,65) ;
      g.drawString("PARCE QU'ON LE VAUT BIEN....",10,90) ;
    }    
  }
  
  /**
   * Constructeur de la fenetre.
   */
  public About() {    
    setTitle("About");
    setSize(400,150);
    
    AboutPanel aPanel = new AboutPanel();
    this.getContentPane().add(aPanel);    
    this.setResizable(false);
    setVisible(true);
  } 
  
}


