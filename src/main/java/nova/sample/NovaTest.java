package nova.sample;

import nova.core.block.Block;
import nova.core.block.BlockManager;
import nova.core.item.Item;
import nova.core.item.ItemManager;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;
import nova.core.render.BlockTexture;
import nova.core.render.ItemTexture;
import nova.core.render.RenderManager;
import nova.sample.block.BlockSimpleTest;
import nova.sample.block.BlockStateTest;
import nova.sample.item.ItemScrewdriver;

/**
 * A test Nova Mod
 * @author Calclavia
 */
@NovaMod(id = "novatest", name = "Nova Test", version = "0.0.1", novaVersion = "0.0.1")
public class NovaTest implements Loadable {

	public static Block blockTest, blockStateTest;
	public static Item itemScrewdriver;
	public static BlockTexture steelTexture;
	public static ItemTexture screwTexture;

	public final BlockManager blockManager;
	public final ItemManager itemManager;
	public final RenderManager renderManager;
	
	public NovaTest(BlockManager blockManager, ItemManager itemManager, RenderManager renderManager) {
		this.blockManager = blockManager;
		this.itemManager = itemManager;
		this.renderManager = renderManager;
	}
	
	@Override
	public void preInit() {
		blockTest = blockManager.registerBlock(BlockSimpleTest.class);
		blockStateTest = blockManager.registerBlock(BlockStateTest.class);

		itemScrewdriver = itemManager.registerItem(ItemScrewdriver.class);

		screwTexture = renderManager.registerTexture(new ItemTexture("novatest:screwdriver"));
		steelTexture = renderManager.registerTexture(new BlockTexture("blockSteel"));
	}
}
