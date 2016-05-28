package os;

import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mantas
 */
public class OS implements Runnable {

	public static final String FP_PREFIX = "fluffies/";
	public static final String FP_END = ".fluffy";
	public static boolean VERBOSE = true;
	public static boolean LOG = false;
	public static int SPEED = 100;

	public static String TEST_FILEPATH = "fluffies/t_input.fluffy";
	public static String TEST_FILEPATH2 = "fluffies/t_halt.fluffy";
	public static String TEST_FILEPATH3 = "fluffies/t_output.fluffy";

	public enum ProcessState {
		RUN, READY, STOPPED, BLOCKED
	}

	public enum ProcName {
		START_STOP, WAIT_FOR_JOB, MAIN_PROC, LOADER, JOB_GOVERNOR, VIRTUAL_MACHINE, INTERRUPT, PRINT_LINE, GET_LINE
	}

	public enum ResName {
		KANALAS_1,  KANALAS_2, KANALAS_3, INPUT,
                DO_INPUT, OUTPUT, DO_OUTPUT, PROGRAMA_PARENGTA,
                ISKELK_PROGRAMA, PROGRAMA_ISKELTA, PERTRAUKIMAS, INTERRUPT,
                VARTOTOJO_ATMINTIS, PAKRAUK_PROGRAMA, LAUKIAMA, SHUTDOWN,
                START_READER,
	}

	public enum IntType {
		READY,

		// Lower priority interrupts
		HALT, INPUT, OUTPUT,

		// Critical interrupts
		ILLEGAL_COMMAND, NEGATIVE_RESULT, DIV_BY_ZERO, OVERFLOW
	}

	public LinkedList<Process> processes;
	public LinkedList<Process> readyProcesses;
	public LinkedList<Process> blockedProcesses;
	public LinkedList<Process> stoppedProcesses;

	/**
	 * The currently running process
	 */
	public Process runProcess;

	public LinkedList<Resource> resources;
	public LinkedList<Resource> usedResources;
	public LinkedList<Resource> freeResources;

	public Machine realMachine;

	public ProcessManager procMan;
	public ResourceManager resMan;
	public boolean stopOS;

	public Process starStopProc;
	private int newProcId;

	public OS(Machine realMachine) {
		this.setRealMachine(realMachine);
		initLists();

		this.resMan = new ResourceManager(this);
		this.procMan = new ProcessManager(this);

		this.stopOS = false;

		this.starStopProc = this.createProcess(null, ProcName.START_STOP, 99);
		this.runProcess = this.starStopProc;

		this.realMachine.usrInputDevice.setOS(this);
		this.realMachine.sysInputDevice.setOS(this);
		initInputDevices();
		this.realMachine.usrInputDevice.getInpField().setEditable(false);
	}

	@Override
	public void run() {
		while (!stopOS) {
			osStep();

			try {
				Thread.sleep(OS.SPEED);
			} catch (InterruptedException ex) {

			}
		}
	}

	public void reset() {
		stopOS = true;

		processes.clear();
		stoppedProcesses.clear();
		readyProcesses.clear();
		blockedProcesses.clear();

		resources.clear();
		usedResources.clear();
		freeResources.clear();

		realMachine.memory.reset();

		this.starStopProc = this.createProcess(runProcess, ProcName.START_STOP);
		this.runProcess = this.starStopProc;
	}

	private void osStep() {
		printStuff("!!!!!!!!!!!!!===OS_STEP===!!!!!!!!!!!!!!");
		for (Process prc : processes) {
			OS.printStuff(prc.toString());
		}
		if (runProcess != null) {
			OS.printStuff("RUNNNING: " + runProcess.toString());

			runProcess.fullStep();
		} else {
			resMan.execute();
		}

		printStuff("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	/**
	 * Initializes resources and processes lists
	 */
	private void initLists() {
		processes = new LinkedList<>();
		readyProcesses = new LinkedList<>();
		blockedProcesses = new LinkedList<>();
		stoppedProcesses = new LinkedList<>();

		resources = new LinkedList<>();
		usedResources = new LinkedList<>();
		freeResources = new LinkedList<>();

	}

	public Process createProcess(Process creator, ProcName extId) {
		return createProcess(creator, extId, 1);
	}

	public Process createProcess(Process creator, ProcName extId, int priority) {
		Process proc = null;
		int intId = generateProcessInternalID();

		switch (extId) {
		case GET_LINE:
			proc = new GetLine(intId, extId, "GetLine", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 91);
			break;
		case INTERRUPT:
			proc = new Interrupt(intId, extId, "Int", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 90);
			break;
		case JOB_GOVERNOR:
			proc = new JobGovernor(intId, extId, "JobGov", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 89);
			break;
		case LOADER:
			proc = new Loader(intId, extId, "Loader", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 88);
			break;
		case MAIN_PROC:
			proc = new MainProc(intId, extId, "MainProc", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 92);
			break;
		case PRINT_LINE:
			proc = new PrintLine(intId, extId, "PrintLine", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 91);
			break;
		case START_STOP:
			proc = new StartStop(intId, extId, "StartStop", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 99);
			break;
		case VIRTUAL_MACHINE:
			proc = new VirtualMachine(intId, extId, "VM", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 80);
			break;
		case WAIT_FOR_JOB:
			proc = new WaitForJob(intId, extId, "WFJ", processes, creator,
					realMachine.getCpu(), this, ProcessState.READY, 93);

			break;
		default:
			break;
		}

		if (proc != null) {
			printStuffDevice("CREATED PROC: " + extId);

			this.processes.add(proc);
			this.readyProcesses.add(proc);
			procMan.execute();
			return proc;
		} else {
			printStuffDevice("CREATION FAILED: " + extId);
		}
		procMan.execute();
		return null;
	}

	/**
	 * Destroys selected process
	 * 
	 * @param proc
	 */
	public void destroyProcess(Process proc) {
		// 1 - destroy created resources
		LinkedList<Resource> destrRes = new LinkedList<>();
		for (Resource crRes : proc.pDesc.createdResList) {
			destrRes.add(crRes);
		}
		for (Resource desRes : destrRes) {
			destroyResource(desRes);
		}

		// 2 - destroy created processes (recursion)
		LinkedList<Process> destrProc = new LinkedList<>();
		for (Process crProc : proc.pDesc.childrenList) {
			destrProc.add(crProc);
		}
		for (Process desProc : destrProc) {
			destroyProcess(desProc);
		}

		// 3 - remove from all lists
		this.readyProcesses.remove(proc);
		this.blockedProcesses.remove(proc);
		this.stoppedProcesses.remove(proc);
		this.processes.remove(proc);

		Process parent = proc.pDesc.parentProcess;
		parent.pDesc.createdResList.remove(proc);

		printStuffDevice("DESTROYED: " + proc.toString());
	}

	/**
	 * Marks process for a stop
	 * 
	 * @param proc
	 */
	public void stopProcess(FProcess proc) {
		if (runProcess.pDesc.intId == proc.pDesc.intId) {
			runProcess = null;
		}

		switch (proc.pDesc.pState) {
		case BLOCKED:
			blockedProcesses.remove(proc);
			stoppedProcesses.add(proc);
			proc.pDesc.pState = ProcessState.STOPPED;
			break;
		case READY:
			readyProcesses.remove(proc);
			stoppedProcesses.add(proc);
			proc.pDesc.pState = ProcessState.STOPPED;
			break;
		case RUN:
			runProcess = null;
			readyProcesses.add(proc);
			proc.pDesc.pState = ProcessState.READY;
			break;
		default:
			break;
		}
		procMan.execute();
	}

	/**
	 * Activates stopped process
	 */
	public void activateProcess(Process proc) {
		if (this.stoppedProcesses.contains(proc)) {
			this.stoppedProcesses.remove(proc);
			this.readyProcesses.add(proc);
			proc.pDesc.pState = ProcessState.READY;
		} else {
			OS.printStuff("ERROR: process is not stopped");
		}
	}

	/**
	 * Create new resource
	 * 
	 * @param creator
	 *            Creator process
	 * @param extId
	 *            Type of process
	 * @param component
	 *            Object containing the resource
	 * @return
	 */
	public Resource createResource(Process creator, ResName extId,
			Object component) {
		int intId = generateResourceInternalID();
		Resource res = null;

		switch (extId) {
		// Vienkartiniai
		case VARTOTOJO_ATMINTIS:
		case UZDUOTIS_VM:
		case UZDUOTIS_ISOR:
		case IVEDIMO_SRAUTAS:
		case IVEDIMO_SRAUTAS2:
		case UZDUOTIS_MP:
		case IVESTA_EILUTE:
		case ISVESTA_EILUTE:
		case EILUTE_ATMINTYJE:
		case PRANESIMAS_LOADER:
		case PRANESIMAS_PERTR:
		case PERTRAUKIMAS:
		case PRANESIMAS_GETLINE:
		case MOS_PABAIGA:
		case UZDUOTIS_VYKDYMUI:
		case RESUME_VM:

			res = new Resource(intId, extId, this, creator, false, component);
			break;

		// Daugkartiniai
		case IVEDIMO_IRENGINYS:
		case ISVEDIMO_IRENGINYS:

			res = new Resource(intId, extId, this, creator, false, component);
			break;

		default:
			throw (new IllegalArgumentException("Illegal resource"));
		}

		printStuffDevice("CREATED RES: " + extId);

		// Add to lists
		creator.pDesc.createdResList.add(res);
		this.resources.add(res);
		this.freeResources.add(res);
		this.resMan.execute();
		return res;
	}

	/**
	 * Releases the resource and puts it to the list of free resources
	 * 
	 * @param res
	 */
	public void releaseResource(Resource res) {
		Process currentUser;
		if (res.getResDesc().getUser() != null) {
			currentUser = res.getResDesc().getUser();
			currentUser.pDesc.ownedResList.remove(res);
			res.getResDesc().setUser(null);
		}

		this.usedResources.remove(res);
		this.freeResources.add(res);

		resMan.execute();
	}

	/**
	 * Releases the resource and removes from lists
	 * 
	 * @param res
	 */
	public void destroyResource(Resource res) {
		Process currentUser;
		if (res.getResDesc().getUser() != null) {
			currentUser = res.getResDesc().getUser();
			currentUser.pDesc.ownedResList.remove(res);
			res.getResDesc().setUser(null);
		}

		res.resDesc.getCreatorProcess().pDesc.createdResList.remove(res);
		this.freeResources.remove(res);
		this.usedResources.remove(res);
		this.resources.remove(res);

		// resMan.execute();
	}

	public void giveResource(Process proc, Resource res) {
		this.resMan.giveResource(proc, res);
	}

	public boolean requestResource(Process proc, ResName res) {
		proc.pDesc.waitingFor.add(res);
		this.resMan.execute();

		if (proc.pDesc.waitingFor.size() == 0)
			return true;
		else
			return false;
	}

	private int generateProcessInternalID() {
		newProcId++;
		return newProcId;
	}

	private int generateResourceInternalID() {
		int newId = Resource.numberOfInstances;
		Resource.numberOfInstances++;
		return newId;
	}

	/**
	 * Print stuff to console/log
	 * 
	 * @param stuff
	 *            line to print
	 */
	public static void printStuff(String stuff) {
		printStuff(stuff, Level.FINE,
				Thread.currentThread().getStackTrace()[0].getClassName());
	}

	/**
	 * Print stuff to console/log with level
	 * 
	 * @param stuff
	 *            line to print
	 */
	public static void printStuff(String stuff, Level lvl, Object obj) {
		if (VERBOSE) {
			System.out.println(stuff);
		}

		if (LOG) {
			Logger.getLogger("fLog").log(lvl, stuff, obj);
		}

	}

	public void printStuffDevice(String stuff) {
		final String tmp = stuff;
		if (VERBOSE) {
			System.out.println(stuff);
		}

		if (LOG) {
			Logger.getLogger("fLog").log(Level.FINE, stuff);
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				realMachine.sysOutputDevice.appendLine("OS", tmp);
			}
		});
	}

	/**
	 * Adds some jobs to test OS - creates "IVEDIMO_SRAUTAS"
	 */
	public void addTestJobs() {
		this.createResource(starStopProc, ResName.IVEDIMO_SRAUTAS, new File(
				TEST_FILEPATH2));
		this.createResource(starStopProc, ResName.IVEDIMO_SRAUTAS, new File(
				TEST_FILEPATH3));
		this.createResource(starStopProc, ResName.IVEDIMO_SRAUTAS, new File(
				TEST_FILEPATH));
		this.createResource(starStopProc, ResName.IVEDIMO_SRAUTAS, new File(
				TEST_FILEPATH2));
		this.createResource(starStopProc, ResName.IVEDIMO_SRAUTAS, new File(
				TEST_FILEPATH2));
		this.createResource(starStopProc, ResName.IVEDIMO_SRAUTAS, new File(
				TEST_FILEPATH2));

	}

	private int getTimer() {
		return realMachine.getCpu().getRegTIME().getValue();
	}

	public void decrementTimer() {
		int timer = getTimer();
		if (timer > 0) {
			timer--;
			realMachine.getCpu().getRegTIME().setValue(timer);
		}
	}

	/**
	 * creates resource without running resMan
	 * 
	 * @param creator
	 *            creator of resource
	 * @param extId
	 *            external ID
	 * @param component
	 *            object of resource
	 * @return
	 */
	public Resource createSimpleResource(Process creator, ResName extId,
			Object component) {
		Resource res = new Resource(generateResourceInternalID(), extId,
				this, creator, false, component);
		resources.add(res);
		return res;
	}

	private void initInputDevices() {

		// USER
		realMachine.usrInputDevice.getInpField().addActionListener(
				realMachine.usrInputDevice.usrAL);
		realMachine.usrInputDevice.getButton().addActionListener(
				realMachine.usrInputDevice.usrAL);

		// SYSTEM
		realMachine.sysInputDevice.getInpField().addActionListener(
				realMachine.sysInputDevice.sysAL);
		realMachine.sysInputDevice.getButton().addActionListener(
				realMachine.sysInputDevice.sysAL);
		realMachine.sysInputDevice.getInpField().setEditable(true);
		realMachine.sysInputDevice.getInpField().setEnabled(true);
	}

	public Machine getRealMachine() {
		return realMachine;
	}

	public void setRealMachine(Machine realMachine) {
		this.realMachine = realMachine;
	}

}
