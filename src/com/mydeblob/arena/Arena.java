package com.mydeblob.arena;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.mydeblob.game.Status;
import com.mydeblob.main.Main;

public class Arena {
	private int taskID;
	public static ArrayList<Arena> arenaObject = new ArrayList<Arena>(); //Arena object
	private String name; //Arena name
	private int maxplayers; //Max amount of players
	private ArrayList <String> players = new ArrayList<String>();
	private Location lobby, spawnpoint; //Just a single spawnpoint
	Status status;
	private Main m;
	public Arena(String name, Location lobby, Location spawnpoint, int maxplayers){
		this.name = name;
		this.lobby = lobby;
		this.status = Status.LOBBY;
		this.spawnpoint = spawnpoint;
		this.maxplayers = maxplayers;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getMaxPlayers(){
		return this.maxplayers;
	}
	public void setMaxPlayers(int maxPlayers){
		this.maxplayers = maxPlayers;
	}
	public Location getLobby(){
		return this.lobby;
	}
	public void setLobby(Location lobby){
		this.lobby = lobby;
	}
	public Status getStatus(){
		return this.status;
	}
	public void setStatus(Status status){
		this.status = status;
	}
	public Location getSpawn(){
		return this.spawnpoint;
	}
	public void setSpawnPoint(Location spawnPoint){
		this.spawnpoint = spawnPoint;
	}
	public ArrayList<String> getPlayers(){
		return this.players;
	}
	public boolean isFull(){
		if(players.size() > maxplayers){
			return true;
		}else{
			return false;
		}
	}
	public void sendMessage(String message){
		for(String p:players){
			Bukkit.getPlayer(p).sendMessage(message);
		}
	}
	public void countdown(final int countdownTime){
		 		taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(m, new Runnable(){
				public void run(){
					int counter = countdownTime;
					if(counter == 0){
						for(String p:players){
							Bukkit.getPlayer(p).teleport(getSpawn());
						}
						Bukkit.getScheduler().cancelTask(taskID);
					}
					counter--;
					for(String p:players){
						Bukkit.getPlayer(p).sendMessage("Starting in " + counter);
					}
				}
			}, 0L, 20L);
	}
}
