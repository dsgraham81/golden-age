package com.gamedev.ld26.goldenage.games.spaceinvaders;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.DeltaTimer;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.TimerListener;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.Bullet;
import com.gamedev.ld26.goldenage.games.BulletFactory;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.Player;
import com.gamedev.ld26.goldenage.games.PlayerTransition;
import com.gamedev.ld26.goldenage.games.breakout.Block;

public class SpaceInvadersState extends GameState implements TimerListener {

	private final int Right = 0;
	private final int RightDown = 1;
	private final int Left = 2;
	private final int LeftDown = 3;
	
	
	static final float TransitionTime = 1.5f;
	private ArrayList<PlayerTransition> _playerTransitions = new ArrayList<PlayerTransition>();
	
	private final int Rows = 3;
	private final int Columns = 15;
	
	private Bullet _bullet;
	
	private ArrayList<BaseInvader> _aliens = new ArrayList<BaseInvader>(); 
	private final int _initialSpeed = 100;
	private int _alienSpeed = _initialSpeed;
	private int _downHeight = -30;
	private int _dy;
	private BulletFactory _bulletFactory;
	private Rectangle _alienBounds;
	
	private DeltaTimer _timer;
	private int _movement = Right;
	
	public SpaceInvadersState(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		setupScreen();
	    setupTransition(previous);
	}
	
	protected Player createPlayer() {
		return new Player(new Vector2(50, 50), Color.RED, this);
	}
	
	private void setupScreen() {
		_bulletFactory = new BulletFactory(this, Color.GRAY, 3);
		_timer = new DeltaTimer(this, 0.5f);
		
		Color[] alienColor = new Color[] { Color.CYAN, Color.MAGENTA, Color.YELLOW };
		
		_alienBounds = new Rectangle(_windowBounds.x + 10, 0, _windowBounds.width - 20, _windowBounds.height);
				
		for (int y = 0; y < Rows; y++) {
			for (int x = 0; x < Columns; x++) {
				_aliens.add(new BaseInvader(this, 35, alienColor[y]));
			}
		}
		
		resetScreen();
	}

	
	@Override
	protected void updateScreen(float delta) {	
		if (_game.input.isButtonDown(0) && !bulletExists()) {
			_bullet = _bulletFactory.GetBullet(_player);
		}		
		_timer.Update(delta);
		
		Vector2 offset = GetOffset(delta);
		
		boolean changeDirections = false;
		
		int aliens = 0;
		
		for (BaseInvader alien : _aliens) {
			if (alien.isAlive()) {
				if (checkBullet(alien)) continue;
				
				alien.offset(offset);
				aliens++;
				
				if (!isMovingDown()){
					changeDirections |= !alien.inBounds(_alienBounds);
				}

				if (shouldFire()){
					Bullet bullet = _bulletFactory.GetBullet(alien);
					bullet.setTarget(_player);
				}
			}
		}
		
		if (aliens < 30) {
			_gameWon = true;
			return;
		}
		
		changeDirection(changeDirections || (_dy < _downHeight));
	}
	
	protected void resetScreen() {
		_alienSpeed = _initialSpeed;
		float hgap = 800 / (Columns + 1);
		float vgap = 300 / (Rows + 1);
		
		float xPos = _windowBounds.x + ((_windowBounds.width - 800)/2) + hgap;
		float initX = xPos;
		float yPos = _windowBounds.height - vgap;
				
		int index = 0;
		for (int y = 0; y < Rows; y++) {
			for (int x = 0; x < Columns; x++) {
				_aliens.get(index++).setPosition(xPos, yPos);
				xPos += hgap;
			}
			xPos = initX;
			yPos -= vgap;
		}
		
		super.resetScreen();
	}
	
	private boolean bulletExists()
	{
		return (_bullet != null) && (_bullet.isAlive());
	}
	
	private boolean checkBullet(BaseInvader alien) {
		if (bulletExists()) {
			if (_bullet.collides(alien)) {
				alien.setAlive(false);
				_bullet.setAlive(false);
				return true;
			}
		}
		return false;
	}
	
	private boolean isMovingDown()
	{
		return ((_movement == LeftDown) || (_movement == RightDown));
	}
	
	
	private boolean shouldFire() {
		if (_alienFire) {
			if (Assets.random.nextInt(100) < 5) {
				_alienFire = false;
				return true;
			}
		}
		return false;
	}
	
	private void changeDirection(boolean change) {
		if (!change) return;
		
		_alienSpeed += 20;
		
		if (++_movement == 4) {
			_movement = 0;
		}
	}
	
	private Vector2 GetOffset(float delta) {
		float dy = 0;
		float dx = 0;
		
		if (_movement == LeftDown || _movement == RightDown) {
			float tempDy = delta*_alienSpeed;
			_dy -= tempDy;
			if (_dy < _downHeight) {
				dy = _dy - _downHeight;
			} else {
				dy = -tempDy;
			}
		} else {
			_dy = 0;
			dx = delta*_alienSpeed;
			if (_movement == Left) {
				dx = -dx;
			}			
		}
		
		return new Vector2(dx, dy);
	}
	
	
	private float _transition = 0;	
	
	protected boolean transitionScreen(float delta) {
		_transition += delta;
		
		for (PlayerTransition pt : _playerTransitions) {
			pt.Update(delta);
		}
		
		return (_transition < TransitionTime);
	}
	
	private void setupTransition(GameState previousScreen) {
		if (previousScreen == null) return;
		
		int index = 0;
		
		for (GameObject obj : previousScreen.getGameObjects()) {
			if (obj.isAlive() && obj.getClass() == Block.class) {
				if (index < _aliens.size()) {
					_playerTransitions.add(new PlayerTransition(_aliens.get(index++), obj, TransitionTime));
				} else {
					obj.setDraw(false);
				}
			}
		}
		
		_playerTransitions.add(new PlayerTransition(_player,  previousScreen.getPlayer(), TransitionTime, false));
	}
	
	protected void handleReset(float delta) {
		for (GameObject bullet : getGameObjects()) {
			if (bullet.getClass() == Bullet.class) {
				bullet.setAlive(false);
			}
		}		
	}

	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

	private boolean _alienFire = false;
	@Override
	public void OnTimer() {
		_alienFire = true;
	}
}
