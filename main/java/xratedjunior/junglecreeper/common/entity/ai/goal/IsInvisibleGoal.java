package xratedjunior.junglecreeper.common.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.Vec3d;

public class IsInvisibleGoal <T extends LivingEntity> extends Goal {
   protected final CreatureEntity entity;
   protected T closestEntity;
   protected final float avoidDistance;
   protected Path path;
   protected final PathNavigator navigation;
   protected final Class<T> classToAvoid;
   protected final Predicate<LivingEntity> avoidTargetSelector;
   protected final Predicate<LivingEntity> field_203784_k;
   private final EntityPredicate field_220872_k;

   public IsInvisibleGoal(CreatureEntity entityIn, Class<T> classToAvoidIn, float avoidDistanceIn) {
      this(entityIn, classToAvoidIn, (isInvisibleGoal) -> {
         return true;
      }, avoidDistanceIn, EntityPredicates.CAN_AI_TARGET::test);
   }

   public IsInvisibleGoal(CreatureEntity entityIn, Class<T> avoidClass, Predicate<LivingEntity> targetPredicate, float distance, Predicate<LivingEntity> p_i48859_9_) {
      this.entity = entityIn;
      this.classToAvoid = avoidClass;
      this.avoidTargetSelector = targetPredicate;
      this.avoidDistance = distance;
      this.field_203784_k = p_i48859_9_;
      this.navigation = entityIn.getNavigator();
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      this.field_220872_k = (new EntityPredicate()).setDistance((double)distance).setCustomPredicate(p_i48859_9_.and(targetPredicate));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      this.closestEntity = this.entity.world.func_225318_b(this.classToAvoid, this.field_220872_k, this.entity, this.entity.func_226277_ct_(), this.entity.func_226278_cu_(), this.entity.func_226281_cx_(), this.entity.getBoundingBox().grow((double)this.avoidDistance, 3.0D, (double)this.avoidDistance));
      if (this.closestEntity == null) {
         return false;
      } else {
         Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(this.closestEntity.func_226277_ct_(), this.closestEntity.func_226278_cu_(), this.closestEntity.func_226281_cx_()));
         if (vec3d == null) {
            return false;
         } else if (this.closestEntity.getDistanceSq(vec3d.x, vec3d.y, vec3d.z) < this.closestEntity.getDistanceSq(this.entity)) {
        	 return false;
         } else {
            return true;
         }
      }
   }
   
   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (this.entity.getDistanceSq(this.closestEntity) < avoidDistance) {
    	  entity.setInvisible(false);
      } else {
    	  entity.setInvisible(true);
      }

   }
}