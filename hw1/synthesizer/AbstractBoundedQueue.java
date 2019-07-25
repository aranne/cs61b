package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    /** Returns the size of the queue. */
    @Override
    public int capacity() {
        return capacity;
    }

    /** Returns number of items currently in the queue. */
    @Override
    public int fillCount() {
        return fillCount;
    }
}
