package model.values;

import java.util.Objects;

public class RefValue implements Value{
    private final int address;
    private final Type locationType;

    public RefValue(int a, Type lt) {
        address = a;
        locationType = lt;
    }

    public int getAddress(){
        return address;
    }

    public Type getLocationType(){
        return locationType;
    }

    @Override
    public Type getType(){
        return new RefType(locationType);
    }

    @Override
    public String toString(){
        return String.format("&(%s type at address %d)", locationType, address);
    }

    public Value deepCopy() {
        Type type = locationType.deepCopy();
        return new RefValue(address, type);
    }
}
