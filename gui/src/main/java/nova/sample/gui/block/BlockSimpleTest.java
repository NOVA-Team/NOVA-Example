package nova.sample.gui.block;

import nova.core.block.Block;
import nova.core.component.Category;
import nova.core.component.inventory.Inventory;
import nova.core.component.inventory.InventorySimple;
import nova.core.component.misc.Collider;
import nova.core.network.Syncable;
import nova.core.network.Packet;
import nova.sample.gui.NovaGui;

import nova.core.component.renderer.StaticRenderer;
import nova.core.render.model.MeshModel;
import nova.core.render.pipeline.BlockRenderPipeline;

/**
 * Literally, this is a test block.
 * @author Calclavia
 */
public class BlockSimpleTest extends Block implements Syncable {

	public Inventory inventory = new InventorySimple(1);

	public BlockSimpleTest() {
		components.add(new StaticRenderer().onRender(new BlockRenderPipeline(this).withTexture(NovaGui.steelTexture).build()));
		components.add(new Collider(this));
		components.add(new Category("buildingBlocks"));
		events.on(RightClickEvent.class).bind(this::onRightClick);
	}

	public void onRightClick(RightClickEvent evt) {
		NovaGui.guiFactory.showGui("testgui", evt.entity, position());

		System.out.println("Sending Packet: 1234");
		NovaGui.networkManager.sync(this);
	}

	@Override
	public void read(Packet packet) {
		NovaGui.logger.info("Received packet: {}", packet.readInt());
	}

	@Override
	public void write(Packet packet) {
		packet.writeInt(1234);
	}
}
