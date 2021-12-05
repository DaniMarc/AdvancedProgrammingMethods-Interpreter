package model.values;

public class IntValue implements Value{
    private final int value;

    public IntValue(int v){
        this.value = v;
    }

    public int getValue(){
        return this.value;
    }

    public String toString(){
        return (String.format("%d(int)", value));
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object another) {
        if(this == another) return true;
        if(!(another instanceof IntValue)) return false;
        IntValue anotherInt = (IntValue)another;
        return getValue() == anotherInt.getValue();
    }
}
