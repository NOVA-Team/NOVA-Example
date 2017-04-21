package nova.sample.entity;

import nova.core.entity.EntityManager;
import nova.core.event.bus.GlobalEvents;
import nova.core.loader.Mod;
import nova.core.render.RenderManager;
import org.slf4j.Logger;

/**
 * Used to test NOVA entities.
 *
 * @author ExE Boss
 */
@Mod(id = NovaEntity.MOD_ID, name = "Nova Example Entity", version = "0.0.1", novaVersion = "0.0.1")
public class NovaEntity {

	public static final String MOD_ID = "novaentity";

	public final Logger logger;
	public final GlobalEvents events;
	public final EntityManager entityManager;
	public final RenderManager renderManager;

	public NovaEntity(Logger logger,
	                  GlobalEvents events,
	                  EntityManager entityManager,
	                  RenderManager renderManager) {
		this.logger = logger;
		this.events = events;
		this.entityManager = entityManager;
		this.renderManager = renderManager;

		events.on(RenderManager.Init.class).bind(evt -> this.registerRenderer(evt.manager));
		events.on(EntityManager.Init.class).bind(evt -> this.registerEntities(evt.manager));
	}

	private void registerRenderer(RenderManager renderManager) {

	}

	private void registerEntities(EntityManager entityManager) {

	}
}
