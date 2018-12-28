
public class Board {
	Cases[][] cases;
	int Larg;
	int Haut;
	
	// Creation d'un simple plateau et affichage de celui-ci
	public Board(int hauteur, int largeur) {
		this.Larg = largeur;
		this.Haut = hauteur;
		System.out.println(Haut+" "+Larg);
		this.cases = new Cases[Haut][Larg];
		for ( int i = 0; i < Larg; i++) {
			for ( int j = 0; j < Haut; j++) {
				this.cases[j][i]=new Cases(i,j);
			}
		}
	};
	
	// Affichage du plateau 
	void printBoard() {
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				this.cases[j][i].printPosition();
			}
			System.out.println(" ");
		}
	}
	
	// Creation d'une simple case contenant la coordonnee
	private class Cases {
		int x;
		int y;
		Pion pion;
		
		private Cases(int x, int y) {
			this.x = x;
			this.y = y;
		};
		void printPosition() {
			System.out.print("x= "+x+" ");
			System.out.print("y= "+y+" ");
		}
	}
	
	public static void main(String[] args) {
		Board MonBoard = new Board( 10, 10);
		MonBoard.printBoard();
		
	}
}
