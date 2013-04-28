package com.gamedev.ld26.goldenage.games.G1942;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.Bullet;
import com.gamedev.ld26.goldenage.games.BulletFactory;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.PlayerTransition;
import com.gamedev.ld26.goldenage.games.Transition;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class G1942State extends GameState {

	private final float GUN_INTERVAL = .15f; 
	
	static final Vector2 size = new Vector2(80, 80);
	private BulletFactory _bulletFactory;
	private float _respawnTime = 0;
	private int _shipsKilled;
	private PlaneSpawnInfo _leftCircleSpawner;
	private PlaneSpawnInfo _rightCircleSpawner;
	private float _gunDelay;
	private MovingBackground back1;
	private MovingBackground back2;
	private final Color _backGroundColor1 = new Color(0,0f,.3f,1f);
	private final Color _backGroundColor2 = new Color(0,.3f,0,1f);
	
	public G1942State(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		// TODO Auto-generated constructor stub
		_bulletFactory = new BulletFactory(this, Color.GRAY, 3);
		_windowBounds = Config.g1942_window_bounds;
		_shipsKilled = 0;
		_gunDelay = 0;
		_leftCircleSpawner = new PlaneSpawnInfo(15, .15f, 10f);
		_rightCircleSpawner = new PlaneSpawnInfo(15, .15f, 12f);
		_rightCircleSpawner.SetSpawnTime(15f);
		back1 = new MovingBackground(_windowBounds, _backGroundColor1, _backGroundColor2);
		back2 = new MovingBackground(_windowBounds, _backGroundColor2, _backGroundColor1);
		back2.SetYOffset(_windowBounds.height);
		_player.setImmunity(0);
		
		setupTransition(previous);
	}

	@Override
	protected void updateScreen(float delta) {
		if (_respawnTime > 0)
		{
			respawnPlayer(delta);
		}
		if (_gunDelay > 0) _gunDelay -= delta;
		if (_player.getImmunity() > 0) 
		{
			_player.setImmunity(_player.getImmunity() - delta);
			_player.setDraw((int)(_player.getImmunity() * 5) % 2 == 0);
		}
		else {
			_player.setDraw(true);
		}
		
		if (_game.input.isButtonDown(0) && _player.getImmunity() <=0 &&  _gunDelay <= 0 && _player.isAlive()) {
			_bulletFactory.GetBullet(_player);
			_gunDelay = GUN_INTERVAL;
		}	
		
		if (Assets.random.nextDouble() > .99)
		{
			Plane plane = new Plane(new Vector2(Assets.random.nextFloat() * Config.window_width,Config.window_height), new Vector2(20,20), Color.WHITE, this, _bulletFactory);
			//AddGameObject(plane);
		}
		
		spawnCircleShips(delta);
		checkCollisions();
		back1.Update(delta);
		back2.Update(delta);
		
		// Fuck it a catch all
		if (!_player.isAlive() && _respawnTime <= 0)
		{
			_respawnTime = 2f;
		}
	}
	
	private void spawnCircleShips(float dt)
	{	
		if (_leftCircleSpawner.ShouldSpawn(dt))
		{
			CirclePlane cirPlane = new CirclePlane(new Vector2(0,500), new Vector2(20,20), Color.RED, this, _bulletFactory);
		}
		if (_rightCircleSpawner.ShouldSpawn(dt))
		{
			CirclePlane cirPlane = new CirclePlane(new Vector2(Config.window_width,400), new Vector2(20,20), Color.BLUE, this, _bulletFactory);
		}
	}
	
	private void checkCollisions()
	{
		for (GameObject obj: _gameObjects)
		{
			if (obj.getClass() == Bullet.class)
			{
				if (!obj.isAlive()) continue;
				Bullet bullet = (Bullet)obj;
				if (bullet.getTarget() == _player)
				{
//					if (bullet.collides(_player))
//					{
//						bullet.setAlive(false);
//						killPlayer();
//					}
				}
				else { // Must be from player
					for (GameObject tar : _gameObjects)
					{
						if (tar instanceof Plane && obj.collides(tar))
						{
							
							obj.setAlive(false);
							if (((Plane)tar).gotHit())
								_shipsKilled++;
						}
					}
				}
			}
			if (obj instanceof Plane)
			{
				if (_player.isAlive() && _player.getImmunity() <= 0 && obj.collides(_player))
				{
					killPlayer();
					if (((Plane)obj).gotHit())
						_shipsKilled++;
				}
			}
		}
	}

	private void killPlayer()
	{
		_respawnTime = 3f;
		_player.setAlive(false);
	}
	
	private void respawnPlayer(float dt){
		_respawnTime -= dt;
		if (_respawnTime <= 0)
		{
			_player.setImmunity(2f);
			_player.setAlive(true);
			AddGameObject(_player);
		}
	}

	protected boolean updateReset(float delta) {
		return false;
	}
	
	
	protected void updatePlayer(float delta) {
		_player.setPosition(_game.input.getCurrMouse().x - _player.getRect().width/2, Config.window_height - _game.input.getCurrMouse().y);
	}
	
	@Override
	protected void renderScreen(float delta) {
		back1.render();
		back2.render();
		
	}
	
	
	private float _transition = 0;	
	static final float TransitionTime = 1.5f;
	private ArrayList<Transition> _playerTransitions = new ArrayList<Transition>();

	
	protected boolean transitionScreen(float delta) {
		_transition += delta;
		
		for (Transition pt : _playerTransitions) {
			pt.Update(delta);
		}
		
		return (_transition < TransitionTime);
	}
	
	private void setupTransition(GameState previousScreen) {
		if (previousScreen == null) return;
		_player.setDraw(false);
				
		for (GameObject obj : previousScreen.getGameObjects()) {
			
			if (obj.isAlive()) {
				if ((obj.isTransitionObject())) {
					_playerTransitions.add(new PlayerTransition(createScaleTransition(), obj, TransitionTime/3));
				}
			}
		}
		
		_playerTransitions.add(new PlayerTransition(_player,  previousScreen.getPlayer(), TransitionTime, false));
	}
	
	private GameObject createScaleTransition() {
		
		Random rand = Assets.random;
		float x, y;
		
		switch (rand.nextInt(3)) {
			case 1:
				x = _windowBounds.x - rand.nextInt(100);
				y = getSide(100);
				break;
			case 2:
				x = _windowBounds.x + _windowBounds.height + rand.nextInt(100);
				y = getSide(100);
				break;
			default:
				x = getTop(0);
				y = (rand.nextBoolean()) ? _windowBounds.y - rand.nextInt(100) 
						: _windowBounds.y + _windowBounds.height + rand.nextInt(100); 
				break;
		}
		
		
		float size = Assets.random.nextInt(20) + 20;
		Vector2 pos = new Vector2(x, y);
				
		Color color = Utils.getRandomColor();
		GameObject object = new GameObject(pos, new Vector2(size, size), color, this);		
		object.IsTemporary = true; 
		return object;
	}
	
	
	private float getTop(float extra) {
		return -extra + ((_windowBounds.width + (extra*2)) * Assets.random.nextFloat());
	}
	
	private float getSide(float extra) {
		return -extra + ((_windowBounds.height + (extra*2)) * Assets.random.nextFloat());
	}

}
