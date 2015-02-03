package nova.sample.block;

import nova.core.block.Block;
import nova.core.block.components.Stateful;
import nova.core.entity.Entity;
import nova.core.network.NetworkManager;
import nova.core.network.Packet;
import nova.core.network.PacketReceiver;
import nova.core.network.PacketSender;
import nova.core.network.Sync;
import nova.core.render.model.Model;
import nova.core.util.components.Storable;
import nova.core.util.components.Stored;
import nova.core.util.transform.Vector3d;
import nova.sample.NovaTest;

/**
 * This is a test block that has state.
 * @author Calclavia
 */
public class BlockGrinder extends Block implements Storable, Stateful, PacketReceiver, PacketSender {

	/**
	 * Angle to rotate around
	 */
	@Stored
	@Sync
	private double angle;

	@Override
	public boolean onRightClick(Entity entity, int side, Vector3d hit) {
		if (NetworkManager.instance.get().isServer()) {
			angle = (angle + Math.PI / 12) % (Math.PI * 2);
			NetworkManager.instance.get().sync(this);
		}
		return true;
	}

	@Override
	public void renderStatic(Model model) {
		Model grinderModel = NovaTest.grinderModel.getModel();
		model.scale = new Vector3d(1 / 16, 1 / 16, 1 / 16);
		model.children.add(grinderModel);
		model.bind(NovaTest.steelTexture);
	}

	@Override
	public void read(int id, Packet packet) {
		PacketReceiver.super.read(id, packet);
		getWorld().markStaticRender(getPosition());
	}

	@Override
	public String getID() {
		return "stateful";
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
