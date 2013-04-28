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
	private GameState _previousGame;
	
	protected Player _player;
	protected ArrayList<GameObject> _gameObjects = new ArrayList<GameObject>();

	public Rectangle _windowBounds = new Rectangle(0,0, Config.window_width, Config.window_height);
	protected boolean _gameWon = false;
	protected Music _stageMusic;
	
	protected GameState(GoldenAgeGame game, GameState previous) {
		_game = game;
		_previousGame = previous;
		setupPlayer(previous);
	}
	
	public Rectangle getBounds() {
		return _windowBounds;
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
	
	protected Player createPlayer() {
		return new Player(new Vector2(100, 20), Color.WHITE, this);
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
		if (!_player.isAlive()) {
			if (updateReset(delta)) {
				return;
			}
		}
		
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
	
	public Player getPlayer()
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
		_player.setPosition(_game.input.getCurrMouse().x - _player.getRect().width/2, 0);
	}
	
	private float _resetTime = 0;
	
	// override to handle update image
	protected boolean updateReset(float delta) {
		_resetTime += delta;
		
		if (_resetTime > 3f) {
			_resetTime = 0;
			resetScreen();
			return false;
		}
		
		handleReset(delta);
		return true;
	}
	
	protected void handleReset(float delta) { }
	
	protected void resetScreen() { 
		_player.setAlive(true);
	}
	
	protected abstract void updateScreen(float delta);
	
	public void render(float delta) {
		Assets.shapes.begin(ShapeType.Filled);


		
		renderScreen(delta);
		render(_player);
		try {
			for (GameObject object : _gameObjects)
			{
				render(object);
			}
		} catch (Exception e) { 

			System.out.println(e.getMessage());
	
		} 
		Assets.shapes.end();
	}
	
	protected void render(GameObject gameObject) {
		if (gameObject != null && gameObject.isAlive()) {
			gameObject.render();
		}
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
