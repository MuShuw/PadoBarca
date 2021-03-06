import java.util.Formatter;
import java.util.Map;

// Cette classe cree des objets avec lesquelles les joeurs peuvent interagir 
public class Plateau {
	private Cases[][] cases; // TODO remettre en private
	protected int Larg;
	protected int Haut;
	Cases[] PionNoirs = new Cases[6]; // TODO rendre le nombre de pions modulable
	Cases[] PionBlancs = new Cases[6]; // Est la pour le bot
	Config Configuration;
	
	/** 
	 * Creation du plateau sans dimension, appelle la creation de plateau a 2 entiers pour creer
	 * le plateau par defaut
	 */
	public Plateau() {
		this(10,10);
	}
	/** 
	 * Creation du plateau avec 2 entiers, appelle la creation de plateau avec congifguration
	 */
	public Plateau(int i, int j) {
		this(new Config(10, 10));
	}
	/** 
	 * Creation d'un simple plateau avec une hauteur et largeur donnes
	 * et affichage de celui-ci @ToDo completer les doc
	 * 
	 */
	public Plateau(Config config) {
		setConfig(config);
		setLarg();
		setHaut();
		System.out.println(Haut+" "+Larg); // @remove safe print
		Cases[][] TmpCases = new Cases[Haut][Larg];
		for ( int i = 0; i < Larg; i++) {
			for ( int j = 0; j < Haut; j++) {
				TmpCases[j][i]=new Cases(i,j);
			}
		}
		setCases(TmpCases);
		setMarres();
		setPions();
		
		// Listes des pions, temporaire à récupérer depuis les maps
		trackBlanc();
		trackNoir();
		setCercleDeLaVie();
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
	 * Methode pour l'affichage ASCII du plateau
	 */
	public void printASCII(){
		Cases caseAct;
		StringBuilder sbuf;
		Formatter fmt;
		String pion;
		char marre = ' ';
		// On affiche d'abord la case d'angle
		sbuf = new StringBuilder();
		fmt = new Formatter(sbuf);
		fmt.format("%-5s", "__");
		System.out.print(fmt);
		
		// Puis on affiche le numéro de la colonne
		for ( int k = 0; k < this.Haut; k++){
			// méthode provenant de dzone.com
			sbuf = new StringBuilder();
			fmt = new Formatter(sbuf);
			fmt.format("%-5d", k);
			System.out.print(fmt);
		}
		System.out.println();
		
		
		for ( int i = 0; i < this.Larg; i++) {
			// On affiche le numéro de la colonne
				sbuf = new StringBuilder();
				fmt = new Formatter(sbuf);
				fmt.format("%-5d", i);
				System.out.print(fmt);
			for ( int j = 0; j < this.Haut; j++) {
				// On récupère la case
				caseAct = getCases()[i][j];

				
				// On verifie si il y a une marre sur la case
				if (caseAct.isMarre()) marre = 'O';
				else marre = ' ';
				sbuf = new StringBuilder();
				fmt = new Formatter(sbuf);
				if (caseAct.contientPion()){
					if (caseAct.isMarre()) pion = this.cases[i][j].getCaseASCII().toUpperCase();
					else pion = this.cases[i][j].getCaseASCII().toLowerCase();
					fmt.format("%-5s", pion);
				}
				else fmt.format("%c%-4s",marre,"_|");
				
				System.out.print(fmt);
			}
			System.out.println();
		}
	}
	/**
	 * Fin des methodes d'affichage.
	 */
	/** 
	 * Getter et setter des attributs du plateau
	 */
	
	// TODO remettre en privé
	public Cases[][] getCases() {
		return cases;
	}
	private void setCases(Cases[][] cases) {
		this.cases = cases;
	}
	public int getLarg() {
		return Larg;
	}
	private void setLarg() {
		Larg = getConfig().getPlateauDim().getX();
	}
	public int getHaut() {
		return Haut;
	}
	private void setHaut() {
		Haut = getConfig().getPlateauDim().getY();
	}
	private void setConfig(Config config) {
		this.Configuration = config;
		// TODO Auto-generated method stub	
	}
	private Config getConfig() {
		return this.Configuration;
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
		Map<Coordonnees, Boolean> map = getConfig().getPosMarre();
		System.out.print("Plop \n");
		for (Map.Entry<Coordonnees, Boolean> entry : map.entrySet()) {
		    Coordonnees key = entry.getKey();
		    Boolean value = entry.getValue();
		    this.cases[key.getX()][key.getY()].setMarre(value);
		}
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
		Map<Coordonnees, Pion> map = getConfig().getPosPions();
		for (Map.Entry<Coordonnees, Pion> entry : map.entrySet()) {
		    Coordonnees key = entry.getKey();
		    Pion value = entry.getValue();
		    this.cases[key.getX()][key.getY()].setPion(value);
		    System.out.println("le pion est"+value.Type+" en position "+key.getX()+" "+key.getY()+" de couleur "+value.getCol());
		}
	}
	/**
	 * Cette methode cherche touts les pions noirs
	 */
	public void trackNoir() {
		int k = 0;
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				if( this.cases[j][i].getPionCol() == "Noir") {
					PionNoirs[k++] = this.cases[j][i]; 
				}
			}
		}
	}
	/**
	 * Cette methode cherche touts les pions noirs
	 */
	public void trackBlanc() {
		int k = 0;
		for ( int i = 0; i < this.Larg; i++) {
			for ( int j = 0; j < this.Haut; j++) {
				if( this.cases[j][i].getPionCol() == "Blanc") {
					PionBlancs[k++] = this.cases[j][i]; 
				}
			}
		}
	}
	// Met en place le graphe orienté des relations entre les pions
	private void setCercleDeLaVie(){
		// Création d'un graphe 
		Vertex[] Testest = Vertex.Relation(getConfig().TypeDePion);
		// Orientation du graphe
		Vertex.CercleDeLaVie(Testest);
		// print de test TODO remova that
		for ( int i = 0 ; i < Testest.length ; i++){
			System.out.print(Testest[i].getType()+" "+ Testest[i].getBloque().getType());
		}
		for ( int i = 0 ; i < PionBlancs.length; i ++ ){
			for ( int j = 0 ; j < Testest.length; j ++){
				if ( Testest[j].getType() == PionBlancs[i].getPion().getType() ){
					PionBlancs[i].getPion().setParalyse(Testest[j]);
					System.out.println("i ="+i+" j ="+j);
					System.out.println(Testest[j].getType()+" "+PionBlancs[i].getPion().getType());
					System.out.println(PionBlancs[i].getPion().getParalyse().getBloque().getType());

				}
				if ( Testest[j].getType() == PionNoirs[i].getPion().getType() ){
					PionNoirs[i].getPion().setParalyse(Testest[j]);
					System.out.println("i ="+i+" j ="+j);
					System.out.println(Testest[j].getType()+" "+PionNoirs[i].getPion().getType());
					System.out.println(PionNoirs[i].getPion().getParalyse().getBloque().getType());

				}
			}
		}
		
	}
	// Cette methode revoie la couleur d'un pion d'une case du plateau
	public String contientCol(Cases Case) {
		return Case.getPionCol();		
	}
	// Cette methode deplace un pion
	public boolean movePion(Cases Depart, Cases Arrivee, String tourDeJeu) {
		int xd=Depart.getX(), yd = Depart.getY(), xa = Arrivee.getX(), ya = Arrivee.getY();
		if(Depart.contientPion() && !Arrivee.contientPion()) {
			System.out.println("Contient un pion et arrivée vide");
			Pion ToMove = Depart.getPion();
			if ( ToMove.getCol() != tourDeJeu) {
				System.out.println("La main est à "+tourDeJeu);
				return false;
			}
			// Block pour vérifier si un pion de cette couleur est paralysé et si oui
			// si ce pion est paralysé 
			if ( ToMove.getCol() == "Blanc" && (!ToMove.isEtatParalysie() && isThereBPara())) {
				System.out.println("Vous devez bouger un des pions pralysés.");
				return false;
			}
			if ( ToMove.getCol() == "Noir" && (!ToMove.isEtatParalysie() && isThereNPara())) {
				System.out.println("Vous devez bouger un des pions pralysés.");
				return false;
			}

			if ( ToMove.canMove(xd, yd, xa, ya) && emptyRoad(Depart, Arrivee)){
				// Si il a pu bouger il est forcément sur une case o` il n'est plus
				// paralysé
				Depart.getPion().setEtatParalysie(false);
				// On vérifie si il paralyse un pion
				// TODO emprunter de la methode isRoadBlocked
				Arrivee.setPion(Depart.getPion());
				Depart.setPion();
				trackBlanc();
				trackNoir();
				checkParalyse();
				return true;
			}
		}
		System.out.println("Mouvement impossible");
		return false; // TODO peut-etre utile de recupere une valeur pour plus tard
	}
	public void checkParalyse() {
		int x, y;
		Pion pion;
		for ( int i = 0 ; i < PionBlancs.length; i++){
			x = PionBlancs[i].getX(); y = PionBlancs[i].getY();
			pion = PionBlancs[i].getPion();
			if ( checkVoisinParal(y, x, pion)){
				pion.setEtatParalysie(true);
			}else{
				pion.setEtatParalysie(false);
			}
		}
		for ( int i = 0 ; i < PionNoirs.length; i++){
			System.out.println(" In check for black");
			pion = PionNoirs[i].getPion();
			x = PionNoirs[i].getX(); y = PionNoirs[i].getY();
			if ( checkVoisinParal(y, x, pion)){
				pion.setEtatParalysie(true);
			}else{
				pion.setEtatParalysie(false);
			}
		}
		// TODO Auto-generated method stub
	}
	private boolean checkVoisinParal(int x, int y, Pion pion){
		Pion Voisin;
		for ( int j = -1 ; j < 2; j++ ){
			for ( int k = -1 ; k < 2; k++){
				System.out.println(x+" "+y);
				if ( ( (x+j) < 0 || (x+j) >= getLarg() ) || // new X hors du plateau ?
						( (y+k) < 0 || (y+k) >= getHaut() ) ) { // new y hors du plateau ?
					System.out.println(pion.Type+" "+pion.Col+" "+(x+j)+" "+(y+k)+" ");
				}
				else if ( this.cases[x+j][y+k].contientPion() ){
					System.out.println("-----------------------------------------------------------------");
//					System.out.println("CHECCCCCCK :::::::::: "+(x+j)+" :::::::: "+(y+k)+ " "
//							+this.cases[x+j][y+k].getPionCol()+" "
//							+this.cases[x+j][y+k].getPion().Type
//							+" "+pion.Col+" "+pion.Type);
					Voisin = this.cases[x+j][y+k].getPion();
					if ( Voisin.getParalyse().getBloque().getType() == pion.getType() &&
							Voisin.getCol() != pion.getCol()
							){
						System.out.println("Passe le true dans check voiin------------------------------------------");
						System.out.println(" "+ Voisin.getCol()+" "+Voisin.getType());
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean isThereBPara(){
		for ( int i = 0 ; i < this.PionBlancs.length; i++){
			if (this.PionBlancs[i].getPion().isEtatParalysie()) return true;
		}
		return false;
	}
	private boolean isThereNPara(){
		for ( int i = 0 ; i < this.PionNoirs.length; i++){
			if (this.PionNoirs[i].getPion().isEtatParalysie()) return true;
		}
		return false;
	}
	public boolean movePion(int xd, int yd, int xa, int ya, String tourDeJeu){
		return movePion(this.cases[yd][xd], this.cases[ya][xa], tourDeJeu);
	}
	public boolean emptyRoad(Cases Depart, Cases Arrivee){
		int xd=Depart.getX(), yd = Depart.getY(), xa = Arrivee.getX(), ya = Arrivee.getY();
		return emptyRoad(xd, yd, xa, ya);
	}
	private boolean emptyRoad(int xd, int yd, int xa, int ya) {
		System.out.println(""+xd+" "+yd);
		Pion VeutBouger = this.cases[yd][xd].getPion();
		if ( VeutBouger == null ) System.out.println("merde");
		int count = 0;
		int DirectionY;
		int DirectionX;
		if ( ( ya - yd) != 0) {
			count = Math.abs(ya - yd);
			DirectionY = (ya - yd)/count;
		}
		else DirectionY = 0;

		if ( ( xa - xd) != 0){
			count = Math.abs(xa - xd);
			DirectionX = (xa - xd)/count;
		}
		else DirectionX = 0;
		
		// On va commencer par la première case de son déplacement
		int i = xd+DirectionX, j = yd+DirectionY;
		if ( this.cases[j][i].contientPion() || isRoadBlocked(j, i, VeutBouger) ) return false;
		count --;
		// Puis on va tester toutes les cases à venir
		while ( ( DirectionX !=0 && count >= 0 ) || ( DirectionY != 0 && count >= 0 ) ){
			if ( this.cases[j][i].contientPion() || isRoadBlocked(j, i, VeutBouger) ){
				return false;
			}
			i += DirectionX; j += DirectionY;
			count --;
		}
		return true;
	}
	@SuppressWarnings("unused")
	private boolean isRoadBlocked(Cases Check, Pion pion){
		int x=Check.getX(), y = Check.getY();
		return isRoadBlocked(x, y, pion);
	}
	private boolean isRoadBlocked(int x, int y, Pion pion){
		System.out.println("premier check -------------------------------------------------- :"+x+" "+y);
		for ( int i = - 1 ; i < 2; i++){
			for ( int j = -1 ; j < 2; j++ ){
				if ( ( (x+j) < 0 || (x+j) >= getLarg() ) || // new X hors du plateau ?
						( (y+i) < 0 || (y+i) >= getHaut() ) ) { // new y hors du plateau ?
					System.out.println("if sous check :"+(x+j)+" "+(y+i));
					System.out.println("Hors plateau.");
					continue;
				}
				else if ( this.cases[x+j][y+i].contientPion() ){
					System.out.println("else sous check :"+(x+j)+" "+(y+i));
					Pion PionCroise = this.cases[x+j][y+i].getPion();
					System.out.println("Passe le getpion");
					if ( PionCroise == null ) {
						System.out.println("Dans le check null");
						continue;
					}
					System.out.println("Passe le check null");
					String cible = PionCroise.getParalyse().getBloque().getType();
					System.out.println("Passe le get stuff "+cible+" face à "+pion.getType());
					if ( pion.getType() == cible && pion.getCol() != PionCroise.getCol()) {
						System.out.println("Entre le dernier if");
						return true;
					}
				}
				else continue;
			}
		}	
		return false;
	}	
	
	public int pionSurMarre(){
		int blanc = 0, noir = 0;
		for ( int i = 0 ; i < this.PionBlancs.length; i++){
			if ( this.PionBlancs[i].isMarre() ) blanc++;
		}
		for ( int i = 0 ; i < this.PionNoirs.length; i++){
			if ( this.PionNoirs[i].isMarre() ) noir++;
		}
		if ( noir >= blanc) return noir;
		return blanc;
	}
	
	public String getCaseASCII(int i, int j){
		return this.cases[i][j].getCaseASCII();
	}
	
	/** 
	 * Classe interne Cases contenant les infos sur une case d'un plateau
	 */
 	private class Cases { // TODO remettre en private
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
//		public void setPion(int type, String col) {
//			if ( type == 0 || col == null ) { // TODO voir pourquoi int can't == null
//				// ca me peut me poser probleme
//				setPion();
//			}else {
//				/*
//				 *  ici je dois mettre un switch pour le type de pion
//				 *  1 : elephant
//				 *  2 : lion
//				 *  3 : souris
//				 */
//				switch(type) {
//				case 1 :
//					this.pion = new Elephant(col);
//					break;
//				case 2 :
//					this.pion = new Lion(col);
//					break;
//				case 3 :
//					this.pion = new Souris(col);
//					break;
//				default :
//					System.out.print("DAAAAMN!"); // TODO repenser cette partie
//					break;
//				}
//			}
//		}
//		// 
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
		
		/**
		 * Méthodes pour l'interface graphique ASCII
		 * Permettent de représenter les case et leur contenu
		 */
		public String getCaseASCII(){
			if ( contientPion() ){
				return (""+getPionTypeASCII()+getPionColASCII());
			}
			return "  ";
		}
		public char getPionTypeASCII(){
			char type = getPion().getType().charAt(0);
			return type;
		}
		public char getPionColASCII(){
			char col = getPionCol().charAt(0);
			return col;
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
		MonPlateau.printPlateau();
		MonPlateau.trackBlanc();
		System.out.print(MonPlateau.PionBlancs.length);
		for ( int i = 0; i < MonPlateau.PionBlancs.length; i++ ){
			System.out.print(i);
			System.out.print(MonPlateau.PionBlancs[i].getPion().getType());
		}
		
//		Vertex[] Testest = Vertex.Relation(TypeDePion);
//		Vertex.CercleDeLaVie(Testest);
//		for ( int i = 0 ; i < Testest.length ; i++){
//			System.out.print(Testest[i].getType()+" "+ Testest[i].getBloque().getType());
//		}
//		System.out.println("\nset  Cercle ");
//
//		for ( int i = 0 ; i < MonPlateau.PionBlancs.length; i ++ ){
//			for ( int j = 0 ; j < Testest.length; j ++){
//				if ( Testest[j].getType() == MonPlateau.PionBlancs[i].getPion().getType() ){
//					MonPlateau.PionBlancs[i].getPion().setParalyse(Testest[j]);
//					System.out.println("i ="+i+" j ="+j);
//					System.out.println(Testest[j].getType()+" "+MonPlateau.PionBlancs[i].getPion().getType());
//					System.out.println(MonPlateau.PionBlancs[i].getPion().getParalyse().getBloque().getType());
//
//				}
//			}
//		}
		System.out.println(" Cercle ? Blanc");
		for ( int i = 0 ; i < MonPlateau.PionBlancs.length; i ++ ){
			System.out.println(MonPlateau.PionBlancs[i].getPion().getType()+" "
					+MonPlateau.PionBlancs[i].getPion().getParalyse().getType()+" "
					+MonPlateau.PionBlancs[i].getPion().getParalyse().getBloque().getType());
		}
		System.out.println(" Cercle ? Noir");
		for ( int i = 0 ; i < MonPlateau.PionNoirs.length; i ++ ){
			System.out.println(MonPlateau.PionNoirs[i].getPion().getType()+" "
					+MonPlateau.PionNoirs[i].getPion().getParalyse().getType()+" "
					+MonPlateau.PionNoirs[i].getPion().getParalyse().getBloque().getType());
		}
//		boolean test = MonPlateau.cases[5][5].getPion().canMove(5, 5, 3, 3);
//		System.out.print(MonPlateau.getCases()[-1][-1]);
		
	}
}
