import java.util.Map;
import java.util.HashMap;
public class Config {
	Coordonnees PlateauDim;
	Map<Coordonnees, Pion> PosPions;
	Map<Coordonnees, Boolean> PosMarre;

	
	public Config(int x, int y) {
		setPlateauDim(x, y);
		System.out.println("passe coordonnées");
		setPosPions(null);
		System.out.println("passe pospions");
		setPosMarre(null);
		System.out.println("passe posmarre");

		// TODO Auto-generated constructor stub
	}
	public Config() {
		this(10,10);
		// TODO Auto-generated constructor stub
	}
	private void setPlateauDim(int i, int j) {
		if ( i%2 != 0 || j%2 != 0 || i < 6 || j < 6) setPlateauDim(new Coordonnees(10, 10));
		else setPlateauDim(new Coordonnees(i, j));		
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
		if ( posPions == null) {
			PosPions = new HashMap<Coordonnees, Pion>();
			
			// Creation des variables nécessaires
			int driftL = 1;
			int driftH = 1;
			int midL = PlateauDim.getX()/2;
			int h = PlateauDim.getY();
			
			// Creation des lions
			PosPions.put( new Coordonnees(0, midL+driftL), new Lion("Blanc"));
			PosPions.put( new Coordonnees(0, midL-2*driftL), new Lion("Blanc"));
			PosPions.put( new Coordonnees(h-driftH, midL+driftL), new Lion("Noir"));
			PosPions.put( new Coordonnees(h-driftH, midL-2*driftL), new Lion("Noir"));
			
			// Creation des elephants
			PosPions.put( new Coordonnees(0, midL), new Elephant("Blanc"));
			PosPions.put( new Coordonnees(0, midL-driftL), new Elephant("Blanc"));
			PosPions.put( new Coordonnees(h-driftH, midL), new Elephant("Noir"));
			PosPions.put( new Coordonnees(h-driftH, midL-driftL), new Elephant("Noir"));
			
			// Creation des souris
			PosPions.put( new Coordonnees(driftH, midL), new Souris("Blanc"));
			PosPions.put( new Coordonnees(driftH, midL-driftL), new Souris("Blanc"));
			PosPions.put( new Coordonnees(h-2*driftH, midL), new Souris("Noir"));
			PosPions.put( new Coordonnees(h-2*driftH, midL-driftL), new Souris("Noir"));

		}
		else PosPions = posPions;
	}

	public Map<Coordonnees, Boolean> getPosMarre() {
		return PosMarre;
	}

	public void setPosMarre(Map<Coordonnees, Boolean> posMarre) {
		if ( posMarre == null) { 
			PosMarre = new HashMap<Coordonnees, Boolean>();
			
			// Creation des variables necessaires
			int drift = 1;
			int midH = PlateauDim.getY()/2;
			int midL = PlateauDim.getX()/2;
			
			// Creation des marres
			PosMarre.put(new Coordonnees(midH+drift,midL+drift), true);
			PosMarre.put(new Coordonnees(midH+drift,midL-2*drift), true);
			PosMarre.put(new Coordonnees(midH-2*drift,midL+drift), true);
			PosMarre.put(new Coordonnees(midH-2*drift,midL-2*drift), true);
		}
		else PosMarre = posMarre;
	}
}
