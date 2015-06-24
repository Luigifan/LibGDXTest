package com.mikesantiago.libgdxtest.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter 
{
	public boolean keyDown(int k)
	{
		if(k == Keys.UP)
		{
			Input.setKey(Input.MOVE_UP, true);
		}
		if(k == Keys.DOWN)
		{
			Input.setKey(Input.MOVE_DOWN, true);
		}
		if(k == Keys.LEFT)
		{
			Input.setKey(Input.MOVE_LEFT, true);
		}
		if(k == Keys.RIGHT)
		{
			Input.setKey(Input.MOVE_RIGHT, true);
		}
		return true;
	}
	
	
	public boolean keyUp(int k)
	{
		if(k == Keys.UP)
		{
			Input.setKey(Input.MOVE_UP, false);
		}
		if(k == Keys.DOWN)
		{
			Input.setKey(Input.MOVE_DOWN, false);
		}
		if(k == Keys.LEFT)
		{
			Input.setKey(Input.MOVE_LEFT, false);
		}
		if(k == Keys.RIGHT)
		{
			Input.setKey(Input.MOVE_RIGHT, false);
		}
		
		return true;
	}
}
