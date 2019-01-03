import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class BarcaGUI {
	private static final JPanel GUI = new JPanel(new BorderLayout(3, 3));
	
	public BarcaGUI() {
		GUI.setBounds(5, 5, 5, 5);
		JToolBar BarDoutil = new JToolBar();
		GUI.add(BarDoutil, BorderLayout.NORTH);
		JToolBar plop = new JToolBar();
		GUI.add(plop, BorderLayout.EAST);
		
		BarDoutil.add(new JButton("New Game")); // TODO - add functionality!
        BarDoutil.addSeparator();
        BarDoutil.add(new JButton("Save")); // TODO - add functionality!
        BarDoutil.addSeparator();
        BarDoutil.add(new JButton("Restore")); // TODO - add functionality!
        BarDoutil.addSeparator();
        BarDoutil.add(new JButton("Resign")); // TODO - add functionality!
        BarDoutil.addSeparator();
        
        
        JPanel chessBoard = new JPanel(new BorderLayout(0, 9));
	}
	
    public final JComponent getGui() {
        return GUI;
    }
	
	public static void main(String[] args){
		BarcaGUI cg = new BarcaGUI();
		
		JFrame f = new JFrame("ChessChamp");
		f.add(cg.getGui());
	    // Ensures JVM closes after frame(s) closed and
	    // all non-daemon threads are finished
	    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    // See http://stackoverflow.com/a/7143398/418556 for demo.
	    f.setLocationByPlatform(true);
	 // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
	}
}
