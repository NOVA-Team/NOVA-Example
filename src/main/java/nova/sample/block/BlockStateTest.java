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
import nova.core.render.texture.Texture;
import nova.core.util.Direction;
import nova.core.util.Stored;
import nova.core.util.components.Storable;
import nova.core.util.transform.Quaternion;
import nova.core.util.transform.Vector3d;
import nova.sample.NovaTest;

import java.util.Optional;

/**
 * This is a test block that has state.
 *
 * @author Calclavia
 */
public class BlockStateTest extends Block implements Storable, Stateful, PacketReceiver, PacketSender {

	/**
	 * Angle to rotate around
	 */
	@Stored
	@Sync
	private double angle;

	@Override
	public boolean onRightClick(Entity entity, int side, Vector3d hit) {
		angle = (angle + Math.PI / 12) % (Math.PI * 2);
		NetworkManager.instance.get().sync(this);
		return true;
	}

	@Override
	public Optional<Texture> getTexture(Direction side) {
		return Optional.of(NovaTest.steelTexture);
	}

	@Override
	public void renderStatic(Model model) {
		super.renderStatic(model);
		model.rotation = Quaternion.fromEuler(angle, 0, 0);
	}

	@Override
	public void load() {
		System.out.println("BlockState Load: " + this + " : " + angle);
		NetworkManager.instance.get().sync(this);
	}

	@Override
	public void unload() {
		System.out.println("BlockState Unload");
	}

	@Override
	public void read(Packet packet) {
		PacketReceiver.super.read(packet);
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
