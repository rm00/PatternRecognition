package com.rmeloni.PatternRecognition.persistence.memDB;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class MemDBPointPK implements Serializable {

    @NotNull
    private double x;

    @NotNull
    private double y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemDBPointPK that = (MemDBPointPK) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
