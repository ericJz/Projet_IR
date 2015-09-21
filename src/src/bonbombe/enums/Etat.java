package bonbombe.enums;

public enum	Etat {

	VIVANT ("Vivant"),
	MORT ("Mort");

	private String etat;

	Etat(String _etat){
		this.etat = _etat;
	}

	public String getEtat(){
		return this.etat;
	}

	public String toString(){
		return "Etat du joueur : "+this.etat;
	}

}
