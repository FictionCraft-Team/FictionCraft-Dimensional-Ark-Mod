package wintersteve25.dautils.common.item.heat_orbs;

public enum EnumOrbData {
    LAVA ("lava", 0),
    BLAZING("blazing", 1),
    SCORCHING("scorching", 2);

    private String name;
    private int tier;

    EnumOrbData(String name, int tier) {
        this.name = name + "_orb";
        this.tier = tier;
    }

    public String getName() {
        return this.name;
    }

    public int getTier() {
        return this.tier;
    }
}
