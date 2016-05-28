/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.virtualmachine.registers;

/**
 *
 * @author Mantas
 */
public class Register2B extends Register4B{

	/**
	 * Register size (bytes)
	 */
	final static int DEFAULT_SIZE = 2;
	
	/**
	 * Default constructor
	 */
	public Register2B() {
		super();
	}
	
	/**
	 * Initializes register with initial value
	 * @param initVal initial value
	 */
	public Register2B(int initVal){
		super(initVal);
	}
	
	/**
	 * Initializes register with initial value
	 * @param initVal initial value
	 */
	public Register2B(String initVal){
		super(initVal);
	}

}
