package controller;
import model.SlangWord;
import model.Trie;

import java.util.*;
import java.time.LocalDate;

public class SlangManager {
    private static SlangManager instance;

    private final Map<String, String> data = new LinkedHashMap<>();
    private final Trie trie = new Trie();
    private String cachedDailyDate = null;
    private String cachedDailyKey = null;

    private SlangManager() {
        load();
    }

    public static SlangManager getInstance() {
        if (instance == null) instance = new SlangManager();
        return instance;
    }

    private void load() {
        data.clear();
        FileHandler.CacheBundle bundle = FileHandler.loadCacheBundle();
        if (bundle != null && bundle.map != null && !bundle.map.isEmpty()) {
            data.putAll(bundle.map);
            // replace trie root with cached one
            if (bundle.trie != null) {
                trie.setRoot(bundle.trie.getRoot());
            } else {
                rebuildTrie();
            }
        } else {
            data.putAll(FileHandler.readSlangFile());
            rebuildTrie();
        }
    }

    public List<SlangWord> getAll() {
        List<SlangWord> list = new ArrayList<>();
        for (Map.Entry<String, String> e : data.entrySet()) {
            list.add(new SlangWord(e.getKey(), e.getValue()));
        }
        return list;
    }

    public SlangWord getRandomOfTheDay() {
        if (data.isEmpty()) return null;
        String today = LocalDate.now().toString();
        if (today.equals(cachedDailyDate) && cachedDailyKey != null && data.containsKey(cachedDailyKey)) {
            return new SlangWord(cachedDailyKey, data.get(cachedDailyKey));
        }
        // deterministic by date: seed hash of date
        int size = data.size();
        int hash = Math.abs(today.hashCode());
        int idx = hash % size;
        String key = new ArrayList<>(data.keySet()).get(idx);
        cachedDailyDate = today;
        cachedDailyKey = key;
        return new SlangWord(key, data.get(key));
    }

    public Optional<SlangWord> findBySlang(String slang) {
        if (slang == null) return Optional.empty();
        String def = trie.searchExact(slang);
        if (def == null) return Optional.empty();
        return Optional.of(new SlangWord(slang, def));
    }

    public List<SlangWord> findByDefinitionContains(String keyword) {
        List<SlangWord> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) return result;
        String k = keyword.trim().toLowerCase();
        for (Map.Entry<String, String> e : data.entrySet()) {
            String meaningLowered = e.getValue().toLowerCase();
            if (meaningLowered.contains(k)) {
                result.add(new SlangWord(e.getKey(), e.getValue()));
            }
        }
        return result;
    }

    private void rebuildTrie() {
        trie.clear();
        for (Map.Entry<String, String> e : data.entrySet()) {
            trie.insert(e.getKey(), e.getValue());
        }
    }

    public boolean contains(String slang) {
        return data.containsKey(slang);
    }

    public void addOrUpdate(String slang, String definition, boolean overwrite) {
        if (slang == null || slang.trim().isEmpty() || definition == null || definition.trim().isEmpty()) return;
        String s = slang.trim();
        String d = definition.trim();
        if (data.containsKey(s) && !overwrite) {
            // duplicate: append as an alternative meaning separated by "|"
            String existing = data.get(s);
            data.put(s, existing + " | " + d);
        } else {
            data.put(s, d);
        }
        trie.insert(s, data.get(s));
        save();
    }

    public boolean edit(String slang, String newDefinition) {
        if (!data.containsKey(slang)) return false;
        data.put(slang, newDefinition);
        trie.insert(slang, newDefinition);
        save();
        return true;
    }

    public boolean delete(String slang) {
        if (!data.containsKey(slang)) return false;
        data.remove(slang);
        // Rebuild trie to reflect deletion accurately
        rebuildTrie();
        save();
        return true;
    }

    public void resetOriginal() {
        FileHandler.resetToOriginal();
        load();
    }

    public void save() {
        FileHandler.writeSlangFile(data, trie);
    }
}
