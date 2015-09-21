package bonbombe.enums;

public enum	Statut{

	/* Pour les Joueur et les Jeux */
	ENJEU ("En jeu"),
	PASENJEU ("Pas en jeu"),
	ENCOURS ("En cours"),
	PLEIN ("Plein"),
	OUVERT ("Ouvert");

	private String statut;

	Statut(String _statut){
		this.statut = _statut;
	}

	public String getStatut(){
		return this.statut;
	}

	public String toString(){
		return this.statut;
	}

}
