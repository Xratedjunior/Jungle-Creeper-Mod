package xratedjunior.junglecreeper.client;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class JungleCreeperModRenderInit 
{
	public static void initialization(FMLClientSetupEvent event)
	{
		JungleCreeperModEntityRendering.init();
	}
}
