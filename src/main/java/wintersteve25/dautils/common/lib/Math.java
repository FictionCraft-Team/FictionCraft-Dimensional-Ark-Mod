package wintersteve25.dautils.common.lib;

public class Math {
    public static double randomInRange(double min, double max) {
        return (java.lang.Math.random() * (max - min)) + min;
    }
}
