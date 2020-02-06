package xratedjunior.junglecreeper.configuration;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import xratedjunior.junglecreeper.core.JungleCreeperMod;

@Mod.EventBusSubscriber
public class JungleCreeperModConfig 
{
	private static final ForgeConfigSpec.Builder server_builder = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec server_config;
	
	static
	{
		JungleCreeperConfig.init(server_builder);
		server_config = server_builder.build();
	}
	
	public static void loadConfig(ForgeConfigSpec spec, String path)
	{
		JungleCreeperMod.logger.info("Loading config" + path);
		final CommentedFileConfig configData = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		JungleCreeperMod.logger.info("Built config" + path);
		configData.load();
		JungleCreeperMod.logger.info("Loaded config" + path);
		spec.setConfig(configData);
	}
	
    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }
}
