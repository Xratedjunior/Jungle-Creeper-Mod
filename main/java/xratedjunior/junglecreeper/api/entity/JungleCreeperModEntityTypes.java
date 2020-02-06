package xratedjunior.junglecreeper.api.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.ObjectHolder;
import xratedjunior.junglecreeper.common.entity.JungleCreeperEntity;
import xratedjunior.junglecreeper.core.JungleCreeperMod;
import xratedjunior.junglecreeper.init.JungleCreeperModEntityRegistryHandler;

@ObjectHolder(JungleCreeperMod.MOD_ID)
public class JungleCreeperModEntityTypes 
{
	public static final EntityType<JungleCreeperEntity> JUNGLE_CREEPER_ENTITY = buildEntity("jungle_creeper_entity", EntityType.Builder.create(JungleCreeperEntity::new, EntityClassification.MONSTER).size(0.6F, 1.7F));
	
	public static void init(Register<EntityType<?>> event)
	{
		JungleCreeperModEntityRegistryHandler.register(event.getRegistry(), "jungle_creeper_entity", JUNGLE_CREEPER_ENTITY);
	}
	
	private static <T extends Entity> EntityType<T> buildEntity(String key, EntityType.Builder<T> builder)
	{
		return builder.build(JungleCreeperMod.find(key));
	}
}
