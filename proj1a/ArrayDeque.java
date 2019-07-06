/**
 * Deque means doubled-ended queue, which is a sequence containers with
 * dynamic size that can be expanded or contracted on both ends(front or back).
 * This array based deque is circular, which means
 * nextFirst pointer points at the index that next first item will be put in and
 * nextLast pointer points at the index that next last item will be put in.
 * If nextFirst pointer is at position zero and you addFirst(),
 * nextFirst pointer should look back around to the end of the array. So does nextLast pointer.
 * @param <Generic>
 * @author aranne
 */
public class ArrayDeque<Generic> {
    /** This is a generic array based deque. */
    private Generic[] items;
    private int size;
    private static final int RFactor = 2;
    private int nextFirst;
    private int nextLast;

    /** initiate an empty ArrayDeque. */
    public ArrayDeque() {
        items = (Generic[]) new Object[8]; // The starting size of this deque.
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** Resize the underlying deque to the target capacity. */
    public void resize(int capacity) {
        Generic[] a = (Generic[]) new Object[capacity];
        // In these two cases, we need to copy an array through a circle from nextFirst to items.length to nextLast.
        if (nextLast - nextFirst == 1 || (nextLast <= nextFirst && size != items.length)) {
            // put all items in the middle of the new capacity deque in increasing direction.
            System.arraycopy(items, nextFirst + 1, a, (capacity - size) / 2, items.length - nextFirst - 1);
            System.arraycopy(items, 0, a, ((capacity - size) / 2) + (items.length - nextFirst - 1), nextLast);
        }
        // In this case, we just need to copy an array from nextFirst to nextLast.
        if ((nextLast == 0 && nextFirst == items.length - 1) || (nextFirst < nextLast && size != items.length)) {
            int first = nextFirst;
            int last = nextLast;
            if (nextLast == 0) {   // if deque is full.
                first = -1;
                last = items.length;
            }
            System.arraycopy(items, first + 1, a, (capacity - size) / 2, last - first - 1);
        }
        nextFirst = (capacity - size) / 2 - 1;
        nextLast = (capacity - size) / 2 + size;
        items = a;
    }

    /** Adds an item of type Generic to the front of this deque. */
    public void addFirst(Generic item) {
        if (size == items.length) {
            resize(items.length * RFactor);
        }
        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        size += 1;
    }

    /** Adds an item of type Generic to the back of this deque. */
    public void addLast(Generic item) {
        if (size == items.length) {
            resize(items.length * RFactor);
        }
        items[nextLast] = item;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
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
        // if deque is full but with wrong order OR deque is not full and with wrong order.
        if (nextLast - nextFirst == 1 || (nextLast <= nextFirst && size != items.length)) {
            for (int i = nextFirst + 1; i < items.length; i += 1) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < nextLast; i += 1) {
                System.out.print(items[i]);
                if (i != nextLast - 1) {
                    System.out.print(" ");
                }
            }
        }
        // if deque is full and with right order OR the deque is with right order.
        if ((nextFirst == items.length - 1 && nextLast == 0) || (nextFirst < nextLast && size != items.length)) {
            int first = nextFirst;
            int last = nextLast;
            if (nextLast == 0) {    // if deque is full.
                first = -1;
                last = items.length;
            }
            for (int i = first + 1; i < last; i += 1) {
                System.out.print(items[i]);
                if (i != last - 1) {
                    System.out.print(" ");
                }
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
        Generic firstItem;
        if (nextFirst == items.length - 1) {
            firstItem = items[0];
            items[0] = null;                    // nulling out the deleted item.
            nextFirst = 0;
        } else {
            firstItem = items[nextFirst + 1];
            items[nextFirst + 1] = null;
            nextFirst += 1;
        }
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
        Generic lastItem;
        if (nextLast == 0) {
            lastItem = items[items.length - 1];
            items[items.length - 1] = null;
            nextLast = items.length - 1;
        } else {
            lastItem = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast -= 1;
        }
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
        if (nextFirst + index + 1 < items.length) {
            return items[(nextFirst + 1) + index];
        }
        return items[(index + 1) - (items.length - (nextFirst + 1)) - 1];

    }
}
