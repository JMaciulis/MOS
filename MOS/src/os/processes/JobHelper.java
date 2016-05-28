/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.processes;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import os.OS;
import os.OS.ProcName;
import os.OS.ProcessState;
import os.machine.CPU;

/**
 *
 * @author Mantas
 */
public class JobHelper extends os.Process {

    private Process myVM;
    public boolean isJobHalted;

    public JobHelper(int intId, ProcName extId, String pName, LinkedList<os.Process> processList, os.Process parentProcess, CPU cpu, OS os, ProcessState pState, int priority) {
        super(intId, extId, pName, processList, parentProcess, cpu, os, pState, priority);
    }

    @Override
    public void step() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
