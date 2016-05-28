/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.virtualmachine.registers;

import os.machine.Word;

/**
 *
 * @author Mantas
 */
public class Register4B {
	
	/**
	 * Default init register value
	 */
	final static int DEFAULT_INIT_VAL = 0;
	
	/**
	 * Register size (bytes)
	 */
	final static int DEFAULT_SIZE = 4;
	
	/**
	 * Value is a word. 
	 */
	private Word value;
	
	/**
	 * Default constructor
	 */
	public Register4B() {
		this(DEFAULT_INIT_VAL);
	}
	
	/**
	 * Initializes register with initial value
	 * @param initVal initial value
	 */
	public Register4B(int initVal){
		this.initFWord(DEFAULT_SIZE);
		this.setValInt(initVal);
	}
	
	/**
	 * Initializes register with initial value
	 * @param initVal initial value
	 */
	public Register4B(String initVal){
		this.initFWord(DEFAULT_SIZE);
		this.setValStr(initVal);
	}
	
	protected void initFWord(int size){
		value = new Word(size);
	}
	
	/**
	 * Sets new register value
	 * @param value new value
	 */
	public void setValInt(int value){
		this.value.setValInt(value);
	}

	/**
	 * Sets new register value
	 * @param valuenew value
	 */
	public void setValStr(String value){
		this.value.setVal(value);
	}
	
	/**
	 * Get register value (String)
	 * @return register value String
	 */
	public String getValStr(){
		return this.value.getVal();
	}
	
	/**
	 * Get register value (int)
	 * @return register value int
	 */
	public int getValInt(){
		return this.value.getValInt();
	}
	
	public Word getValWord() {
		return value;
	}

}
