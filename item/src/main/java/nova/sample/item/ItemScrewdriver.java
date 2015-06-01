package nova.sample.item;

import nova.core.component.Category;
import nova.core.item.Item;
import nova.core.render.texture.ItemTexture;
import java.util.Optional;

/**
 * @author Calclavia
 */
public class ItemScrewdriver extends Item {

	public ItemScrewdriver() {
		add(new Category("tools"));
		useEvent.add(event -> event.action = true);
	}

	@Override
	public Optional<ItemTexture> getTexture() {
		return Optional.of(NovaItem.screwTexture);
	}

	@Override
	public String getID() {
		return "testscrewdriver";
	}
}