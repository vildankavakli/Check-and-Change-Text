package datastructuresproject2;

import static datastructuresproject2.Utility.getLevenshteinDistance;

/**
 * @author Nouredeen Hammad
 *         Vildan Kavaklı
 */

//This is a special Binary search tree that can store a generic type that extends Node
//Therefore it can work with both Node and LevenshteinNode classes
public class BST<T extends Comparable<T>, N extends Node<T>> {

    N root;

    //The insert function for Levenshtein nodes
    void insert(T newData, int newLevenshtein) {
        if(root instanceof Node && !(root instanceof LevenshteinNode)){
            System.out.println("Cannot add a levenshtein node to a BST that has Normal nodes");
            return;
        }
        
        LevenshteinNode newNode = new LevenshteinNode(newData,newLevenshtein);

        if (root == null) {
            root = (N)newNode;
            return;
        }

        LevenshteinNode temp = (LevenshteinNode)root;

        //Navigates the tree according to levenshtein
        while (temp != null) {
            if (temp.levenshteinValue<newLevenshtein) {

                if (temp.right == null) {
                    temp.right = newNode;
                    return;
                }
                temp = (LevenshteinNode)temp.right;

            } else if (temp.levenshteinValue>=newLevenshtein) {

                if (temp.left == null) {
                    temp.left = newNode;
                    return;
                }
                temp = (LevenshteinNode)temp.left;

            }
        }

    }
    
    //The insert function for normal nodes
    void insert(T newData) {
        Node<T> newNode = new Node<>(newData);

        if (root == null) {
            root = (N)newNode;
            return;
        }

        Node<T> temp = root;

        while (temp != null) {
            if (newData.compareTo(temp.data) > 0) {

                if (temp.right == null) {
                    temp.right = newNode;
                    return;
                }
                temp = temp.right;

            } else if (newData.compareTo(temp.data) < 0) {

                if (temp.left == null) {
                    temp.left = newNode;
                    return;
                }
                temp = temp.left;

            }
        }

    }

    //Returns true if the value exists in the BST
    boolean search(T key){
        
        if(root == null) return false;
        
        Node<T> temp = root;
        while(temp != null){
            if(key.compareTo(temp.data)>0){
                temp = temp.right;
            } else if(key.compareTo(temp.data)<0) {
                temp = temp.left;
            } else {
                return true;
            }
        }
        return false;
    }
    
    //Returns the minimum value
    T findMin() {
        if (root == null) {
            System.out.println("Empty!");
            return null;
        }

        Node<T> temp = root;
        while (temp.left != null) {
            temp = temp.left;
        }

        return temp.data;
    }

    //Returns the maximum value
    T findMax() {
        if (root == null) {
            System.out.println("Empty!");
            return null;
        }

        Node<T> temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }

        return temp.data;
    }

    //Recursively navigates the dictionary BST and adds to a new BST according to
    //each node's levenshtein value so that it is ordered according to levenshtein algorithm
    public static void newBSTAccordingToLevenshtein(Node node, String targetWord, BST newBst){
        if (node == null)
            return;
 
        // Traverse left subtree
        newBSTAccordingToLevenshtein(node.left,targetWord,newBst);
        
        // Visit node
        int levenshteinDistance = getLevenshteinDistance(""+node.data, targetWord);
        newBst.insert(node.data, levenshteinDistance);
        
        // Traverse right subtree
        newBSTAccordingToLevenshtein(node.right,targetWord,newBst);
    }
    
    //Returns a BST of suggestions according to levenshtein
    public static BST returnSuggestionsTree(String str, BST dict){
        BST levenshteinBST = new BST();
        newBSTAccordingToLevenshtein(dict.root, str, levenshteinBST);
        return levenshteinBST;
    }
    
    //Returns the autocomplete word suggestion
    public static String returnAutocompleteWord(String str, BST dict){
        BST levenshteinBST = returnSuggestionsTree(str,dict);
        
        String min = ""+levenshteinBST.findMin();
        
        //Makes sure that the autocomplete suggestion is not shorter than the original word
        //and makes sure that it does not equal the original word.
        //It also makes sure that the autocomplete suggestion DOES NOT change the currently written word.
        while(true){
            if(min.length()>str.length()){
                if(str.compareTo(min.substring(0, str.length()))==0 && min.compareTo(str)!=0){
                    break;
                }
            }
            
            levenshteinBST.deleteMin();
            
            //Returns -1 when the suggestions tree is exhausted
            if(levenshteinBST.root==null){
                min = "-1";
                break;
            }
            
            min = ""+levenshteinBST.findMin();
        }
        
        return min;
    }
    
    void delete(T key) {
        Node<T> parent = null;
        Node<T> current = root;

        if (root == null) {
            System.out.println("empty!");
            return;
        }

        while (current != null && current.data != key) {
            parent = current;

            if (key.compareTo(current.data) > 0) {
                current = current.right;
            } else {
                current = current.left;
            }

            if (current == null) {
                return;
            }

            if (current.left == null && current.right == null) {

                if (current == root) {
                    root = null;
                } else {
                    if (current == parent.left) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }

                }
            } else if (current.left == null || current.right == null) {
                Node<T> child;

                if (current.left != null) {
                    child = current.left;
                } else {
                    child = current.right;
                }

                if (current == root) {
                    root = null;
                } else {
                    if (current == parent.left) {
                        parent.left = child;
                    } else {
                        parent.right = child;
                    }
                }

            } else {
                Node<T> successor = current.right;
                Node<T> successorParent = current;

                while (successor.left != null) {
                    successorParent = successor;
                    successor = successor.left;
                }

                current.data = successor.data;
            }

        }
    }

    //Deletes the minimum (the far left) node in the tree
    void deleteMin() {
        LevenshteinNode current = (LevenshteinNode)root;

        if (root == null) {
            System.out.println("empty!");
            return;
        }
        
        if(root.left==null && root.right==null){
            root = null;
            return;
        }
        
        if(root.left==null && root.right!=null){
            root = (N)root.right;
            return;
        }
        
        while(current.left.left!=null){
            current = (LevenshteinNode)current.left;
        }
        
        if(current.left.right==null) {
            current.left = null;
        } else {
            current.left = current.left.right;
        }
        

    }
}
