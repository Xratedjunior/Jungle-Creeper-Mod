package xratedjunior.junglecreeper.configuration;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class JungleCreeperConfig 
{
	public static ForgeConfigSpec.IntValue jungle_creeper_weight;
	public static ConfigValue<Integer> jungle_creeper_min_group;
	public static ConfigValue<Integer> jungle_creeper_max_group;

	private static int jungleCreeperSpawnWeight = 90;
	private static int jungleCreeperSpawnWeightMin = 0;
	private static int jungleCreeperSpawnWeightMax = 10000;
	private static int jungleCreeperMinGroup = 1;
	private static int jungleCreeperMaxGroup = 3;
	
	public static void init(ForgeConfigSpec.Builder server)
	{
		server.comment("Jungle Creeper Config");
		
		jungle_creeper_weight = server
			.comment("Spawn weight for the Jungle Creeper Entity (Default: " + jungleCreeperSpawnWeight + ")")
			.defineInRange("Jungle Creeper.weight", jungleCreeperSpawnWeight, jungleCreeperSpawnWeightMin, jungleCreeperSpawnWeightMax);
		
		jungle_creeper_min_group = server
			.comment("Minimum amount of Jungle Creepers to spawn in a group (Default: " + jungleCreeperMinGroup + ")")
			.define("Jungle Creeper.min_group", jungleCreeperMinGroup);
		
		jungle_creeper_max_group = server
			.comment("Maximum amount of Jungle Creepers to spawn in a group (Default: " + jungleCreeperMaxGroup + ")")
			.define("Jungle Creeper.max_group", jungleCreeperMaxGroup);
	}
}
