package nova.sample.entity;

import nova.core.entity.EntityManager;
import nova.core.loader.Loadable;
import nova.core.loader.Mod;
import nova.core.render.RenderManager;

/**
 * Used to test NOVA entities.
 *
 * @author ExE Boss
 */
@Mod(id = NovaEntity.MOD_ID, name = "Nova Example Entity", version = "0.0.1", novaVersion = "0.0.1")
public class NovaEntity implements Loadable {

	public static final String MOD_ID = "novaentity";

	public final EntityManager entityManager;
	public final RenderManager renderManager;

	public NovaEntity(EntityManager entityManager,
	                  RenderManager renderManager) {
		this.entityManager = entityManager;
		this.renderManager = renderManager;
	}
}
