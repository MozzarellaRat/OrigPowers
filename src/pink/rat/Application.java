package pink.rat;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;

import pink.rat.Biomes.Biome;
import pink.rat.Biomes.BiomeManager;
import pink.rat.Biomes.Simplex;

public class Application extends JavaPlugin {
    private BiomeManager biomeManager;

    @Override
    public void onEnable() {
        System.out.println("The sparrow prince lies somewhere way up ahead.");
        biomeManager = new BiomeManager();
        biomeManager.addBiome(new Biome("Plains", 60, 0.8));
        biomeManager.addBiome(new Biome("Mountains", 90, 0.5));
        biomeManager.addBiome(new Biome("Desert", 50, 1.0));
    }

    @Override
    public void onDisable() {}

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new CustomChunkGenerator(biomeManager);
    }

    public static class CustomChunkGenerator extends ChunkGenerator {
        private BiomeManager biomeManager;

        public CustomChunkGenerator(BiomeManager biomeManager) {
            this.biomeManager = biomeManager;
        }

        @Override
        public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
            int worldSeed = (int) worldInfo.getSeed();
            double heightMultiplier = 60;
            int baseHeight = 60;
            List<Material> dirts = Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.MOSS_BLOCK);
            Random randomDirt = new Random();
            randomDirt.setSeed(worldSeed);

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int xw = (chunkX * 16) + x;
                    int zw = (chunkZ * 16) + z;

                    // Get biome at this location
                    Biome biome = biomeManager.getBiome(xw, zw, 0.25);
                    baseHeight = biome.getBaseHeight();  // Adjust base height by biome

                    double baseNoise = Simplex.noise(xw * 0.25, 1, zw * 0.25);
                    double smoothNoise = baseNoise * heightMultiplier;
                    double smoothHeight = baseHeight + smoothNoise;
                    smoothHeight = Math.max(chunkData.getMinHeight(), Math.min(chunkData.getMaxHeight(), smoothHeight));
                    smoothHeight += baseHeight / 2;

                    // Fill blocks based on biome properties
                    for (int y = 0; y < smoothHeight; y++) {
                        chunkData.setBlock(x, y, z, Material.STONE);
                    }
                    for (int y = (int) (smoothHeight - 5); y < smoothHeight; y++) {
                        chunkData.setBlock(x, y, z, dirts.get(randomDirt.nextInt(dirts.size())));
                    }
                    chunkData.setBlock(x, (int) smoothHeight, z, Material.MOSS_BLOCK);
                }
            }
            generateUnderground(worldInfo, random, chunkX, chunkZ, chunkData);
        }

        public void generateUnderground(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = chunkData.getMinHeight(); y < 0; y++) {
                        chunkData.setBlock(x, y, z, Material.DEEPSLATE);
                    }
                    int xw = (chunkX * 16) + x;
                    int zw = (chunkZ * 16) + z;
                    double noise = Simplex.noise(xw, zw);
                    chunkData.setBlock(x, 0, z, Material.STONE);
                    if (noise > 0.1) {
                        chunkData.setBlock(x, 0, z, Material.DEEPSLATE);
                    }
                    if (noise > 0.2) {
                        chunkData.setBlock(x, 1, z, Material.DEEPSLATE);
                    }
                    chunkData.setBlock(x, 1, z, Material.STONE);
                }
            }
        }

        @Override
        public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunkData.setBlock(x, chunkData.getMinHeight(), z, Material.BEDROCK);
                }
            }
        }

        @Override
        public boolean shouldGenerateCaves() {
            return true;
        }

        @Override
        public boolean shouldGenerateStructures() {
            return false;
        }
    }
}
