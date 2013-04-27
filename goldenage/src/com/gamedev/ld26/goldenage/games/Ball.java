package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class Ball {

	private Circle _circle;
	private Color _color;
	private Vector2 _vel;
	private float _speed = 500.f;
	private float _maxSpeed = 750.f;
	
	public Ball(Vector2 pos, float radius, Color color) {
		_circle = new Circle(pos.x, pos.y, radius);
		_color = color;
		float x = Assets.random.nextFloat() * _speed - _speed / 2;
		float y = Assets.random.nextFloat() * _speed - _speed / 2;
		_vel = new Vector2(x, y);
	}
	
	public void update(float delta) {
		_circle.x += _vel.x * delta;
		_circle.y += _vel.y * delta;
		
		// TODO : updated to allow for arbitrary angles, not just 45's
		// Keep inside window boundary
		if ((_circle.x - _circle.radius) < 0) {
			_circle.x = _circle.radius;
			_vel.x *= -1.5;
		}
		if ((_circle.x + _circle.radius) > Config.window_width) {
			_circle.x = Config.window_width - _circle.radius;
			_vel.x *= -1.5;
		}
		if ((_circle.y - _circle.radius) < 0) {
			_circle.y = _circle.radius;
			_vel.y *= -1.5;
		}
		if ((_circle.y + _circle.radius) > Config.window_height) {
			_circle.y = Config.window_height - _circle.radius;
			_vel.y *= -1.5;
		}
		
		_vel.x = Utils.clamp(_vel.x, -_maxSpeed, _maxSpeed);
		_vel.y = Utils.clamp(_vel.y, -_maxSpeed, _maxSpeed);
	}
	
	public void render() {
		Assets.shapes.setColor(_color);
		Assets.shapes.circle(_circle.x, _circle.y, _circle.radius);
	}
	
	public Circle getCircle() { return _circle; }
	public Color getColor() { return _color; }
	
}
