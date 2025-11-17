package controller;

import model.Trie;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;


public class FileHandler {
    private static final String DATA_FILE = "data\\slang.txt";
    private static final String ORIGINAL_FILE = "data\\slang_original.txt";
    private static final String CACHE_FILE = "data\\slang_cache.ser";

    public static Map<String, String> readSlangFile() {
        ensureOriginalSnapshot();
        Map<String, String> map = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { // skip header
                    first = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("`", -1);
                if (parts.length < 2) continue;
                String slang = parts[0] != null ? parts[0].trim() : "";
                String meaning = parts[1] != null ? parts[1].trim() : "";
                if (slang.isEmpty() || meaning.isEmpty()) continue; // drop incomplete
                map.put(slang, meaning);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return map;
    }
    // AI-referenced for caching data structure
    public static void writeSlangFile(Map<String, String> map) {
        // Save to the text file
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE, false))) {
            pw.println("Slang`Meaning");
            for (Map.Entry<String, String> e : map.entrySet()) {
                pw.println(e.getKey() + "`" + e.getValue());
            }
        } catch (IOException e) {
            System.err.println("Failed to write slang file: " + e.getMessage());
        }

        // refresh cache as well
        saveCache(map);
    }

    public static void writeSlangFile(Map<String, String> map, Trie trie) {
        writeSlangFile(map);
        saveCacheBundle(map, trie);
    }

    public static void resetToOriginal() {
        try {
            Files.copy(Path.of(ORIGINAL_FILE), Path.of(DATA_FILE), StandardCopyOption.REPLACE_EXISTING);
            // Update cache to original content
            Map<String, String> map = readSlangFile();
            // Recreate trie for the original map
            Trie t = new Trie();
            for (Map.Entry<String, String> e : map.entrySet()) t.insert(e.getKey(), e.getValue());
            saveCacheBundle(map, t);
        } catch (IOException e) {
            System.err.println("Failed to reset to original: " + e.getMessage());
        }
    }

    private static void ensureOriginalSnapshot() {
        try {
            Path original = Path.of(ORIGINAL_FILE);
            Path data = Path.of(DATA_FILE);
            if (Files.exists(data) && !Files.exists(original)) {
                Files.createDirectories(original.getParent());
                Files.copy(data, original, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Failed to create original snapshot: " + e.getMessage());
        }
    }

    // Simple cache: serialize the map to skip parsing and re-indexing
    public static void saveCache(Map<String, String> map) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CACHE_FILE))) {
            oos.writeObject(new LinkedHashMap<>(map));
        } catch (IOException e) {
            System.err.println("Failed to save cache: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> loadCache() {
        File f = new File(CACHE_FILE);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                return (Map<String, String>) obj;
            }
        } catch (Exception e) {
            System.err.println("Failed to load cache: " + e.getMessage());
        }
        return null;
    }

    // Bundle both map and trie for faster startup (no re-indexing)
    public static void saveCacheBundle(Map<String, String> map, Trie trie) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CACHE_FILE))) {
            oos.writeObject(new CacheBundle(new LinkedHashMap<>(map), trie));
        } catch (IOException e) {
            System.err.println("Failed to save cache bundle: " + e.getMessage());
        }
    }

    public static CacheBundle loadCacheBundle() {
        File f = new File(CACHE_FILE);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof CacheBundle) {
                return (CacheBundle) obj;
            }
        } catch (Exception e) {
            System.err.println("Failed to load cache bundle: " + e.getMessage());
        }
        return null;
    }

    public static class CacheBundle implements Serializable {
        public final Map<String, String> map;
        public final Trie trie;

        public CacheBundle(Map<String, String> map, Trie trie) {
            this.map = map;
            this.trie = trie;
        }
    }
}
