package wintersteve25.dautils.common.item.heat_orbs;

import java.awt.*;

public enum EnumOrbData {
    LAVA ("lava", 0, Color.red),
    BLAZING("blazing", 1, Color.orange),
    SCORCHING("scorching", 2, Color.blue);

    private String name;
    private int tier;
    private Color color;

    EnumOrbData(String name, int tier, Color color) {
        this.name = name + "_orb";
        this.tier = tier;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public int getTier() {
        return this.tier;
    }
}
