package com.gamedev.ld26.goldenage.games.breakout;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.Globals;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.core.Score;
import com.gamedev.ld26.goldenage.games.Ball;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.Player;
import com.gamedev.ld26.goldenage.games.TextTransition;
import com.gamedev.ld26.goldenage.games.G1942.G1942State;
import com.gamedev.ld26.goldenage.games.pong.PongState;
import com.gamedev.ld26.goldenage.games.spaceinvaders.SpaceInvadersState;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class BreakoutState extends GameState {

	static final Vector2 size = new Vector2(80, 20);
	private Ball _ball;
	private Vector2 _startPos;
	private ArrayList<Block> _blocks = new ArrayList<Block>();
	private float _respawnTime =0;
	private boolean transistionOn = false;
	private float textScale =0;
	
	Rectangle edgeLeft = new Rectangle(Config.pong_window_bounds.x - 20, 0, 20, Config.window_height);
	Rectangle edgeRight = new Rectangle(Config.pong_window_bounds.x + Config.pong_window_bounds.width, 0, 20, Config.window_height);
	
	public BreakoutState(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		if (previous != null && previous.getClass() == PongState.class)
		{
			for (GameObject object : previous.getGameObjects())
			{
				if (object.getClass() == Ball.class )
				{
					_ball = (Ball)object;
					_ball.getCircle().y = Config.window_height - _ball.getCircle().radius;
					_ball.multiplyDir(new Vector2(1, -1));
					_ball.getDir().nor();
					_ball.setGameState(this);
					_ball.setSpeed(300);
					_gameObjects.add(_ball);
				}
				if (object.getClass() == Player.class && object != previous.getPlayer())
				{
					_startPos = new Vector2(object.getRect().x, Config.window_height);
				}
			}
		}
		else {
			_ball = new Ball(new Vector2(Config.window_width/2, Config.window_height),  10, Color.WHITE, this);
			_ball.setVelocity(new Vector2(.2f, -.8f));
			_startPos = new Vector2(Config.window_width/2, Config.window_height);
		}

		for (int y = Block.TOP_GAP; y < Block.BLOCKS_TALL; y ++)
		{
			for (int x = 0; x < Block.BLOCKS_WIDE; x++)
			{
				float greyLevel = (float)y/Block.BLOCKS_TALL;
				Color blockColor = new Color(greyLevel, greyLevel, greyLevel, 1.0f);
				Block newBlock = new Block(new Vector2(x,y), blockColor, _startPos, this);
				newBlock.Score = 2 * (Block.BLOCKS_TALL - y);
				_blocks.add(newBlock);
			}
		}
		
		_transitionTime = (Config.window_height *  Block.BLOCK_AREA) / (_ball.getSpeed() * Math.abs(_ball.getDir().y));
		System.out.println("TransitionTime : " + _transitionTime);
		_windowBounds = new Rectangle(0,-20, Config.window_width, Config.window_height+20);
		_stageMusic = Assets.breakoutMusic;


		String textString = "Welcome to 1976";
		clearTransitions();
		addTransition(new TextTransition(textString,
				new Vector2(Config.window_half_width - (textString.length() * 30 /2.0f), Config.window_half_height), Color.RED, _transitionTime));
	}

	@Override
	protected void updateScreen(float delta) {

		if (_respawnTime > 0)
		{
			respawnBall(delta);
		}
		//_ball.update(delta);
		int blockCount = 0;
		for (GameObject block : _gameObjects)
		{
			if (block.getClass() == Block.class)
			{
				blockCount++;
				if (_ball.collides(block))
				{
					Assets.bricksSound.play();
					Score.AddToScore(block.Score);
					block.setAlive(false);
					
					boolean intersectLeft = Intersector.intersectSegmentCircle(block.getUpperLeftPoint(), 
																		  block.getLowerLeftPoint(), 
																		  _ball.getPos(), _ball.getCircle().radius * _ball.getCircle().radius);
					
					boolean intersectRight = Intersector.intersectSegmentCircle(block.getUpperRightPoint(), 
																		        block.getLowerRightPoint(), 
																		        _ball.getPos(), _ball.getCircle().radius * _ball.getCircle().radius);
					
					boolean intersectTop = Intersector.intersectSegmentCircle(block.getUpperLeftPoint(), 
							  											  block.getUpperRightPoint(), 
							  											  _ball.getPos(), _ball.getCircle().radius * _ball.getCircle().radius);
							  											
					boolean intersectBottom= Intersector.intersectSegmentCircle(block.getLowerLeftPoint(), 
							        		   							  block.getLowerRightPoint(), 
							        		   							  _ball.getPos(), _ball.getCircle().radius * _ball.getCircle().radius);
					float dirX =0;
					float dirY =0;
					if (intersectBottom) dirY = -1;
					if (intersectTop) dirY = 1;
					if (intersectLeft) dirX = -1;
					if (intersectRight) dirX = 1;
					_ball.setCollisionDirection(new Vector2(dirX, dirY));
				}
			}
		}
		if (blockCount < (Block.BLOCKS_TALL- Block.TOP_GAP) * Block.BLOCKS_WIDE * .5f) _gameWon = true;
		handlePaddleCollisions();
	}
	
	
	
	private void respawnBall(float dt){
		_respawnTime -=dt;
		if (_respawnTime <=0)
		{
			_ball.setAlive(true);
			AddGameObject(_ball);
			_ball.setPosition(_player.getRect().x + (_player.getRect().width /2.0f), _player.getRect().height + _ball.getCircle().radius + 1);
			_ball.setVelocity(new Vector2(.2f, 1f));
			_ball.setSpeed(500f);
		}
	}
	
	private void handlePaddleCollisions() {
		final Rectangle playerRect = _player.getRect();
		Circle ballCircle = _ball.getCircle();
		Vector2 ballDir = _ball.getDir();
		
		// Intersection tests - ball/paddle
		if (Intersector.overlaps(ballCircle, playerRect)) {
			Assets.beep.play();
			ballCircle.y = playerRect.y + playerRect.height + ballCircle.radius;
			ballDir.y = Math.abs(ballDir.y);
			float hitPos = (ballCircle.x - playerRect.x) / playerRect.width;
			ballDir.x += hitPos - .5f;
			_ball.setSpeed(_ball.getSpeed() + 50.f);
		}
		if (_ball.getPos().y <= 0 && _ball.isAlive())
		{
			Assets.lifeLostSound.play();
			_ball.setAlive(false);
			_respawnTime = 3f;
		}
	}
	
	
	@Override
	public boolean transitionScreen(float delta)
	{
		super.transitionScreen(delta);
		boolean done = true;
		float scale = (Config.window_height - (_ball.getCircle().y + _ball.getCircle().radius))/ Config.window_height;
		scale /= Block.BLOCK_AREA;
		if (scale > 1f)
		{
			scale = 1f;
			done = false;
		}
		for (Block block :_blocks)
		{
			block.setSize(scale);
		}
		//_ball.update(delta);
		
		textScale = 1.0f - scale;
		transistionOn = done;
		
		super.transitionScreen(delta);		
		
		return done;
	}

	@Override
	protected void renderScreen(float delta) {
		if (transistionOn)
		{
			Assets.shapes.setColor(new Color(textScale,textScale,textScale,textScale));
			Assets.shapes.rect(edgeLeft.x * textScale, edgeLeft.y, edgeLeft.width, edgeLeft.height);
			Assets.shapes.rect(edgeRight.x + ((Config.window_width - edgeRight.x) *(1.0f - textScale)), edgeRight.y, edgeRight.width, edgeRight.height);
			
			String textString = "Welcome to 1976";			
			//Utils.drawText(textString, Config.window_half_width - (textString.length() * 30 /2.0f), Config.window_half_height, 30, 30, new Color(1f,0,0,textScale));
		}
		
		
		Utils.drawText(Score.getScoreString(3), 10, Config.window_height - 40, 20, 20, new Color(1f,1f,1f,1f-textScale));
	}

	public Globals.Games nextScreen() {
		return Globals.Games.spaceinvaders;
	}
}
