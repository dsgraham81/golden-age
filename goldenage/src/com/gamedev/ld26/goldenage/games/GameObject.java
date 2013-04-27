package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class GameObject {
	
	protected Rectangle _rect;
	protected Color _color;
	protected GameState _gState;
	
	public GameObject(Vector2 pos, Vector2 size, Color color, GameState gs) {
		_gState = gs;
		_gState.AddGameObject(this);
		_rect = new Rectangle(pos.x, pos.y, size.x, size.y);
		this._color = color;
	}
	
	public GameObject(GameState gs)
	{
		_gState = gs;
		_gState.AddGameObject(this);
	}
		
	public void setPosition(float x, float y)
	{
		_rect.x = Utils.clamp(x, 0, Config.window_width - _rect.width);
		_rect.y = Utils.clamp(y, 0, Config.window_height - _rect.height);
	}
	
	public void render() {
		Assets.shapes.setColor(_color);
		Assets.shapes.rect(_rect.x, _rect.y, _rect.width, _rect.height);
	}
	
	public Rectangle getRect() { return _rect; }
	public Color getColor() { return _color; }
}
