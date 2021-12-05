package model.values;

public class BoolType implements Type{
    public boolean equals(Object another){
//         if(another instanceof BoolType)
//              return true;
//         return false;
        return another instanceof BoolType;
    }

    @Override
    public String toString(){
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
