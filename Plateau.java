// Cette classe cree des objets avec lesquelles les joeurs peuvent interagir 
public class Plateau {
	Cases[][] cases;
	int Larg;
	int Haut;
	Cases[] PionNoirs = new Cases[6]; // TODO rendre le nombre de pions modulable
	Cases[] PionBlancs = new Cases[6]; // Est la pour le bot
	
	/** 
	 * Creation du plateau sans dimension, appelle la creation de plateau a 2 entiers pour creer
	 * le plateau par defaut
	 */
	public Plateau() {
		this(10,10);
	}
	/** 
	 * Creation d'un simple plateau avec une hauteur et largeur donnes
	 * et affichage de celui-ci @ToDo completer les doc
	 * 
	 */
	public Plateau(int hauteur, int largeur) {
		setLarg(largeur);
		setHaut(hauteur);
		System.out.println(Haut+" "+Larg); // @remove safe print
		setCases(new Cases[Haut][Larg]);
		for ( int i = 0; i < Larg; i++) {
			for ( int j = 0; j < Haut; j++) {
				this.cases[j][i]=new Cases(i,j);
			}
		}
		setMarres();
		setPions();
	};
	
	/** 
	 * Affichage du plateau 
	 * methode de test pour voir la composition du plateau
	 */
	void printPlateauCoord() {
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				this.cases[j][i].printPosition();
			}
			System.out.println(" ");
		}
	}
	void printPlateauPion() {
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				System.out.print(this.cases[j][i].getPion());
			}
			System.out.println(" ");
		}
	}
	void printPlateauMarre() {
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				System.out.print(this.cases[j][i].isMarre());
			}
			System.out.println(" ");
		}
	}
	void printPlateau() {
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				this.cases[i][j].printPosition();
				if ( this.cases[i][j].getPion() == null ) {
				System.out.print(this.cases[i][j].getPion());
				}else {
					System.out.print(this.cases[i][j].getPion().getType()+" "
									+this.cases[i][j].getPion().getCol()+" "
									+(this.cases[i][j].getPion() instanceof Elephant));
				}
				System.out.print(this.cases[i][j].isMarre());
			}
			System.out.println(" ");
		}
	}
	
	/**
	 * Getter et setter des attributs du plateau
	 */
	@SuppressWarnings("unused")
	private Cases[][] getCases() {
		return cases;
	}
	private void setCases(Cases[][] cases) {
		this.cases = cases;
	}
	public int getLarg() {
		return Larg;
	}
	private void setLarg(int larg) {
		Larg = larg;
	}
	public int getHaut() {
		return Haut;
	}
	private void setHaut(int haut) {
		Haut = haut;
	}

	/* 
	 * Methodes pour l'itialisation complete du plateau
	 */
	/**
	 * Cette methode injecte des marres dans les cases qui doivent en contenir
	 * Les marres sont a deux cases en diagonale du centre du plateau
	 * 		Voir schema pour les valeurs choisies
	 * Le drift est la pour permettre le decallage depuis le point de reference ( midH midL )
	 * 		Il est amene a etre modulable @ToDo 
	 * 
	 */
	private void setMarres() {
		int drift = 1;
		int midH = getHaut()/2;
		int midL = getLarg()/2;
		
		this.cases[midH+drift][midL+drift].setMarre(true);
		this.cases[midH+drift][midL-2*drift].setMarre(true);
		this.cases[midH-2*drift][midL+drift].setMarre(true);
		this.cases[midH-2*drift][midL-2*drift].setMarre(true);

	}
	/**
	 * Cette methode injecte des pions dans les cases qui doivent en contenir
	 * Les pions sont cree par rapport au haut et au bas du plateau 
	 * 		Voir schema pour les valeurs choisies
	 * 		Elles sont amenees a être modulable @ToDo 
	 * Les driftL et driftH sont la pour permettre le decallage depuis le point de reference ( midH )
	 * 		Il est amene a etre modulable @ToDo 
	 * Les pion blancs sont pour l'instant en ligne 0
	 * Les pions noirs sont pour l'instant en ligne hauteur-1
	 */ 
	private void setPions() {
		int driftL = 1;
		int driftH = 1;
		int midL = getLarg()/2;
		int h = getHaut();
		
		// Creation des lions
		this.cases[0][midL+driftL].setPion(2,"Blanc");
		this.cases[0][midL-2*driftL].setPion(2,"Blanc");
		this.cases[h-driftH][midL+driftL].setPion(2,"Noir");
		this.cases[h-driftH][midL-2*driftL].setPion(2,"Noir");
		
		// Creation des elephants
		this.cases[0][midL].setPion(1,"Blanc");
		this.cases[0][midL-driftL].setPion(1,"Blanc");
		this.cases[h-driftH][midL].setPion(1,"Noir");
		this.cases[h-driftH][midL-driftL].setPion(1,"Noir");
		
		// Creation des souris
		this.cases[driftH][midL].setPion(3,"Blanc");
		this.cases[driftH][midL-driftL].setPion(3,"Blanc");
		this.cases[h-2*driftH][midL].setPion(3,"Noir");
		this.cases[h-2*driftH][midL-driftL].setPion(3,"Noir");


	}
	/**
	 * Cette methode cherche touts les pions noirs
	 */
//	private Cases[] trackNoir() {
//		for ( int i = 0; i < this.Larg; i++) {
//			for ( int j = 0; j < this.Haut; j++) {
//				if this.cases[j][i].printPosition();
//			}
//			System.out.println(" ");
//		}
//	}
	// Cette methode revoie la couleur d'un pion d'une case du plateau
	public String contientCol(Cases Case) {
		return Case.getPionCol();		
	}
	// Cette methode deplace un pion
	public int movePion(Cases Depart, Cases Arrivee) {
		if(Depart.contientPion() && !Arrivee.contientPion()) {
			Arrivee.setPion(Depart.getPion());
			Depart.setPion();
			return 0;
		}
		return 1; // TODO peut-etre utile de recupere une valeur pour plus tard
	}
	public void movePion(int xd, int yd, int xa, int ya){
		movePion(this.cases[yd][xd], this.cases[ya][xa]);
	}
	/** 
	 * Classe interne Cases contenant les infos sur une case d'un plateau
	 */

	private class Cases {
		int x;
		int y;
		private Pion pion;
		boolean Marre;
		
		/**
		 * Creation d'une case
		 * initialement :
		 * elles contient leur coordonnees dans le tableau de cases
		 * elles ne contiennent pas de pion ( setPion() => pion = null )
		 * elles ne contiennent pas de marre ( setMarre(false) )
		 */
		private Cases(int x, int y) {
			setX(x);
			setY(y);
			setPion();
			setMarre(false);
		}
		
		/**
		 * Cette methode permet d'imprimer les coordonnees d'une case
		 */
		void printPosition() {
			System.out.print(getX()+" ");
			System.out.print(getY()+" ");
		}
		
		// EN DESSOUS
		// getter et setter pour les coordonnees et les pions
		@SuppressWarnings("unused")
		private int getX() {
			return x;
		}
		private void setX(int x) {
			this.x = x;
		}
		@SuppressWarnings("unused")
		private int getY() {
			return y;
		}
		private void setY(int y) {
			this.y = y;
		}
		/**
		 * @return the pion
		 */
		// Retourne le contenu de l'attribut pion : null ou un objet pion
		@SuppressWarnings("unused")
		private Pion getPion() {
			return pion;
		}
		/**
		 * @param pion the pion to set
		 */
		// Cette methode sans argument set l'attribut pion a null
		private void setPion() {
			this.pion = null;
		}
		/**
		 * Cette methode initiale le pion d'une case
		 * Prend deux arguments String
		 * Le premier ( type ) indique le type du pion
		 * Le second ( col ) indique la couleur du pion
		 * Si l'un de ces arguments est null  appelle setPion() => pion = null
		 * autrement elle cree un pion du type et de la couleur donnees
		 */
		private void setPion(int type, String col) {
			if ( type == 0 || col == null ) { // TODO voir pourquoi int can't == null
				// ca me peut me poser probleme
				setPion();
			}else {
				/*
				 *  ici je dois mettre un switch pour le type de pion
				 *  1 : elephant
				 *  2 : lion
				 *  3 : souris
				 */
				switch(type) {
				case 1 :
					this.pion = new Elephant(col);
					break;
				case 2 :
					this.pion = new Lion(col);
					break;
				case 3 :
					this.pion = new Souris(col);
					break;
				default :
					System.out.print("DAAAAMN!"); // TODO repenser cette partie
					break;
				}
			}
		}
		// 
		private void setPion(Pion pion) {
			this.pion = pion;
		}
		
		/**
		 * Cette methode renvoie vrai ou faux
		 * si la case ne contient pas de pion ( pion == null ) renvoie faux
		 * si la case contient un pion ( donc pion != null ) renvoie vrai
		 */
		@SuppressWarnings("unused")
 		private boolean contientPion() {
			if ( getPion() == null) {
				return false;
			}
			return true;
		}
		// Cette methode renvoie la couleur du pion d'une case si elle contient un pion
		private String getPionCol(){
			if ( contientPion() ) {
				return pion.getCol();
			}
			return null;
			
		}
		// Cette methode renvoie vrai ou faux de l'attribut Marre d'une case
 		public boolean isMarre() {
			return Marre;
		}
		// Cette methode rentre vrai ou faux dans l'attribut Marre d'une case
		public void setMarre(boolean marre) {
			Marre = marre;
		}
		
	}
	
	/**
	 * Main pour tester les classes
	 */
	public static void main(String[] args) {
		Plateau MonPlateau = new Plateau( 10, 10);
		MonPlateau.printPlateauCoord();
		MonPlateau.printPlateauPion();
		MonPlateau.printPlateauMarre();
		MonPlateau.printPlateau();
		System.out.println(MonPlateau.cases[2][3].isMarre());
		System.out.println(MonPlateau.cases[2][3].getPion());
		System.out.print((10-1)/2);
		System.out.print(MonPlateau.cases[0][3].getPionCol());
		MonPlateau.movePion(3, 0, 5, 5);
		MonPlateau.printPlateau();

	}
}

