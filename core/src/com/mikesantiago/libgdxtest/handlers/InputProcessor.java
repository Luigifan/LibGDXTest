package com.mikesantiago.libgdxtest.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter 
{
	public boolean keyDown(int k)
	{
		if(k == Keys.UP)
		{
			Input.setKey(Input.BUTTON1, true);
		}
		if(k == Keys.DOWN)
		{
			Input.setKey(Input.BUTTON2, true);
		}
		return true;
	}
	
	
	public boolean keyUp(int k)
	{
		if(k == Keys.UP)
		{
			Input.setKey(Input.BUTTON1, false);
		}
		if(k == Keys.DOWN)
		{
			Input.setKey(Input.BUTTON2, false);
		}
		
		return true;
	}
}
