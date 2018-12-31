
public abstract class Pion implements Mouvement {
	String Type;
	String Col;
	boolean EtatParalysie;
	private Vertex Paralyse;

	public Pion(String col) {
		setCol(col);	
		setEtatParalysie(false);
		setParalyse(null);
	}

	/** 
	 * Getter et setter pour les attributs de Pion
	 */
	public abstract String getType();
	public abstract void setType();
	public String getCol(){
		return Col;
	};
	public void setCol(String Col) {
		this.Col = Col;
	}

	public boolean isEtatParalysie() {
		return EtatParalysie;
	}

	public void setEtatParalysie(boolean paralyse) {
		this.EtatParalysie = paralyse;
	}

	public Vertex getParalyse() {
		return Paralyse;
	}

	public void setParalyse(Vertex paralyse) {
		Paralyse = paralyse;
	}
	
//	public abstract static Vertex setParalyse();
}
