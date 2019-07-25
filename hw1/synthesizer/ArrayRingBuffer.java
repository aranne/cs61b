package synthesizer;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (this.isFull()) {
            throw new RuntimeException("This Queue is full!");
        }
        rb[last] = x;
        fillCount += 1;
        last = (last + 1) % this.capacity;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (this.isEmpty()) {
            throw new RuntimeException("This Queue is empty!");
        }
        T returnItem = rb[first];
        fillCount -= 1;
        first = (first + 1) % this.capacity;
        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    /**
     * Return true if buffer contains item x.
     */
    @Override
    public boolean contains(T x) {
        int p = first;
        for (int i = 0; i < fillCount; i += 1) {
            if (rb[p].equals(x)) {
                return true;
            }
            p = (p + 1) % capacity;
        }
        return false;
    }

    /**
     * toString method.
     */
    @Override
    public String toString() {
        List<String> sl = new ArrayList<>();
        for (int i = 0; i < fillCount; i += 1) {
            sl.add(rb[i].toString());
        }
        return "[" + String.join(", ", sl) + "]";
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    /**
     * Support iteration.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    /** Provide ArrayRingBuffer iterator. */
    private class ArrayRingBufferIterator implements Iterator<T> {
        int p;
        int output;
        private ArrayRingBufferIterator() {
            p = first;
            output = 0;
        }

        @Override
        public boolean hasNext() {
            return output != fillCount;
        }

        @Override
        public T next() {
            T returnItem = rb[p];
            p = (p + 1) % capacity;
            output += 1;
            return returnItem;
        }
    }
}
