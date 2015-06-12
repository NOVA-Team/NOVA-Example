package nova.sample.item;

import nova.core.component.Category;
import nova.core.component.renderer.ItemRenderer;
import nova.core.item.Item;

/**
 * @author Calclavia
 */
public class ItemScrewdriver extends Item {

	public ItemScrewdriver() {
		add(new Category("tools"));
		add(new ItemRenderer())
			.setTexture(NovaItem.screwTexture);

		events.on(UseEvent.class).bind(event -> event.action = true);
	}

	@Override
	public String getID() {
		return "testscrewdriver";
	}
}
