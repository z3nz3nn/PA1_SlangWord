package model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Trie implements Serializable {
    private static final long serialVersionUID = 1L;
    private TrieNode root = new TrieNode();

    public void insert(String word, String meaning) {
        if (word == null) return;
        String w = word.trim();
        if (w.isEmpty()) return;
        TrieNode node = root;
        for (char c : w.toCharArray()) {
            node = node.children.computeIfAbsent(Character.toLowerCase(c), k -> new TrieNode());
        }
        node.isEndOfWord = true;
        if (meaning != null) {
            node.meaning = meaning;
        }
    }

    public String searchExact(String word) {
        if (word == null) return null;
        TrieNode node = root;
        for (char c : word.trim().toCharArray()) {
            node = node.children.get(Character.toLowerCase(c));
            if (node == null) return null;
        }
        if (node.isEndOfWord) return node.meaning;
        return null;
    }

    private void dfs(TrieNode node, StringBuilder path, List<SlangWord> out) {
        if (node.isEndOfWord) {
            out.add(new SlangWord(path.toString(), node.meaning));
        }
        for (Map.Entry<Character, TrieNode> e : node.children.entrySet()) {
            path.append(e.getKey());
            dfs(e.getValue(), path, out);
            path.deleteCharAt(path.length() - 1);
        }
    }

    public void clear() {
        root.children.clear();
        root.isEndOfWord = false;
        root.meaning = null;
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode newRoot) {
        if (newRoot == null) {
            clear();
        } else {
            this.root = newRoot;
        }
    }
}
