/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import java.util.LinkedList;
import os.OS.ProcName;
import os.OS.ProcessState;
import os.OS.ResName;
import os.machine.CPU;

/**
 *
 * @author Mantas
 */
public class ProcessDescriptor {

	public LinkedList<Process> processList;
	public LinkedList<Process> childrenList;
	
	public LinkedList<Resource> createdResList;
	public LinkedList<Resource> ownedResList;
	public LinkedList<ResName> waitingFor;
	
	public int intId;
	public ProcName extId;
	public String pName;
	public Process myProc;
	public Process parentProcess;
	public int priority;
	
	public CPU cpu;
	public OS os;
	
	public ProcessState pState;
	public RegState savedState;
	
	public ProcessDescriptor(int intId, ProcName extId, String pName,
			LinkedList<Process> processList,
			Process parentProcess,
			Process myProcess,
			CPU cpu, OS os,
			ProcessState pState, int priority) {
		this.intId = intId;
		this.extId = extId;
		this.pName = pName;
		this.priority = priority;
		this.myProc = myProcess;
		
		this.processList = processList;
		this.parentProcess = parentProcess;
		this.cpu = cpu;
		this.os = os;
		
		this.savedState = new RegState();
		this.pState = ProcessState.READY;
		
		childrenList = new LinkedList<>();
		createdResList = new LinkedList<>();
		ownedResList = new LinkedList<>();
		waitingFor = new LinkedList<>();
	}
}

