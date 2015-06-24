package com.mikesantiago.libgdxtest.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mikesantiago.libgdxtest.handlers.Animation;
import com.mikesantiago.libgdxtest.handlers.Box2DVars;

public class Box2DSprite 
{
	protected Body body;
	protected Animation animation;
	protected float width, height;
	
	public Box2DSprite(Body body)
	{
		this.body = body;
		animation = new Animation();
	}

	public void setAnimation(TextureRegion[] region, float delay)
	{
		animation.setFrames(region, delay);
		width = region[0].getRegionWidth();
		height = region[0].getRegionHeight();
	}
	
	public void update(float deltaTime)
	{
		animation.update(deltaTime);
	}
	
	public void render(SpriteBatch sb)
	{
		sb.begin();
		sb.draw(animation.getFrame(),
				body.getPosition().x * Box2DVars.PIXELS_PER_METER - width / 2,
				body.getPosition().y * Box2DVars.PIXELS_PER_METER - height / 2);
		sb.end();
	}
	
	public Body getBody(){return body;}
	public Vector2 getPosition(){return body.getPosition();}
	public float getWidth(){return width;}
	public float getHeight(){return height;}
	
}
