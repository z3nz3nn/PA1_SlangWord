package model;

public class TrieNode {
    public java.util.Map<Character, TrieNode> children = new java.util.HashMap<>();
    public boolean isEndOfWord = false;
    public java.util.List<String> meanings = new java.util.ArrayList<>();
}
