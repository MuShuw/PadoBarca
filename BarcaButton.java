import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class BarcaButton extends JButton {
	String sourceImage; 
	public String getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(String sourceImage) {
		this.sourceImage = sourceImage;
	}

	public BarcaButton() {
		
		// TODO Auto-generated constructor stub
	}
	
	public BarcaButton(String source) {
		
		super(icon);
		// TODO Auto-generated constructor stub
	}
	
	public BarcaButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public BarcaButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public BarcaButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public BarcaButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

}
