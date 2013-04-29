package com.gamedev.ld26.goldenage.games;

import sun.java2d.pipe.hw.AccelDeviceEventListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Utils;

public class Particle {

	private Vector2 _pos;
	private Color _color;
	private Vector2 _vel;
	private Vector2 _accel;
	private float _ttl;
	private boolean _alive;
	
	public Particle(Vector2 pos)
	{
		this(pos, Utils.getRandomColor());
	}
	
	public Particle(Vector2 pos, Color color) {
		this(pos, color, Vector2.Zero);
	}
	
	public Particle(Vector2 pos, Color color, Vector2 vel)
	{
		this(pos, color, vel, Vector2.Zero);
	}
	
	public Particle(Vector2 pos, Color color, Vector2 vel, Vector2 accel)
	{
		_pos = new Vector2(pos);
		_color = color;
		_vel = vel;
		_accel = accel;
		_ttl = 1f;
		_alive = true;
	}
	
	public void Update(float dt)
	{
		_ttl -= dt;
		if (_ttl < 0) _alive = false;
		_vel.x += _accel.x * dt;
		_vel.y += _accel.y * dt;
		_pos.x += _vel.x * dt;
		_pos.y += _vel.y * dt;
	}
	
	public void Render()
	{
		if (!_alive) return;
		Assets.shapes.setColor(_color);
		Assets.shapes.rect(_pos.x, _pos.y, 2, 2);
	}

	public boolean isAlive()
	{
		return _alive;
	}
}
