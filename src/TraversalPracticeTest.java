import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TraversalPracticeTest {

    // We will capture System.out in these tests to verify output of print methods
    // You do not need to memorize how to do this, but look at it with curiosity!
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Before each test set up a fake system out to capture output
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    // After each test go back to the real system out
    @AfterEach
    void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);
        outContent.reset();
    }

    /*
     * =========================================================================
     *  1) TESTS FOR printNodesWithOneChild(Node<T> node)
     *     - Must traverse PRE-ORDER
     *     - Print nodes that have EXACTLY one child
     *     - Generic method, so we will also test with Strings
     * =========================================================================
     */

    @Test
    void testPrintNodesWithOneChild_NullRoot() {
        // Should print nothing
        TraversalPractice.printNodesWithOneChild(null);
        assertTrue(outContent.toString().isEmpty(), 
            "Expected no output when tree is null");
    }

    @Test
    void testPrintNodesWithOneChild_SingleNode() {
        // Single node has no children, so it does NOT have exactly one child
        Node<Integer> root = new Node<>(50, null, null);
        TraversalPractice.printNodesWithOneChild(root);

        // No output expected
        assertTrue(outContent.toString().isEmpty(),
            "A single node has zero children, not one child. No output expected.");
    }

    @Test
    void testPrintNodesWithOneChild_MultipleLevels() {
        /*
                 10
                /  \
               4   15
              / \    \
             2  7    20
                 \
                 9
        */
        Node<Integer> root = new Node<>(10,
                new Node<>(4,
                        new Node<>(2, null, null),
                        new Node<>(7, null, new Node<>(9, null, null))
                ),
                new Node<>(15, null, new Node<>(20, null, null))
        );

        TraversalPractice.printNodesWithOneChild(root);

        // Capture lines, then compare
        String[] lines = outContent.toString().trim().split("\\r?\\n");
        assertEquals(2, lines.length, "Expected exactly two lines of output");
        assertEquals("7", lines[0], "First node with one child (pre-order) should be 7");
        assertEquals("15", lines[1], "Second node with one child should be 15");
    }

    @Test
    void testPrintNodesWithOneChild_NoOneChildNodes() {
        /*
               99
              /  \
            50   120
           /  \  /  \
         25  75 110 130
        */
        Node<Integer> root = new Node<>(99,
                new Node<>(50,
                        new Node<>(25, null, null),
                        new Node<>(75, null, null)
                ),
                new Node<>(120,
                        new Node<>(110, null, null),
                        new Node<>(130, null, null)
                )
        );

        TraversalPractice.printNodesWithOneChild(root);
        // Expect nothing printed
        assertTrue(outContent.toString().isEmpty(), 
            "No nodes have exactly one child, so no output expected");
    }

    @Test
    void testPrintNodesWithOneChild_Strings() {
        /*
              "zebra"
              /     \
       "apple"      "mango"
          \
          "dog"

         Pre-order: zebra -> apple -> dog -> mango

         - "zebra" has 2 children -> not printed
         - "apple" has 1 child (right = "dog") -> PRINT "apple"
         - "dog" has 0 children -> not printed
         - "mango" has 0 children -> not printed

         Expected output:
           apple
        */
        Node<String> root = new Node<>("zebra",
                new Node<>("apple", null, new Node<>("dog", null, null)),
                new Node<>("mango", null, null)
        );

        TraversalPractice.printNodesWithOneChild(root);

        String printed = outContent.toString().trim();
        assertEquals("apple", printed, 
            "Expected only 'apple' to be printed");
    }


    /*
     * =========================================================================
     *  2) TESTS FOR maxVal(Node<Integer> node)
     *     - Returns the maximum value in the tree
     *     - Returns 0 if the tree is null
     *     - All values are assumed positive
     * =========================================================================
     */

    @Test
    void testMaxVal_NullRoot() {
        // Expect 0
        assertEquals(0, TraversalPractice.maxVal(null),
                "Expected 0 for null root");
    }

    @Test
    void testMaxVal_SingleNode() {
        Node<Integer> root = new Node<>(42, null, null);
        assertEquals(42, TraversalPractice.maxVal(root),
                "Max of single node 42 should be 42");
    }

    @Test
    void testMaxVal_MultipleLevels() {
        /*
               10
              /   \
             3     15
            / \   /  \
           2  7  11  20
                / 
               18
        */
        Node<Integer> root = new Node<>(10,
                new Node<>(3,
                        new Node<>(2, null, null),
                        new Node<>(7, null, null)
                ),
                new Node<>(15,
                        new Node<>(11,
                                new Node<>(18, null, null),
                                null
                        ),
                        new Node<>(20, null, null)
                )
        );

        assertEquals(20, TraversalPractice.maxVal(root),
                "Max should be 20 in this tree");
    }

    @Test
    void testMaxVal_AnotherTree() {
        /*
                 42
                /  
              17 
             /  \
            13  25
               / \
              19  32
        */
        Node<Integer> root = new Node<>(42,
                new Node<>(17,
                    new Node<>(13, null, null),
                    new Node<>(25, 
                        new Node<>(19, null, null),
                        new Node<>(32, null, null)
                    )
                ), 
                null
        );

        assertEquals(42, TraversalPractice.maxVal(root),
                "Max should be 42 in this tree");
    }


    /*
     * =========================================================================
     *  3) TESTS FOR printOddNodes(Node<Integer> node)
     *     - Print odd-valued nodes in POST-ORDER
     *     - Each value on its own line
     *     - If null root, prints nothing
     * =========================================================================
     */

    @Test
    void testPrintOddNodes_NullRoot() {
        TraversalPractice.printOddNodes(null);
        // Expect nothing printed
        assertTrue(outContent.toString().isEmpty(), 
            "No output if tree is null");
    }

    @Test
    void testPrintOddNodes_SingleNode_Odd() {
        // Single node = 7 -> it's odd -> print "7"
        Node<Integer> root = new Node<>(7, null, null);
        TraversalPractice.printOddNodes(root);

        String printed = outContent.toString().trim();
        assertEquals("7", printed, 
            "Expected single odd node to be printed");
    }

    @Test
    void testPrintOddNodes_SingleNode_Even() {
        // Single node = 12 -> it's even -> print nothing
        Node<Integer> root = new Node<>(12, null, null);
        TraversalPractice.printOddNodes(root);
        assertTrue(outContent.toString().isEmpty(), 
            "Even single node should not be printed");
    }

    @Test
    void testPrintOddNodes_MultipleLevels() {
        /*
                 10
                /  \
               5   16
              / \    \
             3  8    15
                /    /  \
               7   11   19
        */
        Node<Integer> root = new Node<>(10,
                new Node<>(5,
                        new Node<>(3, null, null),
                        new Node<>(8, 
                                new Node<>(7, null, null), 
                                null
                        )
                ),
                new Node<>(16, null, 
                        new Node<>(15,
                                new Node<>(11, null, null),
                                new Node<>(19, null, null)
                        )
                )
        );

        TraversalPractice.printOddNodes(root);

        String[] lines = outContent.toString().trim().split("\\r?\\n");
        String[] expected = { "3", "7", "5", "11", "19", "15" };
        assertEquals(expected.length, lines.length, 
            "Expected 6 odd values in post-order");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], lines[i], 
                "Mismatch in odd node at index " + i);
        }
    }

    @Test
    void testPrintOddNodes_NoOddValues() {
        /*
                2
               / \
              4   6
                 / \
                8  10
        */
        Node<Integer> root = new Node<>(2,
                new Node<>(4, null, null),
                new Node<>(6,
                        new Node<>(8, null, null),
                        new Node<>(10, null, null)
                )
        );

        TraversalPractice.printOddNodes(root);
        assertTrue(outContent.toString().isEmpty(),
            "All even values -> no odd nodes -> no output");
    }


    /*
     * =========================================================================
     *  4) TESTS FOR treeSum(Node<Integer> node)
     *     - Return sum of values in tree
     *     - Return 0 if node is null
     * =========================================================================
     */

    @Test
    void testTreeSum_NullRoot() {
        assertEquals(0, TraversalPractice.treeSum(null),
                "Expected sum of 0 for null tree");
    }

    @Test
    void testTreeSum_SingleNode() {
        Node<Integer> root = new Node<>(37, null, null);
        assertEquals(37, TraversalPractice.treeSum(root),
                "Sum of single node 37 should be 37");
    }

    @Test
    void testTreeSum_MultipleLevels() {
        /*
                10
               /  \
              5    20
             / \
            2   7
        */
        Node<Integer> root = new Node<>(10,
                new Node<>(5,
                        new Node<>(2, null, null),
                        new Node<>(7, null, null)
                ),
                new Node<>(20, null, null)
        );

        assertEquals(44, TraversalPractice.treeSum(root),
                "Sum should be 44");
    }

    @Test
    void testTreeSum_BiggerTree() {
        /*
              3
             / \
            12  1
               / \
              5  10
             /
            4
        */
        Node<Integer> root = new Node<>(3,
                new Node<>(12, null, null),
                new Node<>(1,
                        new Node<>(5, new Node<>(4, null, null), null),
                        new Node<>(10, null, null)
                )
        );

        assertEquals(35, TraversalPractice.treeSum(root),
                "Sum should be 35");
    }

    /*
     * =========================================================================
     *  5) TESTS FOR numLevels(Node<T> node)
     *     - Return # of levels in tree
     *     - Return 0 if node is null
     *     - Return 1 if there's only a root with no children
     *     - Also test with Strings to show it's generic
     * =========================================================================
     */

    @Test
    void testNumLevels_NullRoot() {
        assertEquals(0, TraversalPractice.numLevels(null),
                "Expected 0 levels for null root");
    }

    @Test
    void testNumLevels_SingleNode() {
        Node<Integer> root = new Node<>(99, null, null);
        assertEquals(1, TraversalPractice.numLevels(root),
                "Single node should have 1 level");
    }

    @Test
    void testNumLevels_ChainTree() {
        /*
            10
            /
           5
          /
         2
        */
        Node<Integer> root = new Node<>(10,
                new Node<>(5, new Node<>(2, null, null), null),
                null
        );
        assertEquals(3, TraversalPractice.numLevels(root),
                "Expected 3 levels in a chain of height 3");
    }

    @Test
    void testNumLevels_BalancedTree() {
        /*
                15
               /  \
              7    30
             / \   / \
            3  10 25  40
        */
        Node<Integer> root = new Node<>(15,
                new Node<>(7,
                        new Node<>(3, null, null),
                        new Node<>(10, null, null)
                ),
                new Node<>(30,
                        new Node<>(25, null, null),
                        new Node<>(40, null, null)
                )
        );
        assertEquals(3, TraversalPractice.numLevels(root),
                "Expected 3 levels in this balanced example");
    }

    @Test
    void testNumLevels_Strings() {
        /*
              "root"
               /   \
         "left"   "right"
                  /
            "sub-left"
        */
        Node<String> root = new Node<>("root",
                new Node<>("left", null, null),
                new Node<>("right",
                        new Node<>("sub-left", null, null),
                        null
                )
        );
        assertEquals(3, TraversalPractice.numLevels(root),
                "String tree has 3 levels");
    }
}
