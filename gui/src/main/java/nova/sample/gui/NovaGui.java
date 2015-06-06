package nova.sample.gui;

import nova.core.block.BlockFactory;
import nova.core.block.BlockManager;
import nova.core.gui.Background;
import nova.core.gui.ComponentEvent.ActionEvent;
import nova.core.gui.Gui;
import nova.core.gui.GuiEvent.BindEvent;
import nova.core.gui.GuiEvent.UnBindEvent;
import nova.core.gui.component.Button;
import nova.core.gui.component.Container;
import nova.core.gui.component.Label;
import nova.core.gui.component.inventory.Slot;
import nova.core.gui.factory.GuiManager;
import nova.core.gui.layout.Anchor;
import nova.core.gui.layout.FlowLayout;
import nova.core.item.ItemFactory;
import nova.core.item.ItemManager;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;
import nova.core.nativewrapper.NativeManager;
import nova.core.network.NetworkManager;
import nova.core.network.NetworkTarget.Side;
import nova.core.recipes.RecipeManager;
import nova.core.recipes.crafting.ItemIngredient;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.render.Color;
import nova.core.render.RenderManager;
import nova.core.render.texture.BlockTexture;
import nova.sample.gui.block.BlockSimpleTest;

/**
 * A test Nova Mod
 *
 * @author Calclavia
 */
@NovaMod(id = NovaGui.id, name = "Nova GUI example", version = "0.0.1", novaVersion = "0.0.1")
public class NovaGui implements Loadable {

	public static final String id = "novagui";

	public static BlockFactory blockTest;
	public static ItemFactory itemBlockTest;

	public static BlockTexture steelTexture;
	public static GuiManager guiFactory;
	public static NetworkManager networkManager;

	public final BlockManager blockManager;
	public final ItemManager itemManager;
	public final RenderManager renderManager;
	public final NativeManager nativeManager;
	public final RecipeManager recipeManager;

	public NovaGui(BlockManager blockManager,
				   ItemManager itemManager,
				   RenderManager renderManager,
				   GuiManager guiFactory,
				   NativeManager nativeManager,
				   RecipeManager recipeManager,
				   NetworkManager networkManager) {
		this.blockManager = blockManager;
		this.itemManager = itemManager;
		this.renderManager = renderManager;
		this.nativeManager = nativeManager;
		this.recipeManager = recipeManager;

		NovaGui.networkManager = networkManager;

		NovaGui.guiFactory = guiFactory;
	}

	public static void initializeGUI() {
		guiFactory.register(() -> new Gui("testgui")
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
				event.gui.addInventory("main", ((BlockSimpleTest)event.block.get()).inventory);
				System.out.println("Test GUI initialized! " + event.player.getDisplayName() + " " + event.position);
			}, BindEvent.class)

			.onGuiEvent((event) -> {
				System.out.println("Test GUI closed!");
			}, UnBindEvent.class)
		);
	}

	@Override
	public void preInit() {
		blockTest = blockManager.register(BlockSimpleTest.class);

		itemBlockTest = itemManager.getItemFromBlock(blockTest);

		steelTexture = renderManager.registerTexture(new BlockTexture(id, "blockSteel"));

		// try to add a recipe
		ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick"); //TODO: This should be obtained from some dictonary too
		ItemIngredient ingotIngredient = ItemIngredient.forDictionary("ingotIron");
		recipeManager.addRecipe(new ShapedCraftingRecipe(itemBlockTest.makeItem(), "AAA-ABA-AAA", ingotIngredient, stickIngredient));

		initializeGUI();
	}
}
