package com.mikesantiago.libgdxtest.handlers;

import java.util.Stack;

import com.mikesantiago.libgdxtest.main.Game;
import com.mikesantiago.libgdxtest.states.GameState;
import com.mikesantiago.libgdxtest.states.Play;

public class GameStateManager 
{
	private Game game;
	private Stack<GameState> gameStates;
	
	private static final int PLAY = 0;
	private static final int STATETEST = 1;
	
	public GameStateManager(Game game)
	{
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(PLAY);
	}
	
	public Game game() {return game;}
	
	private GameState getState(int STATE)
	{
		if(STATE == PLAY)
			return new Play(this);
		else if(STATE == STATETEST)
			return new StateTest(this);
		return null;
	}
	
	public void setState(int state)
	{
		popState();
		pushState(state);
	}
	
	public void pushState(int state)
	{
		gameStates.push(getState(state));
	}
	
	public void popState()
	{
		GameState g = gameStates.pop();
		g.dispose();
	}
	
	public void update(float deltaTime)
	{
		gameStates.peek().update(deltaTime);
	}
	
	public void render()
	{
		gameStates.peek().render();
	}
}
