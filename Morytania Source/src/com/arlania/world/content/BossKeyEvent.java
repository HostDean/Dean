package com.arlania.world.content;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.GraphicHeight;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;



public class BossKeyEvent {
	
	//Item ids that will be dropped
	public static int pvmKey = 2944;
	
	//useless, just needed to write down object id
	public static int chest = 13291;
	

	public static int rareLoots[] = {4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4708, 4710, 4712, 4714, 4753, 4755, 4757, 4759, 4745, 4747, 4749, 4751};
	
	public static int ultraLoots[] = {11696, 11724, 11726, 11728, 11720, 11722, 11718, 11716, 11846, 11848, 11850, 11852, 11854, 11856};
	
	public static int amazingLoots[] = {4453, 20555, 18891, 18892, 18893, 18899, 14484, 607, 12601, 13740, 13742, 13738, 13744, 4450, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 13239, 12708, 13235};
	
	public static int commonLoots[] = {4151, 7158, 1215, 5680, 1377, 6524, 10828, 1434, 4153, 4131, 11126, 6528, 13734, 13736, 2503, 2497, 2491};
	/*
	 * Start methods below
	 */
	
	
	/*
	 * Grabs a random item from aray
	 */
	public static int getRandomItem(int[] array) {
		return array[Misc.getRandom(array.length - 1)];
	}

	
	/*
	 * Opening the chest with suspense, give reward
	 */
	public static void openChest(Player player) {
		/*if(!p.getClickDelay().elapsed(1000)) 
			return;*/
		if (player.getInventory().contains(2944)) {   
		
			TaskManager.submit(new Task(2, player, false) {
			@Override
			public void execute() {
				player.performAnimation(new Animation(7271));
				player.getPacketSender().sendMessage("@gre@Opening Chest, Good Luck...");
				player.getInventory().delete(2944, 1);
				giveReward(player);
				this.stop();
			}
		});
      } else {
		  
    	  player.getPacketSender().sendMessage("@red@You need a Boss Key to open this chest");
    	  return;
      }
	 
	}
	
	public static void giveReward(Player player) {
		if (Misc.getRandom(20) == 5) { //Rare Item
			int rareDrops = getRandomItem(rareLoots);
			player.getInventory().add(rareDrops, 1);
			World.sendMessage("@or3@[Red Key Chest]@bla@ "+player.getUsername()+ " has recieved a Rare from the chest!");
		} else if (Misc.getRandom(225) == 147) {//Ultra Rare items
			World.sendMessage("@or3@[Red Key Chest]@bla@ "+player.getUsername()+ " has recieved an Ultra Rare from the chest!");
			int ultraDrops = getRandomItem(ultraLoots);
			player.getInventory().add(ultraDrops, 1);
		} else if (Misc.getRandom(400) == 388) {//Amazing items
			World.sendMessage("@or3@[Red Key Chest]@bla@ "+player.getUsername()+ " has recieved a Legendary Reward from the chest, Congrats!");
			int amazingDrops = getRandomItem(amazingLoots);
			player.getInventory().add(amazingDrops, 1);
		} else {//Common items
			int commonDrops = getRandomItem(commonLoots);
			player.getInventory().add(commonDrops, 1);
		}
			
	}
	
}