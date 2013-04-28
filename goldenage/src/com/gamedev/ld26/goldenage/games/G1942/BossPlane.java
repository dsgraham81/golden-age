package com.gamedev.ld26.goldenage.games.G1942;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.Bullet;
import com.gamedev.ld26.goldenage.games.BulletFactory;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class BossPlane extends Plane {

	private Vector2 _targetPosition;
	private static final Vector2 _size = new Vector2(100,100);
	
	public BossPlane(Color color, GameState gs,	BulletFactory bf) {
		super(new Vector2(Config.window_half_width - _size.x/2, 0), _size, color, gs, bf);
		_targetPosition = new Vector2(_rect.x, Config.window_half_height);
		_hitPoints = 50;
	}

	public void update(float dt){
		Vector2 tempAngle = new Vector2(_targetPosition.x -_rect.x, _targetPosition.y - _rect.y);
		float distanceSq = _targetPosition.dst2(_rect.x, _rect.y);
		if (distanceSq <= (speed * dt) * (speed * dt))
		{
			_rect.x = _targetPosition.x;
			_rect.y = _targetPosition.y;
			fireArc(10);
			_targetPosition = new Vector2(Assets.random.nextFloat() * Config.window_width,
										  Assets.random.nextFloat() * Config.window_height);
		}
		else {
			tempAngle.nor();
			_rect.x += tempAngle.x * speed * dt;
			_rect.y += tempAngle.y * speed * dt;
		}
	}
	
	public void fireArc(int bullets)
	{
		float angleDelta = 360.0f / bullets;
		float angle = 0;
		
		for (int i =0; i < bullets; i++)
		{
			angle += angleDelta;
			float x = (float)Math.cos(angle / 180.0 * Math.PI);
			float y = -(float)Math.sin(angle / 180.0 * Math.PI);
			
			Bullet bullet = bFactory.GetBullet(Utils.rectCenter(_rect), 100, _color, new Vector2(x, y), 5); 
			bullet.setTarget(_gState.getPlayer());
		}
		
	}
}
