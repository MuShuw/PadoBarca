import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
//import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


public class BarcaGUI {
	private int[][] couleur = {{255, 204, 153},
			{153, 51, 51}};
	private Image[][] pions = new Image[2][3];
	private Image invisible;
	private static int width=700;
	private static int height=500;
	private int nrow;
	private int ncol;
	public JFrame barcaFrame;
	private JPanel barcaPlateau;
	private JPanel[][] barcaCases; 
	private BarcaCase[][] barcaButton;
	private JMenuBar barcaMenu;
	public Coordonnees selection1;
	public Coordonnees selection2;
	
	public BarcaGUI() throws InvocationTargetException, InterruptedException {
		this(new Config());
	}
	
	public BarcaGUI(Config config) throws InvocationTargetException, InterruptedException {
		// Recuperation des images
		this.ncol = config.getPlateauDim().getX();
		this.nrow = config.getPlateauDim().getY();
		this.selection1 = new Coordonnees(-1, -1);
		this.selection2 = new Coordonnees(-1, -1);
		setPawnImg();

		// Configuration du Frame principale
		barcaFrame = new JFrame("Barca : un jeu qui Swing !");
		barcaFrame.setPreferredSize(new Dimension(BarcaGUI.width, BarcaGUI.height));
		barcaFrame.setSize(BarcaGUI.width, BarcaGUI.height);
		barcaPlateau = new JPanel(new GridLayout(BarcaGUI.width, BarcaGUI.height, 1, 1));
		barcaPlateau.setPreferredSize(new Dimension(BarcaGUI.width-200,BarcaGUI.height)); 
		JPanel colDroite = new JPanel();
		colDroite.setPreferredSize(new Dimension(100, BarcaGUI.height));
		JPanel colGauche = new JPanel();
		colGauche.setPreferredSize(new Dimension(100, BarcaGUI.height));
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
	public void paintMarre(Config config){
		Map<Coordonnees, Boolean> map = config.getPosMarre();
		for (Map.Entry<Coordonnees, Boolean> entry : map.entrySet()) {
		    Coordonnees key = entry.getKey();
		    this.barcaCases[key.getX()][key.getY()].setBackground(Color.BLUE);
		}
	}
	public void setButImg(Config config){
		int coul, piece;
		Map<Coordonnees, Pion> map = config.getPosPions();
		System.out.print("Plop \n");
		for (Map.Entry<Coordonnees, Pion> entry : map.entrySet()) {
		    Coordonnees key = entry.getKey();
		    Pion value = entry.getValue();
		    if ( value.getCol() == "Blanc" ) coul = 0;
		    else coul = 1;
		    if( value.getType() == "Elephant") piece = 0;
		    else if( value.getType() == "Lion") piece = 1;
		    else piece = 2;
		    
		    this.barcaButton[key.getX()][key.getY()].setPion(this.pions[coul][piece]);
		}
	}
	public void setPawnImg(){
		String[] imPions = {"1a.gif","1b.gif","2a.gif","2b.gif","3a.gif","3b.gif"};
		for ( int i = 0 ; i < imPions.length; i++ ){
			try {
//			    File fileImg = new File("pawns/"+imPions[i]);
			    Image Img = ImageIO.read(getClass().getResourceAsStream("pawns/"+imPions[i]));
			    System.out.println(" La type est :" +i%3+" et l'image est "+imPions[i]);
			    pions[i%2][i%3] = Img.getScaledInstance(Img.getWidth(null), -1, Image.SCALE_SMOOTH);
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
		}
		// Creation de l'image pour les icons invisibles
		try {
//		    File fileImg = new File("pawns/invisible.gif");
		    invisible = ImageIO.read(getClass().getResourceAsStream("pawns/invisible.gif"));
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
        this.barcaFrame.remove(this.barcaPlateau);


		
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
				
				
				////////////////////////////////////////
				// On crée le bouton du panel qu'on rend transparent
				barcaButton[i][j] = new BarcaCase();
				barcaButton[i][j].setOpaque(true);
				barcaButton[i][j].setContentAreaFilled(false);
				barcaButton[i][j].setBorder(null);
				barcaButton[i][j].setCoord(x, y);
//				barcaButton[i][j].setIcon(new ImageIcon(
//							BarcaGUI.Invisible(
//								barcaCases[i][j].getPreferredSize().width,
//								barcaCases[i][j].getPreferredSize().height
//								)
//							)
//						);
				// On intègre l'image dans le bouton
					try {
					    Image Img = this.invisible;
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
				
				this.barcaButton[i][j].addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {
						System.out.println(" i ="+tmpi+" j= "+tmpj);
						if (BarcaGUI.this.selection1.getX() == -1){
							BarcaGUI.this.selection1.setX(tmpj);
							BarcaGUI.this.selection1.setY(tmpi);
							
						}
						else if (BarcaGUI.this.selection2.getX() == -1){
							BarcaGUI.this.selection2.setX(tmpj);
							BarcaGUI.this.selection2.setY(tmpi);
							
						}
						
					}
				});
				
				barcaCases[i][j].add(barcaButton[i][j], gbc);
				EventQueue.invokeLater(new Runnable() {
					
					public void run() {
						// TODO Auto-geconfignerated method stub
						
					
						barcaCases[tmpi][tmpj].addComponentListener(new ComponentAdapter (){
							public void componentResized(ComponentEvent e){		

									Dimension size = barcaCases[tmpi][tmpj].getSize();
									
									
									barcaButton[tmpi][tmpj].setSize(size);
									size = barcaButton[tmpi][tmpj].getSize();


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
	public void refreshIcon(){
		for ( int i = 0; i < this.nrow ; i++){
			for ( int j = 0; j < this.ncol ; j++  ){
				Dimension size = barcaButton[i][j].getSize();
				Image tmp = barcaButton[i][j].getPion();
				barcaButton[i][j].setIcon(new ImageIcon (
						tmp.getScaledInstance(
								size.width,
								size.height,
								java.awt.Image.SCALE_FAST)
								)
						);
			}
		}
	}
 
	@SuppressWarnings("unused")
	private void setSize(int width, int height) {
		BarcaGUI.width = width;
		BarcaGUI.height = height;		
	}
	public static void main(String[] args) throws InvocationTargetException, InterruptedException{
		BarcaGUI gui = new BarcaGUI();
		Config config = new Config();
		gui.setCases(config);
		gui.barcaFrame.setVisible(true);
		gui.setButImg(config);
		gui.barcaFrame.setVisible(true);
		System.out.print("passe \n");
		gui.refreshIcon();
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