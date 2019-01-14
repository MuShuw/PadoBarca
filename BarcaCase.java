import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;


public class BarcaCase extends JButton {
	Image pion;
	Coordonnees coord;
	String strPion;
	public BarcaCase() {
		// TODO Auto-generated constructor stub
	}

	public BarcaCase(Icon arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BarcaCase(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BarcaCase(Action arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BarcaCase(String arg0, Icon arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public Image getPion() {
		return pion;
	}

	public void setPion(Image pion) {
		this.pion = pion;
	}
	public void setCoord(int x, int y){
		this.coord = new Coordonnees(x, y);
	}
	public Coordonnees getCoord(){
		return this.coord;
	}

}
