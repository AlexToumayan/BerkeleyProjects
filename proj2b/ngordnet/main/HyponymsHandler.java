package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public HyponymsHandler (WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        Set<String> commonHyponyms = null;
        for (String word : words) {
            Set<String> hyponyms = wn.hyponyms(word, ngm, startYear, endYear, k);
            if (hyponyms.isEmpty()) {
                return "Word: " + word + " not found in WordNet.";
            }
            if (commonHyponyms == null) {
                commonHyponyms = new HashSet<>(hyponyms);
            } else {
                commonHyponyms.retainAll(hyponyms);
            }
        }


        TreeSet<String> sortedHyponyms = new TreeSet<>(commonHyponyms);

        return sortedHyponyms.toString();
    }
}
