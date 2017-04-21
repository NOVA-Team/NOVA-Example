package nova.sample.gui;

import nova.core.block.BlockFactory;
import nova.core.block.BlockManager;
import nova.core.event.bus.GlobalEvents;
import nova.gui.Background;
import nova.gui.ComponentEvent.ActionEvent;
import nova.gui.Gui;
import nova.gui.GuiEvent.BindEvent;
import nova.gui.GuiEvent.UnBindEvent;
import nova.gui.component.Button;
import nova.gui.component.Container;
import nova.gui.component.Label;
import nova.gui.component.inventory.Slot;
import nova.gui.factory.GuiManager;
import nova.gui.layout.Anchor;
import nova.gui.layout.FlowLayout;
import nova.core.item.ItemFactory;
import nova.core.item.ItemManager;
import nova.core.loader.Mod;
import nova.core.network.NetworkManager;
import nova.core.network.NetworkTarget.Side;
import nova.core.recipes.RecipeManager;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.recipes.ingredient.ItemIngredient;
import nova.core.render.Color;
import nova.core.render.RenderManager;
import nova.core.render.texture.BlockTexture;
import nova.sample.gui.block.BlockSimpleTest;
import org.slf4j.Logger;

/**
 * A test Nova Mod
 *
 * @author Calclavia
 */
@Mod(id = NovaGui.MOD_ID, name = "Nova GUI example", version = "0.0.1", novaVersion = "0.0.1")
public class NovaGui {

	public static final String MOD_ID = "novaexamplegui";

	public static BlockFactory blockTest;
	public static ItemFactory itemBlockTest;

	public static BlockTexture steelTexture;
	public static GuiManager guiFactory;
	public static NetworkManager networkManager;
	public static Logger logger;

	public final GlobalEvents events;
	public final BlockManager blockManager;
	public final ItemManager itemManager;
	public final RenderManager renderManager;
	public final RecipeManager recipeManager;

	public NovaGui(Logger logger,
	               GlobalEvents events,
	               BlockManager blockManager,
	               ItemManager itemManager,
	               RenderManager renderManager,
	               GuiManager guiFactory,
	               RecipeManager recipeManager,
	               NetworkManager networkManager) {
		this.events = events;
		this.blockManager = blockManager;
		this.itemManager = itemManager;
		this.renderManager = renderManager;
		this.recipeManager = recipeManager;

		events.on(RenderManager.Init.class).bind(evt -> this.registerRenderer(evt.manager));
		events.on(BlockManager.Init.class).bind(evt -> this.registerBlocks(evt.manager));
		events.on(ItemManager.Init.class).bind(evt -> this.registerItems(evt.manager));
		events.on(RecipeManager.Init.class).bind(evt -> this.registerRecipes(evt.manager));
		events.on(GuiManager.Init.class).bind(evt -> this.registerGui(evt.manager));

		NovaGui.logger = logger;
		NovaGui.networkManager = networkManager;
		NovaGui.guiFactory = guiFactory;
	}

	private void registerGui(GuiManager guiManager) {
		guiManager.register("testgui", () -> new Gui("testgui")
				.add(new Button("testbutton2", "I'm EAST")
					.setMaximumSize(Integer.MAX_VALUE, 120)

					.onEvent((event, component) -> {
						System.out.println("Test button pressed! " + Side.get());
					}, ActionEvent.class, Side.BOTH), Anchor.EAST)

				.add(new Button("testbutton3", "I'm CENTER"))
				.add(new Container("test").setLayout(new FlowLayout())
					.add(new Slot("main", 0))
					.add(new Slot("main", 0))
					.add(new Slot("main", 0))
					.add(new Slot("main", 0))
					, Anchor.SOUTH)

				.add(new Container("container").setLayout(new FlowLayout())
					.add(new Button("testbutton5", "I'm the FIRST Button and need lots of space"))
					.add(new Label("testlabel1", "I'm some label hanging around").setBackground(new Background(Color.white)))
					.add(new Button("testbutton7", "I'm THIRD"))
					.add(new Button("testbutton8", "I'm FOURTH"))
					, Anchor.NORTH)

				.onGuiEvent((event) -> {
					event.gui.addInventory("main", ((BlockSimpleTest) event.block.get()).inventory);
					System.out.println("Test GUI initialized! " + event.player.getDisplayName() + " " + event.position);
				}, BindEvent.class)

				.onGuiEvent((event) -> {
					System.out.println("Test GUI closed!");
				}, UnBindEvent.class)
		);
	}

	private void registerBlocks(BlockManager blockManager) {
		blockTest = blockManager.register(MOD_ID + ":gui", BlockSimpleTest::new);
	}

	private void registerItems(ItemManager itemManager) {
		itemBlockTest = itemManager.getItemFromBlock(blockTest);
	}

	private void registerRenderer(RenderManager renderManager) {
		steelTexture = renderManager.registerTexture(new BlockTexture(MOD_ID, "block_steel"));
	}

	private void registerRecipes(RecipeManager recipeManager) {
		// try to add a recipe
		//ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick"); //TODO: This should be obtained from some dictonary too
		ItemIngredient stickIngredient = ItemIngredient.forDictionary("stickWood");
		ItemIngredient ingotIngredient = ItemIngredient.forDictionary("ingotIron");
		recipeManager.addRecipe(new ShapedCraftingRecipe(itemBlockTest, "AAA-ABA-AAA", ingotIngredient, stickIngredient));
	}
}
