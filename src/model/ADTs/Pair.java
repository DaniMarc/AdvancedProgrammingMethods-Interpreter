package model.ADTs;

public class Pair<Type1, Type2> implements IPair<Type1, Type2>{
    private final Type1 first;
    private final Type2 second;

    public Pair(Type1 f, Type2 s){
        first = f;
        second = s;
    }

    @Override
    public Type1 getFirst() {
        return first;
    }

    @Override
    public Type2 getSecond() {
        return second;
    }

    @Override
    public String toString(){
        return "Pair<" + first.toString() + ", " + second.toString() + ">";
    }
}
