/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import java.util.Collections;
import java.util.LinkedList;

import os.OS.ProcessState;
import os.OS.ResName;
import os.machine.Memory;
import os.virtualmachine.registers.Register4B;

/**
 *
 * @author Mantas
 */
public class ResourceManager {
private OS os;
	
	public ResourceManager(OS os) {
		this.os = os;
	}

	/**
	 * Checks if any of the waited resources can be provided
	 */
	public void execute(){
		
		//Create list of processes waiting for some resource
		LinkedList<Process> waitingForRes = new LinkedList<>();
		for (Process process : os.processes) {
			if (process.pDesc.waitingFor.size() > 0){
				waitingForRes.add(process);
			}
		}
		
		sortProcessListByPriority(waitingForRes);
		
		OS.printStuff("=============RESMAN================");
		OS.printStuff("Resman: memory status = (" +
				os.getRealMachine().getMemory().freeBlocksCount() +
				"/"+ os.getRealMachine().getMemory().getSize() + ")");
		OS.printStuff("ResMan: waiting count = " + 
				waitingForRes.size());
		
		LinkedList<Resource> tmpList = null;
		LinkedList<ResName> tmpWaitedList = null;
		
		//Check for available resources
		for (Process process : waitingForRes) {
//			FluffyOS.printStuff(process.toString());
			
			//Copy waited res list
			tmpWaitedList = new LinkedList<>();
			for (ResName resn : process.pDesc.waitingFor) {
				tmpWaitedList.add(resn);
			}
			
			for (ResName waitedRes : tmpWaitedList) {
				
				//Memory is only given when enough free blocks are available
				if (waitedRes == ResName.VARTOTOJO_ATMINTIS){
					
					Memory memory = os.getRealMachine().getMemory();
					
					if (memory.isEnoughMem()){
						Register4B plr = new Register4B();
						plr.setValInt(memory.getFreeBlockIdx());
						VMemory vMem = null;
						try {
							 vMem = new VMemory(memory, plr );
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
						
						Resource vMemRes = os.createSimpleResource(
								os.starStopProc, 
								ResName.VARTOTOJO_ATMINTIS, vMem);
						
						giveResource(process, vMemRes);
						process.pDesc.waitingFor.remove(
								ResName.VARTOTOJO_ATMINTIS);
					} else {
						OS.printStuff("NOT ENOUGH MEMORY!!!");
					}
					
				} else {
					tmpList = ResourceManager.findResourceListByExtId(
							os.freeResources, waitedRes);
					
					for (Resource res : tmpList) {
						if (process.fitRes(res)){
							os.giveResource(process, res);
							OS.printStuff(
									process.pDesc.extId + "<-" + 
										res.resDesc.getExtId());
							break;
						}
					}
				}
			}
			
			//No resource available or not all resources available
			if (process.pDesc.waitingFor.size() == 0){
				process.pDesc.pState = ProcessState.READY;
				if (os.blockedProcesses.contains(process)){
					os.blockedProcesses.remove(process);
					os.readyProcesses.add(process);
				}
			} else {
				if (process.pDesc.pState == ProcessState.READY){
					process.pDesc.pState = ProcessState.BLOCKED;
					os.readyProcesses.remove(process);
					os.blockedProcesses.add(process);
				} else if (process.pDesc.pState == ProcessState.RUN){
					process.pDesc.pState = ProcessState.BLOCKED;
					os.blockedProcesses.add(process);
				}
			}
		}
		os.procMan.execute();
	}
	
	/**
	 * Gives resource to process
	 * @param process
	 * @param res
	 */
	public void giveResource(Process process, Resource res) {
		process.pDesc.ownedResList.add(res);
		process.pDesc.waitingFor.remove(res.resDesc.getExtId());
		
		os.freeResources.remove(res);
		os.usedResources.add(res);
		
		res.resDesc.setUser(process);
	}
	
	/**
	 * Sorts list of processes by priority in descending order
	 * @param list
	 */
	public static void sortProcessListByPriority(
			LinkedList<Process> list){
		Collections.sort(list);
	}
	
	/**
	 * Finds resource by name in a list
	 * @param list List of resources
	 * @param extId Resource name (extId)
	 * @return The resource. Null if not found.
	 */
	public static Resource findResourceByExtId(
			LinkedList<Resource> list, ResName extId){
		for (Resource res : list) {
			if (res.resDesc.getExtId() == extId)
				return res;
		}
		return null;
	}
	
	/**
	 * Finds all resources with the provided extId in a list
	 * @param list List of resources
	 * @param extId Resource name (extId)
	 * @return List of resources
	 */
	public static LinkedList<Resource> findResourceListByExtId(
			LinkedList<Resource> list, ResName extId) {
		LinkedList<Resource> tmpList = new LinkedList<>();
		for (Resource res : list) {
			if (res.resDesc.getExtId() == extId)
				tmpList.add(res);
		}
		return tmpList;
	}    
}
