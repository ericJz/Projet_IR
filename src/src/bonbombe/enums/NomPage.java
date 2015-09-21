package bonbombe.enums;

public enum	NomPage {

	MENU ("Menu"),
	CREERJEU ("CreerJeu"),
	WELCOME ("Welcome"),
	JEU ("Jeu");

	private String nom;

	NomPage(String _nom){
		this.nom = _nom;
	}

	public String getNom(){
		return this.nom;
	}

	public String toString(){
		return "Nom de la page : "+this.nom;
	}

}
