package nova.sample.item;

import nova.core.component.Category;
import nova.core.component.renderer.StaticRenderer;
import nova.core.item.Item;
import nova.core.render.pipeline.ItemRenderPipeline;

/**
 * @author Calclavia
 */
public class ItemScrewdriver extends Item {

	public ItemScrewdriver() {
		components.add(new Category("tools"));
		components.add(new StaticRenderer().onRender(new ItemRenderPipeline(this).withTexture(NovaItem.screwTexture).build()));

		events.on(UseEvent.class).bind(event -> event.action = true);
	}
}
