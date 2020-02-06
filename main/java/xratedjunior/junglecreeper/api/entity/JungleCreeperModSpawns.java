package xratedjunior.junglecreeper.api.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import xratedjunior.junglecreeper.configuration.JungleCreeperConfig;

public class JungleCreeperModSpawns 
{
	public static void registerEntityWorldSpawns()
	{
		registerEntityWorldSpawn(JungleCreeperModEntityTypes.JUNGLE_CREEPER_ENTITY, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE);
	}
	
	public static void registerEntityWorldSpawn(EntityType<?> entity, Biome...biomes)
	{
		for(Biome biome : biomes)
		{
			if(biome != null)
			{
				biome.getSpawns(entity.getClassification()).add(new SpawnListEntry(JungleCreeperModEntityTypes.JUNGLE_CREEPER_ENTITY, JungleCreeperConfig.jungle_creeper_weight.get(), JungleCreeperConfig.jungle_creeper_min_group.get(), JungleCreeperConfig.jungle_creeper_max_group.get()));
			}
		}
	}
}
