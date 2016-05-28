/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.machine;

import devices.InputPanel;
import devices.OutputPanel;
import os.gui.IOwindow;

/**
 *
 * @author Mantas
 */
public class Machine {
	public CPU cpu;
	public Memory memory;
        
    //System io devices
    public InputPanel sysInputDevice;
    public OutputPanel sysOutputDevice;
    private IOwindow sysIoWindow;
        
    //User io devices
    public InputPanel usrInputDevice;
    public OutputPanel usrOutputDevice;
    private IOwindow usrIoWindow;
        
	
	/**
	 * Constructor
	 */
	public Machine() {
		this.cpu = new CPU();
		this.memory = new Memory();
                
				//Used by PrintLine, GetLine
                this.usrIoWindow = new IOwindow();
                this.usrInputDevice = usrIoWindow.getInputDevice();
                this.usrOutputDevice = usrIoWindow.getOutputDevice();
                usrIoWindow.setName("VM IO devices");
                usrIoWindow.setTitle("VM IO devices");
                
                this.sysIoWindow = new IOwindow();
                this.sysInputDevice = sysIoWindow.getInputDevice();
                this.sysOutputDevice = sysIoWindow.getOutputDevice();
                sysIoWindow.setName("System IO devices");
                sysIoWindow.setTitle("System IO devices");
                
                
                this.sysIoWindow.setVisible(true);
                this.usrIoWindow.setVisible(true);
	}
	
	public CPU getCpu() {
		return cpu;
	}
	
	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}    
}
