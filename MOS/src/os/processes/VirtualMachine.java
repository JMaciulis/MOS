/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.processes;

import java.util.LinkedList;

import os.OS;
import os.OS.ProcName;
import os.OS.ProcessState;
import os.OS.ResName;
import os.ProcessDescriptor;
import os.ResourceManager;
import os.VMemory;
import os.machine.CPU;
import os.machine.ProcessingUtility;
import os.virtualmachine.registers.Register2B;
import os.virtualmachine.registers.Register4B;
import os.virtualmachine.registers.RegisterLogicByte;

/**
 *
 * @author Mantas
 */
public class VirtualMachine extends os.Process {
	
	public static int vmCount = 0;
	
	
	//References to registers for ease of use
	public Register4B regR1, regR2;
	public Register2B regIC;
	public RegisterLogicByte regC;
	public Register4B regSP;
	
	public VMemory memory;
		
		
	public VirtualMachine(int intId, ProcName extId, String pName,
			LinkedList<Process> processList, Process parentProcess, CPU cpu,
			OS os, ProcessState pState, int priority) {
		super(intId, extId, pName, processList, parentProcess, cpu, os, pState,
				priority);
		
		//For ease of use
		this.regC = cpu.getRegC();
		this.regIC = cpu.getRegIC();
		this.regR1 = cpu.getRegR1();
		this.regR2 = cpu.getRegR2();
		this.regSP = cpu.getRegSP();
				
		this.pDesc.savedState.saveState(cpu);
				
		vmCount++;
		saveCPU();
	}
	
	
	@Override
	public void step() {
		switch (nxtInstruction){
		case 1:
			//Request JOB in memory
			if (os.requestResource(this, ResName.UZDUOTIS_VYKDYMUI)) {
				nxtInstruction++;
			} else {
				setTimer(0);
			}
			break;
		case 2:
			//Take JOB
			Resource memRes = ResourceManager.findResourceByExtId(
					pDesc.ownedResList, ResName.UZDUOTIS_VYKDYMUI);
			this.memory = (VMemory) memRes.getComponent();
			pDesc.pName = memory.pName;
			pDesc.cpu.regIC.setValInt(0);
			os.destroyResource(memRes);
			nxtInstruction++;
			break;
		case 3:
			//STEP!
			vmStep();
			break;
		case 4:
			//Wait for resume
			os.requestResource(this, ResName.RESUME_VM);
			nxtInstruction = 5;
			break;
		case 5:
			nxtInstruction = 3;
			os.printStuffDevice("VM:" + pDesc.pName + " - RESUMING AFTER INT");
			Resource resumeRes = ResourceManager.findResourceByExtId(
					pDesc.ownedResList, ResName.RESUME_VM);
			if (resumeRes != null) {
				os.destroyResource(resumeRes);
			}
			
			break;
		}
	}

	/**
	 * VM job step
	 */
	private void vmStep() {
		int tmpIC = pDesc.cpu.regIC.getValInt();
		
		String cmd = getCommandAtIC();
		if (cmd.equals("HALT")){
			halted = true;
			os.printStuffDevice("HALTED: " + toString());
		}
		
		try {
			ProcessingUtility.processCommand(this, cmd);
		} catch (IllegalArgumentException e){
			OS.printStuff(e.toString());
			pDesc.cpu.regPI.setValue(1);
		}
		
		if (pDesc.cpu.regIC.getValInt() == tmpIC){
			tmpIC++;
			pDesc.cpu.regIC.setValInt(tmpIC);
		}
		
		saveCPU();
		
		if (chkInterrupts() != 0){
			os.createResource(this, ResName.PRANESIMAS_PERTR, pDesc);
			os.stopProcess(this);
			os.requestResource(this, ResName.RESUME_VM);
			nxtInstruction = 5;
			
			os.printStuffDevice("INTERRUPT:" + toString());
			os.printStuffDevice(pDesc.savedState.toString());
		} else {
			nxtInstruction = 3;
		}
	}
	
	/**
	 * Get next job instruction string
	 * @return job instruction
	 */
	private String getCommandAtIC(){
		String tmp = memory.getWordAtAddress(
				pDesc.cpu.regIC.getValInt()).getVal();
		return tmp;
	}
	
	public void destroy(){
		if (this.memory != null){
			this.memory.returnMem();
			OS.printStuff("VM #" + pDesc.intId +
					" memory released...");
		} else {
			OS.printStuff("VM #" + pDesc.intId +
					" memory failed to release...");
			OS.printStuff(toString());
		}
		this.memory = null;
	}
	
	@Override
	public boolean fitRes(Resource res) {
		if (res.resDesc.getExtId() == ResName.RESUME_VM){
			if (((ProcessDescriptor) res.getComponent()).intId ==
					pDesc.intId){
				
				os.printStuffDevice("VM>> Took RESUME_VM (" +
						((ProcessDescriptor) res.getComponent()).intId + "=" +
						pDesc.intId + ")");
				for (ResName r : pDesc.waitingFor) {
					os.printStuffDevice(String.valueOf(r));
				}
				
				return true;
			} else{
				return false;
			}
		} else {
			return super.fitRes(res);
		}
	}
	
	public CPU getCpu(){
		return this.pDesc.cpu;
	}
	
	public Register4B getRegR1() {
		return regR1;
	}

	public void setRegR1(Register4B regR1) {
		this.regR1 = regR1;
	}

	public Register4B getRegR2() {
		return regR2;
	}

	public void setRegR2(Register4B regR2) {
		this.regR2 = regR2;
	}

	public Register2B getRegIC() {
		return regIC;
	}

	public void setRegIC(Register2B regIC) {
		this.regIC = regIC;
	}

	public RegisterLogicByte getRegC() {
		return regC;
	}

	public void setRegC(RegisterLogicByte regC) {
		this.regC = regC;
	}

	public Register4B getRegSP() {
		return regSP;
	}

	public void setRegSP(Register4B regSP) {
		this.regSP = regSP;
	}

	public VMemory getMemory() {
		return memory;
	}

	public void setMemory(VMemory memory) {
		this.memory = memory;
	}
}