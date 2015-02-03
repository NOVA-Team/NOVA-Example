package nova.sample;

import nova.core.block.Block;
import nova.core.block.BlockManager;
import nova.core.game.Game;
import nova.core.item.Item;
import nova.core.item.ItemManager;
import nova.core.item.ItemStack;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;
import nova.core.recipes.crafting.ItemIngredient;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.render.RenderManager;
import nova.core.render.model.ModelProvider;
import nova.core.render.model.TechneModel;
import nova.core.render.texture.BlockTexture;
import nova.core.render.texture.ItemTexture;
import nova.sample.block.BlockGrinder;
import nova.sample.block.BlockSimpleTest;
import nova.sample.item.ItemScrewdriver;

/**
 * A test Nova Mod
 * @author Calclavia
 */
@NovaMod(id = NovaTest.id, name = "Nova Test", version = "0.0.1", novaVersion = "0.0.1")
public class NovaTest implements Loadable {

	public static final String id = "novatest";

	public static Block blockTest, blockGrinder;
	public static Item itemScrewdriver;
	public static BlockTexture steelTexture;
	public static ItemTexture screwTexture;
	public static BlockTexture grinderTexture;
	public static ModelProvider grinderModel;

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
		blockGrinder = blockManager.registerBlock(BlockGrinder.class);

		itemScrewdriver = itemManager.registerItem(ItemScrewdriver.class);

		screwTexture = renderManager.registerTexture(new ItemTexture(id, "screwdriver"));
		steelTexture = renderManager.registerTexture(new BlockTexture(id, "blockSteel"));
		grinderTexture = renderManager.registerTexture(new BlockTexture(id, "grinder"));
		grinderModel = renderManager.registerModel(new TechneModel(id, "grinder"));

        // try to add a recipe
        ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick");
        ItemIngredient ingotIngredient = ItemIngredient.forItem("minecraft:iron_ingot");
        Game.instance.get().recipeManager.addRecipe(new ShapedCraftingRecipe(new ItemStack(itemScrewdriver, 1), "A- B", ingotIngredient, stickIngredient));
        System.out.println("Added recipe for the screwdriver");
    }
}
