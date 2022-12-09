package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. Please do not import java.util.Deque here!*/

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        lld = new LinkedListDeque<Integer>();
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
        lld.addFirst(0);

        assertFalse("lld1 should now contain 1 item", lld.isEmpty());
        lld = new LinkedListDeque<Integer>(); //Assigns lld equal to a new, clean LinkedListDeque!


    }

    /** Adds an item, removes an item, and ensures that dll is empty afterwards. */
    @Test
    public void addRemoveTest() {
        lld.addFirst(1);
        assertEquals(1,lld.size());
        int removed=lld.removeFirst();
        assertEquals(1, removed);
        assertEquals(new LinkedListDeque<Integer>(), lld);
        assertTrue("lld should now be empty", lld.isEmpty());
        lld = new LinkedListDeque<Integer>();
    }
    /** Make sure that removing from an empty LinkedListDeque does nothing */
    @Test
    public void removeEmptyTest() {
        Deque<Integer> copy=lld;
        lld.removeLast();
        assertEquals(copy, lld);
        lld = new LinkedListDeque<Integer>();
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
        lld = new LinkedListDeque<Integer>();
        assertEquals(null, lld.removeFirst());
    }
    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */
    @Test
    public void testgets() {
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        int got=lld.get(1);
        assertEquals(2, got);
        lld = new LinkedListDeque<Integer>();

    }
    @Test
    public void testgets2() {
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        int got=lld.get(1);
        assertEquals(2, got);
        lld = new LinkedListDeque<Integer>();

    }

    @Test
    public void testremove(){
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        int removed=lld.removeLast();
        assertEquals(3, removed);
        assertNull(lld.get(2));
        lld.addFirst(0);
        lld.addLast(3);
        assertTrue(lld.get(3)==3);

    }
}
