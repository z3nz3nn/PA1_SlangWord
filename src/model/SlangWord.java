package model;

public class SlangWord {
    private final String slang;
    private final java.util.List<String> meanings;

    public SlangWord(String slang, java.util.List<String> meanings) {
        this.slang = slang;
        this.meanings = new java.util.ArrayList<>();
        if (meanings != null) {
            for (String m : meanings) {
                if (m != null) {
                    String t = m.trim();
                    if (!t.isEmpty()) this.meanings.add(t);
                }
            }
        }
    }

    public String getSlang() {
        return slang;
    }

    public java.util.List<String> getMeanings() {
        return java.util.Collections.unmodifiableList(meanings);
    }

    @Override
    public String toString() {
        return slang + ": " + String.join(" | ", meanings);
    }
}
