package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class GameObject {
	
	protected Rectangle _rect;
	protected Color _color;
	protected GameState _gState;
	protected boolean _alive;
	protected Object collideObject;
	
	public GameObject(Vector2 pos, Vector2 size, Color color, GameState gs) {
		this(gs);
		_rect = new Rectangle(pos.x, pos.y, size.x, size.y);
		this._color = color;
		this.collideObject = _rect;
	}
	
	public GameObject(GameState gs)
	{
		_gState = gs;
		_gState.AddGameObject(this);
		_alive = true;
	}
	
	public boolean collides(GameObject other)
	{
		//TODO make this use a real collideable object
		return _rect.overlaps(other.getRect());
	}
	
	public void setAlive(boolean alive)
	{
		_alive = alive;
	}
	
	public void setPosition(float x, float y)
	{
		_rect.x = Utils.clamp(x, 0, Config.window_width - _rect.width);
		_rect.y = Utils.clamp(y, 0, Config.window_height - _rect.height);
	}
	
	public void setSize(float width, float height) {
		_rect.width = width;
		_rect.height = height;		
	}
	
	public void render() {
		Assets.shapes.setColor(_color);
		Assets.shapes.rect(_rect.x, _rect.y, _rect.width, _rect.height);
	}
	
	public Vector2 getUpperLeftPoint()
	{
		return new Vector2(_rect.x,  _rect.y + _rect.height);
	}
	
	public Vector2 getLowerLeftPoint()
	{
		return new Vector2(_rect.x,  _rect.y);
	}
	
	public Vector2 getUpperRightPoint()
	{
		return new Vector2(_rect.x + _rect.width,  _rect.y + _rect.height);
	}
	
	public Vector2 getLowerRightPoint()
	{
		return new Vector2(_rect.x + _rect.width,  _rect.y);
	}
	public Rectangle getRect() { return _rect; }
	public Color getColor() { return _color; }


}
