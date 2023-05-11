package ngordnet.ngrams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NGramMap {
    private Map<String, TimeSeries> wordHistories;
    private TimeSeries totalCountHistory;

    public NGramMap(String wordsFilename, String countsFilename) {
        wordHistories = new HashMap<>();

        try {
            BufferedReader wordsReader = new BufferedReader(new FileReader(wordsFilename));
            String line;
            while ((line = wordsReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 3) {
                    continue; // Skip lines with fewer than 3 tokens
                }
                String word = tokens[0];
                int year = Integer.parseInt(tokens[1]);
                double count = Double.parseDouble(tokens[2]);

                if (!wordHistories.containsKey(word)) {
                    wordHistories.put(word, new TimeSeries());
                }
                wordHistories.get(word).put(year, count);
            }
            wordsReader.close();

            totalCountHistory = new TimeSeries();
            BufferedReader countsReader = new BufferedReader(new FileReader(countsFilename));
            while ((line = countsReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 2) {
                    continue; // Skip lines with fewer than 2 tokens
                }
                int year = Integer.parseInt(tokens[0]);
                double totalCount = Double.parseDouble(tokens[1]);
                totalCountHistory.put(year, totalCount);
            }
            countsReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public TimeSeries countHistory(String word) {
        TimeSeries wordHistory = wordHistories.get(word);
        if (wordHistory != null) {
            return new TimeSeries(wordHistory, wordHistory.firstKey(), wordHistory.lastKey());
        }
        return null;
    }

    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries wordHistory = wordHistories.get(word);
        if (wordHistory != null) {
            return new TimeSeries(wordHistory, startYear, endYear);
        }
        return null;
    }

    public TimeSeries totalCountHistory() {
        int startYear = totalCountHistory.firstKey();
        int endYear = totalCountHistory.lastKey();
        return new TimeSeries(totalCountHistory, startYear, endYear);
    }


    public TimeSeries weightHistory(String word) {
        return wordHistories.get(word).dividedBy(totalCountHistory);
    }

    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordHistory = countHistory(word, startYear, endYear);
        TimeSeries totalHistory = totalCountHistory(startYear, endYear);
        return wordHistory.dividedBy(totalHistory);
    }

    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            TimeSeries wordWeightHistory = weightHistory(word);
            sum = sum.plus(wordWeightHistory);
        }
        return sum;
    }

    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            TimeSeries wordWeightHistory = weightHistory(word, startYear, endYear);
            sum = sum.plus(wordWeightHistory);
        }
        return sum;
    }

    private TimeSeries totalCountHistory(int startYear, int endYear) {
        return new TimeSeries(totalCountHistory, startYear, endYear);
    }
}
