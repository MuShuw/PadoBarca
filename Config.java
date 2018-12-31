import java.util.Map;


public class Config {
	Coordonnees PlateauDim;
	Map<Coordonnees, Pion> PosPions;
	Map<Coordonnees, Boolean> PosMarre;
	
	public Config() {
		setPlateauDim(10, 10);
		
		// TODO Auto-generated constructor stub
	}

	private void setPlateauDim(int i, int j) {
		PlateauDim = new Coordonnees(i, j);
		// TODO Auto-generated method stub
		
	}

	public Coordonnees getPlateauDim() {
		return PlateauDim;
	}

	public void setPlateauDim(Coordonnees plateauDim) {
		PlateauDim = plateauDim;
	}

	public Map<Coordonnees, Pion> getPosPions() {
		return PosPions;
	}

	public void setPosPions(Map<Coordonnees, Pion> posPions) {
		PosPions = posPions;
	}

	public Map<Coordonnees, Boolean> getPosMarre() {
		return PosMarre;
	}

	public void setPosMarre(Map<Coordonnees, Boolean> posMarre) {
		PosMarre = posMarre;
	}
}
