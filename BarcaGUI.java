import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class BarcaGUI {
	private int[][] couleur = {{255, 204, 153},
			{153, 51, 51}};
	private static Image[][] pions = new Image[2][3];
	private int width;
	private int height;
	private int nlig=8;
	private int ncol=8;
	private JFrame barcaFrame;
	private JPanel barcaPanel;
	private JPanel[][] barcaCases; 
	private JButton[][] barcaButton = new JButton[8][8];
	private JMenuBar barcaMenu;
	
	public BarcaGUI() {
		this(500, 500);
	}
	public void setPawnImg(){
		
	}
	public void setCases(Coordonnees dim) {
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
				
				// On crée le bouton du panel qu'on rend transparent
				barcaButton[i][j] = new JButton();
				barcaButton[i][j].setOpaque(true);
				barcaButton[i][j].setContentAreaFilled(false);
				barcaButton[i][j].setBorder(null);
				// On intègre l'image dans le bouton
				try {
				    File fileImg = new File("pawns/white_mouse.gif");
				    Image Img = ImageIO.read(fileImg);
				    Img = Img.getScaledInstance(this.width/this.ncol,
							this.height/this.nlig,
				    		java.awt.Image.SCALE_SMOOTH);
				    barcaButton[i][j].setIcon(new ImageIcon(Img));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				barcaCases[i][j].add(barcaButton[i][j], gbc);
				barcaFrame.add(barcaCases[i][j]);
			}
		}
		
	}
	public BarcaGUI(int width, int height) {
		
		// Configuration du Frame principale
		barcaFrame = new JFrame();
		setSize(width, height);
		barcaFrame.setSize(this.width, this.height);
		barcaFrame.setLayout(new GridLayout(8, 8, 1, 1));
		barcaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//
		
		// La barre de menu va contenir plusieurs boutons
		// On va les stocker dans une liste de string et boucler pour
		// leur création
		barcaMenu = new JMenuBar();
		String[] ButtonNames = {"Nouvelle Partie","Configurer","Sauvegarder","Restaurer","Abandon"};
		for ( int i = 0 ; i < ButtonNames.length; i++) {
			barcaMenu.add(new JMenu(ButtonNames[i])); // TODO - add functionality!
		}
		barcaFrame.setJMenuBar(barcaMenu);
		setCases(new Coordonnees(8, 8));
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
		barcaFrame.setVisible(true);
	}

	private void setSize(int width, int height) {
		this.width = width;
		this.height = height;		
	}
	public static void main(String[] args){
		new BarcaGUI(1000, 1000);
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