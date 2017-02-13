package nova.sample.worldgen;

import nova.core.block.Block;
import nova.core.component.Category;
import nova.core.component.misc.Collider;
import nova.core.component.renderer.StaticRenderer;
import nova.core.render.pipeline.BlockRenderPipeline;

/**
 *
 * @author ExE Boss
 */
public class BlockSteelOre extends Block {

	public BlockSteelOre() {
		components.add(new StaticRenderer().onRender(new BlockRenderPipeline(this).withTexture(NovaWorldgen.steelOreTexture).build()));
		components.add(new Collider(this));
		components.add(new Category("buildingBlocks"));
	}
}
