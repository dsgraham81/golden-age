package com.gamedev.ld26.goldenage.games.G1942;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.IShooter;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.BulletFactory;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.utils.Config;

public class Plane extends GameObject implements IShooter {

	private float distance;
	private float _dir;
	private float speed;
	private BulletFactory bFactory;
	
	public Plane(Vector2 pos, Vector2 size, Color color, GameState gs, BulletFactory bf) {
		super(pos, size, color, gs);
		distance = Assets.random.nextFloat() * .4f + .3f;
		distance *= Config.window_height;
		_dir = 70 + Assets.random.nextInt(40);
		speed = 100;
		bFactory = bf;
	}

	public void update(float dt){
		float x = (float)Math.cos(_dir / 180 * Math.PI);
		float y = -(float)Math.sin(_dir / 180 * Math.PI);
		
		_rect.x += x * speed * dt;
		_rect.y += y * speed * dt;
		
		if (_rect.y < distance)
		{
			bFactory.GetBullet(this);
			_dir += 180;
		}
		
		_alive = _gState._windowBounds.overlaps(_rect);
	}

	@Override
	public Vector2 GetTip() {
		return new Vector2(_rect.x + (_rect.width/2), _rect.y);
	}

	@Override
	public int getShootSpeed() {
		return 1000 + Assets.random.nextInt(500);
	}

	@Override
	public Vector2 getShootDirection() {
		float x = (float)Math.cos(_dir / 180 * Math.PI);
		float y = -(float)Math.sin(_dir / 180 * Math.PI);
		return new Vector2(x, y);
	}
}