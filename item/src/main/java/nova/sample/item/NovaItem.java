package nova.sample.item;

import nova.core.event.bus.GlobalEvents;
import nova.core.item.ItemFactory;
import nova.core.item.ItemManager;
import nova.core.loader.Mod;
import nova.core.recipes.RecipeManager;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.recipes.ingredient.ItemIngredient;
import nova.core.render.RenderManager;
import nova.core.render.texture.ItemTexture;
import org.slf4j.Logger;

/**
 * Created by magik6k on 5/29/15.
 */
@Mod(id = NovaItem.MOD_ID, name = "Nova Example Item", version = "0.0.1", novaVersion = "0.0.1")
public class NovaItem {
	public static final String MOD_ID = "novaitem";

	public static ItemFactory itemScrewdriver;
	public static ItemTexture screwTexture;

	public final Logger logger;
	public final GlobalEvents events;
	public final ItemManager itemManager;
	public final RenderManager renderManager;
	public final RecipeManager recipeManager;

	public NovaItem(Logger logger,
		            GlobalEvents events,
		            ItemManager itemManager,
		            RenderManager renderManager,
		            RecipeManager recipeManager) {
		this.logger = logger;
		this.events = events;
		this.itemManager = itemManager;
		this.renderManager = renderManager;
		this.recipeManager = recipeManager;

		events.on(ItemManager.Init.class).bind(evt -> this.registerItems(evt.manager));
		events.on(RenderManager.Init.class).bind(evt -> this.registerRenderer(evt.manager));
		events.on(RecipeManager.Init.class).bind(evt -> this.registerRecipes(evt.manager));
    }

	private void registerRenderer(RenderManager blockManager) {
		screwTexture = renderManager.registerTexture(new ItemTexture(MOD_ID, "screwdriver"));
	}

	private void registerItems(ItemManager blockManager) {
		itemScrewdriver = itemManager.register(MOD_ID + ":testscrewdriver", ItemScrewdriver::new);
	}

	private void registerRecipes(RecipeManager recipeManager) {
		//ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick"); //TODO: This should be obtained from some dictonary too
		ItemIngredient stickIngredient = ItemIngredient.forDictionary("stickWood");
		ItemIngredient ingotIngredient = ItemIngredient.forDictionary("ingotIron");
		recipeManager.addRecipe(new ShapedCraftingRecipe(itemScrewdriver, "A- B", true, ingotIngredient, stickIngredient));
	}
}
