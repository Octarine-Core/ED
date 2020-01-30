package Trees;

public class LinkedBinarySearchTree<T extends Comparable<? super T>> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T>{
    @Override
    public void addElement(T element) {
        root = insert(root, element);
        count++;
    }

    private LinkedBinaryNode<T> insert(LinkedBinaryNode<T> node, T key) {

        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return (new LinkedBinaryNode<T>(key));

        if (key.compareTo(node.getElement()) < 0)
            node.setLeft(insert(node.getLeft(), key));
        else if (key.compareTo( node.getElement()) > 0)
            node.setRight(insert(node.getRight(), key));
        else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.setHeight( 1 + max(height(node.getLeft()),
                height(node.getRight())));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && key.compareTo(node.getLeft().getElement()) < 0)
            return rightRotation(node);

        // Right Right Case
        if (balance < -1 && key.compareTo(node.getRight().getElement()) > 0)
            return leftRotation(node);

        // Left Right Case
        if (balance > 1 && key.compareTo(node.getLeft().getElement()) > 0) {
            node.setLeft(leftRotation(node.getLeft()));
            return rightRotation(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo( node.getRight().getElement()) < 0) {
            node.setRight(rightRotation(node.getRight()));
            return leftRotation(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }


    @Override
    public T removeElement(T targetElement) {
        return null;
    }
    @Override
    public void removeAllOccurrences(T targetElement) {

    }
    @Override
    public T removeMin() {
        LinkedBinaryNode<T> current = root;
        LinkedBinaryNode<T> parent = new LinkedBinaryNode<>(null);
        T element;
        while (true){
            LinkedBinaryNode<T> leftChild = current.getLeft();
            if(leftChild != null){
                parent = current;
                current = leftChild;
            }
            else {
                element = current.getElement();
                parent.setLeft(null);
                return element;
            }
        }
    }
    private LinkedBinaryNode<T> minValueNode(LinkedBinaryNode<T> node)
    {
        LinkedBinaryNode<T> current = node;

        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null)
            current = current.getLeft();

        return current;
    }

    private LinkedBinaryNode<T> deleteNode(LinkedBinaryNode<T> root, T key)
    {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key.compareTo( root.getElement()) < 0)
            root.setLeft( deleteNode(root.getLeft(), key));

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key.compareTo( root.getElement()) > 0)
            root.setRight(deleteNode(root.getRight(), key));

            // if key is same as root's key, then this is the node
            // to be deleted
        else
        {

            // node with only one child or no child
            if ((root.getLeft() == null) || (root.getRight() == null))
            {
                LinkedBinaryNode<T> temp = null;
                if (temp == root.getLeft())
                    temp = root.getRight();
                else
                    temp = root.getLeft();

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            }
            else
            {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                LinkedBinaryNode<T> temp = minValueNode(root.getRight());

                // Copy the inorder successor's data to this node
                root.setElement(temp.getElement());

                // Delete the inorder successor
                root.setRight(deleteNode(root.getRight(), temp.getElement()));
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.setHeight( max (height(root.getLeft()), height(root.getRight())) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotation(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0)
        {
            root.setLeft( leftRotation(root.getLeft()));
            return rightRotation(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotation(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0)
        {
            root.setRight(rightRotation(root.getRight()));
            return leftRotation(root);
        }

        return root;
    }
    @Override
    public T removeMax() {
        LinkedBinaryNode<T> current = root;
        LinkedBinaryNode<T> parent = new LinkedBinaryNode<>(null);
        T element;
        while (true){
            LinkedBinaryNode<T> rightChild = current.getRight();
            if(rightChild != null){
                parent = current;
                current = rightChild;
            }
            else {
                element = current.getElement();
                parent.setLeft(null);
                return element;
            }
        }
    }
    @Override
    public T findMin() {
        LinkedBinaryNode<T> current = root;
        while (true){
            LinkedBinaryNode<T> leftChild = current.getLeft();
            if(leftChild != null){
                current = leftChild;
            }
            else {
                return current.getElement();
            }
        }

    }
    @Override
    public T findMax() {
        LinkedBinaryNode<T> current = root;
        while (true){
            LinkedBinaryNode<T> rightChild = current.getRight();
            if(rightChild != null){
                current = rightChild;
            }
            else {
                return current.getElement();
            }
        }
    }

    private static int max(int a, int b){
        return (a > b) ? a : b;
    }

    private int getBalance(LinkedBinaryNode node){
        if (node==null){
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
    }

    public static int height(LinkedBinaryNode node) {
        if (node == null)
            return 0;
        return node.getHeight();
    }

    private LinkedBinaryNode<T> leftRotation(LinkedBinaryNode<T> X){
        LinkedBinaryNode Y = X.getRight();
        LinkedBinaryNode pivotNode = Y.getLeft();

        //Rotation
        X.setRight(pivotNode);
        Y.setLeft(X);

        //Update heights
        X.setHeight(max(height(X.getLeft()), height(X.getRight()) + 1));
        Y.setHeight(max(height(Y.getLeft()), height(Y.getRight()) + 1));

        //New root
        return Y;
    }

    private LinkedBinaryNode<T> rightRotation(LinkedBinaryNode<T> Y){
        LinkedBinaryNode X = Y.getLeft();
        LinkedBinaryNode pivotNode = X.getRight();

        X.setRight(Y);
        Y.setLeft(pivotNode);

        Y.setHeight(max(height(Y.getLeft()), height(Y.getRight()) + 1));
        X.setHeight(max(height(X.getLeft()), height(X.getRight()) + 1));

        return X;
    }

    public static void main(String[] args) {
        LinkedBinarySearchTree<Integer> tree = new LinkedBinarySearchTree<>();

        System.out.println(tree.count);

        tree.addElement(1);
        tree.addElement(4);
        tree.addElement(6);
        tree.addElement(10);
        tree.addElement(20);
        tree.addElement(0);

        tree.root = tree.deleteNode(tree.root, 10);

        System.out.println(tree.findMax());
        System.out.println(tree.findMin());
        System.out.println(tree.count);
    }
}
