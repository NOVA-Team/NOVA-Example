package nova.sample.block;

import nova.core.block.Block;
import nova.core.block.components.Stateful;
import nova.core.entity.Entity;
import nova.core.network.PacketManager;
import nova.core.network.PacketReceiver;
import nova.core.network.PacketSender;
import nova.core.network.Sync;
import nova.core.render.model.Model;
import nova.core.render.texture.Texture;
import nova.core.util.Direction;
import nova.core.util.Stored;
import nova.core.util.transform.Quaternion;
import nova.core.util.transform.Vector3d;
import nova.sample.NovaTest;

import java.util.Optional;

/**
 * This is a test block that has state.
 * @author Calclavia
 */
public class BlockStateTest extends Block implements Stateful, PacketReceiver, PacketSender {

	/**
	 * Angle to rotate around
	 */
	@Stored
	@Sync
	private int angle;

	@Override
	public boolean onRightClick(Entity entity, int side, Vector3d hit) {
		angle += 10;
		System.out.println("Sending Packet: " + this + " with " + angle);
		PacketManager.instance.get().sync(this);
		return true;
	}

	@Override
	public Optional<Texture> getTexture(Direction side) {
		return Optional.of(NovaTest.steelTexture);
	}

	@Override
	public void renderWorld(Model model) {
		super.renderWorld(model);
		model.rotation = Quaternion.fromEuler(angle, 0, 0);
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public String getID() {
		return "stateful";
	}
}
