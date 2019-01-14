
public class Barca {
	private BarcaGUI gui;
	private PartieBarca game;
	
	private Barca(){
		
	}
	public static void linkFrontBack(BarcaGUI gui, PartieBarca jeu){
		
	}
	
	public static void main(String[] args) throws Throwable {
		int x = 10, y = 10;
		try {
			x = Integer.parseInt(args[0]);
			y = Integer.parseInt(args[1]);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Config config;
		if ( x%2 != 0 || y%2 != 0 || x < 6 || y < 6 || x > 20 || y > 20 ) config = new Config(x, y);
		else config = new Config();
		
		// lancement de l'interface
		
		BarcaGUI gui = new BarcaGUI();
		
		// Création des configuration par défaut
		gui.setCases(config);
		
		gui.setCases(config);
		gui.barcaFrame.setVisible(true);
		gui.setButImg(config);
		gui.barcaFrame.setVisible(true);
		System.out.print("passe \n");
		gui.refreshIcon();
		gui.barcaFrame.setVisible(true);
		gui.paintMarre(config);
		gui.barcaFrame.setVisible(true);
		PartieBarca game = new PartieBarca(config);
		
	}
}
