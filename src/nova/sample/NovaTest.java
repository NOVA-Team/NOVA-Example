package nova.sample;

import nova.core.block.Block;
import nova.core.game.Game;
import nova.core.loader.NovaMod;

/**
 * A test Nova Mod
 * @author Calclavia
 */
@NovaMod(id = "Nova Test", name = "Nova Test", version = "0.0.1", novaVersion = "0.0.1")
public class NovaTest {

	public final NovaTest instance = new NovaTest();
	public final Block blockTest = new BlockTest();

	public void init() {
		Game.instance.get().blockManager.registry.register(blockTest);
	}
}
