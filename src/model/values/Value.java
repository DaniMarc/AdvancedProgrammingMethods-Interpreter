package model.values;

public interface Value {
    Type getType();
    boolean equals(Object another);
}
