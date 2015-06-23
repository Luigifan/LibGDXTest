package com.mikesantiago.libgdxtest.handlers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mikesantiago.libgdxtest.states.GameState;

public class StateTest extends GameState 
{
	BitmapFont fnt = new BitmapFont();

	public StateTest(GameStateManager gsm)
	{
		super(gsm);
	}
	@Override
	public void handleInput() 
	{
	}

	@Override
	public void update(float deltaTime) 
	{

	}

	@Override
	public void render() 
	{
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		fnt.draw(sb, "State Test", 0, fnt.getLineHeight());
		sb.end();
	}

	@Override
	public void dispose() 
	{
	}

}
