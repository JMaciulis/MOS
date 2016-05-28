/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.virtualmachine.registers;

/**
 *
 * @author Mantas
 */
public class RegisterInterrupt {

    /**
     * Register value
     */
    byte value;

    /**
     * Constructor
     */
    public RegisterInterrupt() {
        value = 0;
    }

    /**
     * @return the value
     */
    public byte getValue() {
        return value;
    }

    /**
     * @param i the value to set
     */
    public void setValue(int i) {
        this.value = (byte) i;
    }

    /**
     * @param i the value to set
     */
    public void setValue(byte i) {
        this.value = i;
    }

}
