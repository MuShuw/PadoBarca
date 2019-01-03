
public class PartieBarca {
	private Plateau plateau;
	private Player blanc;
	private Player noir;
	boolean GameOver = false;
	
	public PartieBarca() {
		
		// TODO Auto-generated constructor stub
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public Player getBlanc() {
		return blanc;
	}

	public void setBlanc(Player blanc) {
		this.blanc = blanc;
	}

	public Player getNoir() {
		return noir;
	}

	public void setNoir(Player noir) {
		this.noir = noir;
	}
}
