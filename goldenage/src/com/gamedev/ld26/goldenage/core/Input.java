package com.gamedev.ld26.goldenage.core;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Input extends InputAdapter {
	
	private final Vector2 prevMouse= new Vector2(0,0);
	private final Vector2 currMouse = new Vector2(0,0);
	private final boolean[] keys = new boolean[256];
	
	public Input() {
		super();
		resetKeys();
	}
	
	public void resetKeys() {
		for(int i = 0; i < keys.length; ++i) {
			keys[i] = false;
		}
	}
	
	public boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
	
	public boolean isKeyUp(int keycode) {
		return !keys[keycode];
	}
	
	public Vector2 getCurrMouse() { return currMouse; }
	public Vector2 getPrevMouse() { return prevMouse; }
	
	@Override
	public boolean keyDown(int keycode) {
		keys[keycode] = true;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		keys[keycode] = false;
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		prevMouse.set(currMouse);
		currMouse.set(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
