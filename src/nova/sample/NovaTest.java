package nova.sample;

import nova.core.block.Block;
import nova.core.game.Game;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;

/**
 * A test Nova Mod
 * @author Calclavia
 */
@NovaMod(id = "Nova Test", name = "Nova Test", version = "0.0.1", novaVersion = "0.0.1")
public class NovaTest implements Loadable {

	public final Block blockTest = new BlockTest();

	@Override
	public void preInit() {
		Game.instance.get().blockManager.registry.register(blockTest);
	}
}
