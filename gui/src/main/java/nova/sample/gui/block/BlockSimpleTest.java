package nova.sample.gui.block;

import nova.core.block.Block;
import nova.core.block.component.StaticBlockRenderer;
import nova.core.component.Category;
import nova.core.component.misc.Collider;
import nova.core.component.renderer.ItemRenderer;
import nova.core.game.Game;
import nova.core.inventory.Inventory;
import nova.core.inventory.InventorySimple;
import nova.core.network.Packet;
import nova.core.network.PacketHandler;
import nova.sample.gui.NovaGui;

import java.util.Optional;

/**
 * Literally, this is a test block.
 * @author Calclavia
 */
public class BlockSimpleTest extends Block implements PacketHandler {

	public Inventory inventory = new InventorySimple(1);

	public BlockSimpleTest() {
		add(new StaticBlockRenderer(this)).setTexture((dir) -> Optional.of(NovaGui.steelTexture));

		add(new Collider());

		add(new ItemRenderer(this));

		add(new Category("buildingBlocks"));
		rightClickEvent.add(this::onRightClick);
	}

	public void onRightClick(RightClickEvent evt) {
		NovaGui.initializeGUI();
		NovaGui.guiFactory.showGui("testgui", evt.entity, position());

		System.out.println("Sending Packet: 1234");
		Game.instance.networkManager.sync(this);
	}

	@Override
	public void read(Packet packet) {
		System.out.println("Received packet: " + packet.readInt());
	}

	@Override
	public void write(Packet packet) {
		packet.writeInt(1234);
	}

	@Override
	public String getID() {
		return "simple";
	}
}
