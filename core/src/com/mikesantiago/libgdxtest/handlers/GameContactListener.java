package com.mikesantiago.libgdxtest.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener
{
	//private boolean playerIsOnGround = false;
	private int numFootContact = 0;
	
	//colliding
	@Override
	public void beginContact(Contact contact) 
	{
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB(); //fb is the later created one, so foot is usually going to be fb
		
		if(fa.getUserData() != null && fa.getUserData().equals("FOOT"))
		{
			numFootContact++;
			System.out.println("player is on ground");
		}
		if(fb.getUserData() != null && fb.getUserData().equals("FOOT"))
		{
			numFootContact++;
			System.out.println("player is on ground");
		}
	}

	//not colliding
	@Override
	public void endContact(Contact contact) 
	{
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB(); //fb is the later created one, so foot is usually going to be fb
		
		if(fa.getUserData() != null && fa.getUserData().equals("FOOT"))
		{
			numFootContact--;
			System.out.println("player is not on ground");
		}
		if(fb.getUserData() != null && fb.getUserData().equals("FOOT"))
		{
			numFootContact--;
			System.out.println("player is not on ground");
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
