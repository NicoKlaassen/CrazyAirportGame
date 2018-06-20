package games.CrazyAirportGame;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;
import userManagement.User;

import java.io.IOException;
import java.util.ArrayList;

public class CrazyAirportGame extends Game{

	//Testkommenatar
	
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
