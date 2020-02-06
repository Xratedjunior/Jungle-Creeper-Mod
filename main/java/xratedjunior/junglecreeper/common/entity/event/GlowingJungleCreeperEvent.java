package xratedjunior.junglecreeper.common.entity.event;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xratedjunior.junglecreeper.api.entity.JungleCreeperModEntityTypes;

public class GlowingJungleCreeperEvent 
{
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.getEntity().getType() == JungleCreeperModEntityTypes.JUNGLE_CREEPER_ENTITY)
		{
			if (!event.getWorld().isRemote)
			{
				event.getEntity().setGlowing(true);
			}
		}
	}
}
