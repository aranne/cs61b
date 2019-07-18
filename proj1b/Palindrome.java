public class Palindrome {

    /**
     * Given a String, returns a Deque where the characters appear
     * in the same order as in the String.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    /** Returns true if the given word is a palindrome, false otherwise. */
    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        return helpIsPalindrome(d, 0, d.size() - 1);
    }

    /**
     * A helper function to check palindrome.
     * first points to the first item in deque.
     * last points to the last item in deque.
     */
    private boolean helpIsPalindrome(Deque deque, int first, int last) {
        if (deque.isEmpty()) {
            return true;
        }
        if (first >= last) {
            return true;
        }
        if (deque.get(first) != deque.get(last)) {
            return false;
        }
        return helpIsPalindrome(deque, first + 1, last - 1);
    }
}
