
public class Elephant extends Pion {

	public Elephant(String Col) {
		super(Col);
		setType();
		// TODO Auto-generated constructor stub
	}

	/** Getter et setter pour les attributs de Pion
	 * 
	 */
	public String getType() {
		return Type;
	}
	public void setType() {
		Type = "Elephant";
	}
}

