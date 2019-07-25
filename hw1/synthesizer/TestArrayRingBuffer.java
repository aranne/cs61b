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

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
