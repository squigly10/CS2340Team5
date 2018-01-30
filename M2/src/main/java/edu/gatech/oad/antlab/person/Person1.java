package edu.gatech.oad.antlab.person;

/**
 *  A simple class for person 1
 *  returns their name and a
 *  modified string
 *
 *  @author David Jose Fernandez
 *  @version 1.1
 */
public class Person1 {
  /** Holds the persons real name */
  private String name;
  	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
  public Person1(String pname) {
    name = pname;
  }
  	/**
	 * This method should take the string
	 * input and return its characters rotated
	 * 2 positions.
	 * given "gtg123b" it should return
	 * "g123bgt".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
        int stringLength = input.length();
        char[] charArray = new char[stringLength];
        for (int i = 0; i < stringLength; i++) {
            charArray[i] = input.charAt(i);
        }
        char[] finalCharArray = new char[stringLength];
        for (int i = 0; i < stringLength; i++) {
            finalCharArray[(i + 2) % stringLength] = charArray[i];
        }
        StringBuilder stringToBuild = new StringBuilder();
        stringToBuild.append(finalCharArray);
        String finalString = stringToBuild.toString();
        return finalString;
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
