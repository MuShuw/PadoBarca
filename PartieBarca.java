import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PartieBarca {
	private Plateau plateau;
//	private Player noir;
//	private Player blanc;
	private String AlaMain;
	private IA bot;
	private boolean GameOver;
	
	
	public PartieBarca(Config config) {
		setPlateau(new Plateau(config));
		this.GameOver = false;
		
		
		// TODO Auto-generated constructor stub
	}
	
	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	/**
	 * Methodes relatives aux joueurs
	 */
	
//	public Player getBlanc() {
//		return blanc;
//	}
//
//	public void setBlanc(Player blanc) {
//		this.blanc = blanc;
//	}
//
//	public Player getAlaMain() {
//		return AlaMain;
//	}
//
//	public void setAlaMain(Player alaMain) {
//		AlaMain = alaMain;
//	}
//	
//	public Player getNoir() {
//		return noir;
//	}
//	
//	public void setNoir(Player noir) {
//		this.noir = noir;
//	}

//	public void tourDeJeu(Player ASonTour){
//		
//        Scanner in = new Scanner(System.in);
//		while ( ASonTour == getAlaMain() ){
//			
//			in.nextInt();
//		}
//	}

	
	public Player getBot() {
		return bot;
	}

	public void setBot(IA bot) {
		this.bot = bot;
	}
//	
//	private boolean isGameOver() {
//		return GameOver;
//	}
//
//	private void setGameOver(boolean gameOver) {
//		GameOver = gameOver;
//	}

	
	/**
	 * Méthodes pour l'initialisation de la partie et son déroulement
	 */
	// Creation de la configuration
	public static Config createConfig(Scanner scan){
		Config config = new Config();
		String entree;
		int classique = 1;
		
		// On va vérifier si l'utilisateur veut les configurations classiques
		while ( classique == 1 ){
			System.out.println("Voulez-vous utilisez la configuration classique ? O/N");
			entree = scan.nextLine();
			
			// On va vérifier si la réponse est oui ou non.
			if ( entree.charAt(0) == 'O') {
				return config;
			}
			else if ( entree.charAt(0) == 'N') classique = 0;
			
		}
		classique = 0;
		
		// On va lui demander si il veut choisir les dimensions
		while ( classique == 0 ){
			System.out.println("Voulez-vous personnaliser la dimension du plateau classique ? O/N");
			entree = scan.nextLine();
			
			// On va vérifier si la réponse est oui ou non.
			if ( entree.charAt(0) == 'O') { 
				config.setPlateauDim(PartieBarca.creatDim(scan));
				classique = 1;
			}
			else if ( entree.charAt(0) == 'N') classique = 1;
		}
		
		// On va lui demander si il veut conserver les types de pions classiques
		// A faire plus tard
		
		// On va lui demander si il veut modifier les relations
		
		// On va lui demander si il vaut sauvegarder  la config
		
		
		return config;
	}
	// Modification des dimensions du plateau classique
	private static Coordonnees creatDim(Scanner scan) {
		String entree;
		
		// Première étape : création des dimensions du plateau :
				int x = 0; int y = 0;
				// On boucle le temps que l'utilisateur choisisse une largeur valide
				while ( x == 0){
					System.out.print("Veuillez choisir une largeur de plateau, paire, et entre 6 et 20.");
					entree = scan.nextLine();
					x = Integer.parseInt(entree);
					if ( x%2 != 0 || x < 6 || x > 20 ){
						x = 0;
					}
				}
				// On fait la même chose pour la hauteur
				while ( y == 0){
					System.out.print("Veuillez choisir une hauteur de plateau, paire, et entre 6 et 20.");
					entree = scan.nextLine();
					y = Integer.parseInt(entree);
					if ( y%2 != 0 || y < 6 || y > 20 ){
						y = 0;
					}
				}
				Coordonnees dimension = new Coordonnees(x, y);
		return dimension;
	}

	private String runGame(Scanner scan){
		int xd, yd, xa, ya;
		this.AlaMain = "Blanc";
		while(!this.GameOver){
			System.out.println(" A la main = "+this.AlaMain);

			do{
				System.out.println("Veuillez tapez les coordonées de départ et d'arrivée ( col lin col lin )\n");
				yd = scan.nextInt();
				xd = scan.nextInt();
				ya = scan.nextInt();
				xa = scan.nextInt();
				System.out.println(xd+" "+yd+" "+xa+" "+ya+" ");
			}while(!moveFromTo(xd, yd, xa, ya, this.AlaMain));
			// rafraichissement de la liste des pions
			if ( this.AlaMain == "Noir") this.AlaMain = "Blanc";
			else this.AlaMain = "Noir";
			this.GameOver=didSmnWon();
			this.plateau.printASCII();
		}
		if ( this.AlaMain == "Noir") return "Blanc";
		else return "Noir";
	}
	
	public String[][] asciiForGui(){
		String[][] aFG = new String[this.plateau.Larg][this.plateau.Haut];
		for ( int i = 0 ; i < this.plateau.Larg ; i++) {
			for ( int j = 0 ; j < this.plateau.Haut ; j++) {
				aFG[i][j] = this.plateau.getCaseASCII(i,j);
			}
		}
		return aFG;
	}
	public String runGame(BarcaGUI gui){
		Coordonnees selection1 = gui.selection1;
		Coordonnees selection2 = gui.selection2; 
		int xd = 0, yd = 0, xa = 0, ya = 0;
		this.AlaMain = "Blanc";
		while(!this.GameOver){
			System.out.println(" A la main = "+this.AlaMain);

			do{
//				System.out.println("Veuillez tapez les coordonées de départ et d'arrivée ( col lin col lin )\n");
				selection1.setX(-1);
				selection2.setX(-1);
				while(selection2.getX() == -1){
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(" "+selection1.getX()+" "+selection1.getY());
					if ( selection1.getX() != -1){
						xd = selection1.getX();
						yd = selection1.getY();
						System.out.println(" passssssssse");
					}
					System.out.println(" "+selection2.getX()+" "+selection2.getY());

					if ( selection2.getX() != -1){
						xa = selection2.getX();
						ya = selection2.getY();
					}
//					System.out.println(xd+" "+yd+" "+xa+" "+ya+" ");
				}
//				System.out.println(xd+" "+yd+" "+xa+" "+ya+" ");
			}while(!moveFromTo(xd, yd, xa, ya, this.AlaMain));
			// rafraichissement de la liste des pions
			if ( this.AlaMain == "Noir") this.AlaMain = "Blanc";
			else this.AlaMain = "Noir";
			gui.refreshButImg(this.asciiForGui());
			this.GameOver=didSmnWon();
			this.plateau.printASCII();
		}
		if ( this.AlaMain == "Noir") return "Blanc";
		else return "Noir";
	}
	
	
	public boolean didSmnWon(){
		System.out.println("********* "+this.plateau.pionSurMarre());
		if ( this.plateau.pionSurMarre() >= 3 ) return true;
		return false;
	}
	
	private boolean moveFromTo(int xd, int yd, int xa, int ya, String tourDeJeu){
		return this.plateau.movePion(xd, yd, xa, ya, tourDeJeu);
	}
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);	
		System.out.println("Bienvenue dans Barca !");
		
		Config config;
		// Là on peut vérifier si une config a été donnée en argument
		
		// Sinon on demande à l'utilisateur ce qu'il veut faire
		config = PartieBarca.createConfig(scan);
		
		// Pour l'instant je vais créer une partie
		
		// Les configuration sont établis, nous allons crée la partie
		PartieBarca game = new PartieBarca(config);
		
		game.plateau.printASCII();
		
		// lancer une methode game() de partie 
		game.runGame(scan);
		
		// TESTS
		String userName = " ";
	    System.out.println("Enter username");
	    userName = scan.nextLine();
	    System.out.println("Username is: " + userName);  // Output user input 
	    // fin du programme
	    scan.close();
		
	}
}
