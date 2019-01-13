
public class Barca {
	private BarcaGUI gui;
//	private Config config;
	
	public static void main(String[] args) throws Throwable {
		// lancement de l'interface
		BarcaGUI gui = new BarcaGUI();
		
		// Création des configuration par défaut
		Config config = new Config();
		gui.setCases(config);
	}
}
