/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.processes;

import java.io.File;
import java.util.LinkedList;

import os.OS;
import os.OS.ProcName;
import os.OS.ProcessState;
import os.OS.ResName;
import os.machine.CPU;

/**
 *
 * @author Mantas
 */
public class MainProc extends os.Process{


	public MainProc(int intId, ProcName extId, String pName,
			LinkedList<Process> processList, Process parentProcess, CPU cpu,
			OS os, ProcessState pState, int priority) {
		super(intId, extId, pName, processList, parentProcess, cpu, os, pState,
				priority);
	}

	@Override
	public void step() {
		switch(nxtInstruction){
		case 1:
			os.requestResource(this, ResName.UZDUOTIS_ISOR);
			nxtInstruction = 2;
			break;
		case 2:
			takeFile();
			nxtInstruction = 3;
			break;
		case 3:
			chkGovernors();
			os.createProcess(this, ProcName.JOB_GOVERNOR);
			nxtInstruction = 1;
			break;
		}
	}

	private void chkGovernors() {
		JobGovernor govproc;
		for (FProcess proc : pDesc.childrenList) {
			govproc = (JobGovernor) proc;
			if (govproc.isJobHalted){
				os.destroyProcess(proc);
			}
		}
	}
	
	private void takeFile() {
		Resource fileRes;
		fileRes = FResourceManager.findResourceByExtId(pDesc.ownedResList,
				ResName.UZDUOTIS_ISOR);
		
		if (fileRes == null){
			OS.printStuff("FAILED: file is null :(");
			System.out.println("waiting: " + pDesc.waitingFor.size());
			nxtInstruction = 1;
			return;
		}
		
		File jobFile = (File) fileRes.getComponent();
		
		os.createResource(this, ResName.UZDUOTIS_MP,
				jobFile);
		os.destroyResource(fileRes);
	}
}
