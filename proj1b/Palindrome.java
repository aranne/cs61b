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
        if (d.isEmpty()) {
            return true;
        } else {
            int first = 0;
            int last = d.size() - 1;
            while (first <= last) {
                if (d.get(first) != d.get(last)) {
                    return false;
                }
                first += 1;
                last -= 1;
            }
            return true;
        }
    }
}
