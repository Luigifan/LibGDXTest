package com.mikesantiago.libgdxtest.entities;

import com.mikesantiago.libgdxtest.main.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Crystal extends Box2DSprite
{
	public Crystal(Body body)
	{
		super(body);
		
		Texture tex = Game.content.getTexture("crystal");
		TextureRegion[] frames = TextureRegion.split(tex, 16, 16)[0]; //first row
		
		setAnimation(frames, 1 / 12f);
	}
	
	
}
