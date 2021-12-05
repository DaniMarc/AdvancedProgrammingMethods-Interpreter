package model.values;

import java.util.Objects;

public class StringValue implements Value{
    private final String value;

    public StringValue(String v){
        value = v;
    }

    public String getValue(){
        return value;
    }

    public String toString(){
        return (String.format("%s(str)", value));
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        if(this == another) return true;
        if(!(another instanceof StringValue)) return false;
        StringValue anotherString = (StringValue)another;
        return Objects.equals(getValue(), anotherString.getValue());
    }
}
