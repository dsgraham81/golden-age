package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.math.Rectangle;

public class PlayerTransition {
	
	private Rectangle _initialBounds;
	private GameObject _transitionObject;
	
	private float _transition = 0;
	private float _dw, _dh, _initWidth, _initHeight;
		
	public PlayerTransition(GameObject transition, GameObject initial, float transitionTime)
	{
		_transitionObject = transition;
		Rectangle bounds = transition.getRect();
		
		_initialBounds = initial.getRect();
			
		_dw = -(_initialBounds.width - bounds.width)/ transitionTime;
		_dh = -(_initialBounds.height - bounds.height)/transitionTime;
		_initWidth = _initialBounds.width;
		_initHeight = _initialBounds.height;		
	}
	
	public void Update(float delta) {
		_transition += delta;
		_transitionObject.setSize(_initWidth + (_transition * _dw), _initHeight + (_transition * _dh));
	}
}
