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
import os.Resource;
import os.ResourceManager;
import os.VMemory;
import os.machine.CPU;
import os.machine.ProcessingUtility;
import os.virtualmachine.registers.Register2B;

public class VirtualMachine extends os.Process {

    public static int vmCount = 0;

    public Register2B regKS;
    public Register2B regSK;
    public Register2B regSV;

    public VMemory memory;

    public VirtualMachine(int intId, ProcName extId, String pName, LinkedList<os.Process> processList, os.Process parentProcess, CPU cpu, OS os, ProcessState pState, int priority) {
        super(intId, extId, pName, processList, parentProcess, cpu, os, pState, priority);
        this.regKS = cpu.getRegKS();
        this.regSK = cpu.getRegSK();
        this.regSV = cpu.getRegSV();

        this.pDesc.savedState.saveState(cpu);

        vmCount++;
        saveCPU();
    }

    @Override
    public void step() {
        switch (nxtInstruction) {
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
                Resource memRes = ResourceManager.findResourceByExtId(pDesc.ownedResList, ResName.UZDUOTIS_VYKDYMUI);
                this.memory = (VMemory) memRes.getComponent();
                pDesc.pName = memory.pName;
                pDesc.cpu.regKS.setValInt(0);
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
        int tmpIC = pDesc.cpu.regKS.getValInt();

        String cmd = getCommandAtIC();
        if (cmd.equals("HALT")) {
            halted = true;
            os.printStuffDevice("HALTED: " + toString());
        }

        try {
            ProcessingUtility.executeCommand(this, cmd);
        } catch (IllegalArgumentException e) {
            OS.printStuff(e.toString());
            pDesc.cpu.regPI.setValue(1);
        }

        if (pDesc.cpu.regKS.getValInt() == tmpIC) {
            tmpIC++;
            pDesc.cpu.regKS.setValInt(tmpIC);
        }

        saveCPU();

        if (chkInterrupts() != 0) {
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
     *
     * @return job instruction
     */
    private String getCommandAtIC() {
        String tmp = memory.getWordAtAddress(
                pDesc.cpu.regKS.getValInt()).getVal();
        return tmp;
    }

    public void destroy() {
        if (this.memory != null) {
            this.memory.returnMem();
            OS.printStuff("VM #" + pDesc.intId + " memory released...");
        } else {
            OS.printStuff("VM #" + pDesc.intId + " memory failed to release...");
            OS.printStuff(toString());
        }
        this.memory = null;
    }

    @Override
    public boolean fitRes(Resource res) {
        if (res.resDesc.getExtId() == ResName.RESUME_VM) {
            if (((ProcessDescriptor) res.getComponent()).intId == pDesc.intId) {
                os.printStuffDevice("VM>> Took RESUME_VM (" + ((ProcessDescriptor) res.getComponent()).intId + "=" + pDesc.intId + ")");
                for (ResName r : pDesc.waitingFor) {
                    os.printStuffDevice(String.valueOf(r));
                }
                return true;
            } else {
                return false;
            }
        } else {
            return super.fitRes(res);
        }
    }

    public CPU getCpu() {
        return this.pDesc.cpu;
    }

    public Register2B getRegKS() {
        return regKS;
    }

    public Register2B getRegSK() {
        return regSK;
    }

    public Register2B getRegSV() {
        return regSV;
    }

    public void setRegKS(Register2B reg) {
        this.regKS = reg;
    }

    public void setRegSK(Register2B reg) {
        this.regSK = reg;
    }

    public void setRegSV(Register2B reg) {
        this.regSV = reg;
    }

    public VMemory getMemory() {
        return memory;
    }

    public void setMemory(VMemory memory) {
        this.memory = memory;
    }
}
