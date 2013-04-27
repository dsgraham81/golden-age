package com.gamedev.ld26.goldenage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.core.Input;
import com.gamedev.ld26.goldenage.screens.TitleScreen;

public class GoldenAgeGame extends Game {

	private Input input;
	
	private TitleScreen title;
	
	@Override
	public void create() {		
		Assets.load();
		
		input = new Input();
		Gdx.input.setInputProcessor(input);
		
		title = new TitleScreen(this);
		setScreen(title);
	}

	@Override
	public void dispose() {
		Assets.dispose();
	}
	
	public Input getInput() { return input; }
	
}
