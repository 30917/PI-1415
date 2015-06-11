package com.roguelike.game;


public class Potion extends Obj{
	private static final long serialVersionUID = -7849725346203960352L;
	
	int hp;
	
	public Potion(Place p){
		super(p);
		this.hp = 20;
	}
	public boolean onTouch(Unit u){
		if(u instanceof CoreGame.Player){
			u.hp += hp;
			return true;
		}
		return false;
	}
}