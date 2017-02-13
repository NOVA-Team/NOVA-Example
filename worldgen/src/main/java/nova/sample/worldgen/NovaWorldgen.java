package nova.sample.worldgen;

import nova.core.block.BlockFactory;
import nova.core.block.BlockManager;
import nova.core.item.ItemFactory;
import nova.core.item.ItemManager;
import nova.core.loader.Loadable;
import nova.core.loader.Mod;
import nova.core.recipes.RecipeManager;
import nova.core.render.RenderManager;
import nova.core.render.texture.BlockTexture;
import nova.core.render.texture.ItemTexture;
import nova.core.util.EnumSelector;
import nova.worldgen.WorldgenManager;
import nova.worldgen.ore.Ore;
import nova.worldgen.ore.OreHeight;

@Mod(id = NovaWorldgen.MOD_ID, name = "Nova Worldgen Example", version = "0.0.1", novaVersion = "0.0.1")
public class NovaWorldgen implements Loadable {
	public static final String MOD_ID = "novaexampleworldgen";

	public static BlockFactory blockSteelOre;

	public static ItemFactory itemBlockSteelOre;
	public static ItemFactory itemSteelIngot;

	public static Ore oreSteel;

	public static BlockTexture steelOreTexture;
	public static ItemTexture steelIngotTexture;

	public final BlockManager blockManager;
	public final ItemManager itemManager;
	public final RenderManager renderManager;
	public final RecipeManager recipeManager;
	public final WorldgenManager worldgenManager;

	public NovaWorldgen(BlockManager blockManager,
	                    ItemManager itemManager,
	                    RenderManager renderManager,
	                    RecipeManager recipeManager,
	                    WorldgenManager worldgenManager) {
		this.blockManager = blockManager;
		this.itemManager = itemManager;
		this.renderManager = renderManager;
		this.recipeManager = recipeManager;
		this.worldgenManager = worldgenManager;
	}

	@Override
	public void preInit() {
		steelOreTexture = renderManager.registerTexture(new BlockTexture(MOD_ID, "ore_steel"));
		steelIngotTexture = renderManager.registerTexture(new ItemTexture(MOD_ID, "ingot_steel"));

		blockSteelOre = blockManager.register(MOD_ID + ":steel_ore", BlockSteelOre::new);
		itemSteelIngot = itemManager.register(MOD_ID + ":steel_ingot", ItemSteelIngot::new);

		itemBlockSteelOre = itemManager.getItemFromBlock(blockSteelOre);

		oreSteel = worldgenManager.register(new Ore(MOD_ID + ":steel_ore", blockSteelOre, 1, 1,
				EnumSelector.of(OreHeight.class).blockAll().apart(OreHeight.DEEP).lock()));
	}
}
