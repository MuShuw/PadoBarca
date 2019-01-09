import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class BarcaGUI {
	private int width;
	private int height;
	private JFrame barcaFrame;
	private JPanel barcaPanel;
	private JPanel[][] barcaCases = new JPanel[8][8];
	private JButton[][] barcaButton = new JButton[8][8];
	private JMenuBar barcaMenu;
	
	public BarcaGUI() {
		this(500, 500);
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
		
		// Panel
//		barcaPanel = new JPanel(new BorderLayout());
//		barcaPanel.setBackground(new Color(255,255,255));
		GridBagConstraints gridBagC;
		gridBagC = new GridBagConstraints();
		gridBagC.fill=GridBagConstraints.BOTH;
//		gridBagC.gridx=0;
//		gridBagC.gridy=0;
		gridBagC.weightx = 1.0; 
		gridBagC.weighty = 1.0; 
		
		int red, green, blue;
		for ( int i = 0; i < 8; i++) {
			for ( int j = 0; j < 8; j++) {
				if ( (i+j)%2 == 0) {
					red = 255 ; green = 204; blue = 153;
				}
				else {
					red = 153 ; green = blue = 51;
				}
				barcaCases[i][j] = new JPanel(new GridBagLayout());
				barcaCases[i][j].setBackground(new Color(red, green, blue));
				barcaButton[i][j] = barcaButton[i][j];
				barcaButton[i][j] = new JButton("yo");
				barcaButton[i][j].setOpaque(true);
				barcaButton[i][j].setContentAreaFilled(false);
				barcaButton[i][j].setBorder(null);
				
				// On va régler un gridBag pour que le bouton prenne tout l'espace du panel 
			
				barcaCases[i][j].add(barcaButton[i][j], gridBagC);
				barcaFrame.add(barcaCases[i][j]);
			}
		}
		
		
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
