package edu.gatech.oad.antlab.person;


import java.util.*;
import java.lang.*;
/**
 *  A simple class for person 2
 *  returns their name and a
 *  modified string
 *
 * @author Youssef
 * @version 1.1
 */


public class Person6 {

    /** Holds the persons real name */
    private String name;
	 	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
	 public Person6(String pname) {
	   name = pname;
	 }
	/**
	 * This method should take the string
	 * input and return its characters in
	 * random order.
	 * given "gtg123b" it should return
	 * something like "g3tb1g2".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
	  //Person 6 put your implementation here
	  ArrayList<Character> oldWord = new ArrayList<>();
	  for(int x = 0; x < input.length(); x++) {
	  	oldWord.add(input.charAt(x));
	  }

	  ArrayList<Character> newWord = new ArrayList<>();
	  while(oldWord.size() > 0) {
	  	int randNum = (int) (Math.random() * oldWord.size());
	  	newWord.add(oldWord.remove(randNum));
	  }
	  return newWord.toString();
	}
	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the
	 *         object
	 */
	public String toString(String input) {
	  return name + calc(input);
	}
}
