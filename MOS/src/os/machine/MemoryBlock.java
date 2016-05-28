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
public class MemoryBlock {
	static final int DEFAULT_BLOCK_SIZE = 16;
	
	private Word[] block;
	
	private boolean used;
	
	public MemoryBlock(int size) {
		this.block = new Word[size];
		for (int i = 0; i < size; i++){
			block[i] = new Word();
		}
	}
	public MemoryBlock() {
		this(DEFAULT_BLOCK_SIZE);
	}
	public Word getWordAtIdx(int index) {
		return block[index];
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public Word[] getBlock(){
		return block;
	}
}
