package xratedjunior.junglecreeper.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.junglecreeper.client.models.JungleCreeperModel;
import xratedjunior.junglecreeper.common.entity.JungleCreeperEntity;
import xratedjunior.junglecreeper.core.JungleCreeperMod;

@OnlyIn(Dist.CLIENT)
public class JungleCreeperRenderer extends MobRenderer<JungleCreeperEntity, JungleCreeperModel<JungleCreeperEntity>> {
   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation(JungleCreeperMod.MOD_ID,"textures/entity/jungle_creeper_entity.png");

   public JungleCreeperRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new JungleCreeperModel<>(), 0.5F);
      //this.addLayer(new CreeperChargeLayer(this));
   }

   protected void func_225620_a_(CreeperEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      float f = p_225620_1_.getCreeperFlashIntensity(p_225620_3_);
      float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
      f = MathHelper.clamp(f, 0.0F, 1.0F);
      f = f * f;
      f = f * f;
      float f2 = (1.0F + f * 0.4F) * f1;
      float f3 = (1.0F + f * 0.1F) / f1;
      p_225620_2_.func_227862_a_(f2, f3, f2);
   }

   protected float func_225625_b_(JungleCreeperEntity p_225625_1_, float p_225625_2_) {
      float f = p_225625_1_.getJungleCreeperFlashIntensity(p_225625_2_);
      return (int)(f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
   }

   public ResourceLocation getEntityTexture(JungleCreeperEntity entity) {
      return CREEPER_TEXTURES;
   }
}