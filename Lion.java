
public class Lion extends Pion {

	public Lion(String Col) {
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
		Type = "Lion";
	}
}

