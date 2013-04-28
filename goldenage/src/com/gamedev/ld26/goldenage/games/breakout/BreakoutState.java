package com.gamedev.ld26.goldenage.games.breakout;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
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
				}
				if (object.getClass() == Paddle.class && object != previous.getPlayer())
				{
					_startPos = new Vector2(object.getRect().x, 0);
				}
			}
		}
		else {
			_ball = new Ball(new Vector2(Config.window_width/2, Config.window_height),  10, Color.WHITE, this);
			_ball.setVelocity(new Vector2(.2f, -.8f));
			_startPos = new Vector2(Config.window_width/2, 0);
		}

		for (int y = 0; y < Block.BLOCKS_TALL; y ++)
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
		//_ball.render();

	}

}
