package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    /** Returns the size of the BoundedQueue. */
    int capacity();

    /** Returns number of items currently in the queue. */
    int fillCount();

    /** Adds item x to the end of the queue. */
    void enqueue(T x);

    /** Deletes and returns item from the front of the queue. */
    T dequeue();

    /** Returns (but not deletes) item from the front of the queue. */
    T peek();

    /** Returns true if the queue contains item x. */
    boolean contains(T x);

    /** Returns true if the queue is empty. */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /** Returns true if the queue is full. */
    default boolean isFull() {
        return fillCount() == capacity();
    }

    /** Becomes iterable. */
    @Override
    Iterator<T> iterator();
}
