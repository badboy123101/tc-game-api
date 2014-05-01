package com.mydeblob.main;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mydeblob.arena.Arena;
import com.mydeblob.arena.ArenaManager;

public class Commands implements CommandExecutor{
	ArrayList<String> creating = new ArrayList<String>();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("basecommand")){
			if(args[0].equalsIgnoreCase("help") && args.length == 1){
				if(p.hasPermission("baseplugin.help") || p.isOp() || p.hasPermission("baseplugin.*") || p.hasPermission("baseplugin.user")){
					p.sendMessage(ChatColor.DARK_RED + "---=" + ChatColor.BLUE + "BasePlugin Help" + ChatColor.DARK_RED + "=---");
					p.sendMessage(ChatColor.YELLOW + "/basecommand help");
					p.sendMessage(ChatColor.GREEN + "    Brings you to the help page");
					p.sendMessage(ChatColor.YELLOW + "/basecommand join <Arena Name>");
					p.sendMessage(ChatColor.GREEN + "    Joins the specified arena");
					p.sendMessage(ChatColor.YELLOW + "/basecommand leave");
					p.sendMessage(ChatColor.GREEN + "    Leave the arena you are currently in");
					p.sendMessage(ChatColor.YELLOW + "/basecommand forcestart");
					p.sendMessage(ChatColor.GREEN + "    Force start an arena you are in");
					p.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.RED + "/basecommand help 2" + ChatColor.YELLOW + " to read the next page.");
					return true;
				}else{
					p.sendMessage(ChatColor.DARK_RED + "Error: You do not have permission!");
					return true;
				}
			}if(args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("2") && args.length == 2){
				if(p.hasPermission("baseplugin.help") || p.isOp() || p.hasPermission("baseplugin.*") || p.hasPermission("baseplugin.user")){
					p.sendMessage(ChatColor.DARK_RED + "---=" + ChatColor.BLUE + "BasePlugin Help" + ChatColor.DARK_RED + "=---");
					p.sendMessage(ChatColor.YELLOW + "/baseplugin forceend");
					p.sendMessage(ChatColor.GREEN + "    Force end an arena you are in");
					p.sendMessage(ChatColor.YELLOW + "/baseplugin create <Arena Name>");
					p.sendMessage(ChatColor.GREEN + "    Creates an arena with the specified name");
					p.sendMessage(ChatColor.YELLOW + "/baseplugin setlobby");
					p.sendMessage(ChatColor.GREEN + "    Sets the lobby for an arena");
					p.sendMessage(ChatColor.YELLOW + "/baseplugin setspawnpoint");
					p.sendMessage(ChatColor.GREEN + "    Sets the spawn point for an arena");
					return true;
				}else{
					p.sendMessage(ChatColor.DARK_RED + "Error: You do not have permission!");
					return true;
				}
			}if(args[0].equalsIgnoreCase("join")){
				if(p.hasPermission("baseplugin.join") || p.isOp() || p.hasPermission("baseplugin.*") || p.hasPermission("baseplugin.user")){
					if(args.length == 2){
						if(ArenaManager.getManager().getArena(args[1]) != null){
							ArenaManager.getManager().addPlayers(p, args[1]);
							return true;
						}
					}else{
						p.sendMessage("Incorrect format! Correct format: /baseplugin join <Arena Name>");
						return true;
					}
				}else{
					p.sendMessage(ChatColor.DARK_RED + "Error: You do not have permission!");
					return true;
				}
			}if(args[0].equalsIgnoreCase("leave")){
				if(p.hasPermission("baseplugin.leave") || p.isOp() || p.hasPermission("baseplugin.*") || p.hasPermission("baseplugin.user")){
					ArenaManager.getManager().removePlayer(p);
					return true;
				}else{
					p.sendMessage(ChatColor.DARK_RED + "Error: You do not have permission!");
					return true;
				}
			}if(args[0].equalsIgnoreCase("forcestart")){
				if(p.hasPermission("baseplugin.forcestart") || p.isOp() || p.hasPermission("baseplugin.*")){
					for(Arena a:Arena.arenaObject){
						if(a.getPlayers().contains(p.getName())){
							String arena = a.getName();
							ArenaManager.getManager().startArena(arena);
							return true; 
						}
					}
						p.sendMessage(ChatColor.RED + "Error! You must be in a arena to start it!");
						return true; 
					}else{
						p.sendMessage(ChatColor.DARK_RED + "Error: You do not have permission!");
						return true;
					}
				}if(args[0].equalsIgnoreCase("forceend")){
					if(p.hasPermission("baseplugin.forceend") || p.isOp() || p.hasPermission("baseplugin.*")){
						for(Arena a:Arena.arenaObject){
							if(a.getPlayers().contains(p.getName())){
								String arena = a.getName();
								ArenaManager.getManager().stopArena(arena);
								return true; 
							}
						}
							p.sendMessage(ChatColor.RED + "Error! You must be in a arena to end it!");
							return true; 
						}else{
							p.sendMessage(ChatColor.DARK_RED + "Error: You do not have permission!");
							return true;
						}
				}if(args[0].equalsIgnoreCase("create")){
					if(p.hasPermission("baseplugin.create") || p.isOp() || p.hasPermission("baseplugin.*")){
						creating.add(p.getName()); 
					}else{
						p.sendMessage(ChatColor.DARK_RED + "Error: You do not have permission!");
						return true;
					}
				}
				return false;
		}
		return false;
	}

}
