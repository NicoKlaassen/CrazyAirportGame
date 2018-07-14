package games.CrazyAirportGame;

import userManagement.User;

public class AI extends Player {

	
	
	public AI(User user, String color) {
		super(user, color);
	}

	//in case u need to replace a player who left
	public AI(Player toCopy) {
		super(toCopy.getUser(), toCopy.getColor());
		this.score=toCopy.getScore();
		this.skipNextRound=toCopy.skipNextRound;
		this.hasVCard11=toCopy.isHasVCard11();
		this.hasVCard23=toCopy.isHasVCard23();
		this.chips=toCopy.getChips();
		this.vCards=toCopy.getvCards();
		this.eCards=toCopy.geteCards();
	}
    
	
    
}
