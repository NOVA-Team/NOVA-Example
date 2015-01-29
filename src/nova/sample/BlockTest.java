package nova.sample;

import nova.core.block.Block;
import nova.core.block.BlockManager;

/**
 * @author Calclavia
 */
public class BlockTest extends Block {
	
	@Override
	public String getID() {
		return "test";
	}
}
