public class TraversalPractice {
  private static class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;

    public Node(T value, Node<T> left, Node<T> right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }
  }

  /**
   * Prints the odd values of the nodes in a tree.
   * Each value is printed on a separate line.
   * The nodes are traversed post-order.
   * 
   * @param node The root of the tree to print
   */
  private static void printOddNodes(Node<Integer> node) {

  }

  /**
   * Prints the values of the nodes that have exactly one child in a tree.
   * Each value is printed on a separate line.
   * The nodes are traversed pre-order
   * 
   * @param <T> The type of value the nodes hold
   * @param node The root of the tree to print
   */
  private static <T> void printNodesWithOneChild(Node<T> node) {

  }

  /**
   * Returns the sum of the values of all nodes in a tree.
   *  
   * @param node The root of the tree
   * @return the sum 
   */
  private static int treeSum(Node<Integer> node) {
    return 0;
  }

  /**
   * Returns the maximum value stored in a tree.
   * 
   * @param node The root of the tree
   * @return the max value
   */
  private static int maxVal(Node<Integer> node) {
    return 0;
  }

  public static void main(String[] args) {

  }
}