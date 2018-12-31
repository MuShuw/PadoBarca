
public class Vertex {
	String Type;
	Vertex Bloque;
	
	Vertex(String type){
		setType(type);		
	}
	static void CercleDeLaVie(Vertex arbre[]){
		int nombreAnimaux = arbre.length;
		for ( int i = 0 ; i < nombreAnimaux-1; i++ ) arbre[i].setBloque(arbre[i+1]);
		arbre[nombreAnimaux-1].setBloque(arbre[0]);
	}
	static Vertex[] Relation(String[] TypeDePion){
		int nombreAnimaux = TypeDePion.length;
		Vertex arbre[] = new Vertex[nombreAnimaux];
		for ( int i = 0 ; i < nombreAnimaux ; i++ ){
			arbre[i] = new Vertex(TypeDePion[i]);
		}
		return arbre;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Vertex getBloque() {
		return Bloque;
	}
	public void setBloque(Vertex bloque) {
		Bloque = bloque;
	}
	
}
