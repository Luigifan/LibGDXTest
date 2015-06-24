package com.mikesantiago.libgdxtest.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mikesantiago.libgdxtest.handlers.GameStateManager;
import com.mikesantiago.libgdxtest.handlers.Input;
import com.mikesantiago.libgdxtest.handlers.InputProcessor;

public class Game implements ApplicationListener
{
	public static final String TITLE = "TEST";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	private float accumulator = 0;
	
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	//
	
	public SpriteBatch getSpriteBatch()
	{
		return sb;
	}
	
	public OrthographicCamera getCam()
	{
		return cam;
	}
	
	public OrthographicCamera getHudCam()
	{
		return hudCam;
	}
	
	@Override
	public void create() 
	{
		Gdx.input.setInputProcessor(new InputProcessor());
		sb = new SpriteBatch();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render() 
	{
		accumulator += Gdx.graphics.getDeltaTime();
		while(accumulator >= STEP)
		{
			accumulator -= STEP;
			gsm.update(STEP);
			gsm.render();
			Input.update();
		}
		
	}
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
