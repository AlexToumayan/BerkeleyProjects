package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                put(year, ts.get(year));
            }
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        return new ArrayList<>(keySet());
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        return new ArrayList<>(values());
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
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

    /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
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
