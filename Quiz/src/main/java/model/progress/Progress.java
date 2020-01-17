package model.progress;

import model.Dictionary;
import model.Word;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Progress {
    private static final Progress instance = new Progress();
    Level[] levels;
    int accessedWordNumber;

    private Progress() {
        levels = new Level[]{
                new Level("A1"),
                new Level("A2"),
                new Level("B1"),
                new Level("B2"),
                new Level("C1"),
                new Level("C2")
        };
        try {
            loadProgress();
        } catch (IOException e) {
            for (Level level : levels) {
                Dictionary dictionary = new Dictionary(level);
                for (Word word : dictionary.getWordList()) {
                    updateProgressLevel(level, word, 0);
                }
            }
            try {
                saveProgress();
            } catch (IOException e2) {
                //todo obsłużyć wyjątki
            }
        }

        accessedWordNumber = 0;
    }

    public static Progress getInstance() {
        return Progress.instance;
    }

    public void updateProgressLevel(Level level, Word word, int value) {
        int oldValue = levels[level.toInteger()].map.get(word);
        levels[level.toInteger()].map.replace(word, oldValue + value);
    }

    public void resetLevelProgress(Level level) {
        level.setMap(null);
        level.setMap(new LinkedHashMap<>());
    }

    public void resetWholeProgress() {
        for (Level level : levels) {
            resetLevelProgress(level);
        }
    }

    public void saveProgress() throws IOException {
        FileWriter writer = new FileWriter("Quiz\\src\\main\\resources\\progress");
        for (Level level : levels) {
            writer.write("$ " + level.name + "\n");

            HashSet entrySet = new HashSet<>(level.getMap().entrySet());
            ArrayList<Map.Entry<Word, Integer>> entryList = new ArrayList<>(entrySet);
            entryList.sort(Map.Entry.comparingByValue());

            for (int j = 0; j < level.map.size(); j++) {
                writer.write(entryList.get(j).getKey() + ":" + entryList.get(j).getValue() + "\n");
            }
        }
        writer.close();
    }

    public void loadProgress() throws IOException {
        //todo sprawdzić, czy plik istnieje!
        Scanner scanner = new Scanner(new FileReader("Quiz\\src\\main\\resources\\progress"));
        while (scanner.hasNextLine()) {
            String[] levelLine = scanner.nextLine().split(" ");
            Level level = new Level(levelLine[1]);
            while (scanner.hasNextLine() && !scanner.hasNext("\\$")) {
                String line = scanner.nextLine();
                String[] columns = line.split("[=:]");
                levels[level.toInteger()].map.put(new Word(columns[0], columns[1]), Integer.parseInt(columns[2]));
            }
        }
        scanner.close();
    }

    public Word getWeakestWord(Level level, boolean firstWord) {
        if (firstWord) accessedWordNumber = 0;
        else accessedWordNumber++;

        Set<Map.Entry<Word, Integer>> entrySet = levels[level.toInteger()].map.entrySet();
        List<Map.Entry<Word, Integer>> entryList = new ArrayList<Map.Entry<Word, Integer>>(entrySet);

        Map.Entry<Word, Integer> entry = entryList.get(accessedWordNumber % entryList.size());

        return entry.getKey();
    }
}