package devices;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JTextField;
import os.OS;

public class InputPanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 4668104880966701462L;
    private OS os;
    
    public InputPanel() {
        initComponents();
    }

    public void initInput(Process initializer){
    	String tmp = initializer.pDesc.intId + ":" +
    			initializer.pDesc.extId + ":" +
    			initializer.pDesc.pName;
        jLabel1.setText(tmp);
        inputField.setEditable(true);
    }
    
    public void setOS(OS os){
        this.os = os;
    }
    
    public JTextField getInpField(){
        return inputField;
    }
    
    public JButton getButton(){
        return enterBut;
    }
    
    public ActionListener usrAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tmp = inputField.getText();
            os.createResource(os.starStopProc, ResName.IVEDIMO_SRAUTAS2, tmp);
            jLabel1.setText("");
            inputField.setText("");
            inputField.setEditable(false);
        }
	};
	
	
	
	public ActionListener sysAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tmp = inputField.getText();
                String prefix = OS.FP_PREFIX;
                String fp = prefix + tmp + OS.FP_END;
			
                File f = new File(fp);
                if (f.exists()){
                    os.createResource(
                    os.starStopProc, ResName.IVEDIMO_SRAUTAS, new File(fp));
                    os.printStuffDevice("SUCCESS: " + fp + " loaded :(");
                } else {
                    os.printStuffDevice("ERROR: " + fp + " does not exist :(");
                }
            }
	};
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        inputField = new javax.swing.JTextField();
        enterBut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(400, 40));

        inputField.setName("inputField"); // NOI18N

        enterBut.setText("Enter");
        enterBut.setName("enterBut"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inputField, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enterBut, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(enterBut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputField, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
    }// </editor-fold>                        
    // Variables declaration - do not modify                     
    private javax.swing.JButton enterBut;
    private javax.swing.JTextField inputField;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration                   
}

