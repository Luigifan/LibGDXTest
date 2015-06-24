package com.mikesantiago.libgdxtest.states;

import static com.mikesantiago.libgdxtest.handlers.Box2DVars.PIXELS_PER_METER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mikesantiago.libgdxtest.handlers.Box2DVars;
import com.mikesantiago.libgdxtest.handlers.GameContactListener;
import com.mikesantiago.libgdxtest.handlers.GameStateManager;
import com.mikesantiago.libgdxtest.handlers.Input;
import com.mikesantiago.libgdxtest.main.Game;



public class Play extends GameState{
	
	private BitmapFont bmp = new BitmapFont();
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dcam;
	
	//100 PIXELS PERMETER
	
	Body playerBody;
	BodyDef playerBodyDef;
	FixtureDef playerFixDef;
	
	public Play(GameStateManager gsm)
	{
		super(gsm);
		
		Fixture fixture;
		
		//probably just set to 0 for all direction movement
		world = new World(new Vector2(0,-9.81f), true);
		b2dr = new Box2DDebugRenderer();
		world.setContactListener(new GameContactListener());
		
		//Ground
		BodyDef bdef = new BodyDef();
		{
			bdef.position.set(160 / PIXELS_PER_METER, 120 / PIXELS_PER_METER);
			bdef.type = BodyType.StaticBody;
		
			Body b = world.createBody(bdef);
		
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(Game.V_WIDTH / PIXELS_PER_METER, 8 / PIXELS_PER_METER);
		
			FixtureDef fdef = new FixtureDef();
			fdef.shape = shape;
			fdef.filter.categoryBits = Box2DVars.BIT_GROUND;
			fdef.filter.maskBits = Box2DVars.BIT_PLAYER;
			b.createFixture(fdef).setUserData("GROUND");
		}
		//Player
		playerBodyDef = new BodyDef();
		{
			playerBodyDef.position.set(160 / PIXELS_PER_METER, 160 / PIXELS_PER_METER);
			playerBodyDef.type = BodyType.StaticBody;
			
			playerBody = world.createBody(playerBodyDef);
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PIXELS_PER_METER, 16 / PIXELS_PER_METER);
			
			playerFixDef = new FixtureDef();
			playerFixDef.shape = shape;
			playerFixDef.filter.categoryBits = Box2DVars.BIT_PLAYER;
			playerFixDef.filter.maskBits = Box2DVars.BIT_GROUND;
			playerBody.createFixture(playerFixDef);
		}
		
		//Camera
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, Game.V_WIDTH / PIXELS_PER_METER, Game.V_HEIGHT / PIXELS_PER_METER);
		//b2dcam.setToOrtho(false, playerBodyDef.position.x, playerBodyDef.position.y);
	}
	
	public void handleInput()
	{
		if(Input.isDown(Input.BUTTON1))
		{
			System.out.println("moving up");
			Vector2 newPosition = new Vector2(playerBodyDef.position.x, playerBodyDef.position.y - 2);
			playerBodyDef.linearVelocity.set(0, 1);
			playerBody.setTransform(playerBodyDef.position, 0);
			playerBodyDef.position.set(newPosition);
		}
	}
	public void update(float deltaTime)
	{
		handleInput();
		
		world.step(deltaTime, 6, 2);
		
	}
	public void render()
	{
		Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
		
		b2dr.render(world, b2dcam.combined);
	}
	public void dispose(){}
}
