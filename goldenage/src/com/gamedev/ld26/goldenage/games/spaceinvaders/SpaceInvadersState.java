package com.gamedev.ld26.goldenage.games.spaceinvaders;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.games.Bullet;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.Paddle;
import com.gamedev.ld26.goldenage.games.PlayerTransition;
import com.gamedev.ld26.goldenage.utils.Config;

public class SpaceInvadersState extends GameState {

	static final float TransitionTime = 1.5f;
	private PlayerTransition _playerTransition;
	
	private final int Rows = 3;
	private final int Columns = 15;
	
	private Bullet _bullet;
	
	private ArrayList<BaseInvader> _aliens = new ArrayList<BaseInvader>(); 
	
	public SpaceInvadersState(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		setupScreen();
	    setupTransition(previous);
	}
	
	protected Paddle createPlayer() {
		return new Paddle(new Vector2(Config.window_width / 2, 0), new Vector2(50, 50), Color.RED, this);
	}
	
	private void setupScreen() {
		Color[] alienColor = new Color[] { Color.CYAN, Color.MAGENTA, Color.YELLOW };
		float[] alienSize = new float[] { 8f, 10f, 9f }; 
		
		float hgap = 800 / (Columns + 1);
		float vgap = 300 / (Rows + 1);
		
		float xPos = _windowBounds.x + ((_windowBounds.width - 800)/2) + hgap;
		float initX = xPos;
		float yPos = _windowBounds.height - vgap;
				
		for (int y = 0; y < Rows; y++) {
			for (int x = 0; x < Columns; x++) {
				float size = alienSize[y];
				BaseInvader alien = new BaseInvader(this, size * 2, alienColor[y]);
				alien.setPosition(xPos - size, yPos - size);
				_aliens.add(alien);
				xPos += hgap;
			}
			xPos = initX;
			yPos -= vgap;
		}
	}

	@Override
	protected void updateScreen(float delta) {	
		if (_game.input.isButtonDown(0) && _bullet == null) {
			_bullet = new Bullet(_player, this);
		}
	}
	
	private float _transition = 0;	
	
	protected boolean transitionScreen(float delta) {
		_transition += delta;
		
		if (_playerTransition != null) {
			_playerTransition.Update(delta);
		}
		return (_transition < TransitionTime);
	}
	
	private void setupTransition(GameState previousScreen) {
		if (previousScreen == null) return;
		
		_playerTransition = new PlayerTransition(_player,  previousScreen.getPlayer(), TransitionTime);
	}

	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

}
