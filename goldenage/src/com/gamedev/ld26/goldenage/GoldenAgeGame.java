package com.gamedev.ld26.goldenage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.core.Input;
import com.gamedev.ld26.goldenage.screens.PlayScreen;
import com.gamedev.ld26.goldenage.screens.TitleScreen;

public class GoldenAgeGame extends Game {

	public Input input;
	
	public TitleScreen title;
	public PlayScreen play;
	
	@Override
	public void create() {		
		Assets.load();
		
		input = new Input();
		Gdx.input.setInputProcessor(input);
		
		title = new TitleScreen(this);
		play = new PlayScreen(this);
		setScreen(title);
	}

	@Override
	public void dispose() {
		Assets.dispose();
	}
	
}
