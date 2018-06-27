package games.CrazyAirportGame;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;
import userManagement.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CrazyAirportGame extends Game{
	
	Card[] verantworungsLOSE=new Card[55];
	Card[] ereignisLOSE=new Card[24];
	
	
	public Card[] fillVerantworungsLOSArray() {
	for(int i=1; i<=55; i++) {
		verantworungsLOSE[i]=new Card(i) ;
	}
	return verantworungsLOSE;
	}
	
	public Card[] fillEreignisLOSArray() {
		for(int i=1; i<=55; i++) {
			ereignisLOSE[i]=new Card(i) ;
		}
	return ereignisLOSE;
	}
	
	public void drawVerantwortungsLOS(int id) {
		int randomPull = (int)(Math.random() * verantworungsLOSE.length) + 1;
		Card drawnCard = new Card(randomPull);
		switch(drawnCard.getId()) {
		case 1:
		}
	}

	@Override
	public String getSite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCSS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJavaScript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxPlayerAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPlayerAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void execute(User user, String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<User> getPlayerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getSpectatorList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGameData(String eventName, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSpectator(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isJoinable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playerLeft(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameState getGameState() {
		// TODO Auto-generated method stub
		return null;
	}

}
