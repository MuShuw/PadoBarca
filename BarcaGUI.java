import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class BarcaGUI {
	private static final JPanel GUI = new JPanel(new BorderLayout(100, 100));
	
	public BarcaGUI() {
		GUI.setBounds(500, 5, 5, 5);
		JToolBar BarDoutil = new JToolBar();
        BarDoutil.setFloatable(false);
		GUI.add(BarDoutil, BorderLayout.SOUTH);
		
		BarDoutil.add(new JButton("New Game")); // TODO - add functionality!
        BarDoutil.addSeparator(new Dimension(20, 20));
        BarDoutil.add(new JButton("Save")); // TODO - add functionality!
        BarDoutil.addSeparator(new Dimension(1, 1));
        BarDoutil.add(new JButton("Restore")); // TODO - add functionality!
        BarDoutil.addSeparator(new Dimension(1, 1));
        BarDoutil.add(new JButton("Resign")); // TODO - add functionality!
        BarDoutil.addSeparator(new Dimension(1, 1));
        
        
        JPanel chessBoard = new JPanel(new BorderLayout(500, 500));
	}
	
    public final JComponent getGui() {
        return GUI;
    }
	
	public static void main(String[] args){
		BarcaGUI bg = new BarcaGUI();
		
		JFrame f = new JFrame("ChessChamp");
		f.add(bg.getGui());
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
