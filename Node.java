
package datastructuresproject2;

/**
 * @author Nouredeen Hammad
 *         Vildan Kavaklı
 *
 * @param <T>
 */
public class Node<T extends Comparable<T>> {
    
    T data;
    Node<T> right,left;
    
    public Node(T data){
        this.data = data;
    }
    
}
