package ngordnet.ngrams;

import java.util.*;

public class TimeSeries extends TreeMap<Integer, Double> {

    public TimeSeries() {
        super();
    }

    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                put(year, ts.get(year));
            }
        }
    }

    public List<Integer> years() {
        return new ArrayList<>(keySet());
    }

    public List<Double> data() {
        return new ArrayList<>(values());
    }

    public TimeSeries plus(TimeSeries ts) {
        TimeSeries sum = new TimeSeries();

        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            int year = entry.getKey();
            double value = entry.getValue();
            if (ts.containsKey(year)) {
                value += ts.get(year);
            }
            sum.put(year, value);
        }

        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            int year = entry.getKey();
            if (!this.containsKey(year)) {
                sum.put(year, entry.getValue());
            }
        }

        return sum;
    }

    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries quotient = new TimeSeries();
        for (Integer year : keySet()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException("TS is missing a year that exists in this TimeSeries.");
            }
            double value1 = get(year);
            double value2 = ts.get(year);
            quotient.put(year, value1 / value2);
        }
        return quotient;
    }
}
