/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import os.machine.CPU;
import os.virtualmachine.registers.Register2B;
import os.virtualmachine.registers.Register4B;
import os.virtualmachine.registers.RegisterInterrupt;
import os.virtualmachine.registers.RegisterLogic;

/**
 *
 * @author Mantas sss
 */
public class RegState {
	
    public Register2B regKS;
    public Register2B regSV;
    public Register4B regSK;

    public RegisterInterrupt regSI;
    public RegisterInterrupt regPI;
    
    public RegState() {
    	this.regKS = new Register2B();
    	this.regSV = new Register2B();
    	this.regSK = new Register2B();
    	
    	this.regSI = new RegisterInterrupt();
    	this.regPI = new RegisterInterrupt();
    }
	public void saveState(CPU cpu){
		this.regKS.setValStr(cpu.getRegKS().getValStr());
		this.regSV.setValStr(cpu.getRegSV().getValStr());
		this.regSK.setValInt(cpu.getRegSK().getValInt());
		
		this.regPI.setValue(cpu.regPI.getValue());
		this.regSI.setValue(cpu.regSI.getValue());
	}

	/**
	 * Restore saved cpu state
	 * @param cpu
	 */
	public void restoreState(CPU cpu){
		cpu.getRegKS().setValStr(this.regKS.getValStr());
		cpu.getRegSV().setValStr(this.regSV.getValStr());
		cpu.getRegSK().setValInt(this.regSK.getValInt());
		
		resetInt();
		cpu.regPI.setValue(this.regPI.getValue());
		cpu.regSI.setValue(this.regSI.getValue());
	}
	
	private void resetInt() {
		this.regPI.setValue(0);
		this.regSI.setValue(0);
	}
	
	@Override
	public String toString() {
		String str = 
				"KS = " + this.regKS.getValStr() +
				", SV = " + this.regSV.getValStr() +
				", SK = " + this.regSK.getValStr();
		return str;
	}
}
