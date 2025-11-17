package model;

import java.util.List;

public class SlangWord {
    private final String slang;
    private final String meaning;

    public SlangWord(String slang, String meaning) {
        this.slang = slang;
        this.meaning = meaning;
    }

    public String getSlang() {
        return slang;
    }

    public String getMeaning() {
        StringBuilder formattedMeaning = new StringBuilder();
        String[] meaningList = meaning.split("\\|", -1);
        for (int i = 0; i < meaningList.length; i++) {
            formattedMeaning.append((i + 1));
            formattedMeaning.append(". ");
            formattedMeaning.append(meaningList[i].trim());
            if (i < meaningList.length - 1) {
                formattedMeaning.append("\n");
            }
        }
        return formattedMeaning.toString();
    }

    @Override
    public String toString() {
        StringBuilder formattedMeaning = new StringBuilder();
        String[] meaningList = meaning.split("\\|", -1);
        formattedMeaning.append("\n");
        for (int i = 0; i < meaningList.length; i++) {
            formattedMeaning.append((i + 1));
            formattedMeaning.append(". ");
            formattedMeaning.append(meaningList[i].trim());
            if (i < meaningList.length - 1) {
                formattedMeaning.append("\n");
            }
        }
        return slang + ": " + formattedMeaning;
    }
}
