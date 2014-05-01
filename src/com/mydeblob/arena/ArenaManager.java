package com.mydeblob.arena;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mydeblob.game.Status;
import com.mydeblob.main.Main;

public class ArenaManager {
	private HashMap<String, Location> lastLoc = new HashMap<String, Location>(); //Location of the player before he joined 
	private HashMap<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>(); //Last inventory of the player
	private HashMap<String, ItemStack[]> armor = new HashMap<String, ItemStack[]>(); //Last armor of the player
	private static Main m;
	public ArenaManager(Main m){
		ArenaManager.m = m;
	}
	private static ArenaManager am = new ArenaManager(m);
	public static ArenaManager getManager(){
		return am;
	}
	public Arena getArena(String arenaName){
		for(Arena a:Arena.arenaObject){
			if(a.getName().equals(arenaName)){
				return a;
			}
		}
	  return null;
	}
	public void addPlayers(Player p, String arenaName){
		if(getArena(arenaName) != null){
			Arena a = getArena(arenaName);
			if(!getArena(arenaName).isFull()){
				if(a.getStatus() != Status.DISABLED || a.getStatus() !=Status.FULL || a.getStatus() != Status.INGAME){
					lastLoc.put(p.getName(), p.getLocation());
					inv.put(p.getName(), p.getInventory().getContents()); //Save inventory
					armor.put(p.getName(), p.getInventory().getArmorContents()); //Save armor
					p.getInventory().clear(); 
					p.getInventory().setArmorContents(null);
					p.setFoodLevel(20);
					p.setFireTicks(0);
					p.setHealth(20);
					p.teleport(a.getLobby());
					a.getPlayers().add(p.getName());
					if(a.getPlayers().size() >= m.getConfig().getInt("min-players")){
						startArena(a.getName());
						//Add permission to join when full
					}
				}else{ //Arena status was disabled, full, or in game
					if(a.getStatus() == Status.DISABLED){
						p.sendMessage("The requested arena is disabled!");
					}else if(a.getStatus() == Status.FULL){
						p.sendMessage("The requested arena is full!");
					}else if(a.getStatus() == Status.INGAME){
						p.sendMessage("The requested arena has already started!");
					}
				}
			}else{ //Arena is full
				p.sendMessage("The requested arena is full!");
			}
		}
	}
	public void removePlayer(Player p){
		for(Arena a:Arena.arenaObject){
			if(a.getPlayers().contains(p.getName())){
				p.setFoodLevel(20);
				p.setFireTicks(20);
				p.setHealth(20);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.getInventory().setContents(inv.get(p.getName()));
				p.getInventory().setArmorContents(armor.get(p.getName()));
				p.teleport(lastLoc.get(p.getName()));
				lastLoc.remove(p.getName());
				a.getPlayers().remove(p.getName());
				return;
			}
		}
		p.sendMessage("Could not remove you! Are you sure you are in a arena?");
	}
	public void startArena(String arenaName){
		if(getArena(arenaName) != null){
			Arena a = getArena(arenaName);
			a.setStatus(Status.INGAME);
			a.countdown(10); //Count-down method counts down and teleports them
		}
	}
	public void stopArena(String arenaName){
		if(getArena(arenaName) != null){
			Arena a = getArena(arenaName);
			a.setStatus(Status.DISABLED);
			for(String pl: a.getPlayers()){
				Player p = Bukkit.getPlayer(pl);
				p.setFoodLevel(20);
				p.setFireTicks(20);
				p.setHealth(20);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.getInventory().setContents(inv.get(p.getName()));
				p.getInventory().setArmorContents(armor.get(p.getName()));
				p.teleport(lastLoc.get(p.getName()));
				lastLoc.remove(p.getName());
				a.getPlayers().remove(p.getName());
			}
		}
	}
}
