import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class BarcaGUI {
	private int[][] couleur = {{255, 204, 153},
			{153, 51, 51}};
	private Image[][] pions = new Image[2][3];
	private Image invisible;
	private static int width=700;
	private static int height=500;
	private int nrow;
	private int ncol;
	private JFrame barcaFrame;
	private JPanel barcaPlateau;
	private JPanel[][] barcaCases; 
	private BarcaCase[][] barcaButton;
	private JMenuBar barcaMenu;
	
	public BarcaGUI() throws InvocationTargetException, InterruptedException {
		this(new Config());
	}
	
	public BarcaGUI(Config config) throws InvocationTargetException, InterruptedException {
		// Recuperation des images
		this.ncol = config.getPlateauDim().getX();
		this.nrow = config.getPlateauDim().getY();

		setPawnImg();

		// Configuration du Frame principale
		barcaFrame = new JFrame("Barca : un jeu qui Swing !");
		barcaFrame.setPreferredSize(new Dimension(this.width, this.height));
		barcaFrame.setSize(this.width, this.height);
		barcaPlateau = new JPanel(new GridLayout(this.width, this.height, 1, 1));
		barcaPlateau.setPreferredSize(new Dimension(this.width-200,this.height)); 
		JPanel colDroite = new JPanel();
		colDroite.setPreferredSize(new Dimension(100, this.height));
		JPanel colGauche = new JPanel();
		colGauche.setPreferredSize(new Dimension(100, this.height));
		barcaFrame.add(colDroite, BorderLayout.EAST);
		barcaFrame.add(colGauche, BorderLayout.WEST);

		barcaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// La barre de menu va contenir plusieurs boutons
		// On va les stocker dans une liste de string et boucler pour
		// leur création
		barcaMenu = new JMenuBar();
		String[] ButtonNames = {"Nouvelle Partie","Configurer","Sauvegarder","Restaurer","Abandon"};
		for ( int i = 0 ; i < ButtonNames.length; i++) {
			barcaMenu.add(new JMenu(ButtonNames[i])); // TODO - add functionality!
		}
		
		barcaFrame.setJMenuBar(barcaMenu);
		barcaFrame.add(barcaPlateau, BorderLayout.CENTER);
		barcaFrame.setVisible(true);
		

		barcaFrame.setVisible(true);


	}

//	private static BufferedImage Invisible(int width, int height) {
//		BufferedImage invisibleIcon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		return invisibleIcon;
//	}
	
	
 	public void setPawnImg(){
		String[] imPions = {"1a.gif","1b.gif","2a.gif","2b.gif","3a.gif","3b.gif"};
		for ( int i = 0 ; i < imPions.length; i++ ){
			try {
			    File fileImg = new File("pawns/"+imPions[i]);
			    Image Img = ImageIO.read(fileImg);
			    pions[i%2][i%3] = Img.getScaledInstance(Img.getWidth(null), -1, Image.SCALE_SMOOTH);
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		}
		// Creation de l'image pour les icons invisibles
		try {
		    File fileImg = new File("pawns/invisible.gif");
		    invisible = ImageIO.read(fileImg);
		  } catch (Exception ex) {
		    System.out.println("Problem Invisible");
		  }
	}
	public void setCases(Config config) throws InvocationTargetException, InterruptedException {
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.weightx = 1.0; 
		gbc.weighty = 1.0; 
		// On récupère la taille du plateau
		int x = config.getPlateauDim().getX(), y = config.getPlateauDim().getY(), tmpCol;

		// On retire le premier plateau
		BorderLayout bl = (BorderLayout) this.barcaFrame.getLayout();
        this.barcaPlateau.remove(bl.getLayoutComponent(BorderLayout.CENTER));


		
		this.barcaPlateau = new JPanel(new GridLayout(x,y));
		this.barcaPlateau.setPreferredSize(new Dimension(500, 500));
		this.barcaCases = new JPanel[x][y];
		// On crée leurs boutons
		this.barcaButton = new BarcaCase[x][y];
		for ( int i = 0; i < x; i++) {
			for ( int j = 0; j < y; j++) {
				tmpCol = (i+j)%2;
				// On crée le panel de cette case
				// avec un gridbacklayout pour pouvoir remplir
				// le panel avec le boutton
				barcaCases[i][j] = new JPanel(new GridBagLayout());
				barcaCases[i][j].setBackground(
						new Color(
								this.couleur[tmpCol][0],
								this.couleur[tmpCol][1],
								this.couleur[tmpCol][2])
						);
				
//				// On crée la couleur de la case
//				barcaCases[i][j].setBackground(new Color(coul[0], coul[1], coul[2]));
				// On set le preferredSize
				barcaCases[i][j].setPreferredSize(new Dimension (this.barcaPlateau.getPreferredSize().width/ncol,
						this.barcaPlateau.getPreferredSize().height/nrow) );	
				System.out.println("La taille des cases est donc de ::"+barcaCases[i][j].getPreferredSize()+" ");
				
				
				////////////////////////////////////////
				// On crée le bouton du panel qu'on rend transparent
				barcaButton[i][j] = new BarcaCase();
				barcaButton[i][j].setOpaque(true);
				barcaButton[i][j].setContentAreaFilled(false);
				barcaButton[i][j].setBorder(null);
//				barcaButton[i][j].setIcon(new ImageIcon(
//							BarcaGUI.Invisible(
//								barcaCases[i][j].getPreferredSize().width,
//								barcaCases[i][j].getPreferredSize().height
//								)
//							)
//						);
				// On intègre l'image dans le bouton
					try {
					    Image Img = this.pions[0][2];
					    System.out.println(" Passe avec le iwidthnvisible.i= "+i+" j="+j);
					    barcaButton[i][j].setPion(Img);
					    barcaButton[i][j].setIcon(
					    		new ImageIcon(
					    				Img.getScaledInstance(
					    						barcaCases[i][j].getPreferredSize().width,
					    						-1,
					    						java.awt.Image.SCALE_SMOOTH)
					    						)
					    		);
					  } catch (Exception ex) {
					    System.out.println(ex);
					  }
				
				final int tmpi = i;
				final int tmpj = j;
				System.out.println(" tmp i et j = "+tmpi+" "+tmpj);
				barcaCases[i][j].add(barcaButton[i][j], gbc);
				EventQueue.invokeLater(new Runnable() {
					
					public void run() {
						// TODO Auto-geconfignerated method stub
						
					
						barcaCases[tmpi][tmpj].addComponentListener(new ComponentAdapter (){
							public void componentResized(ComponentEvent e){		

									Dimension size = barcaCases[tmpi][tmpj].getSize();
									System.out.println("La taille est de ::"+size);
									
									
									barcaButton[tmpi][tmpj].setSize(size);
									size = barcaButton[tmpi][tmpj].getSize();

									System.out.println("La taille est de ::"+size);

	//								Insets insets = barcaButton[tmpi][tmpj].getInsets();
	//								size.width -= insets.left + insets.right;
	//								size.height -= insets.top + insets.bottom;
									if ( size.width > size.height){
										size.width = -1;
									}else{
										size.height = -1;
									}
									Image tmp = barcaButton[tmpi][tmpj].getPion();
									barcaButton[tmpi][tmpj].setIcon(new ImageIcon (
											tmp.getScaledInstance(
													size.width,
													size.height,
													java.awt.Image.SCALE_FAST)
													)
											);
									
							}
						});
					}
				});
					
					
				
				barcaPlateau.add(barcaCases[i][j]);
				
			}
		}
		this.barcaFrame.add(barcaPlateau);
		this.barcaFrame.setVisible(true);
	}

	public void setPawnIcons(Config config) {
		
	}
	private void setSize(int width, int height) {
		BarcaGUI.width = width;
		BarcaGUI.height = height;		
	}
	public static void main(String[] args) throws InvocationTargetException, InterruptedException{
		BarcaGUI gui = new BarcaGUI();
		gui.setCases(new Config() );
		gui.barcaFrame.setVisible(true);
//		BarcaGUI barcaGUI = new BarcaGUI();
//		
//		JFrame barcaFrame = new JFrame("Barca");
//		barcaFrame.setSize(1500, 5000);
//		barcaFrame.add(barcaGUI.getGui());
//	    // Ensures JVM closes after frame(s) closed and
//	    // all non-daemon threads are finished
//	    barcaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	    // See http://stackoverflow.com/a/7143398/418556 for demo.
//	    barcaFrame.setLocationByPlatform(true);
//	    // ensures the frame is the minimum size it needs to be
//        // in order display the components within it
//	    barcaFrame.pack();
//        // ensures the minimum size is enforced.
//	    barcaFrame.setMinimumSize(barcaFrame.getSize());
//	    barcaFrame.setVisible(true);
		
		
	}
}