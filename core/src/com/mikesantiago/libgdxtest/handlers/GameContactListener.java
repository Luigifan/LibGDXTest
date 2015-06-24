package com.mikesantiago.libgdxtest.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class GameContactListener implements ContactListener
{
	//private boolean playerIsOnGround = false;
	private int numFootContact = 0;
	
	private Array<Body> bodiesToRemove = new Array<Body>();
	
	//colliding
	@Override
	public void beginContact(Contact contact) 
	{
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB(); //fb is the later created one, so foot is usually going to be fb
		
		if(fa.getUserData() != null && fa.getUserData().equals("FOOT"))
		{
			numFootContact++;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("FOOT"))
		{
			numFootContact++;
		}
		
		
		if(fa.getUserData() != null && fa.getUserData().equals("Crystal"))
		{
			bodiesToRemove.add(fa.getBody());
		}
		if(fb.getUserData() != null && fb.getUserData().equals("Crystal"))
		{
			bodiesToRemove.add(fb.getBody());
		}
	}

	public Array<Body> getBodiesToRemove(){return bodiesToRemove;}
	
	
	//not colliding
	@Override
	public void endContact(Contact contact) 
	{
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB(); //fb is the later created one, so foot is usually going to be fb
		
		if(fa.getUserData() != null && fa.getUserData().equals("FOOT"))
		{
			numFootContact--;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("FOOT"))
		{
			numFootContact--;
		}
	}

	public int getPlayerOnGround()
	{
		return numFootContact;
	}
	
	
	
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	

}
