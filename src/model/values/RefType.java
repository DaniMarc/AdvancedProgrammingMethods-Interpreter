package model.values;

import java.util.Objects;

public class RefType implements Type{
    private final Type inner;

    public RefType(Type i){
        inner = i;
    }

    public Type getInner(){
        return inner;
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof RefType)
            return inner.equals(((RefType)another).getInner());
        return false;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        Type in = inner.deepCopy();
        return new RefType(in);
    }

    @Override
    public String toString(){
        return "Ref("+inner.toString()+")";
    }
}
