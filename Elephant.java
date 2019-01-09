
public class Elephant extends Pion implements MvtVertHori, MvtDiag {

	public Elephant(String Col) {
		super(Col);
		setType();
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

	public boolean canMove(int xd, int yd, int xa, int ya) {
		if ( Math.abs(xa-xd) == Math.abs(ya-yd) ) return true;
		else if ( xd == xa || yd == ya ) return true;
		return false;
	}
	
}

