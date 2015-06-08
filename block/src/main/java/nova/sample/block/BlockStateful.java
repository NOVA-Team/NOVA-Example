package nova.sample.block;

import nova.core.block.Block;
import nova.core.block.Stateful;
import nova.core.component.Category;
import nova.core.component.Component;
import nova.core.component.Passthrough;
import nova.core.component.misc.Collider;
import nova.core.component.renderer.ItemRenderer;
import nova.core.component.renderer.StaticRenderer;
import nova.core.network.NetworkTarget;
import nova.core.network.Packet;
import nova.core.network.Syncable;
import nova.core.network.Sync;
import nova.core.render.model.Model;
import nova.core.retention.Storable;
import nova.core.retention.Store;
import nova.core.util.transform.matrix.Rotation;

/**
 * This is a test block that has state.
 * @author Calclavia
 */
public class BlockStateful extends Block implements Storable, Stateful, Syncable {

	/**
	 * Angle to rotate around
	 */
	@Store
	@Sync
	private double angle = 0;

	public BlockStateful() {

		add(new Collider().isOpaqueCube(false));

		add(new StaticRenderer(this)
				.setOnRender(model -> {
						Model grinderModel = NovaBlock.grinderModel.getModel();

						grinderModel
							.combineChildren("crank", "crank1", "crank2", "crank3")
							.rotate(Rotation.fromEuler(0, 0, angle));

						model.children.add(grinderModel);
						model.bindAll(NovaBlock.grinderTexture);
					}
				)
		);
		add(new ItemRenderer(this));
		add(new Category("buildingBlocks"));
		//add(new TestComponent());

		rightClickEvent.add(this::onRightClick);
	}

	public boolean onRightClick(RightClickEvent evt) {
		if (NetworkTarget.Side.get().isServer()) {
			angle = (angle + Math.PI / 12) % (Math.PI * 2);
			NovaBlock.networkManager.sync(this);
		}
		//world().addEntity(NovaTest.movableSimpleTestFactory).transform().setPosition(evt.entity.position());
		return true;
	}

	@Override
	public void read(Packet packet) {
		Syncable.super.read(packet);
		world().markStaticRender(position());
	}

	@Override
	public String getID() {
		return "stateful";
	}

	public static interface TestInterface {
		public void test();
	}

	@Passthrough("nova.sample.block.BlockStateful$TestInterface")
	public static class TestComponent extends Component implements TestInterface {

		@Override
		public void test() {
			System.out.println("I do nothing");
		}

	}
}
