package nova.sample.block;

import nova.core.block.Block;
import nova.core.entity.Entity;
import nova.core.game.Game;
import nova.core.network.Packet;
import nova.core.network.PacketHandler;
import nova.core.render.texture.Texture;
import nova.core.util.Category;
import nova.core.util.Direction;
import nova.core.util.transform.Vector3d;
import nova.sample.NovaTest;

import java.util.Optional;

/**
 * Literally, this is a test block.
 * @author Calclavia
 */
public class BlockSimpleTest extends Block implements PacketHandler, Category {

	@Override
	public boolean onRightClick(Entity entity, int side, Vector3d hit) {
		System.out.println("Sending Packet: 1234");
		Game.instance.networkManager.sync(this);
		Game.instance.guiFactory.get().showGui(NovaTest.id, "testgui", entity, position());
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
		packet.writeInt(1234);
	}

	@Override
	public String getID() {
		return "simple";
	}

	@Override
	public String getCategory() {
		return "buildingBlocks";
	}
}
