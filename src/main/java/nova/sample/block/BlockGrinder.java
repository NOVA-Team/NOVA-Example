package nova.sample.block;

import nova.core.block.Block;
import nova.core.block.Stateful;
import nova.core.component.Category;
import nova.core.component.misc.Collider;
import nova.core.component.renderer.ItemRenderer;
import nova.core.component.renderer.StaticRenderer;
import nova.core.game.Game;
import nova.core.network.NetworkTarget;
import nova.core.network.Packet;
import nova.core.network.PacketHandler;
import nova.core.network.Sync;
import nova.core.render.model.Model;
import nova.core.retention.Storable;
import nova.core.retention.Stored;
import nova.core.util.transform.matrix.Quaternion;
import nova.sample.NovaTest;

/**
 * This is a test block that has state.
 * @author Calclavia
 */
public class BlockGrinder extends Block implements Storable, Stateful, PacketHandler {

	/**
	 * Angle to rotate around
	 */
	@Stored
	@Sync
	private double angle = 0;

	public BlockGrinder() {

		add(new Collider().isOpaqueCube(false));

		add(new StaticRenderer(this)
				.onRender(model -> {
						Model grinderModel = NovaTest.grinderModel.getModel();

						grinderModel
							.combineChildren("crank", "crank1", "crank2", "crank3")
							.rotate(Quaternion.fromEuler(0, angle, 0));

						model.children.add(grinderModel);
						model.bindAll(NovaTest.grinderTexture);
					}
				)
		);
		add(new ItemRenderer(this));
		add(new Category("buildingBlocks"));
	}

	public boolean onRightClick(RightClickEvent evt) {
		if (NetworkTarget.Side.get() == NetworkTarget.Side.SERVER) {
			angle = (angle + Math.PI / 12) % (Math.PI * 2);
			Game.instance.networkManager.sync(this);
		}
		world().addEntity(NovaTest.movableSimpleTestFactory).transform().setPosition(evt.entity.position());
		return true;
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
}
