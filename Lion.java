
public class Lion extends Pion implements MvtDiag {

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
	public boolean canMove(int xd, int yd, int xa, int ya){
		if ( Math.abs(xa-xd) == Math.abs(ya-yd) ) return true;
		return false;
	}
}

