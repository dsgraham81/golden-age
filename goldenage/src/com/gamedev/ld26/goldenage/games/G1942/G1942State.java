package com.gamedev.ld26.goldenage.games.G1942;

import org.ietf.jgss.Oid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.Bullet;
import com.gamedev.ld26.goldenage.games.BulletFactory;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.utils.Config;

public class G1942State extends GameState {

	static final Vector2 size = new Vector2(80, 80);
	private BulletFactory _bulletFactory;
	private float respawnTime = 0;
	
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
			AddGameObject(plane);
		}
		checkCollisions();
	}
	
	private void checkCollisions()
	{
		for (GameObject obj: _gameObjects)
		{
			if (obj.getClass() == Bullet.class)
			{
				if (obj.collides(_player))
				{
					killPlayer();
				}
			}
			if (obj.getClass() == Plane.class)
			{
				if (obj.collides(_player))
				{
					killPlayer();
					obj.setAlive(false);
				}
			}
		}
	}

	private void killPlayer()
	{
		respawnTime = 3f;
		_player.setDraw(false);
	}
	
	protected void updatePlayer(float delta) {
		_player.setPosition(_game.input.getCurrMouse().x, -_game.input.getCurrMouse().y);
	}
	
	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

}
