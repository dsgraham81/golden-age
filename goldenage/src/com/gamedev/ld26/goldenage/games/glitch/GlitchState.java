package com.gamedev.ld26.goldenage.games.glitch;

import com.gamedev.ld26.goldenage.Globals.Games;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.games.GameState;

public class GlitchState extends GameState {

	public GlitchState(GoldenAgeGame game, GameState previous) {
		super(game, previous);
	}

	@Override
	protected void updateScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Games nextScreen() {
		return Games.end;
	}

}
