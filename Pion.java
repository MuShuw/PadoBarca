
public abstract class Pion {
	String Type;
	String Col;
	boolean paralyse;
	static final int CercleDeLaVie = 0; // mettre le graphe de bouffe
	

	public Pion(String col) {
		setCol(col);	
		setParalyse(false);
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

	public boolean isParalysed() {
		return paralyse;
	}

	public void setParalyse(boolean paralyse) {
		this.paralyse = paralyse;
	}
}
