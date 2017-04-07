package com.frismos.isotest;

import com.badlogic.gdx.Game;

public class IsometricGame extends Game {

	
	@Override
	public void create () {
		setScreen(new Play());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}

	@Override
	public void render () {

		super.render();

	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
