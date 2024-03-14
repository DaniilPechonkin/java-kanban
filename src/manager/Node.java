package manager;

public class Node<E> {
        public E index;
        public Node<E> next;
        public Node<E> prev;

        public Node(E index) {
            this.index = index;
        }
}
