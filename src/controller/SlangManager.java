package controller;
import model.SlangWord;
import model.Trie;

import java.util.*;

public class SlangManager {
    private static SlangManager instance;

    private final Map<String, List<String>> data = new LinkedHashMap<>();
    private final Trie trie = new Trie();
    private final Random rnd = new Random();

    private SlangManager() {
        load();
    }

    public static SlangManager getInstance() {
        if (instance == null) instance = new SlangManager();
        return instance;
    }

    private void load() {
        data.clear();
        data.putAll(FileHandler.readSlangFile());
        // build trie
        for (Map.Entry<String, List<String>> e : data.entrySet()) {
            trie.insert(e.getKey(), e.getValue());
        }
    }

    public List<SlangWord> getAll() {
        List<SlangWord> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : data.entrySet()) {
            list.add(new SlangWord(e.getKey(), e.getValue()));
        }
        return list;
    }

    public SlangWord getRandom() {
        if (data.isEmpty()) return null;
        int idx = rnd.nextInt(data.size());
        String key = new ArrayList<>(data.keySet()).get(idx);
        return new SlangWord(key, data.get(key));
    }

    public Optional<SlangWord> findBySlang(String slang) {
        if (slang == null) return Optional.empty();
        List<String> defs = trie.searchExact(slang);
        if (defs.isEmpty()) return Optional.empty();
        return Optional.of(new SlangWord(slang, defs));
    }

    public List<SlangWord> findByPrefix(String prefix) {
        return trie.startsWith(prefix);
    }

    public List<SlangWord> findByDefinitionContains(String keyword) {
        List<SlangWord> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) return result;
        String k = keyword.trim().toLowerCase();
        for (Map.Entry<String, List<String>> e : data.entrySet()) {
            for (String m : e.getValue()) {
                if (m.toLowerCase().contains(k)) {
                    result.add(new SlangWord(e.getKey(), e.getValue()));
                    break;
                }
            }
        }
        return result;
    }
}
