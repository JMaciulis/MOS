package os;

import java.util.ArrayList;
import java.util.List;

public class ProcessorDescriptor {
	private int processCount; // procesu skaicius
	private List<Integer> proc = new ArrayList(); // i-tojo proceso vidinis vardas
	
	public void addProc(int intId){
		proc.add(intId);
	}
	
	public List<Integer> getProc() {
		return proc;
	}
	
	public void setProc(List<Integer> proc) {
		this.proc = proc;
	}

	public int getProcessCount() {
		return processCount;
	}

	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}
}
