package bonbombe.enums;

public enum	Direction {

	MONTER ("Up"),
	DESCENDRE ("Down"),
        GAUCHE ("Left"),
        DROITE ("Right");

	private String direction;

	Direction(String _direction){
		this.direction = _direction;
	}

	public String getDirection(){
		return this.direction;
	}

	public String toString(){
		return "Direction du joueur : "+this.direction;
	}

}
