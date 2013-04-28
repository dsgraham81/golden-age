package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.utils.Utils;

public class TextTransition extends FadeTransition {
	
	private String _text;
	private Color _color;
	private Vector2 _position;
	
	public TextTransition(String text, Vector2 position, Color color, float time) {
		super(null, true, time);
		_color = new Color(color);
		_text = text;
		_position = position;
	}

	public void Update(float time) {
		float alpha = (_color.a + _da);
		alpha = Utils.clamp(alpha,  0,  1);
		_color.a = alpha;
		Utils.drawText(_text, _position.x, _position.y, 30, 30, _color);

	}
}
