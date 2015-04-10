package nova.sample.block;

import nova.core.block.Block;
import nova.core.block.components.Modelled;
import nova.core.block.components.Stateful;
import nova.core.entity.Entity;
import nova.core.game.Game;
import nova.core.network.NetworkTarget;
import nova.core.network.Packet;
import nova.core.network.PacketHandler;
import nova.core.network.Sync;
import nova.core.render.model.Model;
import nova.core.retention.Storable;
import nova.core.retention.Stored;
import nova.core.util.Category;
import nova.core.util.components.Updater;
import nova.core.util.transform.Quaternion;
import nova.core.util.transform.Vector3d;
import nova.sample.NovaTest;

/**
 * This is a test block that has state.
 * 
 * @author Calclavia
 */
public class BlockGrinder extends Block implements Storable, Stateful, PacketHandler, Category, Modelled {

	/**
	 * Angle to rotate around
	 */
	@Stored
	@Sync
	private double angle;

	@Override
	public boolean onRightClick(Entity entity, int side, Vector3d hit) {
		if (NetworkTarget.Side.get() == NetworkTarget.Side.SERVER) {
			angle = (angle + Math.PI / 12) % (Math.PI * 2);
			Game.instance.networkManager.sync(this);
		}
		world().createEntity(NovaTest.movableSimpleTestFactory).setPosition(entity.position());
		return true;
	}

	@Override
	public void renderStatic(Model model) {
		Model grinderModel = NovaTest.grinderModel.getModel();

		grinderModel
			.combineChildren("crank", "crank1", "crank2", "crank3")
			.rotate(Quaternion.fromEuler(0, angle, 0));

		model.children.add(grinderModel);
		model.bindAll(NovaTest.grinderTexture);
	}

	@Override
	public void read(Packet packet) {
		PacketHandler.super.read(packet);
		world().markStaticRender(position());
	}

	@Override
	public String getID() {
		return "stateful";
	}

	@Override
	public String getCategory() {
		return "buildingBlocks";
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
