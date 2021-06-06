package geometry.dash.engine;

import java.io.Serializable;
import java.util.Objects;

public class Vector implements Serializable {

    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Math.round(x) == Math.round(vector.x) &&
                Math.round(y) == Math.round(vector.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" +  Math.round(x) +
                ", y=" +  Math.round(y) +
                '}';
    }
}
