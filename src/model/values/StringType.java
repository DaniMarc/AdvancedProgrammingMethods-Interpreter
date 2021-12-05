package model.values;

public class StringType implements Type{
    public boolean equals(Object another){ return another instanceof StringType; }

    @Override
    public String toString(){
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
