package xratedjunior.junglecreeper.common.entity;

import java.util.Collection;
import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.junglecreeper.common.entity.ai.goal.IsInvisibleGoal;
import xratedjunior.junglecreeper.common.entity.ai.goal.JungleCreeperSwellGoal;

public class JungleCreeperEntity extends MonsterEntity{
   private static final DataParameter<Integer> STATE = EntityDataManager.createKey(JungleCreeperEntity.class, DataSerializers.VARINT);
   private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(JungleCreeperEntity.class, DataSerializers.BOOLEAN);
   private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(JungleCreeperEntity.class, DataSerializers.BOOLEAN);
   private int lastActiveTime;
   private int timeSinceIgnited;
   private int fuseTime = 12; //30 Default
   private int explosionRadius = 3;
   private int droppedSkulls;

   public JungleCreeperEntity(EntityType<? extends JungleCreeperEntity> type, World worldIn) {
      super(type, worldIn);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new SwimGoal(this));
      this.goalSelector.addGoal(2, new JungleCreeperSwellGoal(this));
      this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
      this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
      this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
      this.targetSelector.addGoal(3, new IsInvisibleGoal<>(this, PlayerEntity.class, 32.0f));
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0d);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
   }

   /**
    * The maximum height from where the entity is alowed to jump (used in pathfinder)
    */
   public int getMaxFallHeight() {
      return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
   }

   public boolean func_225503_b_(float p_225503_1_, float p_225503_2_) {
      boolean flag = super.func_225503_b_(p_225503_1_, p_225503_2_);
      this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + p_225503_1_ * 1.5F);
      if (this.timeSinceIgnited > this.fuseTime - 5) {
         this.timeSinceIgnited = this.fuseTime - 5;
      }

      return flag;
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(STATE, -1);
      this.dataManager.register(POWERED, false);
      this.dataManager.register(IGNITED, false);
   }

   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      if (this.dataManager.get(POWERED)) {
         compound.putBoolean("powered", true);
      }

      compound.putShort("Fuse", (short)this.fuseTime);
      compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
      compound.putBoolean("ignited", this.hasIgnited());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.dataManager.set(POWERED, compound.getBoolean("powered"));
      if (compound.contains("Fuse", 99)) {
         this.fuseTime = compound.getShort("Fuse");
      }

      if (compound.contains("ExplosionRadius", 99)) {
         this.explosionRadius = compound.getByte("ExplosionRadius");
      }

      if (compound.getBoolean("ignited")) {
         this.ignite();
      }

   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      if (this.isAlive()) {
         this.lastActiveTime = this.timeSinceIgnited;
         if (this.hasIgnited()) {
            this.setJungleCreeperState(1);
         }

         int i = this.getJungleCreeperState();
         if (i > 0 && this.timeSinceIgnited == 0) {
            this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
         }

         this.timeSinceIgnited += i;
         if (this.timeSinceIgnited < 0) {
            this.timeSinceIgnited = 0;
         }

         if (this.timeSinceIgnited >= this.fuseTime) {
            this.timeSinceIgnited = this.fuseTime;
            this.explode();
         }
      }

      super.tick();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_CREEPER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_CREEPER_DEATH;
   }

   protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
      super.dropSpecialItems(source, looting, recentlyHitIn);
      Entity entity = source.getTrueSource();
      if (entity != this && entity instanceof CreeperEntity) {
         CreeperEntity creeperentity = (CreeperEntity)entity;
         if (creeperentity.ableToCauseSkullDrop()) {
            creeperentity.incrementDroppedSkulls();
            this.entityDropItem(Items.CREEPER_HEAD);
         }
      }

   }

   public boolean attackEntityAsMob(Entity entityIn) {
      return true;
   }

   public boolean func_225509_J__() {
      return this.dataManager.get(POWERED);
   }

   /**
    * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
    */
   @OnlyIn(Dist.CLIENT)
   public float getJungleCreeperFlashIntensity(float partialTicks) {
      return MathHelper.lerp(partialTicks, (float)this.lastActiveTime, (float)this.timeSinceIgnited) / (float)(this.fuseTime - 2);
   }

   /**
    * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
    */
   public int getJungleCreeperState() {
      return this.dataManager.get(STATE);
   }

   /**
    * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
    */
   public void setJungleCreeperState(int state) {
      this.dataManager.set(STATE, state);
   }

   /**
    * Called when a lightning bolt hits the entity.
    */
   public void onStruckByLightning(LightningBoltEntity lightningBolt) {
      super.onStruckByLightning(lightningBolt);
      this.dataManager.set(POWERED, true);
   }

   protected boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
         this.world.playSound(player, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
         if (!this.world.isRemote) {
            this.ignite();
            itemstack.damageItem(1, player, (p_213625_1_) -> {
               p_213625_1_.sendBreakAnimation(hand);
            });
         }

         return true;
      } else {
         return super.processInteract(player, hand);
      }
   }

   /**
    * Creates an explosion as determined by this creeper's power and explosion radius.
    */
   private void explode() {
      if (!this.world.isRemote) {
         Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
         float f = this.func_225509_J__() ? 2.0F : 1.0F;
         this.dead = true;
         this.world.createExplosion(this, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), (float)this.explosionRadius * f, explosion$mode);
         this.remove();
         this.spawnLingeringCloud();
      }

   }

   private void spawnLingeringCloud() {
      Collection<EffectInstance> collection = this.getActivePotionEffects();
      if (!collection.isEmpty()) {
         AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_());
         areaeffectcloudentity.setRadius(2.5F);
         areaeffectcloudentity.setRadiusOnUse(-0.5F);
         areaeffectcloudentity.setWaitTime(10);
         areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
         areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());

         for(EffectInstance effectinstance : collection) {
            areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
         }

         this.world.addEntity(areaeffectcloudentity);
      }

   }

   public boolean hasIgnited() {
      return this.dataManager.get(IGNITED);
   }

   public void ignite() {
      this.dataManager.set(IGNITED, true);
   }

   /**
    * Returns true if an entity is able to drop its skull due to being blown up by this creeper.
    *  
    * Does not test if this creeper is charged; the caller must do that. However, does test the doMobLoot gamerule.
    */
   public boolean ableToCauseSkullDrop() {
      return this.func_225509_J__() && this.droppedSkulls < 1;
   }

   public void incrementDroppedSkulls() {
      ++this.droppedSkulls;
   }
   
   public static boolean checkJungleCreeperSpawnRules(EntityType<? extends JungleCreeperEntity> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
      return worldIn.getDifficulty() != Difficulty.PEACEFUL && worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK && func_223315_a(type, worldIn, reason, pos, randomIn) && pos.getY() > worldIn.getSeaLevel();
   }
   
}