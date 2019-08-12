package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     * The flaw is: because of 256 base is too large for hashCode(),
     * The 3th power of 256 is 16777216, when it increase at 4th digit,
     * the 4th power of 256 will be 0. It's overflow!!!
     * So we need to change 256 to 31 in hashCode().
     * The powers of 256 in Java are:
     * 0th power: 1
     * 1th power: 256
     * 2th power: 65536
     * 3th power: 16777216
     * 4th power: 0
     * 5th power: 0
     * 6th power: 0
     * 7th power: 0
     * 8th power: 0
     * 9th power: 0
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            ArrayList<Integer> params = new ArrayList<>(9);
            for (int j = 0; j < 5; j += 1) {
                params.add(StdRandom.uniform(0, 255));
            }
            // Guarantee the last 4th digits are all the same.
            for (int j = 0; j < 4; j += 1) {
                params.add(1);
            }
            deadlyList.add(new ComplexOomage(params));
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
