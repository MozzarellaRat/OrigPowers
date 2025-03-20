package pink.rat.Biomes;

public class Biome {
    protected String name;
    protected IntegerRange heightRange;
    protected IntegerRange temperatureRange;
    
    public Biome(String name, int lowerHeight, int maximumHeight, int lowerTemperature, int maximumTemperature) {
        this.name = name;
        heightRange = new IntegerRange(lowerHeight, maximumHeight);
        temperatureRange = new IntegerRange(lowerTemperature, maximumTemperature);
    }
    
    public Biome(String name, IntegerRange heightRange, IntegerRange temperatureRange) {
        this.name = name;
        this.heightRange = heightRange;
        this.temperatureRange = temperatureRange;
    }

    public int getLowerHeight() {
        return heightRange.lower;
    }
    public int getMaxHeight() {
        return heightRange.higher;
    }
}
