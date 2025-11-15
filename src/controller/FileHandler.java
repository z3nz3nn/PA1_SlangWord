package controller;

public class FileHandler {
    private static final String DEFAULT_PATH = "data\\slang.txt";

    public static java.util.Map<String, java.util.List<String>> readSlangFile() {
        return readSlangFile(DEFAULT_PATH);
    }

    public static java.util.Map<String, java.util.List<String>> readSlangFile(String path) {
        java.util.Map<String, java.util.List<String>> map = new java.util.LinkedHashMap<>();
        java.nio.file.Path p = java.nio.file.Paths.get(path);
        if (!java.nio.file.Files.exists(p)) return map;
        try (java.io.BufferedReader br = java.nio.file.Files.newBufferedReader(p)) {
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
                String meaningRaw = parts[1] != null ? parts[1].trim() : "";
                if (slang.isEmpty() || meaningRaw.isEmpty()) continue; // drop incomplete
                // meanings may be separated by '|'
                String[] ms = meaningRaw.split("\\|", -1);
                java.util.List<String> meanings = new java.util.ArrayList<>();
                for (String m : ms) {
                    if (m != null) {
                        String t = m.trim();
                        if (!t.isEmpty()) meanings.add(t);
                    }
                }
                if (meanings.isEmpty()) continue;
                map.put(slang, meanings);
            }
        } catch (Exception e) {
        }
        return map;
    }
}
