package pink.rat.Biomes;

import org.bukkit.generator.ChunkGenerator.ChunkData;

public interface BiomeInterface {
	public String name = "";
	public int baseHeight = 0;
	public double temperature = 0;
	public double mildue = 0;
	public void generate(ChunkData chunkData, int x, int y);
}
