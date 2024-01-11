/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructuresproject2;

/**
 * @author Nouredeen Hammad
 *         Vildan Kavaklı
 *
 * @param <T>
 */

//A special type of node that carries a Levenshtein value
public class LevenshteinNode<T extends Comparable<T>> extends Node<T>{
    
    int levenshteinValue;
    
    public LevenshteinNode(T data, int levenshtein) {
        super(data);
        levenshteinValue = levenshtein;
    }
    
}
