package model;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class TrieNode implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<Character, TrieNode> children = new HashMap<>();
    public boolean isEndOfWord = false;
    public String meaning;
}
