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
import os.Process;
import os.machine.CPU;

/**
 *
 * @author Mantas
 */
public class Input extends Process{
	
	public Input(int intId, ProcName extId, String pName, LinkedList<Process> processList, Process parentProcess,
			CPU cpu, OS os, ProcessState pState, int priority) {
		super(intId, extId, pName, processList, parentProcess, cpu, os, pState, priority);
	}

	@Override
	public void step() {
		switch(nxtInstruction){
		case 1:
			os.requestResource(this, ResName.DO_INPUT);
			nxtInstruction = 2;
			break;
		case 2:
			os.requestResource(this, ResName.KANALAS_1);
			nxtInstruction = 3;
			break;
		case 3:
			//TODO: nustatomi kanalu irenginio registrai ir vykdoma komanda XCHG
			nxtInstruction = 4;
			break;
		case 4:
			// sudaromas reikalingu duomenu resursas
			nxtInstruction = 5;
			break;
		case 5:
			// sukurtas resursas patalpinamas i vartotojo atminti
			nxtInstruction = 6;
			break;
		case 6:
			// atlaisvinti resursa "1 kanala"
			nxtInstruction = 7;
			break;
		case 7:
			// kurti resursa "input"
			nxtInstruction = 1;
			break;
		}
	}
}
