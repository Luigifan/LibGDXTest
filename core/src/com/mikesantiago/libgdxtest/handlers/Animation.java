package com.mikesantiago.libgdxtest.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation 
{
	private TextureRegion[] frames;
	private float timer, delay;
	private int currentFrame, timesPlayed;
	
	public Animation(){}
	public Animation(TextureRegion[] frames)
	{
		this(frames, 1 / 12f);
	}
	public Animation(TextureRegion[] frames, float delay)
	{
		setFrames(frames, delay);
	}
	
	public void setFrames(TextureRegion[] frames, float delay)
	{
		this.frames = frames;
		this.delay = delay;
		timer = 0;
		currentFrame = 0;
		timesPlayed = 0;
	}
	
	/**
	 * Default is 1 / 12f
	 * @param delay
	 */
	public void setDelay(float delay)
	{
		this.delay = delay;
	}
	
	public void update(float deltaTime)
	{
		if(delay <= 0)
			return; //doesn't move
		timer+=deltaTime;
		while(timer >= delay)
		{
			step();
		}
	}
	
	private void step()
	{
		timer -= delay;
		currentFrame++;
		if(currentFrame == frames.length)
		{	
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public TextureRegion getFrame()
	{
		return frames[currentFrame];
	}
	public int getTimesPlayed()
	{
		return timesPlayed;
	}
	
}
