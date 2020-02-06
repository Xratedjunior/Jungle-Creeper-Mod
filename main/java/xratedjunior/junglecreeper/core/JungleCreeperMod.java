package xratedjunior.junglecreeper.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import xratedjunior.junglecreeper.client.JungleCreeperModRenderInit;
import xratedjunior.junglecreeper.common.entity.event.GlowingJungleCreeperEvent;
import xratedjunior.junglecreeper.common.world.RemoveBiomeFeatures;
import xratedjunior.junglecreeper.configuration.JungleCreeperConfig;
import xratedjunior.junglecreeper.configuration.JungleCreeperModConfig;

@Mod(value = JungleCreeperMod.MOD_ID)
public class JungleCreeperMod 
{
    public static JungleCreeperMod instance;
    public static final String MOD_ID = "junglecreepermod";
    public static Logger logger = LogManager.getLogger(MOD_ID);
	
    public JungleCreeperMod()
    {
    	ModLoadingContext.get().registerConfig(Type.SERVER, JungleCreeperModConfig.server_config);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(JungleCreeperModRenderInit::initialization);
		
		JungleCreeperModConfig.loadConfig(JungleCreeperModConfig.server_config, FMLPaths.CONFIGDIR.get().resolve("JungleCreeperMod.toml").toString());
    }
	
	//Will run at launch (preInit)
	private void commonSetup(final FMLCommonSetupEvent event)
	{
		if (JungleCreeperConfig.remove_vegetal_decoration.get())
		{
	        RemoveBiomeFeatures.removeVegetalDecoration();
		}
		
		if (JungleCreeperConfig.glowing_jungle_creepers.get())
		{
			MinecraftForge.EVENT_BUS.register(new GlowingJungleCreeperEvent());
		}
		
		logger.info("Setup method registered.");
	}
    
	public static ResourceLocation locate(String name)
	{
		return new ResourceLocation(MOD_ID, name);
	}

	public static String find(String key)
	{
		return new String(MOD_ID + ":" + key);
	}

}
