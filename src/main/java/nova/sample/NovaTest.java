package nova.sample;

import nova.core.block.BlockFactory;
import nova.core.block.BlockManager;
import nova.core.entity.EntityFactory;
import nova.core.entity.EntityManager;
import nova.core.game.Game;
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
import nova.core.network.NetworkTarget.Side;
import nova.core.recipes.crafting.ItemIngredient;
import nova.core.recipes.crafting.ShapedCraftingRecipe;
import nova.core.render.Color;
import nova.core.render.RenderManager;
import nova.core.render.model.ModelProvider;
import nova.core.render.model.TechneModel;
import nova.core.render.texture.BlockTexture;
import nova.core.render.texture.EntityTexture;
import nova.core.render.texture.ItemTexture;
import nova.sample.block.BlockGrinder;
import nova.sample.block.BlockSimpleTest;
import nova.sample.entity.EntityMovableSimpleTest;
import nova.sample.item.ItemScrewdriver;

/**
 * A test Nova Mod
 * 
 * @author Calclavia
 */
@NovaMod(id = NovaTest.id, name = "Nova Test", version = "0.0.1", novaVersion = "0.0.1")
public class NovaTest implements Loadable {

	public static final String id = "novatest";

	public static BlockFactory blockTest, blockGrinder;
	public static ItemFactory itemScrewdriver;
	public static ItemFactory itemBlockTest;
	public static ItemFactory itemBlockGrinder;

	public static BlockTexture steelTexture;
	public static ItemTexture screwTexture;
	public static BlockTexture grinderTexture;
	public static EntityTexture grinderEntityTexture;
	public static ModelProvider grinderModel;

	public static EntityFactory movableSimpleTestFactory;

	public static GuiManager guiFactory;

	public final BlockManager blockManager;
	public final ItemManager itemManager;
	public final RenderManager renderManager;
	public final EntityManager entityManager;
	public final NativeManager nativeManager;

	public NovaTest(BlockManager blockManager, ItemManager itemManager, RenderManager renderManager, GuiManager guiFactory, EntityManager entityManager, NativeManager nativeManager) {
		this.blockManager = blockManager;
		this.itemManager = itemManager;
		this.renderManager = renderManager;
		this.entityManager = entityManager;
		this.nativeManager = nativeManager;

		NovaTest.guiFactory = guiFactory;
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

	public static void checkConversion(Object obj, String string) {
		Object nativeObj = Game.instance.nativeManager.toNative(obj);
		Object shouldBeObj = Game.instance.nativeManager.toNova(nativeObj);
		if (shouldBeObj != obj)
		{
			System.out.println("NativeManager is not converting "+string+" properly, set a breakpoint in NovaTest.checkConversion");
		}
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
		grinderEntityTexture = renderManager.registerTexture(new EntityTexture(id, "grinderEntity"));
		grinderModel = renderManager.registerModel(new TechneModel(id, "grinder"));

		movableSimpleTestFactory = entityManager.register(EntityMovableSimpleTest.class);

		// try to add a recipe
		ItemIngredient stickIngredient = ItemIngredient.forItem("minecraft:stick");
		// ItemIngredient ingotIngredient =
		// ItemIngredient.forItem("minecraft:iron_ingot");
		ItemIngredient ingotIngredient = ItemIngredient.forDictionary("ingotIron");
		ItemIngredient screwdriverIngredient = ItemIngredient.forItem(itemScrewdriver.getID());
		Game.instance.recipeManager.addRecipe(new ShapedCraftingRecipe(itemScrewdriver.makeItem(), "A- B", ingotIngredient, stickIngredient));
		Game.instance.recipeManager.addRecipe(new ShapedCraftingRecipe(itemBlockTest.makeItem(), "AAA-ABA-AAA", ingotIngredient, screwdriverIngredient));

		initializeGUI();
	}
}
