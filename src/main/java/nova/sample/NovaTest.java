package nova.sample;

import nova.core.block.Block;
import nova.core.block.BlockManager;
import nova.core.game.Game;
import nova.core.gui.Background;
import nova.core.gui.ComponentEvent.ActionEvent;
import nova.core.gui.Gui;
import nova.core.gui.GuiContainer;
import nova.core.gui.GuiEvent.BindEvent;
import nova.core.gui.GuiEvent.UnBindEvent;
import nova.core.gui.components.Button;
import nova.core.gui.components.Label;
import nova.core.gui.factory.GuiFactory;
import nova.core.gui.layout.Anchor;
import nova.core.gui.layout.FlowLayout;
import nova.core.item.Item;
import nova.core.item.ItemManager;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;
import nova.core.network.NetworkTarget.Side;
import nova.core.recipes.crafting.ItemIngredient;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.render.Color;
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
	public static Item itemBlockTest;
	public static Item itemBlockGrinder;

	public static BlockTexture steelTexture;
	public static ItemTexture screwTexture;
	public static BlockTexture grinderTexture;
	public static ModelProvider grinderModel;
	
	public static GuiFactory guiFactory;

	public final BlockManager blockManager;
	public final ItemManager itemManager;
	public final RenderManager renderManager;

	public NovaTest(BlockManager blockManager, ItemManager itemManager, RenderManager renderManager, GuiFactory guiFactory) {
		this.blockManager = blockManager;
		this.itemManager = itemManager;
		this.renderManager = renderManager;
		
		NovaTest.guiFactory = guiFactory;
	}

	@Override
	public void preInit() {
		blockTest = blockManager.register(BlockSimpleTest.class);
		blockGrinder = blockManager.register(BlockGrinder.class);

		itemScrewdriver = itemManager.register(ItemScrewdriver.class);
		itemBlockTest = itemManager.getItemFromBlock(blockTest);
		itemBlockGrinder = itemManager.getItemFromBlock(blockGrinder);

		screwTexture = renderManager.registerTexture(new ItemTexture(id, "screwdriver"));
		steelTexture = renderManager.registerTexture(new BlockTexture(id, "blockSteel"));
		grinderTexture = renderManager.registerTexture(new BlockTexture(id, "grinder"));
		grinderModel = renderManager.registerModel(new TechneModel(id, "grinder"));

		// try to add a recipe
		ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick");
		//ItemIngredient ingotIngredient = ItemIngredient.forItem("minecraft:iron_ingot");
		ItemIngredient ingotIngredient = ItemIngredient.forDictionary("ingotIron");
		ItemIngredient screwdriverIngredient = ItemIngredient.forItem(itemScrewdriver.getID());
		Game.instance.recipeManager.addRecipe(new ShapedCraftingRecipe(itemScrewdriver, "A- B", ingotIngredient, stickIngredient));
		Game.instance.recipeManager.addRecipe(new ShapedCraftingRecipe(itemBlockTest, "AAA-ABA-AAA", ingotIngredient, screwdriverIngredient));

		initializeGUI();
	}

	public void initializeGUI() {
		Gui testGUI = new Gui("testgui")
			.add(new Button("testbutton2", "I'm EAST")
				.setMaximumSize(Integer.MAX_VALUE, 120)

				.onEvent((event, component) -> {
					System.out.println("Test button pressed! " + Side.get());
				}, ActionEvent.class), Anchor.EAST)

			.add(new Button("testbutton3", "I'm CENTER"))
			.add(new Button("testbutton4", "I'm SOUTH"), Anchor.SOUTH)
			
			.add(new GuiContainer("container").setLayout(new FlowLayout())
				.add(new Button("testbutton5", "I'm the FIRST Button and need lots of space"))
				.add(new Label("testlabel1", "I'm some label hanging around").setBackground(new Background(Color.white)))
				.add(new Button("testbutton7", "I'm THIRD"))
				.add(new Button("testbutton8", "I'm FOURTH"))
			, Anchor.NORTH)

			.onGuiEvent((event) -> {
				System.out.println("Test GUI initialized! " + event.player.getDisplayName() + " " + event.position);
			}, BindEvent.class)

			.onGuiEvent((event) -> {
				System.out.println("Test GUI closed!");
			}, UnBindEvent.class);
		
		guiFactory.registerGui(testGUI, id);
	}
}
