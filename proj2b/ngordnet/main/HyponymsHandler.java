package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        Set<String> allHyponyms = new HashSet<>();

        if (words.isEmpty()) {
            return allHyponyms.toString();
        }

        allHyponyms.addAll(wn.hyponyms(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
            allHyponyms.retainAll(wn.hyponyms(words.get(i)));
        }

        if (q.k() == 0) {
            return new TreeSet<>(allHyponyms).toString();
        }

        int startYear = q.startYear();
        int endYear = q.endYear();
        Map<String, Integer> hyponymCounts = new HashMap<>();

        for (String hyponym : allHyponyms) {
            TimeSeries wordHistory = ngm.countHistory(hyponym, startYear, endYear);
            int count = wordHistory.values().stream()
                    .mapToInt(Number::intValue)
                    .sum();

            if (count > 0) {
                hyponymCounts.put(hyponym, count);
            }
        }

        Comparator<Map.Entry<String, Integer>> valueThenKeyComparator =
                Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey());

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>(valueThenKeyComparator);

        pq.addAll(hyponymCounts.entrySet());

        Set<String> resultHyponyms = new TreeSet<>();
        for (int i = 0; i < q.k() && !pq.isEmpty(); i++) {
            resultHyponyms.add(pq.poll().getKey());
        }

        return resultHyponyms.toString();
    }
}
