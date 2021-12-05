package model.values;

public class BoolValue implements Value{
    boolean value;

    public BoolValue(boolean v){
        this.value = v;
    }

    public boolean getValue(){
        return this.value;
    }

    public String toString(){
        if(value)
            return ("Boolean value of true");
        return ("Boolean value of false");
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another) {
        if(this == another) return true;
        if(!(another instanceof BoolValue)) return false;
        BoolValue anotherBool = (BoolValue)another;
        return getValue() == anotherBool.getValue();
    }
}
