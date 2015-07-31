package nova.sample.worldgen;

import nova.core.block.BlockManager;
import nova.core.item.ItemManager;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;
import nova.worldgen.WorldgenManager;

@NovaMod(id = NovaWorldgen.id, name = "Nova Worldgen Example", version = "0.0.1", novaVersion = "0.0.1")
public class NovaWorldgen implements Loadable {
	public static final String id = "novaworldgen";

	public final BlockManager blockManager;
	public final ItemManager itemManager;
	public final WorldgenManager worldgenManager;

	public NovaWorldgen(BlockManager blockManager,
	                    ItemManager itemManager,
	                    WorldgenManager worldgenManager) {
		this.blockManager = blockManager;
		this.itemManager = itemManager;
		this.worldgenManager = worldgenManager;
	}
}
