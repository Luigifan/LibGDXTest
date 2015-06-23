package com.mikesantiago.libgdxtest.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mikesantiago.libgdxtest.handlers.GameStateManager;
import com.mikesantiago.libgdxtest.main.Game;

public abstract class GameState 
{
	protected GameStateManager gsm;
	protected Game game;
	protected SpriteBatch sb;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;

	
	protected GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
		game = gsm.game();
		sb = game.getSpriteBatch();
		cam = game.getCam();
		hudCam = game.getHudCam();
	}
	
	public abstract void handleInput();
	public abstract void update(float deltaTime);
	public abstract void render();
	public abstract void dispose();
	
}
