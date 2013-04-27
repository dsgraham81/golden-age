package com.gamedev.ld26.goldenage.games.breakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class Block extends GameObject {
	public static final int BLOCKS_WIDE = 20;
	public static final int BLOCKS_TALL = 10;
	public static final float BLOCK_AREA = .3f;

	private final float BLOCK_WIDTH = Config.window_width / BLOCKS_WIDE;
	private final float BLOCK_HEIGHT = (Config.window_height * BLOCK_AREA) / BLOCKS_TALL;
	private final Vector2 _index;
	private Vector2 _pos; 
	private Color _color;
	private float _width;
	private float _height; 
	private final Vector2 _origin;
	
	public Block(Vector2 pos, Color color, Vector2 origin, GameState gs)
	{
		super(gs);
		_index = pos;
		_color = color;
		_origin = origin;
		setSize(0);
	}
	
	public void setSize(float size)
	{
		_width = BLOCK_WIDTH * size;
		_height = BLOCK_HEIGHT * size;
		_pos = Utils.lerpVector2(_origin, new Vector2(_index.x * BLOCK_WIDTH, _index.y * BLOCK_HEIGHT), size);
	}
	

	public void update(float deltaTime)
	{
		
	}
	
	public void render()
	{
		Assets.shapes.setColor(_color);
		Assets.shapes.rect(_pos.x, (Config.window_height - _height) - (_pos.y), _width, _height);
		Assets.shapes.end();
		
		Assets.shapes.begin(ShapeType.Line);
		Assets.shapes.setColor(Color.GRAY);
		Assets.shapes.rect(_pos.x, (Config.window_height - _height) - (_pos.y), _width, _height);
		Assets.shapes.end();
		
		Assets.shapes.begin(ShapeType.Filled);
	}

}
