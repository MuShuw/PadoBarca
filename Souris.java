
public class Souris extends Pion implements MvtVertHori {

	public Souris(String Col) {
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
		Type = "Souris";
	}
	public boolean canMove(int xd, int yd, int xa, int ya){
		if ( xd == xa || yd == ya ) return true;
		return false;
	}
}

