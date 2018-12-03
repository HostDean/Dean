package com.arlania.world.content.combat.strategy.impl;


import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.model.Projectile;
import com.arlania.util.Misc;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;


public class BloodDruid implements CombatStrategy{
	private static final Animation anim = new Animation(811);
	private static final Graphic gfx = new Graphic(2345);

	@Override
	public boolean canAttack(Character entity, Character victim) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public CombatContainer attack(Character entity, Character victim) {
		return null;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC BloodDruid = (NPC)entity;
		if(BloodDruid.isChargingAttack() || victim.getConstitution() <= 0) {
			BloodDruid.getCombatBuilder().setAttackTimer(4);
			return true;
		}
		if(Locations.goodDistance(BloodDruid.getPosition().copy(), victim.getPosition().copy(), 1) && Misc.getRandom(5) <= 3) {
			BloodDruid.performAnimation(new Animation(BloodDruid.getDefinition().getAttackAnimation()));
			BloodDruid.getCombatBuilder().setContainer(new CombatContainer(BloodDruid, victim, 1, 1, CombatType.MELEE, true));
		} else {
			BloodDruid.setChargingAttack(true);
			BloodDruid.performAnimation(anim);
			BloodDruid.getCombatBuilder().setContainer(new CombatContainer(BloodDruid, victim, 1, 3, CombatType.MAGIC, true));
			TaskManager.submit(new Task(1, BloodDruid, false) {
				int tick = 0;
				@Override
				public void execute() {
					if(tick == 1) {
						BloodDruid.forceChat("Let the blood flow upon you!");
						new Projectile(BloodDruid, victim, gfx.getId(), 44, 3, 43, 43, 0).sendProjectile();
						BloodDruid.setChargingAttack(false);
						stop();
					}
					tick++;
				}
			});
		}
		return true;
	}

	@Override
	public int attackDelay(Character entity) {
		return entity.getAttackSpeed();
	}

	@Override
	public int attackDistance(Character entity) {
		// ATTACK DISTANCE
		return 10;
	}

	@Override
	public CombatType getCombatType() {
		// COMBAT TYPE
		return CombatType.MIXED;
	}

}
			
	
