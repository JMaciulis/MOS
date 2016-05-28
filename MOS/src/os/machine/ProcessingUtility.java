package os.machine;

import os.processes.VirtualMachine;

public class ProcessingUtility {

    
    
    public static void executeCommand(VirtualMachine machine, String command){
        // Pagal komandu skaitliuko registra nuskaitoma komanda, nustatoma ar tokia yra, jei yra - ivykdoma
        //String komanda = gautiZodiPagalVirtualuAdresa(ks);
        //String komandosPr = komanda.substring(0, 2);
        //String adresas = komanda.substring(2, 4);
        //int adresasDesimtainis = hex2decimal(adresas);
        //String x = null;
        //String y = null;
        
        /* String command - laikoma komanda, dekodavimas pagal ją.
           Išskaidysime char'akteriais.
         */
        char commandFirstChar = command.charAt(0);
        char commandSecondChar = command.charAt(1);
        
        if(command.equals("HALF")){
            // Pabaiga.
        }
        switch(commandFirstChar){
            case 'C':
            	if(command.equals("CF0")){
                    
                } else if(command.equals("CF1")){
                    
                } else if(command.equals("CF2")){
                    
                } else if(command.equals("CH10")){
                    
                } else if(command.equals("CH11")){
                    
                } else if(command.equals("CH20")){
                    
                } else if(command.equals("CH21")){
                    
                } else if(command.equals("CH30")){
                    
                } else if(command.equals("CH31")){
                    
                } else {
                    /* Visos įmanomos komandos patikrintos, jei atėjome 
                     * iki čia - komanda yra Cxy
                     */
                    
                }
                break;
            case 'P':
                if(command.equals("PT0")){
                    
                } else if(command.equals("PT1")){
                    
                } else if(command.equals("PT2")){
                    
                } else if(command.equals("PI0")){
                    
                } else if(command.equals("PI1")){
                    
                } else if(command.equals("PI2")){
                    
                } else{
                    /* Visos įmanomos komandos patikrintos, jei atėjome 
                     * iki čia - komanda yra Pxyz
                     */
                    
                }
                break;
            case 'S':
                if(command.equals("SI0")){
                    
                } else if(command.equals("SI1")){
                    
                } else if(command.equals("SI2")){
                    
                } else if(command.equals("SI3")){
                    
                } else if(command.equals("SKDE")){
                    
                } else if(command.equals("SI1")){
                    
                }
                break;
            case 'T':
                if(command.equals("TI1")){
                    
                } else if(command.equals("TI3")){
                    
                } else if(commandSecondChar == 'I'){ // TIxy
                    
                } else { // Txyz
                    
                }
                break;
            case 'M':
                if(command.equals("MOD0")){
                    
                } else if(command.equals("MOD1")){
                    
                }
                break;
            case 'X':
                if(command.equals("XCHG")){
                    
                }
            case 'F':
                // Fxyz
                
                break;
            case 'E':    
                //Exyz
                
                break;
            case 'L':
                //Lxyz
                
                break;
            case 'G':
                //Gxyz
                
                break;
            case 'R':
                //Rxyz
                
                break;
            case 'K':
                //Kxyz
                
                break;
            default:
                // Neleistina operacija. 
                
            	// PI = 2 (neleistinas operacijos kodas).
                //this.PI = 2;
                
                break;
        }
        //if (sv < 224 || sv > 255) {
        	//stekas pazeidzia atminti
        //	this.PI = 1;
        //}
        //this.TI--;
    }
}
