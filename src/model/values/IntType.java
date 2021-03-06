package model.values;

public class IntType implements Type{
    public boolean equals(Object another){
//         if(another instanceof IntType)
//             return true;
//         return false;
        return another instanceof IntType;
    }

    @Override
    public String toString(){
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
