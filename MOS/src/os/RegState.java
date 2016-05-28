/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

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
    	this.regSK = new Register4B();
    	
    	this.regSI = new RegisterInterrupt();
    	this.regPI = new RegisterInterrupt();
    }
    
}
