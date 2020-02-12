package xratedjunior.junglecreeper.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class DebugConfig 
{
	public static ForgeConfigSpec.BooleanValue glowing_jungle_creepers;
	public static ForgeConfigSpec.BooleanValue remove_vegetal_decoration;
	
	private static boolean glowingJungleCreepers = false;
	private static boolean removeVegetalDecoration = false;
	
	public static void init(ForgeConfigSpec.Builder builder)
	{	
		glowing_jungle_creepers = builder
				.comment("Gives Jungle Creepers the glowing effect (Default: " + glowingJungleCreepers + ")")
				.define("Debug.glowing_jungle_creepers", glowingJungleCreepers);
		
		remove_vegetal_decoration = builder
				.comment("Removes all vegetation to see if the Jungle Creeper is spawning. Please note: Jungle Creepers can't spawn in complete daylight. (Default: " + removeVegetalDecoration + ")" )
				.define("Debug.remove_vegetal_decoration", removeVegetalDecoration);
	}
}
