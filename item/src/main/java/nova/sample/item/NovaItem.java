package nova.sample.item;

import nova.core.game.Game;
import nova.core.item.ItemFactory;
import nova.core.item.ItemManager;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;
import nova.core.recipes.crafting.ItemIngredient;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.render.RenderManager;
import nova.core.render.texture.ItemTexture;

/**
 * Created by magik6k on 5/29/15.
 */
@NovaMod(id = NovaItem.id, name = "Nova Example Item", version = "0.0.1", novaVersion = "0.0.1")
public class NovaItem implements Loadable {
    public static final String id = "novaitemexample";

    public static ItemFactory itemScrewdriver;
    public static ItemTexture screwTexture;

    public final ItemManager itemManager;
    public final RenderManager renderManager;

    public NovaItem(ItemManager itemManager, RenderManager renderManager) {
        this.itemManager = itemManager;
        this.renderManager = renderManager;
    }

    @Override
    public void preInit() {
        itemScrewdriver = itemManager.register(ItemScrewdriver.class);
        screwTexture = renderManager.registerTexture(new ItemTexture(id, "screwdriver"));

        ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick"); //TODO: This should be obtained from some dictonary too
        ItemIngredient ingotIngredient = ItemIngredient.forDictionary("ingotIron");
        Game.instance().recipeManager().addRecipe(new ShapedCraftingRecipe(itemScrewdriver.makeItem(), "A- B", ingotIngredient, stickIngredient));
    }
}
