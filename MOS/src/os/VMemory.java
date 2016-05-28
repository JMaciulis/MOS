/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import os.machine.*;
import os.virtualmachine.registers.*;

/**
 *
 * @author Mantas
 */
public class VMemory {
	/**
	 * Default virtual memory size
	 */
	public final int DEFAULT_MEMSIZE = 16;
	
	/**
	 * Memory size
	 */
	private int memSize;
	
	/**
	 * Page table register
	 * (ref. to real machine)
	 */
	private Word PLR;
	
	/**
	 * Real machine memory (reference)
	 */
	private Memory realMemory;

	public String pName;
	
	/**
	 * constructor
	 * @param real real memory
	 * @param PLR page table register
	 * @throws CloneNotSupportedException 
	 */
	public VMemory(Memory real, Register4B PLR) throws CloneNotSupportedException {
		this.PLR = PLR.getValWord();
		this.realMemory = real;
		this.memSize = DEFAULT_MEMSIZE;
		this.createTable();
		this.PLR = this.PLR.cloneFWord();
	}

	/**
	 * Creates new page table
	 */
	private void createTable(){
		//We find an unused block for PT:
		this.PLR.setValInt(realMemory.getFreeBlockIdx());
		MemoryBlock tableBlock = 
				realMemory.getBlockAtIdx(this.PLR.getValInt());
		tableBlock.setUsed(true);
		//We get an array of addresses of empty blocks
		int[] tableArray = realMemory.getFreeBlocksArray(memSize);
		
		//Now put this table to THE TABLE!!!
		int tableIndex = 0;
		for (int realAddress : tableArray) {
			//NOTE:it might be wise to multiply by 10...
			tableBlock.getWordAtIdx(tableIndex).
				setValInt(realAddress);
			realMemory.getBlockAtIdx(realAddress).setUsed(true);
			
			tableIndex++;
		}
	}
	
	/**
	 * Get word at virtual address
	 * @param address
	 * @return
	 */
	public Word getWordAtAddress(int address){
		//if (address > (this.memSize * 10 - 1)) return null;
		
		int block = address / 16;
		int word = address % 16;
		
		try{
			return getBlock(block).getWordAtIdx(word);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
			System.out.println("   " + (block * 16 + word));
			return null;
		}
	}
	
	/**
	 * Get block at index (virtualAddress / 10)
	 * @param idx
	 * @return
	 */
	private MemoryBlock getBlock(int idx){
		if (idx > this.memSize) return null;
		
		MemoryBlock pageTable = 
				realMemory.getBlockAtIdx(this.PLR.getValInt());
		int realBlockAddress = pageTable.getWordAtIdx(idx).getValInt();
		
		return realMemory.getBlockAtIdx(realBlockAddress);
	}
	
	/**
	 * Prints whole VM memory to system out
	 */
	public void printMemory() {
		String str;
		int addr;
		for (int i = 0; i < this.DEFAULT_MEMSIZE; i++){
			str = "BLOCK: " + i;
			System.out.println(str);
			for (int j = 0; j < 16; j++){
				addr = i * 16 + j;
				str = addr + ":" + j + ": " + this.getWordAtAddress(addr).getVal();
				System.out.println(str);
			}
		}
	}

	/**
	 * resets "used" boolean
	 */
	public void returnMem() {
		for (int i = 0; i < this.DEFAULT_MEMSIZE; i++){
			this.getBlock(i).setUsed(false);
		}
		realMemory.getBlockAtIdx(this.PLR.getValInt()).setUsed(false);
	}

	public int getPLR() {
		return this.PLR.getValInt();
	}    
}
