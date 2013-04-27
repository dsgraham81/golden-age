package com.gamedev.ld26.goldenage.games;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;


public abstract class GameState {

	protected final GoldenAgeGame _game;	
	protected Paddle _player;
	private GameState _previousGame;
	private ArrayList<GameObject> _gameObjects = new ArrayList<GameObject>();
	static Vector2 size = new Vector2(Config.pong_paddle_size_x, Config.pong_paddle_size_y);
	
	protected GameState(GoldenAgeGame game, GameState previous) {
		_game = game;
		_previousGame = previous;
		if (previous != null)
		{
			_player = previous.getPlayer();
		}
		else {
			_player = new Paddle(new Vector2(Config.window_width / 2, 0), size, Color.WHITE, this);
		}
	}
	
	public void AddGameObject(GameObject obj)
	{
		_gameObjects.add(obj);
	}
	
	public void removeGameObject(GameObject obj)
	{
		_gameObjects.remove(obj);
	}
	
	public ArrayList<GameObject> getGameObjects()
	{
		return _gameObjects;
	}
		
	public void update(float delta) {
		updatePlayer(delta);
		
		if (_previousGame != null) {
			if (!transitionScreen(delta)) {
				_previousGame.dispose();
				_previousGame = null;
			}
		} else {		
			updateScreen(delta);
		}
	}
	
	public Paddle getPlayer()
	{
		return _player;
	}
	
	public boolean isTransitioning() {
		return (_previousGame != null);
	}
	
	protected boolean transitionScreen(float delta) {
		return false;				
	}
	
	protected void dispose() {		
	}
	
	protected void updatePlayer(float delta) {		
		_player.setPosition(_game.input.getCurrMouse().x, 0);
	}
	
	protected abstract void updateScreen(float delta);
	
	public void render(float delta) {
		Assets.shapes.begin(ShapeType.Filled);
		_player.render();
		renderScreen(delta);
		for (GameObject object : _gameObjects)
		{
			object.render();
		}
		Assets.shapes.end();
	}
	
	protected abstract void renderScreen(float delta);
}
