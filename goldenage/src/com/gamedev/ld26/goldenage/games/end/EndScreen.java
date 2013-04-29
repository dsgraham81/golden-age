package com.gamedev.ld26.goldenage.games.end;

import com.gamedev.ld26.goldenage.Globals;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.PlayerTransition;
import com.gamedev.ld26.goldenage.screens.PlayScreen;

public class EndScreen extends GameState {
	public EndScreen(PlayScreen screen, GameState previous) {
		super(screen.game, previous);
		setupTransition(previous);
	}

	@Override
	protected void updateScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}
		
	private void setupTransition(GameState previousScreen) {
		if (previousScreen == null) return;
		_player.setDraw(false);
		
			
		for (GameObject obj : previousScreen.getGameObjects()) {
			
			if (obj.isAlive()) {
				if (obj.isTransitionObject()) {
					addTransition(new PlayerTransition(createScaleTransition(), obj, _transitionTime));
				}
			}
		}
		
		addTransition(new PlayerTransition(_player,  previousScreen.getPlayer(), _transitionTime, false));
	}
	
	public Globals.Games nextScreen() {
		return Globals.Games.pong;
	}

}
