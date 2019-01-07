import java.util.Scanner;

public class PartieBarca {
	private Plateau plateau;
	private Player blanc;
	private Player noir;
	private IA bot;
	private Player AlaMain;
	private boolean GameOver = false;
	
	public PartieBarca(Config config) {
		setPlateau(new Plateau(config));
		
		
		// TODO Auto-generated constructor stub
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public Player getBlanc() {
		return blanc;
	}

	public void setBlanc(Player blanc) {
		this.blanc = blanc;
	}

	public Player getBot() {
		return bot;
	}

	public void setBot(IA bot) {
		this.bot = bot;
	}

	public Player getNoir() {
		return noir;
	}
	
	public void setNoir(Player noir) {
		this.noir = noir;
	}
	public Player getAlaMain() {
		return AlaMain;
	}

	public void setAlaMain(Player alaMain) {
		AlaMain = alaMain;
	}

	private boolean isGameOver() {
		return GameOver;
	}

	private void setGameOver(boolean gameOver) {
		GameOver = gameOver;
	}

	public void tourDeJeu(Player ASonTour){
		
        Scanner in = new Scanner(System.in);
		while ( ASonTour == getAlaMain() ){
			
			in.nextInt();
		}
	}

	public static Config createConfig(Scanner scan){
		Config config = new Config();
		String entree;
		int classique = 1;
		
		// On va vérifier si l'utilisateur veut les configurations classiques
		while ( classique == 1 ){
			System.out.println("Voulez-vous utilisez la configuration classique ? O/N");
			entree = scan.nextLine();
			System.out.print(entree);
			
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
		
		String x;
		int y;
		
		game.plateau.printASCII();
		
		// TESTS
		String userName = " ";
	    System.out.println("Enter username");
	    userName = scan.nextLine();
	    System.out.println("Username is: " + userName);  // Output user input 
		
	}
}
