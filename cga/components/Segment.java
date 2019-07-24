package cga.components;

import java.util.Objects;

import cga.components.Point;
import cga.Utils;

public class Segment {
    public final Point p1;
    public final Point p2;

    public Segment(Point p1, Point p2) {
        if ((new Utils.PointXComparator()).compare(p1, p2) > 0) {
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return "(" + this.p1 + ", " + this.p2 + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Segment))
            return false;
        Segment other = (Segment) o;
        return this.p1.equals(other.p1) && this.p2.equals(other.p2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.p1, this.p1);
    }
}