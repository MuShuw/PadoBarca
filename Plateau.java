import javax.naming.directory.DirContext;

// Cette classe cree des objets avec lesquelles les joeurs peuvent interagir 
public class Plateau {
	Cases[][] cases;
	int Larg;
	int Haut;
	Cases[] PionNoirs = new Cases[6]; // TODO rendre le nombre de pions modulable
	Cases[] PionBlancs = new Cases[6]; // Est la pour le bot
	Player AlaMain;
	Config Configuration;
	static String[] TypeDePion = {"Elephant","Lion","Souris"};  // A modifier pour moduler
	
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
				Pion StatutPion = this.cases[i][j].getPion();
				boolean StatutMarre = this.cases[i][j].isMarre();
				if ( StatutMarre == true ) System.out.print("O");
				else System.out.print("X");
				if ( StatutPion  == null ) {
				System.out.print(" X");
				}else {
					System.out.print(" "+this.cases[i][j].getPion().getType()+" "
									+this.cases[i][j].getPion().getCol()+" ");
				}
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
		
//		for ( int k = 0 ; k < map.length; k++){
//			this.cases[Map.coordonnee.x][Map.coordonnee.y] = Map.pion;
//		}
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
	@SuppressWarnings("unused")
	private void trackNoir() {
		int k = 0;
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				if( this.cases[j][i].getPionCol() == "Noir") {
					PionNoirs[k++] = this.cases[j][i]; 
				}
			}
			System.out.println(" ");
		}
	}
	/**
	 * Cette methode cherche touts les pions noirs
	 */
	private void trackBlanc() {
		int k = 0;
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				if( this.cases[j][i].getPionCol() == "Blanc") {
					PionBlancs[k++] = this.cases[j][i]; 
				}
			}
			System.out.println(" ");
		}
	}
	// Cette methode revoie la couleur d'un pion d'une case du plateau
	public String contientCol(Cases Case) {
		return Case.getPionCol();		
	}
	// Cette methode deplace un pion
	public int movePion(Cases Depart, Cases Arrivee) {
		int xd=Depart.getX(), yd = Depart.getY(), xa = Arrivee.getX(), ya = Arrivee.getY();
		if(Depart.contientPion() && !Arrivee.contientPion()) {
			Pion ToMove = Depart.getPion();
			if ( ToMove.canMove(xd, yd, xa, ya) && emptyRoad(Depart, Arrivee)){
				Arrivee.setPion(Depart.getPion());
				Depart.setPion();
				return 0;
			}
		}
		System.out.println("Mouvement impossible");
		return 1; // TODO peut-etre utile de recupere une valeur pour plus tard
	}
	public void movePion(int xd, int yd, int xa, int ya){
		movePion(this.cases[yd][xd], this.cases[ya][xa]);
	}
	public boolean emptyRoad(Cases Depart, Cases Arrivee){
		int xd=Depart.getX(), yd = Depart.getY(), xa = Arrivee.getX(), ya = Arrivee.getY();
		return emptyRoad(xd, yd, xa, ya);
	}
	private boolean emptyRoad(int xd, int yd, int xa, int ya) {
		int DirectionY;
		int DirectionX;
		if ( ( ya - yd) != 0) DirectionY = (ya - yd)/Math.abs(ya - yd);
		else DirectionY = 0;
		if ( ( xa - xd) != 0) DirectionX = (ya - yd)/Math.abs(ya - yd);
		else DirectionX = 0;
		int i = xd+DirectionX, j = yd+DirectionY;
		while ( ( DirectionX !=0 && i != xa ) || ( DirectionY != 0 &&j != ya) ){
			if ( this.cases[j][i].contientPion() ){
				return false;
			}
			i += DirectionX; j += DirectionY;
		}
		return true;
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
			System.out.print(getX()+"");
			System.out.print(getY()+" ");
		}
		
		// EN DESSOUS
		// getter et setter pour les coordonnees et les pions
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		/**
		 * @return the pion
		 */
		// Retourne le contenu de l'attribut pion : null ou un objet pion
		public Pion getPion() {
			return pion;
		}
		/**
		 * @param pion the pion to set
		 */
		// Cette methode sans argument set l'attribut pion a null
		public void setPion() {
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
		public void setPion(int type, String col) {
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
		public void setPion(Pion pion) {
			this.pion = pion;
		}
		
		/**
		 * Cette methode renvoie vrai ou faux
		 * si la case ne contient pas de pion ( pion == null ) renvoie faux
		 * si la case contient un pion ( donc pion != null ) renvoie vrai
		 */
 		public boolean contientPion() {
			if ( getPion() == null) {
				return false;
			}
			return true;
		}
		// Cette methode renvoie la couleur du pion d'une case si elle contient un pion
		public String getPionCol(){
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
		MonPlateau.movePion(5, 1, 6, 3);
		MonPlateau.printPlateau();
		MonPlateau.trackBlanc();
		System.out.print(MonPlateau.PionBlancs.length);
		for ( int i = 0; i < MonPlateau.PionBlancs.length; i++ ){
			System.out.print(i);
			System.out.print(MonPlateau.PionBlancs[i].getPion().getType());
		}
		
		Vertex[] Testest = Vertex.Relation(TypeDePion);
		Vertex.CercleDeLaVie(Testest);
		for ( int i = 0 ; i < Testest.length ; i++){
			System.out.print(Testest[i].getType()+" "+ Testest[i].getBloque().getType());
		}
		System.out.println("set  Cercle ");

		for ( int i = 0 ; i < MonPlateau.PionBlancs.length; i ++ ){
			for ( int j = 0 ; j < Testest.length; j ++){
				if ( Testest[j].getType() == MonPlateau.PionBlancs[i].getPion().getType() ){
					MonPlateau.PionBlancs[i].getPion().setParalyse(Testest[j]);
					System.out.println("i ="+i+" j ="+j);
					System.out.println(Testest[j].getType()+" "+MonPlateau.PionBlancs[i].getPion().getType());
					System.out.println(MonPlateau.PionBlancs[i].getPion().getParalyse().getBloque().getType());

				}
			}
		}
		System.out.println(" Cercle ?");
		for ( int i = 0 ; i < MonPlateau.PionBlancs.length; i ++ ){
			System.out.println(MonPlateau.PionBlancs[i].getPion().getType()+" "
					+MonPlateau.PionBlancs[i].getPion().getParalyse().getType()+" "
					+MonPlateau.PionBlancs[i].getPion().getParalyse().getBloque().getType());
		}
//		boolean test = MonPlateau.cases[5][5].getPion().canMove(5, 5, 3, 3);
//		System.out.print(test);

	}
}

