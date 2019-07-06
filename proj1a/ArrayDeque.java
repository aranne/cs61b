/**
 * Deque means doubled-ended queue, which is a sequence containers with
 * dynamic size that can be expanded or contracted on both ends(front or back).
 * @param <Generic>
 * @author aranne
 */
public class ArrayDeque<Generic> {
    /** This is a generic array based deque. */
    private Generic[] items;
    private int size;
    private static final int RFactor = 2;

    /** initiate an empty ArrayDeque. */
    public ArrayDeque() {
        items = (Generic[]) new Object[8]; // The starting size of this deque.
        size = 0;
    }

    /** Resize the underlying deque to the target capacity. */
    public void resize(int capacity) {
        Generic[] a = (Generic[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Adds an item of type Generic to the front of this deque. */
    public void addFirst(Generic item) {
        if (size == items.length) {
            resize(items.length * RFactor);
        }
        Generic[] a = (Generic[]) new Object[items.length];
        a[0] = item;
        System.arraycopy(items, 0, a, 1, size);
        size += 1;
    }

    /** Adds an item of type Generic to the back of this deque. */
    public void addLast(Generic item) {
        if (size == items.length) {
            resize(items.length * RFactor);
        }
        items[size] = item;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in this deque. */
    public int size() {
        return size;
    }

    /** Prints the items in this deque from first to last, separated by a space. */
    public void printDeque() {
        if (size == 0) {
            System.out.println("This is an empty deque.");
        }
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[i]);
            if (i != size - 1) {
                System.out.print(" ");
            }
        }
    }

    /** Removes and returns the item at the front of this deque.
     *  If no such item exists, returns null.
     */
    public Generic removeFirst() {
        if (size == 0) {
            return null;
        }
        Generic firstItem = items[0];
        Generic[] a = (Generic[]) new Object[items.length];
        System.arraycopy(items, 1, a, 0, size - 1);
        items = a;
        size -= 1;
        if ((double)size / items.length <= 0.25 && items.length >= 16) {
            resize(items.length / RFactor);
        }
        return firstItem;
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     */
    public Generic removeLast() {
        if (size == 0) {
            return null;
        }
        Generic lastItem = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        if ((double)size / items.length <= 0.25 && items.length >= 16) {
            resize(items.length / RFactor);
        }
        return lastItem;
    }

    /** Gets the item at the given index. If no such item exists, returns null. */
    public Generic get(int index) {
        if (index >= size) {
            return null;
        }
        return items[index];
    }
}
