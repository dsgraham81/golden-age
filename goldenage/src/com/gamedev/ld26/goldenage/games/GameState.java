package com.gamedev.ld26.goldenage.games;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;


public abstract class GameState {

	protected final GoldenAgeGame _game;	
	protected Paddle _player;
	private GameState _previousGame;
	protected ArrayList<GameObject> _gameObjects = new ArrayList<GameObject>();
	public Rectangle _windowBounds = new Rectangle(0,0, Config.window_width, Config.window_height);
	protected boolean _gameWon;
	protected Music _stageMusic;
	
	protected GameState(GoldenAgeGame game, GameState previous) {
		_game = game;
		_previousGame = previous;
		setupPlayer(previous);
		_gameWon = false;
	}
	
	protected void setupPlayer(GameState previous) {
		_player = createPlayer();
		
		float x = Config.window_width / 2;
		float y = 0;
		if (previous != null) 
		{
		
			Rectangle playerPosition = previous.getPlayer().getRect();
			x = playerPosition.x;
			y = playerPosition.y;
		}
	
		_player.setPosition(x, y);
	}
	
	protected Paddle createPlayer() {
		return new Paddle(new Vector2(Config.window_width / 2, 0), new Vector2(100, 20), Color.WHITE, this);
	}
	
	public void AddGameObject(GameObject obj)
	{
		_newObjects.add(obj);
	}
	
	
	public ArrayList<GameObject> getGameObjects()
	{
		return _gameObjects;
	}
		
	public ArrayList<GameObject> _newObjects = new ArrayList<GameObject>();
	
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
		

		
		for (GameObject obj: _gameObjects)
		{
			obj.update(delta);
		}
		
		//TODO make this a reusable list

		try {
		for (GameObject obj: _gameObjects)
		{
			if (obj._alive){
				_newObjects.add(obj);
			}
		}
		} catch (Exception e){ } 
		_gameObjects = _newObjects;
		_newObjects = new ArrayList<GameObject>();
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
		try {
			for (GameObject object : _gameObjects)
			{
				object.render();
			}
		} catch (Exception e) { } 
		Assets.shapes.end();
	}
	
	protected abstract void renderScreen(float delta);

	public boolean getGameWon() {
		return _gameWon;
	}
	
	public Music getMusic()
	{
		return _stageMusic;
	}
	
}
