package xratedjunior.junglecreeper.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xratedjunior.junglecreeper.api.entity.JungleCreeperModEntityTypes;
import xratedjunior.junglecreeper.client.renderer.JungleCreeperRenderer;

public class JungleCreeperModEntityRendering 
{
	public static void init()
	{
		register(JungleCreeperModEntityTypes.JUNGLE_CREEPER_ENTITY, JungleCreeperRenderer::new);
	}

	private static <T extends Entity> void register(EntityType<T> entityClass, IRenderFactory<? super T> renderFactory)
	{
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}
}
