package xratedjunior.junglecreeper.init;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import xratedjunior.junglecreeper.api.entity.JungleCreeperModEntityTypes;
import xratedjunior.junglecreeper.common.entity.JungleCreeperEntity;
import xratedjunior.junglecreeper.core.JungleCreeperMod;

@EventBusSubscriber(modid = JungleCreeperMod.MOD_ID, bus = Bus.MOD)
public class JungleCreeperModEntityRegistryHandler 
{	
	@SubscribeEvent
	public static void onRegisterItems(Register<Item> event)
	{
		register(event.getRegistry(), "jungle_creeper_spawn_egg", new SpawnEggItem(JungleCreeperModEntityTypes.JUNGLE_CREEPER_ENTITY, 0x286926, 0x1a1a1a, new Item.Properties().group(ItemGroup.MISC)));
	}

	@SubscribeEvent
	public static void onRegisterEntityTypes(Register<EntityType<?>> event)
	{
		JungleCreeperModEntityTypes.init(event);
		EntitySpawnPlacementRegistry.register(JungleCreeperModEntityTypes.JUNGLE_CREEPER_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, JungleCreeperEntity::checkJungleCreeperSpawnRules);
		//JungleCreeperModSpawns.registerEntityWorldSpawns();
	}
	
	public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String name, T object)
	{
		object.setRegistryName(JungleCreeperMod.locate(name));
		registry.register(object);
	}
}
