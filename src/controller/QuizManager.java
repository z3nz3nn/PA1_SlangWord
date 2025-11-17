package controller;

import model.SlangWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizManager {

    public static class Question {
        public final String prompt;
        public final String[] options; // length 4
        public final int correctIndex; // 0..3

        public Question(String prompt, String[] options, int correctIndex) {
            this.prompt = prompt;
            this.options = options;
            this.correctIndex = correctIndex;
        }
    }

    private final SlangManager sm = SlangManager.getInstance();
    private final Random rnd = new Random();

    // Quiz: given slang -> choose the correct definition
    public Question generateSlangQuiz() {
        List<SlangWord> all = sm.getAll();
        if (all.size() < 4) return null;
        SlangWord correct = all.get(rnd.nextInt(all.size()));

        Set<Integer> idxSet = new HashSet<>();
        while (idxSet.size() < 3) {
            int x = rnd.nextInt(all.size());
            if (!all.get(x).getSlang().equals(correct.getSlang())) idxSet.add(x);
        }

        List<String> options = new ArrayList<>();
        int correctPos = rnd.nextInt(4);
        for (int i = 0, j = 0; i < 4; i++) {
            if (i == correctPos) {
                options.add(correct.getMeaning());
            } else {
                int pick = (int) idxSet.toArray()[j++];
                options.add(all.get(pick).getMeaning());
            }
        }
        return new Question(correct.getSlang(), options.toArray(new String[0]), correctPos);
    }

    // Quiz: given definition -> choose the correct slang word
    public Question generateDefinitionQuiz() {
        List<SlangWord> all = sm.getAll();
        if (all.size() < 4) return null;
        SlangWord correct = all.get(rnd.nextInt(all.size()));

        Set<Integer> idxSet = new HashSet<>();
        while (idxSet.size() < 3) {
            int x = rnd.nextInt(all.size());
            if (!all.get(x).getSlang().equals(correct.getSlang())) idxSet.add(x);
        }

        List<String> options = new ArrayList<>();
        options.add(correct.getSlang());
        for (Integer i : idxSet) {
            options.add(all.get(i).getSlang());
        }
        Collections.shuffle(options, rnd);
        int correctPos = options.indexOf(correct.getSlang());
        return new Question(correct.getMeaning(), options.toArray(new String[0]), correctPos);
    }
}
