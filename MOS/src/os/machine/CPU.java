/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.machine;

import os.virtualmachine.registers.Register2B;
import os.virtualmachine.registers.Register4B;
import os.virtualmachine.registers.RegisterInterrupt;
import os.virtualmachine.registers.RegisterLogic;
import os.virtualmachine.registers.RegisterLogicByte;

/**
 *
 * @author Mantas
 */
public class CPU {

    public Register4B regPLR;
    public Register2B regKS;
    public Register2B regSV;
    public Register4B regSK;
    public Register2B regC;
    public Register2B regCA;

    public Register2B regPA;
    public RegisterInterrupt regSI;
    public RegisterInterrupt regPI;
    public RegisterInterrupt regTI;

    public RegisterLogic regMODE;
    public RegisterLogic regCH1;
    public RegisterLogic regCH2;
    public RegisterLogic regCH3;
    public RegisterLogic regCF;
    public RegisterLogic regPT;

    public CPU() {

        this.regPLR = new Register4B();

        this.regKS = new Register2B();
        this.regSV = new Register2B();
        this.regSK = new Register2B();
        this.regC = new Register2B();
        this.regCA = new Register2B();
        this.regPA = new Register2B();

        this.regSI = new RegisterInterrupt();
        this.regPI = new RegisterInterrupt();
        this.regTI = new RegisterInterrupt();

        this.regMODE = new RegisterLogic();
        this.regCH1 = new RegisterLogic();
        this.regCH2 = new RegisterLogic();
        this.regCH3 = new RegisterLogic();

        this.regCF = new RegisterLogic(); // Pakeisti į 1 baito nelogini.
        this.regPT = new RegisterLogic(); // Pakeisti į 1 baito nelogini.

        this.regPI.setValue(0);
        this.regSI.setValue(0);
        this.regTI.setValue(9);

    }

    public void setRegPLR(Register4B regPLR) {
        this.regPLR = regPLR;
    }

    public void setRegKS(Register2B regKS) {
        this.regKS = regKS;
    }

    public void setRegSV(Register2B regSV) {
        this.regSV = regSV;
    }

    public void setRegSK(Register4B regSK) {
        this.regSK = regSK;
    }

    public void setRegC(Register2B regC) {
        this.regC = regC;
    }

    public void setRegCA(Register2B regCA) {
        this.regCA = regCA;
    }

    public void setRegPA(Register2B regPA) {
        this.regPA = regPA;
    }

    public void setRegSI(RegisterInterrupt regSI) {
        this.regSI = regSI;
    }

    public void setRegPI(RegisterInterrupt regPI) {
        this.regPI = regPI;
    }

    public void setRegTI(RegisterInterrupt regTI) {
        this.regTI = regTI;
    }

    public void setRegMODE(RegisterLogic regMODE) {
        this.regMODE = regMODE;
    }

    public void setRegCH1(RegisterLogic regCH1) {
        this.regCH1 = regCH1;
    }

    public void setRegCH2(RegisterLogic regCH2) {
        this.regCH2 = regCH2;
    }

    public void setRegCH3(RegisterLogic regCH3) {
        this.regCH3 = regCH3;
    }

    public void setRegCF(RegisterLogic regCF) {
        this.regCF = regCF;
    }

    public void setRegPT(RegisterLogic regPT) {
        this.regPT = regPT;
    }
    
    

    public Register4B getRegPLR() {
        return regPLR;
    }

    public Register2B getRegKS() {
        return regKS;
    }

    public Register2B getRegSV() {
        return regSV;
    }

    public Register4B getRegSK() {
        return regSK;
    }

    public Register2B getRegC() {
        return regC;
    }

    public Register2B getRegCA() {
        return regCA;
    }

    public Register2B getRegPA() {
        return regPA;
    }

    public RegisterInterrupt getRegSI() {
        return regSI;
    }

    public RegisterInterrupt getRegPI() {
        return regPI;
    }

    public RegisterInterrupt getRegTI() {
        return regTI;
    }

    public RegisterLogic getRegMODE() {
        return regMODE;
    }

    public RegisterLogic getRegCH1() {
        return regCH1;
    }

    public RegisterLogic getRegCH2() {
        return regCH2;
    }

    public RegisterLogic getRegCH3() {
        return regCH3;
    }

    public RegisterLogic getRegCF() {
        return regCF;
    }

    public RegisterLogic getRegPT() {
        return regPT;
    }
    
    

}
