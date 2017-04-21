package nova.sample.block;

import nova.core.block.BlockFactory;
import nova.core.block.BlockManager;
import nova.core.event.bus.GlobalEvents;
import nova.core.item.ItemFactory;
import nova.core.item.ItemManager;
import nova.core.loader.Mod;
import nova.core.network.NetworkManager;
import nova.core.recipes.RecipeManager;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.recipes.ingredient.ItemIngredient;
import nova.core.render.RenderManager;
import nova.core.render.model.ModelProvider;
import nova.core.render.model.TechneModelProvider;
import nova.core.render.texture.BlockTexture;
import nova.core.render.texture.EntityTexture;
import org.slf4j.Logger;

/**
 * A test Nova Mod
 *
 * @author Calclavia
 */
@Mod(id = NovaBlock.MOD_ID, name = "Nova Example Block", version = "0.0.1", novaVersion = "0.0.1")
public class NovaBlock {

	public static final String MOD_ID = "novablock";

	public static BlockFactory blockStateful;
	public static BlockFactory blockStateless;

	public static ItemFactory itemBlockStateful;
	public static ItemFactory itemBlockStateless;

	public static BlockTexture steelTexture;
	public static BlockTexture grinderTexture;

	public static EntityTexture grinderEntityTexture;

	public static ModelProvider grinderModel;

	public static NetworkManager networkManager;
	public static Logger logger;

	public final GlobalEvents events;

	public NovaBlock(Logger logger,
		             GlobalEvents events,
		             BlockManager blockManager,
		             ItemManager itemManager,
		             RenderManager renderManager,
		             NetworkManager networkManager,
		             RecipeManager recipeManager) {
		this.events = events;

		this.events.on(RenderManager.Init.class).bind(evt -> this.registerRenderer(evt.manager));
		this.events.on(BlockManager.Init.class).bind(evt -> this.registerBlocks(evt.manager));
		this.events.on(ItemManager.Init.class).bind(evt -> this.registerItems(evt.manager));
		this.events.on(RecipeManager.Init.class).bind(evt -> this.registerRecipes(evt.manager));

		NovaBlock.networkManager = networkManager;
		NovaBlock.logger = logger;
	}

	private void registerRenderer(RenderManager renderManager) {
		steelTexture = renderManager.registerTexture(new BlockTexture(MOD_ID, "block_steel"));
		grinderTexture = renderManager.registerTexture(new BlockTexture(MOD_ID, "grinder"));

		grinderEntityTexture = renderManager.registerTexture(new EntityTexture(MOD_ID, "grinder_entity"));
		grinderModel = renderManager.registerModel(new TechneModelProvider(MOD_ID, "grinder"));
	}

	private void registerBlocks(BlockManager blockManager) {
		blockStateful = blockManager.register(MOD_ID + ":stateful", BlockStateful::new);
		blockStateless = blockManager.register(MOD_ID + ":simple", BlockStateless::new);
	}

	private void registerItems(ItemManager itemManager) {
		itemBlockStateful = itemManager.getItemFromBlock(blockStateful);
		itemBlockStateless = itemManager.getItemFromBlock(blockStateless);
	}

	private void registerRecipes(RecipeManager recipeManager) {
		// try to add a recipe
		//ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick"); //TODO: This should be obtained from some dictonary too
		ItemIngredient stickIngredient = ItemIngredient.forDictionary("stickWood");
		ItemIngredient ingotIngredient = ItemIngredient.forDictionary("ingotIron");

		recipeManager.addRecipe(new ShapedCraftingRecipe(itemBlockStateless, "AAA-ABA-AAA", ingotIngredient, stickIngredient));
	}
}
