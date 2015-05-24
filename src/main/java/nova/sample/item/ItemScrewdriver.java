package nova.sample.item;

import nova.core.entity.Entity;
import nova.core.item.Item;
import nova.core.render.texture.ItemTexture;
import nova.core.util.Category;
import nova.core.util.Direction;
import nova.core.util.transform.vector.Vector3d;
import nova.core.util.transform.vector.Vector3i;
import nova.core.world.World;
import nova.sample.NovaTest;

import java.util.Optional;

/**
 * @author Calclavia
 */
public class ItemScrewdriver extends Item implements Category {

	@Override
	public boolean onUse(Entity entity, World world, Vector3i position, Direction side, Vector3d hit) {
		return true;
	}

	@Override
	public Optional<ItemTexture> getTexture() {
		return Optional.of(NovaTest.screwTexture);
	}

	@Override
	public String getID() {
		return "screwdriver2";
	}

	@Override
	public String getCategory() {
		return "tools";
	}
}
