//DAEMON
//getOptionsForm.java
//Written by The Fightin' Mongooses
//Sorry for eclectic conventions... Most of the code was GUI generated and disallows edits.
//I've fixed it up as best as I can to look nice, but there might still be occasional oddities

package daemon;

import java.util.*; //For the Linked List
import java.io.*; //For file management
import javax.swing.DefaultListModel; //For the Default List Model
/**
 *
 * @author Dave
 */
public class getOptionsForm extends javax.swing.JFrame {

    public List myVars = new LinkedList(); //Used to store the variables
    public Variable tempVar = new Variable(); //Used to store a temporary Variable for editing
    public DefaultListModel listModel = new DefaultListModel(); //Stores the list's ListModel
    public DefaultListModel algModel = new DefaultListModel();
    public static int MIN_INT = -2147483647; //Java has no preprocessor, so this is the closest I can get to #DEFINE MIN_INT -2147483647
    public static int MAX_INT = 2147483647;
    String username;

    public class Variable {
        String name;
        int min = MIN_INT;
        int max = MAX_INT;
        int digits = -1;
        float interval = -1;

    }
    
    public void intervalCheck() { //This checks the interval dialog box to make sure everything's good
        if(!jTextField6.getText().matches("\\d+(\\.\\d+)?")) { //Use regex to make sure it's at least one digit, digits only
            jLabel16.setText("Invalid! Make sure the interval is a non-negative whole number or blank");
            jDialog6.setVisible(true);
            return; //Make sure to exit out            
        }
        if(Float.parseFloat(jTextField6.getText()) <= 0) { //Should only fire if it's equal to zero, but <= just in case
            jLabel16.setText("Invalid! Can't have a 0 interval");
            jDialog6.setVisible(true);
            return;
        }
        jDialog5.setVisible(false);
        if(!jTextField6.getText().isEmpty()) {
            tempVar.interval = Float.parseFloat(jTextField6.getText());
        }
        myVars.add(tempVar);
        listModel.add(listModel.getSize(), tempVar.name);
    }

    public void digitsCheck() { //This checks the digits dialog box to make sure everything's good
        if(!jTextField5.getText().matches("(\\d+)?")) { //Use regex to make sure it's at least one digit, digits only
            jLabel16.setText("Invalid! Make sure digits is a non-negative whole number or blank");
            jDialog6.setVisible(true);
            return; //Make sure to exit out
        }
        jDialog4.setVisible(false);
        jDialog5.setVisible(true);
        if(!jTextField5.getText().isEmpty()) {
            tempVar.digits = Integer.parseInt(jTextField5.getText());
        }
        if(tempVar.interval != -1) {
            jTextField6.setText(Float.toString(tempVar.interval));
        }
        else jTextField6.setText("1");
    }
    
    public void minmaxCheck() { //This checks the minmax dialog box to make sure everything's good
        if(!jTextField3.getText().matches("(-?\\d+(\\.\\d+)?)?")) { //Use regex to make sure it starts off with 0 or 1 -, then at least one digit, followed by maybe a decimal point with at least one digit
            jLabel16.setText("Invalid! Make sure min is a number or blank");
            jDialog6.setVisible(true);
            return; //Make sure to exit out            
        }
        if(!jTextField4.getText().matches("(-?\\d+(\\.\\d+)?)?")) { //Use regex to make sure it starts off with 0 or 1 -, then at least one digit, followed by maybe a decimal point with at least one digit
            jLabel16.setText("Invalid! Make sure max is a number or blank");
            jDialog6.setVisible(true);
            return; //Make sure to exit out
        }

        int min = MIN_INT; //We're setting up temporary values for min and max... We need to compare them
        int max = MAX_INT;
        if (!jTextField3.getText().isEmpty()) {
            min = Integer.parseInt(jTextField3.getText());
        }
        if (!jTextField4.getText().isEmpty()) {
            max = Integer.parseInt(jTextField4.getText());
        }
        if(min > max) { //If min is greater than max, we have a problem
            jLabel16.setText("Invalid! Make sure min is less than max");
            jDialog6.setVisible(true);
            return; //Make sure to exit out              
        }
        tempVar.min = min; //Set the variables
        tempVar.max = max;

        if(tempVar.digits != -1) {
            jTextField5.setText(Integer.toString(tempVar.digits));
        }
        else jTextField5.setText("");
        jDialog3.setVisible(false); //Do the normal switch to the next dialogue box
        jDialog4.setVisible(true);

    }

    public void nameCheck() { //This checks the name dialog box to make sure everything's good
        for(int counter = 0; counter < jList1.getModel().getSize(); counter++) { //Do some error checking
            if(jList1.getModel().getElementAt(counter).equals(jTextField1.getText())) {
                jLabel16.setText("Invalid! Name already in use");
                jDialog6.setVisible(true);
                return; //Make sure to exit out
            }
        }
        if(jTextField1.getText().isEmpty()) { //Do some more error checking
            jLabel16.setText("Don't use an empty name");
            jDialog6.setVisible(true);
        }
        else { //If everything's set, let's save the name to the temp variable and continue on
            jDialog1.setVisible(false);
            jDialog3.setVisible(true);
            tempVar.name = jTextField1.getText();
            if(tempVar.min != MIN_INT) { //Only display min or max if they're not the defaults
                jTextField3.setText(Integer.toString(tempVar.min));
            }
            else jTextField3.setText("");
            if(tempVar.max != MAX_INT) {
            jTextField4.setText(Integer.toString(tempVar.max));
            }
            else jTextField4.setText("");
        }
    }


    public void outputVar(Variable myVar, FileOutputStream fout) {
        new PrintStream(fout).print("        self.constants.add(UserSetConstant(\"" + myVar.name + "\"");
        if(myVar.min != MIN_INT) { //Run through each element contained in a variable. Output them if they're not default values
            new PrintStream(fout).print(", min=" + Integer.toString(myVar.min));
        }
        if(myVar.max != MAX_INT) {
            new PrintStream(fout).print(", max=" + Integer.toString(myVar.max));
        }
        if(myVar.digits >= 0) {
            new PrintStream(fout).print(", digits=" + Integer.toString(myVar.digits));
        }
        if(myVar.interval > 0) {
            new PrintStream(fout).print(", incr=" + Float.toString(myVar.interval));
        }
        new PrintStream(fout).println("))"); //End with two )) and a new line
    }

    public void output() { //This prints out the beginning of dynamically created Python script to tmp.alg1
        FileOutputStream fout; //Some helpful variables for playing around with files
        FileInputStream fin;
        BufferedReader reader;
        try {

            if(new File("tmp.alg1").exists()) { //If the temp file already exists, we want to save the algorithm
                fout = new FileOutputStream("tmp.alg2"); //Copy everything over
                fin = new FileInputStream("tmp.alg1");
                reader = new BufferedReader(new InputStreamReader(fin));
                while(reader.ready()) {
                    new PrintStream(fout).println(reader.readLine());
                }
                fin.close();
                fout.close();
            }

            fout = new FileOutputStream("tmp.alg1"); //Create temporary file

            for(int counter = 0; counter < myVars.size(); counter++) { //Make commented out values for each variable so they're easy to retrieve
                tempVar = (Variable) myVars.get(counter);
                new PrintStream(fout).println("#" + tempVar.name);
                new PrintStream(fout).println("#" + Integer.toString(tempVar.min));
                new PrintStream(fout).println("#" + Integer.toString(tempVar.max));
                new PrintStream(fout).println("#" + Integer.toString(tempVar.digits));
                new PrintStream(fout).println("#" + Float.toString(tempVar.interval));
            }

            new PrintStream(fout).println("class Algorithm():"); //Print out the standard block of code
            new PrintStream(fout).println("    def __init__(self):");
            new PrintStream(fout).println("        self.constants = UserSetConstantContainer()"); //This is a container for user created variables
            for(int counter = 0; counter < myVars.size(); counter++) { //We need an output for each variable
                outputVar((Variable) myVars.get(counter), fout);
            }

            new PrintStream(fout).println("    def Options(self:)");
            new PrintStream(fout).println("        return self.constants");

            new PrintStream(fout).println("    def Run(self):");
            if(new File("tmp.alg2").exists()) { //If the tmp2 file exists, we have copying to do
                fin = new FileInputStream("tmp.alg2");
                reader = new BufferedReader(new InputStreamReader(fin));
                while(!reader.readLine().equals("    def Run(self):")) {} //Chop off data until we get to the algorithm
                while(reader.ready()) {
                    new PrintStream(fout).println(reader.readLine());
                }
                fin.close();
                new File("tmp.alg2").delete();
                fout.close();
            }

            fout.close();

        }
        catch (IOException e) {
            System.err.println ("Error opening tmp.alg1"); //Some mistake... crash gently
            System.exit(-1);

        }
    }

    /** Creates new form getOptionsForm */
    public getOptionsForm(String tempname) {
        username = tempname;
        try { //Check to see if tmp.alg1 exists... If it does, then grab the variables from it
            FileInputStream fin = new FileInputStream("tmp.alg1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            tempVar = new Variable();
            while((tempVar.name = reader.readLine()).startsWith("#")) { //Grab variables from the temp file
                tempVar.name = tempVar.name.substring(1);
                tempVar.min = Integer.parseInt(reader.readLine().substring(1));
                tempVar.max = Integer.parseInt(reader.readLine().substring(1));
                tempVar.digits = Integer.parseInt(reader.readLine().substring(1));
                tempVar.interval = Float.parseFloat(reader.readLine().substring(1));
                myVars.add(tempVar);
                listModel.add(listModel.getSize(), tempVar.name);
                tempVar = new Variable();
            }
            fin.close();
        }
        catch (IOException e) { //If it doesn't then no problem, catch the exception and move on.
        }
        initComponents();
        this.setLocationRelativeTo( null );
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jDialog3 = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jDialog4 = new javax.swing.JDialog();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jDialog5 = new javax.swing.JDialog();
        jLabel14 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jDialog6 = new javax.swing.JDialog();
        jLabel16 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();

        jDialog1.setLocationRelativeTo( null );
        jDialog1.setMinimumSize(new java.awt.Dimension(525, 175));
        jDialog1.setResizable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel6.setText("What would you like the variable name to be?");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 23));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton4.setText("Next");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Cancel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jDialog3.setLocationRelativeTo( null );
        jDialog3.setMinimumSize(new java.awt.Dimension(525, 275));
        jDialog3.setResizable(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel8.setText("What would you like the min/max to be?");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 24));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel9.setText("(Leave blank if you want no min/max)");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 24));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel10.setText("Min");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel11.setText("Max");

        jButton8.setText("Next");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Cancel");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jDialog3Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))))
                    .addGroup(jDialog3Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel10)
                        .addGap(191, 191, 191)
                        .addComponent(jLabel11))
                    .addGroup(jDialog3Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel9)))
                .addContainerGap(319, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jButton8)
                .addGap(150, 150, 150)
                .addComponent(jButton9)
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(35, 35, 35)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3))
                .addGap(18, 18, 18)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton8))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jDialog4.setLocationRelativeTo( null );
        jDialog4.setMinimumSize(new java.awt.Dimension(525, 200));
        jDialog4.setModal(true);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel12.setText("How many decimal places do you want?");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel13.setText("(Leave blank for no requirement, 0 if you want whole numbers)");

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 24));

        jButton10.setText("Next");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Cancel");
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
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel13))
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jDialog4Layout.createSequentialGroup()
                                .addComponent(jButton10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton11))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jDialog4Layout.setVerticalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jDialog5.setLocationRelativeTo( null );
        jDialog5.setMinimumSize(new java.awt.Dimension(575, 200));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel14.setText("What would you like the default increment to be?");

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 24));
        jTextField6.setText("1");

        jButton12.setText("Finish");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Cancel");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog5Layout = new javax.swing.GroupLayout(jDialog5.getContentPane());
        jDialog5.getContentPane().setLayout(jDialog5Layout);
        jDialog5Layout.setHorizontalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addGroup(jDialog5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 401, Short.MAX_VALUE)
                        .addComponent(jButton13))
                    .addGroup(jDialog5Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog5Layout.setVerticalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(41, 41, 41)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        jDialog6.setLocationRelativeTo( null );
        jDialog6.setMinimumSize(new java.awt.Dimension(750, 150));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel16.setText("You shouldn't see this!");

        jButton14.setText("Okay!");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog6Layout = new javax.swing.GroupLayout(jDialog6.getContentPane());
        jDialog6.getContentPane().setLayout(jDialog6Layout);
        jDialog6Layout.setHorizontalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addGroup(jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16))
                    .addGroup(jDialog6Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(jButton14)))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        jDialog6Layout.setVerticalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.red);
        setResizable(false);

        jList1.setModel(listModel);
        jList1.setMinimumSize(new java.awt.Dimension(720, 494));
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("New");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 23));
        jLabel1.setText("Add new variables with the \"New\" button.");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 23));
        jLabel2.setText("You can pick the variable name, its type");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 23));
        jLabel3.setText("and how it's described to the user.");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 23));
        jLabel4.setText("Highlight an existing variable and select");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 23));
        jLabel5.setText("\"Edit\" or \"Remove\" to modify the variable");

        jButton6.setText("Next");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton15.setText("Cancel");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1)
                            .addComponent(jButton3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton15))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tempVar = new Variable(); //We're creating a temporary variable to edit at each dialogue
        jDialog1.setVisible(true);
        jTextField1.setText(tempVar.name);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jDialog1.setVisible(false); //This is a cancel button
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        nameCheck();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jDialog3.setVisible(false); //Cancel button
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        minmaxCheck();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        digitsCheck();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        jDialog4.setVisible(false);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        intervalCheck();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        jDialog5.setVisible(false);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int index = jList1.getSelectedIndex();
        if(index != -1) {
            tempVar = (Variable) myVars.get(index);
            myVars.remove(index);
            listModel.remove(index);
            jDialog1.setVisible(true);
            jTextField1.setText(tempVar.name);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int index = jList1.getSelectedIndex();
        if(index != -1) {
            myVars.remove(index);
            listModel.remove(index);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        jDialog6.setVisible(false); //Break out of error dialogue box
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        output();
        this.setVisible(false);
        new getCode(username).setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        new File("tmp.arg1").delete();
        System.exit(1); //This is the cancel button
    }//GEN-LAST:event_jButton15ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new getOptionsForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JDialog jDialog4;
    private javax.swing.JDialog jDialog5;
    private javax.swing.JDialog jDialog6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

}
