package com.gamedev.ld26.goldenage.games.breakout;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.games.Ball;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.Paddle;
import com.gamedev.ld26.goldenage.games.pong.PongState;
import com.gamedev.ld26.goldenage.utils.Config;

public class BreakoutState extends GameState {

	static final Vector2 size = new Vector2(80, 20);
	private Ball _ball;
	private Vector2 _startPos;
	private ArrayList<Block> _blocks = new ArrayList<Block>();
	
	public BreakoutState(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		if (previous != null && previous.getClass() == PongState.class)
		{
			for (GameObject object : previous.getGameObjects())
			{
				if (object.getClass() == Ball.class )
				{
					_ball = (Ball)object;
					_gameObjects.add(_ball);
				}
				if (object.getClass() == Paddle.class && object != previous.getPlayer())
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

		for (int y = 2; y < Block.BLOCKS_TALL; y ++)
		{
			for (int x = 0; x < Block.BLOCKS_WIDE; x++)
			{
				float greyLevel = (float)y/Block.BLOCKS_TALL;
				Color blockColor = new Color(greyLevel, greyLevel, greyLevel, 1.0f);
				Block newBlock = new Block(new Vector2(x,y), blockColor, _startPos, this);
				_blocks.add(newBlock);
			}
		}
	}

	@Override
	protected void updateScreen(float delta) {
		_ball.update(delta);
		int blockCount = 0;
		for (GameObject block : _gameObjects)
		{
			if (block.getClass() == Block.class)
			{
				blockCount++;
				if (_ball.collides(block))
				{
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
		if (blockCount < Block.BLOCKS_TALL * Block.BLOCKS_WIDE * .5f) gameWon = true;
		handlePaddleCollisions();
	}
	
	
	private void handlePaddleCollisions() {
		final Rectangle playerRect = _player.getRect();
		final Circle ballCircle = _ball.getCircle();
		Vector2 ballDir = _ball.getDir();
		
		// Intersection tests - ball/paddle
		if (Intersector.overlaps(ballCircle, playerRect)) {
			ballCircle.y = playerRect.y + playerRect.height + ballCircle.radius;
			ballDir.y *= -1;
			float hitPos = (ballCircle.x - playerRect.x) / playerRect.width;
			ballDir.x += hitPos - .5f;
			_ball.setSpeed(_ball.getSpeed() + 50.f);
		}
	}
	
	
	@Override
	public boolean transitionScreen(float delta)
	{
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
		_ball.update(delta);
		return done;
	}

	@Override
	protected void renderScreen(float delta) {


	}

}
