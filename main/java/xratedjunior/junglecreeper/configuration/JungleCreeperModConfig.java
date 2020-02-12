package xratedjunior.junglecreeper.configuration;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import xratedjunior.junglecreeper.api.entity.JungleCreeperModSpawns;
import xratedjunior.junglecreeper.core.JungleCreeperMod;

@Mod.EventBusSubscriber(modid = JungleCreeperMod.MOD_ID, bus = Bus.MOD)
public class JungleCreeperModConfig 
{
	public static Logger logger = JungleCreeperMod.logger;

	public static class Common
	{
		
		public Common(ForgeConfigSpec.Builder builder)
		{	
			JungleCreeperConfig.init(builder);
			DebugConfig.init(builder);
			logger.info("Built JungleCreeperMod Config");
		}
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
    	logger.info("Registered Jungle Creeper World Spawns");
    	JungleCreeperModSpawns.registerEntityWorldSpawns();
		logger.info("Loaded JungleCreeperMod Config");
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
    }
}
