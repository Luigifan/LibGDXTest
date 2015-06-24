package com.mikesantiago.libgdxtest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.mikesantiago.libgdxtest.handlers.Box2DVars;
import com.mikesantiago.libgdxtest.main.Game;

public class Player extends Box2DSprite
{
	private int numCrystals;
	private int totalCrystals;
	private int direction = 1;
	private boolean isMoving = false;
	private short[] blockbits = {Box2DVars.BIT_RED, Box2DVars.BIT_GREEN, Box2DVars.BIT_BLUE};
	private int blockbits_index = 0;
	
	public Player(Body body)
	{
		super(body);
		Texture sheet = Game.content.getTexture("bunny");
		TextureRegion[] spriteFrames = TextureRegion.split(sheet, 32, 32)[0];
		setAnimation(spriteFrames, 1 / 12f);
	}

	/**
	 * 
	 * @param dir -1 for backwards, 1 for forwards
	 */
	public void setCollisions(int dir)
	{
		Filter filter = body.getFixtureList().first().getFilterData();
		
		if(dir == 1)
		{
			filter.maskBits &= ~blockbits[blockbits_index];
			if(blockbits_index > blockbits.length - 1)
				blockbits_index = 0;
			blockbits_index++;
			//Box2DVars.BIT_CRYSTAL | blockbits[blockbits_index]
			filter.maskBits |= blockbits[blockbits_index];
		}
		else if(dir == -1)
		{
			blockbits_index--;
			if(blockbits_index < 0)
				blockbits_index = 0;
			
			//Box2DVars.BIT_CRYSTAL | blockbits[blockbits_index]
			filter.maskBits = (short) (Box2DVars.BIT_CRYSTAL | blockbits[blockbits_index]);
		}
	}
	
	@Override
	public void render(SpriteBatch sb)
	{
		sb.begin();
		if(isMoving)
		{
			animation.setDelay(1 / 12f);
			if(direction == 1)
			{
				TextureRegion flipped = animation.getFrame();
				if(flipped.isFlipX())
					flipped.flip(true, false);
				sb.draw(flipped,
						body.getPosition().x * Box2DVars.PIXELS_PER_METER - width / 2,
						body.getPosition().y * Box2DVars.PIXELS_PER_METER - height / 2);
			}
			else if(direction == -1)
			{
				TextureRegion flipped = animation.getFrame();
				if(!flipped.isFlipX())
					flipped.flip(true, false);
				sb.draw(flipped,
						body.getPosition().x * Box2DVars.PIXELS_PER_METER - width / 2,
						body.getPosition().y * Box2DVars.PIXELS_PER_METER - height / 2);
			}
		}
		else
		{
			animation.setDelay(0);
			if(direction == 1)
			{
				TextureRegion flipped = animation.getFrame();
				if(flipped.isFlipX())
					flipped.flip(true, false);
				sb.draw(flipped,
						body.getPosition().x * Box2DVars.PIXELS_PER_METER - width / 2,
						body.getPosition().y * Box2DVars.PIXELS_PER_METER - height / 2);
			}
			else if(direction == -1)
			{
				TextureRegion flipped = animation.getFrame();
				if(!flipped.isFlipX())
					flipped.flip(true, false);
				sb.draw(flipped,
						body.getPosition().x * Box2DVars.PIXELS_PER_METER - width / 2,
						body.getPosition().y * Box2DVars.PIXELS_PER_METER - height / 2);
			}
		}
		sb.end();
	}
	
	//1 is right
	//-1 is left
	public void setMoving(boolean moving){this.isMoving = moving;}
	public boolean getMoving(){return isMoving;}
	public void setDirection(int dir){this.direction = dir;}
	public int getDirection(){return this.direction;}
	public int getNumCrystals(){return numCrystals;}
	public int getTotalCrystals(){return totalCrystals;}
	public void collectCrystal(){numCrystals++;}
	public void setTotalCrystals(int i){totalCrystals = i;}
}
