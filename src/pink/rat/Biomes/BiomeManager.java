package pink.rat.Biomes;

import java.util.ArrayList;
import java.util.List;

public class BiomeManager {
    private List<Biome> biomes = new ArrayList<>();

    public Biome getBiome(double x, double y, double biomeSize) {
        double noise = Simplex.noise(x * 0.125, 1, y * 0.125);
        double noise2 = Simplex.noise(x * 0.125, 2, y * 0.125); 
        double biomeSize2 = Simplex.noise(x * 0.5 / biomeSize, 1, y * 0.5 / biomeSize);
        return null;
    }

    public void addBiome(Biome biome) {
        biomes.add(biome);
    }
}
