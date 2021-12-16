package model.ADTs;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<KeyType, ValueType> implements IDictionary<KeyType, ValueType>{
    private HashMap<KeyType, ValueType> dictionary;

    public Dictionary(){
        dictionary = new HashMap<>();
    }

    public Dictionary(HashMap<KeyType, ValueType> newDict) { dictionary = newDict; }

    @Override // method that adds to the dictionary a value of generic type Type2 having a key of generic type Type1
    public void add(KeyType key, ValueType val) {
        // Using putIfAbsent will add to the dictionary the value 'val' only if the key does not exist already
        dictionary.putIfAbsent(key, val);
    }

    @Override // method that removes an element from the dictionary that has the specified key
    public void remove(KeyType key) {
        // Removes the mapping for the specified key from this map if present
        dictionary.remove(key);
    }

    @Override // method that updates the value of the entry in the dictionary at that specific key
    public void update(KeyType key, ValueType val) {
        // Using replace method will replace the value at the key only if the key is in the dictionary, otherwise it does nothing
        dictionary.replace(key, val);
    }

    @Override // method that searches for the value of the entry in the dictionary at that specific key
    public ValueType lookup(KeyType key) {
        // checking if the key does in fact exists
        if(dictionary.containsKey(key))
            return dictionary.get(key);
        return null;
    }

    @Override // method that checks if the value of the entry is in the dictionary at that specific key and returns the result of containsKey()
    public boolean isDefined(KeyType key) {
        // This method returns true if the dictionary has an entry with that key, otherwise returns false
        return dictionary.containsKey(key);
    }

    @Override
    public String toString(){
        StringBuilder retString = new StringBuilder("{ ");
        for(KeyType i : dictionary.keySet()){
            retString.append(i.toString()).append("->").append(dictionary.get(i).toString()).append("; ");
        }
        retString.append("}");
        return retString.toString();
    }

    @Override
    public Map<KeyType, ValueType> getContent() {
        return dictionary;
    }

    @Override
    public Dictionary<KeyType, ValueType> deepCopy(){
        HashMap<KeyType, ValueType> copiedDictionary = new HashMap<>();
        for (Map.Entry<KeyType, ValueType> element : dictionary.entrySet()){
            KeyType key = element.getKey();
            ValueType value = element.getValue();
            copiedDictionary.put(key, value);
        }
        return new Dictionary<KeyType, ValueType>(copiedDictionary);
    }
}
