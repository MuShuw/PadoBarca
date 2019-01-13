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
	private int width;
	private int height;
	private int nlig=8;
	private int ncol=8;
	private JFrame barcaFrame;
	private JPanel barcaPlateau;
	private JPanel[][] barcaCases; 
	private JButton[][] barcaButton = new JButton[8][8];
	private JMenuBar barcaMenu;
	
	public BarcaGUI() throws InvocationTargetException, InterruptedException {
		this(700, 500);
	}
	
	public BarcaGUI(int width, int height) throws InvocationTargetException, InterruptedException {
		// Recuperation des images
		setPawnImg();
		
		// Configuration du Frame principale
		barcaFrame = new JFrame("Barca : un jeu qui Swing !");
		barcaFrame.setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		barcaFrame.setSize(this.width, this.height);
		barcaPlateau = new JPanel(new GridLayout(8, 8, 1, 1));
		barcaPlateau.setPreferredSize(new Dimension(width-200,height)); 
		JPanel colDroite = new JPanel();
		colDroite.setPreferredSize(new Dimension(100, height));
		JPanel colGauche = new JPanel();
		colGauche.setPreferredSize(new Dimension(100, height));
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
		barcaFrame.setVisible(true);
		// Panel
//		barcaPanel = new JPanel(new BorderLayout());
//		barcaPanel.setBackground(new Color(255,255,255));
		
		
		
//		for ( int i = 0; i < 8; i++) {
//			for ( int j = 0; j < 8; j++) {
//				if ( (i+j)%2 == 0) {
//					red = 255 ; green = 204; blue = 153;
//				}
//				else {
//					red = 153 ; green = blue = 51;
//				}
//				
//				// On met l'image
//				
//				barcaButton[i][j].addComponentListener(new ComponentAdapter() {
//
//                    public void componentResized(ComponentEvent e, Image Img) {
//                        JButton btn = (JButton) e.getComponent();
//                        Dimension size = btn.getSize();
//                        Insets insets = btn.getInsets();
//                        size.width -= insets.left + insets.right;
//                        size.height -= insets.top + insets.bottom;
//                        if (size.width > size.height) {
//                            size.width = -1;
//                        } else {
//                            size.height = -1;
//                        }
//                        Image scaled = Img.getScaledInstance(size.width, size.height,
//                        		java.awt.Image.SCALE_SMOOTH);
//                        btn.setIcon(new ImageIcon(scaled));
//                    }
//				});
//				barcaCases[i][j].add(barcaButton[i][j], gridBagC);
////				barcaCases[i][j].setSize(this.width/this.ncol,
////						this.height/this.nlig);
//				// On va régler un gridBag pour que le bouton prenne tout l'espace du panel 
//			
//				
//				barcaFrame.add(barcaCases[i][j]);
//			}
//		}
		
//		barcaFrame.pack();
		setCases(new Coordonnees(8, 8));
		barcaFrame.add(barcaPlateau, BorderLayout.CENTER);
		System.out.println(""+barcaCases[1][1].getPreferredSize());
		barcaFrame.setVisible(true);
	}

	
	public void setPawnImg(){
		String[] imPions = {"1a.gif","1b.gif","2a.gif","2b.gif","3a.gif","3b.gif"};
		for ( int i = 0 ; i < imPions.length; i++ ){
			try {
				System.out.println("pawns/"+imPions[i]);
			    File fileImg = new File("pawns/"+imPions[i]);
			    Image Img = ImageIO.read(fileImg);
			    System.out.println(i%2+" "+i%3);
			    pions[i%2][i%3] = Img.getScaledInstance(Img.getWidth(null), -1, Image.SCALE_SMOOTH);
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		}
	}
	public void setCases(Coordonnees dim) throws InvocationTargetException, InterruptedException {
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.weightx = 1.0; 
		gbc.weighty = 1.0; 
		// On récupère la taille du plateau
		int x = dim.getX(), y = dim.getY();
		// On crée les panels
		this.barcaCases = new JPanel[x][y];
		// On crée leurs boutons
		this.barcaButton = new JButton[x][y];
		int coul[] = new int[3];
		for ( int i = 0; i < x; i++) {
			for ( int j = 0; j < x; j++) {
				// On va régler la couleur a injecter dans la case
				if ( (i+j)%2 == 0) {
					coul[0] = 255 ; coul[1] = 204; coul[2] = 153;
				}
				else {
					coul[0] = 153 ; coul[1] = coul[2] = 51;
				}
				
				// On crée le panel de cette case
				// avec un gridbacklayout pour pouvoir remplir
				// le panel avec le boutton
				barcaCases[i][j] = new JPanel(new GridBagLayout());
				// On crée la couleur de la case
				barcaCases[i][j].setBackground(new Color(coul[0], coul[1], coul[2]));
				// On set le preferredSize
				barcaCases[i][j].setPreferredSize(new Dimension (barcaPlateau.getPreferredSize().width/ncol,
						barcaPlateau.getPreferredSize().height/nlig) );				
				
				
				////////////////////////////////////////
				// On crée le bouton du panel qu'on rend transparent
				barcaButton[i][j] = new JButton();
				barcaButton[i][j].setOpaque(true);
				barcaButton[i][j].setContentAreaFilled(false);
				barcaButton[i][j].setBorder(null);

				// On intègre l'image dans le bouton
				try {
				    File fileImg = new File("pawns/1a.gif");
				    Image Img = pions[1][2];
				    
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
				barcaCases[i][j].add(barcaButton[i][j], gbc);
				EventQueue.invokeLater(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						
					
						barcaCases[tmpi][tmpj].addComponentListener(new ComponentAdapter (){
							public void componentResized(ComponentEvent e){
		//						System.out.println(barcaCases[i][j].getSize().width+" "+barcaCases[i][j].getSize().height);
								Dimension size = barcaCases[tmpi][tmpj].getSize();
								
								
								barcaButton[tmpi][tmpj].setSize(size);
								Insets insets = barcaButton[tmpi][tmpj].getInsets();
//								size.width -= insets.left + insets.right;
//								size.height -= insets.top + insets.bottom;
								if ( size.width > size.height){
									size.width = -1;
								}else{
									size.height = -1;
								}
								ImageIcon tmp = (ImageIcon) barcaButton[tmpi][tmpj].getIcon();
								barcaButton[tmpi][tmpj].setIcon(new ImageIcon (
										tmp.getImage().getScaledInstance(
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
		
	}

	private void setSize(int width, int height) {
		this.width = width;
		this.height = height;		
	}
	public static void main(String[] args) throws InvocationTargetException, InterruptedException{
		new BarcaGUI();
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