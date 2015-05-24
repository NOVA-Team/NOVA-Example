package nova.sample.entity;

import nova.core.entity.Entity;
import nova.core.entity.components.Collidable;
import nova.core.network.Sync;
import nova.core.render.model.Model;
import nova.core.retention.Storable;
import nova.core.retention.Stored;
import nova.core.util.components.Updater;
import nova.core.util.transform.matrix.Quaternion;
import nova.core.util.transform.shape.Cuboid;
import nova.sample.NovaTest;

import java.util.ArrayList;
import java.util.Collection;

public class EntityMovableSimpleTest extends Entity implements Collidable, Storable, Updater {

	@Stored
	@Sync
	private double excited,timer;

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
	public Collection<Cuboid> getCollisionBoxes() {
		ArrayList<Cuboid> cuboids = new ArrayList<Cuboid>(1);
		cuboids.add(new Cuboid(-0.5, 0, -0.5, 0.5, 1, 0.5));
		return cuboids;
	}

	@Override
	public void onCollide(Entity collidedWith) {
		excited = 1;
	}

	@Override
	public void update(double deltaTime) {
		excited -= deltaTime / 4.0d;
		if (excited < 0.1)
			excited = 0.1;
		timer += deltaTime * excited;
		NovaTest.checkConversion(this,"EntityMovableSimpleTest");
	}

}
