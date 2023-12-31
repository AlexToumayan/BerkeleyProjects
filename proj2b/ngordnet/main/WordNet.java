package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private final Map<String, Set<Integer>> wordToIdsMap = new HashMap<>();
    private final Map<Integer, Set<String>> idToWordsMap = new HashMap<>();
    private final Graph wordNetGraph;

    public WordNet(String synsetsFile, String hyponymsFile) {

        parseSynsets(synsetsFile);


        wordNetGraph = new Graph(idToWordsMap.size());

        parseHyponyms(hyponymsFile);
    }

    private void parseSynsets(String synsetsFile) {
        In in = new In(synsetsFile);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String[] words = fields[1].split(" ");
            idToWordsMap.put(id, new HashSet<>(Arrays.asList(words)));
            for (String word : words) {
                Set<Integer> ids = wordToIdsMap.getOrDefault(word, new HashSet<>());
                ids.add(id);
                wordToIdsMap.put(word, ids);
            }
        }
    }

    private void parseHyponyms(String hyponymsFile) {
        In in = new In(hyponymsFile);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int hyponymId = Integer.parseInt(fields[i]);
                wordNetGraph.addEdge(id, hyponymId);
            }
        }
    }
    public Set<String> hyponyms(String word) {
        Set<Integer> ids = wordToIdsMap.get(word);
        if (ids == null) {

            return new HashSet<>();
        }
        Set<Integer> reachableIds = wordNetGraph.reachable(ids);
        Set<String> hyponyms = new HashSet<>();
        for (Integer id : reachableIds) {
            hyponyms.addAll(idToWordsMap.get(id));
        }
        return hyponyms;
    }

}