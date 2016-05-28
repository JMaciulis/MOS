/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.machine;

/**
 *
 * @author Mantas
 */
public class Word implements Cloneable{

	/**
	 * Default word size
	 */
	public static final int DEFAULT_WORD_SIZE = 4;
	
	/**
	 * Word size
	 */
	private int size;
	
	/**
	 * Word data
	 */
	private String value;
	
	/**
	 * Default constructor
	 */
	public Word() {
		this(DEFAULT_WORD_SIZE);
	}
	
	/**
	 * Constructor
	 * Initializes Word with size
	 * @param value
	 */
	public Word(int size) {
		this.setSize(size);
		this.value = "";
	}

	public Word cloneFWord() throws CloneNotSupportedException{
		Word clone = (Word) this.clone();
		return clone;
	}
	
	/**
	 * @return data string
	 */
	public String getVal() {
		return value;
	}
	
	/**
	 * get integer data
	 * @return integer data
	 */
	public int getValInt() {
		try{
		return Integer.parseInt(this.value, 16);//the string should be hex
		} catch (NumberFormatException e){
			throw new IllegalArgumentException("Wrong type");
		}
	}

	/**
	 * Sets string value
	 * If data is too long - cuts it.
	 * @param value new string value
	 */
	public void setVal(String value) {
		if (value.length() <= this.size){
			this.value = value;
		} else {
			this.value = value.substring(this.size);
		}
	}
	
	/**
	 * Sets int value.
	 * Converts to hex string before setting.
	 * @param value integer value
	 */
	public void setValInt(int value){
		setVal(Integer.toHexString(value));
	}

	/**
	 * Get word size
	 * @return size size of word
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Set word size
	 * @param size new size
	 */
	private void setSize(int size) {
		this.size = size;
		
	}

}

