package nova.sample.block;

import nova.core.block.Block;
import nova.core.block.Stateful;
import nova.core.component.Category;
import nova.core.component.Component;
import nova.core.component.Passthrough;
import nova.core.component.misc.Collider;
import nova.core.component.renderer.StaticRenderer;
import nova.core.component.transform.Orientation;
import nova.core.network.NetworkTarget;
import nova.core.network.Packet;
import nova.core.network.Sync;
import nova.core.network.Syncable;
import nova.core.render.model.MeshModel;
import nova.core.render.model.Model;
import nova.core.render.pipeline.OrientationRenderPipeline;
import nova.core.retention.Storable;
import nova.core.retention.Store;
import nova.core.util.math.RotationUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;

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

	@Store
	@Sync
	private Orientation orientation = new Orientation(this).hookBasedOnEntity();

	public BlockStateful() {
		components.add(new Collider(this).isOpaqueCube(false));
		components.add(new StaticRenderer().onRender(new OrientationRenderPipeline(orientation).build().andThen(model -> {
			Model grinderModel = NovaBlock.grinderModel.getModel();

			grinderModel
				.combineChildren("crank", "crank1", "crank2", "crank3")
				.matrix.rotate(new Rotation(RotationUtil.DEFAULT_ORDER, 0, 0, angle));

			if (grinderModel instanceof MeshModel)
				((MeshModel)grinderModel).bindAll(NovaBlock.grinderTexture);

			model.children.add(grinderModel);
		})));
		components.add(new Category("buildingBlocks"));
		//components.add(new TestComponent());

		events.on(RightClickEvent.class).bind(this::onRightClick);
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
