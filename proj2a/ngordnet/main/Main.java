package ngordnet.main;

import ngordnet.hugbrowsermagic.HugNgordnetServer;
import ngordnet.ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        HugNgordnetServer hns = new HugNgordnetServer();

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap map = new NGramMap(wordFile, countFile);

        hns.startUp();
        hns.register("history", new HistoryHandler(map));
        hns.register("historytext", new HistoryTextHandler(map));
    }
}
