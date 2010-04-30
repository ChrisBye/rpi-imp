//DAEMON
//getCode.java
//Written by The Fightin' Mongooses
//Sorry for eclectic conventions... Most of the code was GUI generated and disallows edits.
//I've fixed it up as best as I can to look nice, but there might still be occasional oddities

package daemon;
import java.io.*; //For file management
import javax.swing.DefaultListModel; //For the Default List Model


public class getCode extends javax.swing.JFrame {

    public DefaultListModel varModel = new DefaultListModel(); //Stores the var list's ListModel
    public DefaultListModel algModel = new DefaultListModel();
    String username;

    public void createAlgorithm() {
        if(jTextField1.getText().isEmpty()) { //Don't want a blank title
            jDialog3.setVisible(false);
            jDialog5.setVisible(true);
        }
        else if(new File(jTextField1.getText() + ".alg").exists()) { //If we're overwriting something, check
            jDialog3.setVisible(false);
            jDialog4.setVisible(true);
        }
        else {
            copyTempFile(); //Copy the temporary file as the algorithm
            new File("tmp.alg1").delete();
            try { //Copy the code
                FileOutputStream fout = new FileOutputStream(username + ".usr", true);
                new PrintStream(fout).println(jTextField1.getText());
            } catch (FileNotFoundException ex) {
                System.out.println("Error opening user file!");
                System.exit(-1);
            }

            System.exit(2);
        }

    }

    public void copyTempFile() { //This is for when we need to copy the temporary file over to the actual algorithm file

        try { //Check to see if tmp.alg1 exists... If it does, then grab the variables from it
            FileInputStream fin = new FileInputStream("tmp.alg1");
            FileOutputStream fout = new FileOutputStream(jTextField1.getText() + ".alg");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String tempLine;
            while(!(tempLine = reader.readLine()).equals("def Run():")) { //Print everything before the function
                new PrintStream(fout).println(tempLine);
            }
                new PrintStream(fout).println(tempLine); //Print out "def Run():"
            while(reader.ready()) { //Now keep outputting, but add in spaces
                new PrintStream(fout).println("    " + reader.readLine());
            }
            fin.close();
            fout.close();
        }
        catch (IOException e) { //If it doesn't then no problem, catch the exception and move on.
            System.err.println("Error copying temp file!");
        }

    }
    
    public Boolean errorCheck() { //This supplies warnings if there's bad code or no return value
        if(jTextArea1.getText().indexOf("import") != -1) {
            jDialog1.setVisible(true);
            return false;
        }
        if(jTextArea1.getText().indexOf("eval") != -1) {
            jDialog1.setVisible(true);
            return false;
        }
        if(jTextArea1.getText().indexOf("return") == -1) {
            jDialog2.setVisible(true);
            return false;
        }
        return true; //All set!
    }

    public void getInfo() {
        try { //Check to see if tmp.alg1 exists... If it does, then grab the variables from it
            FileInputStream fin = new FileInputStream("tmp.alg1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line;
            while((line = reader.readLine()).startsWith("#")) { //Grab variables from the temp file
                reader.readLine(); //Skip the lines we don't care about
                reader.readLine();
                reader.readLine();
                reader.readLine();
                varModel.add(varModel.getSize(), line.substring(1)); //Add the line
            }

            do{ //Just throw out data until we get to the actual algorithm
            }while(!reader.readLine().equals("    def Run():"));

            while(reader.ready()) { //Get the actual algorithm part
                jTextArea1.setText(jTextArea1.getText() + reader.readLine().substring(8));
            }

            fin.close(); //Close up fin, and get the algorithm list
            fin = new FileInputStream(username + ".usr");
            reader = new BufferedReader(new InputStreamReader(fin));
            while(reader.ready()) {
                algModel.add(algModel.getSize(), reader.readLine());
            }
        }
        catch (IOException e) { //If it doesn't then catch the exception and move on.
        }

    }

    public void output() {
        FileOutputStream fout;
        try {
            fout = new FileOutputStream("tmp.alg1", true); //Create temporary file

            new PrintStream(fout).println(jTextArea1.getText()); //Print the text area

            fout.close();
        }
        catch (IOException e) {
            System.err.println ("Error opening tmp.alg1"); //Some mistake... crash gently
            System.exit(-1);

        }

    }

    /** Creates new form getCode */
    public getCode(String tempname) {
        username = tempname;

        initComponents();
        this.setLocationRelativeTo( null );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel7 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jDialog3 = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jDialog4 = new javax.swing.JDialog();
        jLabel9 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jDialog5 = new javax.swing.JDialog();
        jLabel10 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jDialog1.setLocationRelativeTo( null );
        jDialog1.setAlwaysOnTop(true);
        jDialog1.setMinimumSize(new java.awt.Dimension(725, 125));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel7.setText("Warning! Import and Eval statements can lead to security holes!");

        jButton7.setText("I understand the risks");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("I'll change the algorithm");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(98, 98, 98))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jDialog2.setLocationRelativeTo( null );
        jDialog2.setAlwaysOnTop(true);
        jDialog2.setMinimumSize(new java.awt.Dimension(450, 125));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel6.setText("Error! Must include a return statement!");

        jButton6.setText("Okay");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jDialog2Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jButton6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDialog3.setLocationRelativeTo( null );
        jDialog3.setMinimumSize(new java.awt.Dimension(500, 125));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel8.setText("What would you like to name the algorithm?");

        jButton9.setText("Okay");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog3Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9)))
                .addContainerGap())
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDialog4.setLocationRelativeTo( null );
        jDialog4.setAlwaysOnTop(true);
        jDialog4.setMinimumSize(new java.awt.Dimension(500, 125));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel9.setText("An algorithm with this name already exists!");

        jButton10.setText("Rename");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Overwrite");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog4Layout = new javax.swing.GroupLayout(jDialog4.getContentPane());
        jDialog4.getContentPane().setLayout(jDialog4Layout);
        jDialog4Layout.setHorizontalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog4Layout.createSequentialGroup()
                .addContainerGap(312, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap())
        );
        jDialog4Layout.setVerticalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDialog5.setLocationRelativeTo( null );
        jDialog5.setAlwaysOnTop(true);
        jDialog5.setMinimumSize(new java.awt.Dimension(475, 125));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel10.setText("You can't have an empty algorithm name!");

        jButton12.setText("Okay");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog5Layout = new javax.swing.GroupLayout(jDialog5.getContentPane());
        jDialog5.getContentPane().setLayout(jDialog5Layout);
        jDialog5Layout.setHorizontalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog5Layout.createSequentialGroup()
                .addContainerGap(400, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addContainerGap())
        );
        jDialog5Layout.setVerticalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setText("Write code here... To add in the result from another algorithm, or use");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel2.setText("one of your variables, highlight it and select the \"Use\" button.");

        jList1.setModel(varModel);
        jList1.setMaximumSize(new java.awt.Dimension(33, 80));
        jList1.setMinimumSize(new java.awt.Dimension(33, 80));
        jScrollPane1.setViewportView(jList1);

        jList2.setModel(algModel);
        jList2.setMaximumSize(new java.awt.Dimension(33, 80));
        jList2.setMinimumSize(new java.awt.Dimension(33, 80));
        jScrollPane2.setViewportView(jList2);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);
        getInfo();

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel3.setText("Variables");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel4.setText("Algorithms");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel5.setText("Your new algorithm");

        jButton1.setText("Use variable");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Use algorithm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Finish");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Cancel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 441, Short.MAX_VALUE)
                                        .addComponent(jButton5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextArea1.append("self.constant[\""+ jList1.getSelectedValue().toString() + "\"]"); //Use variable: append self.constant["variable name"]
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new File("tmp.arg1").delete();
        System.exit(1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextArea1.append("IMP.GetAlgorithms[\"" + jList2.getSelectedValue().toString() + "\"]"); //Use algorithm: append IMP.GetAlgorithms["algorithm name"]
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(errorCheck()) { //Check for import/eval/return errors
            output();
            jDialog3.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jDialog2.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jDialog1.setVisible(false); //They don't want the risks, so we'll give them another chance to change
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jDialog1.setVisible(false); //They've accepted the risks of import/eval, so let's output
        output();
        jDialog3.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        createAlgorithm();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jDialog4.setVisible(false);
        jDialog3.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        jDialog5.setVisible(false);
        jDialog3.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        new File("tmp.alg1").renameTo(new File(jTextField1.getText() + ".alg")); //If we're okay to overwrite, finish things up
            copyTempFile();
            new File("tmp.alg1").delete();
            System.exit(2);
    }//GEN-LAST:event_jButton11ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new getCode().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JDialog jDialog4;
    private javax.swing.JDialog jDialog5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
