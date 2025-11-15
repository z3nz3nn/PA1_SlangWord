package model;

public class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word, java.util.List<String> meanings) {
        if (word == null) return;
        String w = word.trim();
        if (w.isEmpty()) return;
        TrieNode node = root;
        for (char c : w.toCharArray()) {
            node = node.children.computeIfAbsent(Character.toLowerCase(c), k -> new TrieNode());
        }
        node.isEndOfWord = true;
        if (meanings != null) {
            for (String m : meanings) {
                if (m != null) {
                    String t = m.trim();
                    if (!t.isEmpty()) node.meanings.add(t);
                }
            }
        }
    }

    public java.util.List<String> searchExact(String word) {
        if (word == null) return java.util.Collections.emptyList();
        TrieNode node = root;
        for (char c : word.trim().toCharArray()) {
            node = node.children.get(Character.toLowerCase(c));
            if (node == null) return java.util.Collections.emptyList();
        }
        if (node.isEndOfWord) return new java.util.ArrayList<>(node.meanings);
        return java.util.Collections.emptyList();
    }

    public java.util.List<SlangWord> startsWith(String prefix) {
        java.util.List<SlangWord> result = new java.util.ArrayList<>();
        if (prefix == null) return result;
        TrieNode node = root;
        String p = prefix.trim();
        for (char c : p.toCharArray()) {
            node = node.children.get(Character.toLowerCase(c));
            if (node == null) return result;
        }
        dfs(node, new StringBuilder(p), result);
        return result;
    }

    private void dfs(TrieNode node, StringBuilder path, java.util.List<SlangWord> out) {
        if (node.isEndOfWord) {
            out.add(new SlangWord(path.toString(), new java.util.ArrayList<>(node.meanings)));
        }
        for (java.util.Map.Entry<Character, TrieNode> e : node.children.entrySet()) {
            path.append(e.getKey());
            dfs(e.getValue(), path, out);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
