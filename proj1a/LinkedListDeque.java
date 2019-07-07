/**
 * Deque means doubled-ended queue, which is a sequence containers with
 * dynamic size that can be expanded or contracted on both ends(front or back).
 * @param <T>
 * @author aranne
 */
public class LinkedListDeque<T> {
    /**
     * This is a generic linked list class.
     */
    private class StuffNode {
        private T item;
        private StuffNode next;
        private StuffNode prev;

        public StuffNode(T i, StuffNode n) {
            item = i;
            next = n;
        }
    }

    private StuffNode sentinel;
    private int size;

    /**
     * Instantiates: creates an empty deque.
     */
    public LinkedListDeque() {
        sentinel = new StuffNode(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Adds a generic type item to the front of this deque.
     */
    public void addFirst(T item) {
        StuffNode p = sentinel.next;
        sentinel.next = new StuffNode(item, p);
        p.prev = sentinel.next;
        sentinel.next.prev = sentinel;
        size += 1;
    }

    /**
     * Adds a generic type item to the back of this deque.
     */
    public void addLast(T item) {
        StuffNode p = sentinel.prev;
        sentinel.prev = new StuffNode(item, sentinel);
        sentinel.prev.prev = p;
        p.next = sentinel.prev;
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return sentinel.next.item == null;
    }

    /**
     * Returns the size of this deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in this deque from first to last, separated by space.
     */
    public void printDeque() {
        if (sentinel.next.item == null) {
            System.out.println("This is an empty deque");
        }
        StuffNode p = sentinel.next;
        while (p.item != null) {
            System.out.print(p.item);
            if (p.next.item != null) {
                System.out.print(" ");
            } else {
                System.out.println();
            }
            p = p.next;
        }
    }

    /**
     * Removes and returns the first item in this deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        StuffNode p = sentinel.next;
        sentinel.next = p.next;
        p.next.prev = sentinel;
        size -= 1;
        return p.item;
    }

    /**
     * Removes and returns the last item in this deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        StuffNode p = sentinel.prev;
        p.prev.next = sentinel;
        sentinel.prev = p.prev;
        size -= 1;
        return p.item;
    }

    /**
     * Returns the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     */
    public T get(int index) {
        StuffNode p = sentinel.next;
        while (index != 0) {
            if (p.item == null) {
                return null;
            }
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /**
     * Implements get() using recursion.
     */
    public T getRecursive(int index) {
        return getRecursionHelper(sentinel.next, index);
    }

    /**
     * A helper method of getRecursion().
     */
    private T getRecursionHelper(StuffNode s, int index) {
        if (index == 0) {
            return s.item;
        } else if (s.item == null) {
            return null;
        }
        return getRecursionHelper(s.next, index - 1);
    }
}


