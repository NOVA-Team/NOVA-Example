package nova.sample.entity;

import nova.core.component.Updater;
import nova.core.component.misc.Collider;
import nova.core.entity.Entity;
import nova.core.network.Sync;
import nova.core.render.model.Model;
import nova.core.retention.Storable;
import nova.core.retention.Stored;
import nova.core.util.transform.matrix.Quaternion;
import nova.sample.NovaTest;

public class EntityMovableSimpleTest extends Entity implements Storable, Updater {

	@Stored
	@Sync
	private double excited, timer;

	public EntityMovableSimpleTest() {
		add(new Collider())
			.onCollide(other -> excited = 1);
	}

	public void render(Model model) {
		Model grinderModel = NovaTest.grinderModel.getModel();

		grinderModel
			.combineChildren("crank", "crank1", "crank2", "crank3")
			.rotate(Quaternion.fromEuler(0, -timer, 0));

		grinderModel.translate(0, 0.5, 0);

		model.children.add(grinderModel);
		model.bindAll(NovaTest.grinderEntityTexture);
	}

	@Override
	public String getID() {
		return "movableSimpleTest";
	}

	@Override
	public void update(double deltaTime) {
		excited -= deltaTime / 4.0d;
		if (excited < 0.1) {
			excited = 0.1;
		}
		timer += deltaTime * excited;
		NovaTest.checkConversion(this, "EntityMovableSimpleTest");
	}

}
