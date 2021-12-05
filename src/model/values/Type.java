package model.values;

public interface Type {
    Value defaultValue();
    Type deepCopy();
}
