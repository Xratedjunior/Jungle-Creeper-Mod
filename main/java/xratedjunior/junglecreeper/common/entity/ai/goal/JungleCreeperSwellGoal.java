package xratedjunior.junglecreeper.common.entity.ai.goal;

import java.util.EnumSet;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import xratedjunior.junglecreeper.common.entity.JungleCreeperEntity;

public class JungleCreeperSwellGoal extends Goal {
   private final JungleCreeperEntity jungle_creeper;
   private LivingEntity living_entity;

   public JungleCreeperSwellGoal(JungleCreeperEntity entitycreeperIn) {
      this.jungle_creeper = entitycreeperIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      LivingEntity livingentity = this.jungle_creeper.getAttackTarget();
      return this.jungle_creeper.getJungleCreeperState() > 0 || livingentity != null && this.jungle_creeper.getDistanceSq(livingentity) < 8.0D;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.jungle_creeper.getNavigator().clearPath();
      this.living_entity = this.jungle_creeper.getAttackTarget();
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.living_entity = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (this.living_entity == null) {
         this.jungle_creeper.setJungleCreeperState(-1);
      } else if (this.jungle_creeper.getDistanceSq(this.living_entity) > 9.0D) {
         this.jungle_creeper.setJungleCreeperState(-1);
      } else if (!this.jungle_creeper.getEntitySenses().canSee(this.living_entity)) {
         this.jungle_creeper.setJungleCreeperState(-1);
      } else {
         this.jungle_creeper.setJungleCreeperState(1);
      }
   }
}