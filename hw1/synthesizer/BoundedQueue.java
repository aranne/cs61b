package synthesizer;

public interface BoundedQueue<T> {
    /** Returns the size of the buffer. */
    int capacity();

    /** Returns number of items currently in the buffer. */
    int fillCount();

    /** Adds item x to the end of the queue. */
    void enqueue(T x);

    /** Deletes and returns item from the front of the queue. */
    T dequeue();

    /** Returns (but not deletes) item from the front of the queue. */
    T peek();

    /** Returns true if the buffer is empty. */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /** Returns true if the buffer is full. */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
