package com.gamedev.ld26.goldenage.games.G1942;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.BulletFactory;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.utils.Config;

public class G1942State extends GameState {

	static final Vector2 size = new Vector2(80, 80);
	private BulletFactory _bulletFactory;
	
	public G1942State(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		// TODO Auto-generated constructor stub
		_bulletFactory = new BulletFactory(this, Color.GRAY, 3);
		_windowBounds = Config.g1942_window_bounds;
	}

	@Override
	protected void updateScreen(float delta) {
		if (Assets.random.nextDouble() > .99)
		{
			Plane plane = new Plane(new Vector2(Assets.random.nextFloat() * Config.window_width,Config.window_height), new Vector2(20,20), Color.WHITE, this, _bulletFactory);
			_gameObjects.add(plane);
		}
		
	}

	protected void updatePlayer(float delta) {
		_player.setPosition(_game.input.getCurrMouse().x, -_game.input.getCurrMouse().y);
	}
	
	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

}
