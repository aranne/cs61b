package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(3);
        arb.enqueue("sting");
        arb.dequeue();
        arb.enqueue("ss");
        arb.enqueue("gg");
        assertEquals("ss", arb.dequeue());
        arb.enqueue("aa");
        arb.enqueue("ff");
        assertEquals("gg", arb.peek());
        assertEquals(3, arb.fillCount());

    }

    @Test
    public void testToString() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(arb.toString(), "[1, 2, 3]");
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(5);
        arb.enqueue("xx");
        arb.enqueue("ss");
        arb.enqueue("gg");
        arb.enqueue("aa");
        arb.enqueue("ff");
        StringBuilder str = new StringBuilder();
        for (String s : arb) {
            str.append(s);
        }
        assertEquals(str.toString(), "xxssggaaff");
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
