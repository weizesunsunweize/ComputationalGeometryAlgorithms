package cga.components;

import java.util.Objects;

public class Vector {
    public final double x;
    public final double y;
    public final double norm;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.norm = Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector))
            return false;
        Vector other = (Vector) o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector minus(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public Vector scale(double a) {
        return new Vector(a * this.x, a * this.y);
    }

    public static Vector addition(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector subtraction(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    public static double dot(Vector v1, Vector v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public static double angle(Vector v1, Vector v2) {
        return Math.acos(Vector.dot(v1, v2) / (v1.norm * v2.norm));
    }
}