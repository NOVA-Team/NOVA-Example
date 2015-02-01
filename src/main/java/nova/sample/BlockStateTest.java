package nova.sample;

import nova.core.block.Block;
import nova.core.block.components.Stateful;
import nova.core.entity.Entity;
import nova.core.network.Packet;
import nova.core.network.PacketManager;
import nova.core.network.PacketReceiver;
import nova.core.network.PacketSender;
import nova.core.render.Texture;
import nova.core.util.Direction;
import nova.core.util.transform.Vector3d;

import java.util.Optional;

/**
 * This is a test block that has state.
 * @author Calclavia
 */
public class BlockStateTest extends Block implements Stateful, PacketReceiver, PacketSender {

	@Override
	public boolean onRightClick(Entity entity, int side, Vector3d hit) {
		System.out.println("Sending Packet: 2222");
		PacketManager.instance.get().sync(this);
		return true;
	}

	@Override
	public Optional<Texture> getTexture(Direction side) {
		return Optional.of(NovaTest.steelTexture);
	}

	@Override
	public void read(Packet packet) {
		System.out.println("Received packet: " + packet.readInt());
	}

	@Override
	public void write(Packet packet) {
		packet.writeInt(2222);
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
