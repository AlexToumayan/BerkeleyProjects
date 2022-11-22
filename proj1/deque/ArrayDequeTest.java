package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<Integer>();

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ad = new LinkedListDeque<Integer>();
        assertTrue("A newly initialized adeque should be empty", ad.isEmpty());
        ad.addFirst(0);

        assertFalse("ad1 should now contain 1 item", ad.isEmpty());
        ad = new LinkedListDeque<Integer>(); //Assigns ad equal to a new, clean LinkedListDeque!


    }

    /** Adds an item, removes an item, and ensures that dll is empty afterwards. */
    @Test
    public void addRemoveTest() {
        ad.addFirst(1);
        assertEquals(1,ad.size());
        int removed=ad.removeFirst();
        assertEquals(1, removed);
        assertEquals(new LinkedListDeque<Integer>(), ad);
        assertTrue("ad should now be empty", ad.isEmpty());
        ad = new LinkedListDeque<Integer>();
    }
    /** Make sure that removing from an empty LinkedListDeque does nothing */
    @Test
    public void removeEmptyTest() {
        Deque<Integer> copy=ad;
        ad.removeLast();
        assertEquals(copy, ad);
        ad = new LinkedListDeque<Integer>();
    }
    /** Make sure your LinkedListDeque also works on non-Integer types */
    @Test
    public void multipleParamsTest() {
        Deque<String> stringtest=new LinkedListDeque<>();
        stringtest.addLast("amogus");
        assertEquals(stringtest.get(0), "amogus");
    }
    /** Make sure that removing from an empty LinkedListDeque returns null */
    @Test
    public void emptyNullReturn() {
        ad = new LinkedListDeque<Integer>();
        assertEquals(null, ad.removeFirst());
    }
    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */
    @Test
    public void testgets() {
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        int got=ad.get(1);
        assertEquals(2, got);
        ad = new LinkedListDeque<Integer>();
    }
    @Test
    public void testgets2() {
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        int got=ad.get(1);
        assertEquals(2, got);
        ad = new LinkedListDeque<Integer>();

    }

    @Test
    public void testResize() {
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad.addFirst(1);
        ad=new LinkedListDeque<Integer>();
    }
}
