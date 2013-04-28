package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Utils;

public class Ball extends GameObject {

	private static final float max_speed = 750.f;
	
	private Circle _circle;
	private Color _color;
	private Vector2 _dir;
	private float _speed;
	
	public Ball(Vector2 pos, float radius, Color color, GameState gs) {
		super(pos, new Vector2(radius, radius), color, gs);
		
		_circle = new Circle(pos.x, pos.y, radius);
		_color = color;
		float x = Assets.random.nextFloat() * 2.f - 1.f;
		float y = Assets.random.nextBoolean() ? -1.f : 1.f;
		_dir = new Vector2(x, y).nor();
		_speed = 500.f;
	}
	
	public void setPosition(float x, float y)
	{
		_circle.x = Utils.clamp(x, 0, Config.window_width - _rect.width);
		_circle.y = Utils.clamp(y, 0, Config.window_height - _rect.height);
	}
	
	public void setVelocity(Vector2 newVel)
	{
		_dir = newVel;
	}
	
	public void multiplyDir(Vector2 newVal)
	{
		_dir.scl(newVal);
	}
	
	public void setCollisionDirection(Vector2 bounce)
	{
		if (bounce.x != 0)
		{
			_dir.x = Math.abs(_dir.x);
			_dir.x *= bounce.x;
			_dir.y += (Assets.random.nextFloat() -.5f) * .1f;
		}
		if (bounce.y != 0)
		{
			_dir.y = Math.abs(_dir.y);
			_dir.y *= bounce.y;
			_dir.x += (Assets.random.nextFloat() -.5f) * .1f;
		}
		//_dir.nor();
	}
	
	public boolean collides(GameObject other)
	{
		return Intersector.overlaps(_circle, other._rect);
	}
	
	public void update(float delta) {
		if (!_alive) return;
		_circle.x += _dir.x * _speed * delta;
		_circle.y += _dir.y * _speed * delta;
		
		keepInsideRect(_gState._windowBounds);
		clampSpeed();
		
		_rect.x = _circle.x;
		_rect.y = _circle.y;
	}

	public void clampSpeed() {
		_speed = Utils.clamp(_speed, -max_speed, max_speed);
	}

	public void render() {
		Assets.shapes.setColor(_color);
		Assets.shapes.circle(_circle.x, _circle.y, _circle.radius);
	}
	
	public Circle getCircle() { return _circle; }
	public Color getColor() { return _color; }
	public Vector2 getDir() { return _dir; }
	public float getSpeed() { return _speed; }
	public void setSpeed(float s) { _speed = s; clampSpeed(); }

	public Vector2 getPos()
	{
		return new Vector2(_circle.x, _circle.y);
	}
	
	private void keepInsideRect(Rectangle bounds) {
		if ((_circle.x - _circle.radius) < bounds.x) {
			_circle.x = bounds.x + _circle.radius;
			_dir.x = -_dir.x;
		}
		if ((_circle.x + _circle.radius) > (bounds.x + bounds.width)) {
			_circle.x = (bounds.x + bounds.width) - _circle.radius;
			_dir.x = -_dir.x;
		}
		if ((_circle.y - _circle.radius) < bounds.y) {
			_circle.y = bounds.y + _circle.radius;
			_dir.y = -_dir.y;
		}
		if ((_circle.y + _circle.radius) > (bounds.y + bounds.height)) {
			_circle.y = (bounds.y + bounds.height) - _circle.radius;
			_dir.y = -_dir.y;
		}
	}
	
}
