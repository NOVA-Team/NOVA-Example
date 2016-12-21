package nova.sample.worldgen;

import nova.core.component.Category;
import nova.core.component.renderer.StaticRenderer;
import nova.core.item.Item;
import nova.core.render.pipeline.ItemRenderPipeline;

/**
 *
 * @author ExE Boss
 */
public class ItemSteelIngot extends Item {

	public ItemSteelIngot() {
		components.add(new Category("materials"));
		components.add(new StaticRenderer().onRender(new ItemRenderPipeline(this).withTexture(NovaWorldgen.steelIngotTexture).build()));
	}
}
