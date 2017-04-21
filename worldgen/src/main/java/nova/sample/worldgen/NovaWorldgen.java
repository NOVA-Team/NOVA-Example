package nova.sample.worldgen;

import nova.core.block.BlockFactory;
import nova.core.block.BlockManager;
import nova.core.event.bus.GlobalEvents;
import nova.core.item.ItemDictionary;
import nova.core.item.ItemFactory;
import nova.core.item.ItemManager;
import nova.core.loader.Mod;
import nova.core.recipes.RecipeManager;
import nova.core.recipes.ingredient.ItemIngredient;
import nova.core.recipes.smelting.BasicSmeltingRecipe;
import nova.core.render.RenderManager;
import nova.core.render.texture.BlockTexture;
import nova.core.render.texture.ItemTexture;
import nova.core.util.EnumSelector;
import nova.worldgen.WorldgenManager;
import nova.worldgen.ore.Ore;
import nova.worldgen.ore.OreHeight;
import org.slf4j.Logger;

@Mod(id = NovaWorldgen.MOD_ID, name = "Nova Worldgen Example", version = "0.0.1", novaVersion = "0.0.1")
public class NovaWorldgen {
	public static final String MOD_ID = "novaexampleworldgen";

	public static BlockFactory blockSteelOre;

	public static ItemFactory itemBlockSteelOre;
	public static ItemFactory itemSteelIngot;

	public static Ore oreSteel;

	public static BlockTexture steelOreTexture;
	public static ItemTexture steelIngotTexture;

	public final Logger logger;
	public final GlobalEvents events;
	public final ItemDictionary itemDictionary;

	public NovaWorldgen(Logger logger,
	                    GlobalEvents events,
	                    BlockManager blockManager,
	                    ItemManager itemManager,
	                    RenderManager renderManager,
	                    RecipeManager recipeManager,
	                    WorldgenManager worldgenManager,
	                    ItemDictionary itemDictionary) {
		this.logger = logger;
		this.events = events;
		this.itemDictionary = itemDictionary;

		events.on(RenderManager.Init.class).bind(evt   -> this.registerRenderer(evt.manager));
		events.on(BlockManager.Init.class).bind(evt    -> this.registerBlocks(evt.manager));
		events.on(ItemManager.Init.class).bind(evt     -> this.registerItems(evt.manager));
		events.on(RecipeManager.Init.class).bind(evt   -> this.registerRecipes(evt.manager));
		events.on(WorldgenManager.Init.class).bind(evt -> this.registerWorldgen(evt.manager));
	}

	private void registerRenderer(RenderManager renderManager) {
		steelOreTexture = renderManager.registerTexture(new BlockTexture(MOD_ID, "ore_steel"));
		steelIngotTexture = renderManager.registerTexture(new ItemTexture(MOD_ID, "ingot_steel"));
	}

	private void registerBlocks(BlockManager blockManager) {
		blockSteelOre = blockManager.register(MOD_ID + ":steel_ore", BlockSteelOre::new);
	}

	private void registerItems(ItemManager itemManager) {
		itemSteelIngot = itemManager.register(MOD_ID + ":steel_ingot", ItemSteelIngot::new);
		itemBlockSteelOre = itemManager.getItemFromBlock(blockSteelOre);

		itemDictionary.add("oreSteel", itemBlockSteelOre.build());
		itemDictionary.add("ingotSteel", itemSteelIngot.build());
	}

	private void registerRecipes(RecipeManager recipeManager) {
		recipeManager.addRecipe(new BasicSmeltingRecipe(itemSteelIngot, ItemIngredient.forBlock(blockSteelOre)));
	}

	private void registerWorldgen(WorldgenManager worldgenManager) {
		oreSteel = worldgenManager.register(new Ore(MOD_ID + ":steel_ore", blockSteelOre, 1, 1,
				EnumSelector.of(OreHeight.class).blockAll().apart(OreHeight.DEEP).lock()));
	}
}
