package com.gmail.nossr50.skills;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import com.gmail.nossr50.Users;
import com.gmail.nossr50.m;
import com.gmail.nossr50.config.LoadProperties;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.datatypes.SkillType;
import com.gmail.nossr50.locale.mcLocale;

public class Unarmed {
	public static void berserkActivationCheck(Player player)
	{
    	PlayerProfile PP = Users.getProfile(player);
		if(player.getItemInHand().getTypeId() == 0)
		{
			if(PP.getFistsPreparationMode())
			{
    			PP.setFistsPreparationMode(false);
    		}
	    	int ticks = 2;
	    	int x = PP.getSkillLevel(SkillType.UNARMED);
    		while(x >= 50){
    			x-=50;
    			ticks++;
    		}
    		
	    	if(!PP.getBerserkMode() && Skills.cooldownOver(player, PP.getBerserkDeactivatedTimeStamp(), LoadProperties.berserkCooldown))
	    	{
	    		player.sendMessage(mcLocale.getString("Skills.BerserkOn"));
	    		for(Player y : player.getWorld().getPlayers())
	    		{
	    			if(y != null && y != player && m.getDistance(player.getLocation(), y.getLocation()) < 10)
	    				y.sendMessage(mcLocale.getString("Skills.BerserkPlayer", new Object[] {player.getName()}));
	    		}
	    		PP.setBerserkActivatedTimeStamp(System.currentTimeMillis());
	    		PP.setBerserkDeactivatedTimeStamp(System.currentTimeMillis() + (ticks * 1000));
	    		PP.setBerserkMode(true);
	    	}
	    }
	}
	public static void unarmedBonus(Player attacker, EntityDamageByEntityEvent event)
	{
		PlayerProfile PPa = Users.getProfile(attacker);
		int bonus = 0;
		if (PPa.getSkillLevel(SkillType.UNARMED) >= 250)
			bonus+=2;
		if (PPa.getSkillLevel(SkillType.UNARMED) >= 500)
			bonus+=4;
		event.setDamage(event.getDamage()+bonus);
	}
	public static void disarmProcCheck(Player attacker, Player defender)
	{
		PlayerProfile PP = Users.getProfile(attacker);
		if(attacker.getItemInHand().getTypeId() == 0)
		{
			if(PP.getSkillLevel(SkillType.UNARMED) >= 1000)
			{
	    		if(Math.random() * 4000 <= 1000)
	    		{
	    			Location loc = defender.getLocation();
	    			if(defender.getItemInHand() != null && defender.getItemInHand().getTypeId() != 0)
	    			{
	    				defender.sendMessage(mcLocale.getString("Skills.Disarmed"));
	    				ItemStack item = defender.getItemInHand();
		    			if(item != null)
		    			{
		    				loc.getWorld().dropItemNaturally(loc, item);
		    				ItemStack itemx = null;
		    				defender.setItemInHand(itemx);
		    			}
	    			}
	    		}
	    	} else {
	    		if(Math.random() * 4000 <= PP.getSkillLevel(SkillType.UNARMED)){
	    			Location loc = defender.getLocation();
	    			if(defender.getItemInHand() != null && defender.getItemInHand().getTypeId() != 0)
	    			{
	    				defender.sendMessage(mcLocale.getString("Skills.Disarmed"));
	    				ItemStack item = defender.getItemInHand();
		    			if(item != null)
		    			{
		    				loc.getWorld().dropItemNaturally(loc, item);
		    				ItemStack itemx = null;
		    				defender.setItemInHand(itemx);
	    				}
	    			}
	    		}
	    	}
		}
	}
}
