/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.machine;

import java.lang.reflect.Array;

import os.OS;

/**
 *
 * @author Mantas
 */
public class Memory {
	private static final int DEFAULT_MEMORY_SIZE = 16;
	private MemoryBlock[] blocks;
	
	public Memory(){
		this(DEFAULT_MEMORY_SIZE);
	}
	public Memory(int size){
		blocks = new MemoryBlock[size];
		for(int i = 0; i < size; i ++){
			blocks[i] = new MemoryBlock();
		}
	}
	public MemoryBlock getBlockAtIdx(int index){
		return blocks[index];
	}
	public Word getWordAtAddress(int address){
		int block = address / 16;
		int word = address % 2;
		try{
			return this.blocks[block].getWordAtIdx(word);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e);
			System.out.println(" " + address);
			return null;
		}
	}
	public int getFreeBlockIdx(){
		int i;
		for (i = 0; i < Array.getLength(blocks); i++) {
			if( blocks[i].isUsed() == false){
				return i;
			}
		}
		return -1; //-1 full
	}
	
	public int[] getFreeBlocksArray(int blocksNeeded){
		int tmp;
		int[] results = new int[blocksNeeded];
		for(int i = 0; i < blocksNeeded; i++){
			tmp = this.getFreeBlockIdx();
			if(tmp = -1){
				return OS.printStuff("FMem: ERROR - not enough memory");
			} else {
				this.blocks[tmp].setUsed(true);
				results[i] = tmp;
			}
			return results;
		}
	}
	
	public void printMemory() {
        System.out.println(toString());
	}
	@Override
    public String toString(){
        String str = "";
        int addr;
        int j;
        for (int i = 0; i < DEFAULT_MEMORY_SIZE; i++){
            str += "BLOCK: " + i + "\n";
            for (j = 0; j < 16; j++){
                addr = i * 16 + j;
                str += addr + ": " + blocks[i].getWordAtIdx(j).getVal() + "\n";
            }
        }
        return str;
    }
	public int freeBlocksCount(){
		int freeCount = 0;
		for (MemoryBlock block : blocks) {
			if (!block.isUsed()){
				freeCount++;
			}
		}
		return freeCount;
	}
	
	public boolean isEnoughMem() {
		int freeCount = freeBlocksCount();
		OS.printStuff("FreeBlocks: " + freeCount);
		if (freeCount > 11)
			return true;
		else
			return false;
	}
	
	public int getSize(){
		return Array.getLength(blocks);
	}

    public MemoryBlock[] getBlocks() {
        return blocks;
    }

    public void reset() {
        for (Memory block : blocks) {
            block.setUsed(false);
        }
    }
}
