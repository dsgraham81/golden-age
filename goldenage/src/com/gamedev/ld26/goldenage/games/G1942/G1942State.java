package com.gamedev.ld26.goldenage.games.G1942;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.Paddle;
import com.gamedev.ld26.goldenage.utils.Config;

public class G1942State extends GameState {

	static final Vector2 size = new Vector2(80, 80);
	
	public G1942State(GoldenAgeGame game, GameState previous) {
		super(game, new Paddle(new Vector2(Config.window_width / 2, 0), size, Color.WHITE));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

}
