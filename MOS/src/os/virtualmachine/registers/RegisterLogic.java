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
public class RegisterLogic {

    /**
     * Register value
     */
    private boolean value;

    /**
     * Default constructor
     */
    public RegisterLogic() {
        this(true);
    }

    /**
     * Constructor Initializes register with initial value
     *
     * @param initVal initial value
     */
    public RegisterLogic(boolean initVal) {
        this.value = initVal;
    }

    /**
     * Returns current register value
     *
     * @return register value
     */
    public boolean isValue() {
        return value;
    }

    /**
     * Set new register value
     *
     * @param value new value
     */
    public void setValue(boolean value) {
        this.value = value;
    }

}
