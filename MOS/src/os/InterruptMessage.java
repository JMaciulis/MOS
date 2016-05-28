/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import os.OS.IntType;

/**
 *
 * @author Mantas
 */
public class InterruptMessage {

	public IntType action;
	public int procIntId;
	public InterruptMessage(IntType action, int procIntId) {
		this.action = action;
		this.procIntId = procIntId;
	}

}