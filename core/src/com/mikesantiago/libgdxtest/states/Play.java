package com.mikesantiago.libgdxtest.states;

import static com.mikesantiago.libgdxtest.handlers.Box2DVars.PIXELS_PER_METER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mikesantiago.libgdxtest.entities.Crystal;
import com.mikesantiago.libgdxtest.entities.HUD;
import com.mikesantiago.libgdxtest.entities.Player;
import com.mikesantiago.libgdxtest.handlers.Box2DVars;
import com.mikesantiago.libgdxtest.handlers.GameContactListener;
import com.mikesantiago.libgdxtest.handlers.GameStateManager;
import com.mikesantiago.libgdxtest.handlers.Input;
import com.mikesantiago.libgdxtest.main.Game;



public class Play extends GameState{
	
	private BitmapFont bmp = new BitmapFont();
	private World world;
	private Box2DDebugRenderer b2dr;
	private float tileSize = 32;
	private OrthographicCamera b2dcam;
	private GameContactListener gcl;
	private Player player;
	//World's
	BodyDef bdef;
	FixtureDef fdef;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer otmr;
	private Array<Crystal> crystalList;
	private HUD hud;
	
	
	public Play(GameStateManager gsm)
	{
		super(gsm);
		
		//All of this is box2d related crap...good god this is messy. good thing this isn't an actual product!
		//probably just set to 0 for all direction movement
		world = new World(new Vector2(0,-9.81f), true);
		b2dr = new Box2DDebugRenderer();
		gcl = new GameContactListener();
		world.setContactListener(gcl);
		
		//Player
		createPlayer();
		LoadWorld();
		createCrystals();
		
		//HUD because it needs a non-null player
		hud = new HUD(player);
		//Camera
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, Game.V_WIDTH / PIXELS_PER_METER, Game.V_HEIGHT / PIXELS_PER_METER);
		
		
	}
	
	private void createCrystals()
	{
		crystalList = new Array<Crystal>();
		MapLayer layer = tiledMap.getLayers().get("crystals");
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		if(layer.getObjects().getCount() > 0)
		{
			for(MapObject mo: layer.getObjects())
			{
				bdef.type = BodyType.StaticBody;
				float x = (float)mo.getProperties().get("x", float.class) / PIXELS_PER_METER;
				float y = (float)mo.getProperties().get("y", float.class) / PIXELS_PER_METER;
			
				bdef.position.set(x, y);
			
				CircleShape cshape = new CircleShape();
				cshape.setRadius(8 / PIXELS_PER_METER);
			
				fdef.shape = cshape;
				fdef.isSensor = true;
				fdef.filter.categoryBits = Box2DVars.BIT_CRYSTAL;
				fdef.filter.maskBits = Box2DVars.BIT_PLAYER;
			
				Body body = world.createBody(bdef);
				
			
				Crystal c = new Crystal(body);
				body.createFixture(fdef).setUserData("Crystal");
				crystalList.add(c);
			
				body.setUserData(c);
			}
		}
	}
	
	private void LoadWorld()
	{
		createTiles(); //then calls createLayer();
	}
	
	private void createTiles()
	{
		tiledMap = new TmxMapLoader().load("res/maps/examplelevel.tmx");
		otmr = new OrthogonalTiledMapRenderer(tiledMap);
		tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
		TiledMapTileLayer layer;
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get("red");
		
		createLayer(layer, Box2DVars.BIT_RED);
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get("green");
		createLayer(layer, Box2DVars.BIT_GREEN);
		
		layer = (TiledMapTileLayer)tiledMap.getLayers().get("blue");
		createLayer(layer, Box2DVars.BIT_BLUE);
	}
	
	private void createLayer(TiledMapTileLayer layer, short colBits)
	{
		if(bdef == null)
			bdef = new BodyDef();
		if(fdef == null)
			fdef = new FixtureDef();
		
		for(int y = 0; y < layer.getHeight(); y++)
		{
			for(int x = 0; x < layer.getWidth(); x++)
			{
				//get cell
				Cell cell = layer.getCell(x, y);
				//checks just in case
				if(cell == null) continue;
				if(cell.getTile() == null) continue; //skips
				
				//create body & fixture from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set((x + 0.5f) * tileSize / PIXELS_PER_METER, 
						(y + 0.5f) * tileSize / PIXELS_PER_METER);
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(-tileSize / 2 / PIXELS_PER_METER, -tileSize / 2 / PIXELS_PER_METER); //bot left
				v[1] = new Vector2(-tileSize / 2 / PIXELS_PER_METER, tileSize / 2 / PIXELS_PER_METER); //top left
				v[2] = new Vector2(tileSize / 2 / PIXELS_PER_METER, tileSize / 2 / PIXELS_PER_METER); // top right
				
				cs.createChain(v);
				fdef.friction = 0.4f;
				fdef.shape = cs;
				fdef.filter.categoryBits = colBits; //everything
				fdef.filter.maskBits = Box2DVars.BIT_PLAYER; //everything
				fdef.isSensor = false;
				
				world.createBody(bdef).createFixture(fdef);
			}
		}
	}
	
	private void createPlayer()
	{
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef = new BodyDef();
		{
			bdef.position.set(160 / PIXELS_PER_METER, 160 / PIXELS_PER_METER);
			bdef.type = BodyType.DynamicBody;
			
			Body body = world.createBody(bdef);
			
			shape.setAsBox(13 / PIXELS_PER_METER, 13 / PIXELS_PER_METER);
			
			fdef = new FixtureDef();
			fdef.shape = shape;
			fdef.filter.categoryBits = Box2DVars.BIT_PLAYER;
			fdef.filter.maskBits = Box2DVars.BIT_RED | Box2DVars.BIT_CRYSTAL;
			body.createFixture(fdef).setUserData("BODY");;
			
			// A foot sensor!
			shape.setAsBox(2 / PIXELS_PER_METER, 2 / PIXELS_PER_METER, new Vector2(0, -13 / PIXELS_PER_METER), 0);
			fdef.shape = shape;
			fdef.filter.categoryBits = Box2DVars.BIT_PLAYER;
			fdef.filter.maskBits = Box2DVars.BIT_RED;
			fdef.isSensor = true;
			body.createFixture(fdef).setUserData("FOOT");
			
			player = new Player(body);
		}
	}
	
	public void handleInput()
	{
		if(Input.isDown(Input.MOVE_LEFT))
		{
			player.getBody().applyForceToCenter(new Vector2(-5f, 0), true);
			//playerBody.applyLinearImpulse(new Vector2(-.25f, 0), playerBody.getLocalCenter(), true);
			player.setMoving(true);
			player.setDirection(-1);
		}
		else if(Input.isUp(Input.MOVE_LEFT))
			player.setMoving(false);
		
		if(Input.isDown(Input.MOVE_RIGHT))
		{
			player.getBody().applyForceToCenter(new Vector2(5f, 0), true);
			//playerBody.applyLinearImpulse(new Vector2(0.25f, 0), playerBody.getLocalCenter(), true);
			player.setMoving(true);
			player.setDirection(1);
		}
		
		if(Input.isDown(Input.MOVE_UP))
		{
			if(gcl.getPlayerOnGround() > 0)
			{
				player.setMoving(true);
				player.getBody().applyForceToCenter(new Vector2(0, 100f), true);
			}
		}
		
		if(Input.isPressed(Input.ADVANCE_COLOR))
		{
			player.setCollisions(1);
		}
		else if(Input.isPressed(Input.RETREAT_COLOR))
		{
			player.setCollisions(-1);
		}
		//player.setMoving(false);
	}
	
	public void update(float deltaTime)
	{
		handleInput();
		world.step(deltaTime, 6, 2);
		
		for(Body b: this.gcl.getBodiesToRemove())
		{
			crystalList.removeValue((Crystal)b.getUserData(), true);
			world.destroyBody(b);
			player.collectCrystal();
		}
		this.gcl.getBodiesToRemove().clear();
		
		player.update(deltaTime);
		
		if(player != null)
		{
			if(player.getBody().getPosition().y >= tiledMap.getProperties().get("height", Integer.class)
					||
					player.getBody().getPosition().x >= tiledMap.getProperties().get("width", Integer.class))
			{
				System.out.println("player is death");
			}
		}
		
		for(int i = 0; i < crystalList.size; i++)
		{
			crystalList.get(i).update(deltaTime);
		}
	}
	
	public void render()
	{	
		Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
		//draw map
		otmr.setView(cam);
		
		otmr.render();
		
		sb.setProjectionMatrix(cam.combined);
		for(int i = 0; i < crystalList.size; i++)
		{
			crystalList.get(i).render(sb);
		}
		player.render(sb);
		//b2dr.render(world, b2dcam.combined);		
		sb.setProjectionMatrix(hudCam.combined);
		hud.render(sb);
	}
	
	
	public void dispose(){}
}

