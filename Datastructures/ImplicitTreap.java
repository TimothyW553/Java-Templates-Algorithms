/*
 * An implicit treap is a treap that uses array indexes as its key and the value at that index as its value.
 * As a result, it can perform insertion and deletion in O(log N)
 *
 * Time complexity:
 *  - Remove: O(log N)
 *  - Insertion: O(log N)
 *  - Search: O(log N)
 *  - Access: O(log N)
 */

package codebook.datastructures;

class ImplicitTreap {
  // root of the tree
  Node root = null;

  // object representing the nodes of the tree
  class Node {
    Integer size;
    Integer value;
    Double priority;
    Node left, right;

    Node (int value) {
      this.value = value;
      this.size = 1;
      priority = Math.random();
    }
  }

  // object representing a pair of nodes of the tree
  class NodePair {
    Node left, right;

    NodePair (Node left, Node right) {
      this.left = left;
      this.right = right;
    }
  }

  public void remove (Integer k) {
    NodePair res = split(root, k);
    root = merge(res.left.left, merge(res.left.right, res.right));
  }

  public Integer get (Integer k) {
    if (k > getSize(root) || k <= 0)
      throw new IllegalArgumentException();
    return get(root, k);
  }

  // auxiliary function for get
  private Integer get (Node n, Integer k) {
    if (n == null)
      return null;
    Integer key = getSize(n.left) + 1;
    int cmp = k.compareTo(key);
    if (cmp < 0)
      return get(n.left, k);
    else if (cmp > 0)
      return get(n.right, k - getSize(n.left) - 1);
    return n.value;
  }

  public void pushBack (Integer val) {
    root = merge(root, new Node(val));
  }

  public void add (Integer key, Integer val) {
    NodePair n = split(root, key);
    root = merge(n.left, merge(new Node(val), n.right));
  }

  public void modify (Integer key, Integer val) {
    root = modify(root, key, val);
  }

  // auxiliary method for modify
  private Node modify (Node n, Integer key, Integer val) {
    Integer nKey = getSize(n.left) + 1;
    if (nKey == key)
      n.value = val;
    else if (nKey > key)
      n.left = modify(n.left, key, val);
    else
      n.right = modify(n.right, key, val - getSize(n.left) - 1);
    return n;
  }

  public void traverse () {
    traverse(root);
  }

  // auxiliary method for traverse
  private void traverse (Node n) {
    if (n == null)
      return;
    traverse(n.left);
    System.out.println(n.value + " " + n.priority);
    traverse(n.right);
  }

  public void clear () {
    this.root = null;
  }

  private void resetSize (Node n) {
    if (n != null)
      n.size = getSize(n.left) + getSize(n.right) + 1;
  }

  private int getSize (Node n) {
    return n == null ? 0 : n.size;
  }

  // auxiliary function to merge
  private Node merge (Node t1, Node t2) {
    if (t1 == null)
      return t2;
    else if (t2 == null)
      return t1;

    Node newRoot = null;
    if (t1.priority > t2.priority) {
      t1.right = merge(t1.right, t2);
      newRoot = t1;
    } else {
      t2.left = merge(t1, t2.left);
      newRoot = t2;
    }
    resetSize(newRoot);
    return newRoot;
  }

  // auxiliary function to split
  private NodePair split (Node n, Integer key) {
    NodePair res = new NodePair(null, null);
    if (n == null)
      return res;
    Integer nKey = getSize(n.left) + 1;
    if (nKey > key) {
      res = split(n.left, key);
      n.left = res.right;
      res.right = n;
      resetSize(res.left);
      resetSize(res.right);
      return res;
    } else {
      res = split(n.right, key - getSize(n.left) - 1);
      n.right = res.left;
      res.left = n;
      resetSize(res.left);
      resetSize(res.right);
      return res;
    }
  }

  public static void main (String[] args) {
    java.util.ArrayList<Integer> list = new java.util.ArrayList<Integer>();
    ImplicitTreap treap = new ImplicitTreap();
    for (int i = 0; i < 100000; i++) {
      int rnd = (int)(Math.random() * (i + 1));
      int val = (int)(Math.random() * 10000000);

      list.add(rnd, val);
      treap.add(rnd, val);
    }

    for (int i = 0; i < 100000; i++) {
      assert list.get(i).equals(treap.get(i + 1));
    }

    treap.clear();
    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      int rnd = (int)(Math.random() * (i + 1));
      int val = (int)(Math.random() * 10000000);

      treap.add(rnd, val);
    }

    for (int i = 999999; i >= 0; i--) {
      int rnd = (int)(Math.random() * (i + 1) + 1);
      int prev = treap.get(rnd);
      treap.remove(rnd);
      if (rnd < treap.getSize(treap.root))
        assert prev != treap.get(rnd);
    }

    System.out.println(System.currentTimeMillis() - start);
  }
}
