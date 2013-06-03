package trussoptimizater.Gui.Dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.Tables.*;
import trussoptimizater.Gui.Tables.ElementOutputTable;
import trussoptimizater.Gui.Tables.PassFailCellRenderer;
import trussoptimizater.Gui.Tables.NodeOutputTable;
import trussoptimizater.Gui.Tables.StressOutputTable;
import trussoptimizater.Gui.Tables.SupportOutputTable;
import trussoptimizater.Gui.TrussFactory;
import trussoptimizater.Truss.Analysis.AnalysisMethods;
import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.TrussModel;
import trussoptimizater.Truss.Utils;

/**
 *
 *
 * Base results were done with the following settings:
 *
 * Youngs moduls 2.05*1-^8 KN/m^2
 * Gravity 9.81 m/s^2
 * Yield Strength 0.275 KN/mm^2
 * Includes Self Weight
 *
 *
 * Compares:
 *
 * Nodal Deflections
 * Bar Forces
 * Bar Stresses
 * Support Reactions
 *
 *
 * @author Chris
 */
public class RegressionToolDialog {

    private javax.swing.JDialog dialog;
    private JTable table;
    private TrussModel[] trussModels;
    private JTextPane textPane;
    private JComboBox analyzers;
    public static final int NO_DECIMAL_PLACES_TO_CHECK_TO = 5;
    public static final String[] TEST_CASES = {"45 bar roller Pratt Truss",
        "15 bar single PL Cantilever Truss",
        "35 bar multiple PL Cantilever Truss",
        "7 bar Warren Truss",
        "9 bar Pratt Truss"};
    public static final String[] ANALYZER_PATHS = {"/BaseResults/FrameAnalyzer", "/BaseResults/PinnedAnalyzer"};
    public static final String[] TEST_CASE_PATHS = {"/45BarRollerPratt/",
        "/15BarSinglePL/",
        "/35BarMultiplePL/",
        "/7BarWarren/",
        "/9BarPratt/"};
    public static final String[] FILE_NAMES = {"Deflections.csv", "Forces.csv", "Stresses.csv", "Reactions.csv"};
    private ArrayList<ElementOutputTable[]> outputTables = new ArrayList<ElementOutputTable[]>();
    private String[] regressionOutput = new String[TEST_CASES.length];
    public static final String INTRO_STRING = "<br>Regresson Tool compares:<br>"
            + " - Nodal Deflections<br>"
            + " - Bar Forces<br>"
            + " - Bar Stresses<br>"
            + " - Support Reactions<br>"
            + " <br>Base results were done with the following settings:<br>"
            + " - Youngs moduls 2.05*1-^8 KN/m^2<br>"
            + " - Gravity 9.81 m/s^2<br>"
            + " - Yield Strength 0.275 KN/mm^2<br>"
            + " - Includes Self Weight<br>";
    private boolean hasRun = false;

    public RegressionToolDialog(GUI gui) {
        dialog = new javax.swing.JDialog(gui.getFrame(), "Regression Testing Tool", false);
        initTestCases();
        initTables();

    }

    /**
     * Initilize an array of output tables for each test case
     */
    private void initTables() {
        ElementOutputTable[] tables;
        for (int i = 0; i < trussModels.length; i++) {//
            tables = new ElementOutputTable[FILE_NAMES.length];
            tables[0] = new NodeOutputTable(trussModels[i], null);
            tables[1] = new BarOutputTable(trussModels[i], null);
            tables[2] = new StressOutputTable(trussModels[i]);
            tables[3] = new SupportOutputTable(trussModels[i], null);
            outputTables.add(tables);
        }

    }

    /**
     * Initilize a truss model for each test case. In each truss model create a truss using TrussFactory
     */
    private void initTestCases() {
        trussModels = new TrussModel[RegressionToolDialog.TEST_CASES.length];
        for (int i = 0; i < RegressionToolDialog.TEST_CASES.length; i++) {
            trussModels[i] = new TrussModel();
        }
        TrussFactory.create45BarPrattTruss(trussModels[0]);
        TrussFactory.create15BarCantilever(trussModels[1]);
        TrussFactory.create35BarCantilever(trussModels[2]);
        TrussFactory.create7BarWarrenTruss(trussModels[3]);
        TrussFactory.create9BarPrattTruss(trussModels[4]);

    }

    /**
     * Create the gui
     */
    public void createGui() {

        analyzers = new JComboBox(new String[]{"Frame", "Pin-Jointed"});
        JPanel analysisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        analysisPanel.add(new JLabel("Analysis Method"));
        analysisPanel.add(analyzers);



        String[] columnNames = {"Check", "Test Case", "Pass/Fail"};
        Object[][] data = new Object[RegressionToolDialog.TEST_CASES.length][columnNames.length];

        for (int i = 0; i < RegressionToolDialog.TEST_CASES.length; i++) {
            data[i][0] = false;
            data[i][1] = RegressionToolDialog.TEST_CASES[i];
            data[i][2] = "-";
        }

        table = new JTable(data, columnNames) {

            @Override
            public Class getColumnClass(int c) {
                return super.getValueAt(0, c).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (hasRun) {
                    textPane.setText(regressionOutput[table.getSelectedRow()]);
                }

            }
        });

        table.setGridColor(Color.black);
        table.setShowGrid(
                true);
        table.setShowHorizontalLines(
                true);
        table.setShowVerticalLines(
                true);
        table.setIntercellSpacing(
                new Dimension(1, 1));
        table.setAutoCreateRowSorter(
                true);
        table.setRowHeight(
                20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn col = table.getColumnModel().getColumn(0);

        col.setPreferredWidth(50);
        col = table.getColumnModel().getColumn(1);

        col.setPreferredWidth(270);
        col = table.getColumnModel().getColumn(2);

        col.setCellRenderer(new PassFailCellRenderer());
        col.setPreferredWidth(
                80);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(
                new Dimension(
                400, 200));


        textPane = new JTextPane();
        textPane.setContentType(
                "text/html");
        textPane.setText(INTRO_STRING);
        textPane.setPreferredSize(
                new Dimension(
                400, 200));

        JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, new JScrollPane(textPane));


        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int firstCheckedRow = getFirstCheckedRow();
                if (firstCheckedRow == -1) {
                    textPane.setText("<font color=\"red\">You have not selected any test cases to run!</font>");
                } else {
                    run();
                    textPane.setText(regressionOutput[firstCheckedRow]);
                    hasRun = true;
                }

            }
        });


        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(cancelButton);
        buttonPanel.add(runButton);
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.add(buttonPanel);

        dialog.setResizable(false);
        dialog.add(analysisPanel, BorderLayout.NORTH);
        dialog.add(splitPanel, BorderLayout.CENTER);
        dialog.add(buttonContainer, BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
    }

    /**
     * Loops through table and returns the index of the first checked row. If no rows
     * are checked it returns -1
     * @return Index of first checked row
     */
    private int getFirstCheckedRow() {
        for (int i = 0; i < table.getRowCount(); i++) {
            //testing whether checkbox is ticked
            if ((Boolean) table.getValueAt(i, 0)) {
                return i;
            }
        }
        return -1;
    }

    public void showGui() {
        this.dialog.setVisible(true);
    }

    public JDialog getDialog() {
        return dialog;
    }

    /**
     * This method is invoked when User clicks on "run" button. It will compare selected test cases
     * against base results.
     */
    private void run() {

        Object[][] ouputTableData = null;
        ArrayList<Object[]> baseResult = null;

        for (int i = 0; i < regressionOutput.length; i++) {
            regressionOutput[i] = "";
        }


        //looping through test cases
        for (int i = 0; i < table.getRowCount(); i++) {
            //reset pass/fail values
            table.setValueAt("-", i, 2);

            //testing whether checkbox is ticked
            if (!(Boolean) table.getValueAt(i, 0)) {
                continue;
            }

            switch (this.analyzers.getSelectedIndex()) {
                case 0:
                    //If testing frame analyzer make sure all horizotal bars are chords (ie fixed restraints)
                    setChordRestraintsToFIXED_FIXED(trussModels[i]);
                    trussModels[i].getAnalysisMethods().setAnalysisMethod(AnalysisMethods.FRAME_STIFFNESS_METHOD);
                    break;
                case 1:
                    setChordRestraintsToPINNED_PINNED(trussModels[i]);
                    trussModels[i].getAnalysisMethods().setAnalysisMethod(AnalysisMethods.PIN_JOINTED_STIFFNESS_METHOD);
                    break;
            }
            trussModels[i].getAnalysisMethods().getAnalyzer().fullAnalysis();

            regressionOutput[i] += "Test Case: " + TEST_CASES[i] + "<br>";

            for (int j = 0; j < RegressionToolDialog.FILE_NAMES.length; j++) {
                regressionOutput[i] += "<br>Comparing File: " + FILE_NAMES[j] + "<br>";
                try {
                    String fileName = ANALYZER_PATHS[analyzers.getSelectedIndex()] + TEST_CASE_PATHS[i] + FILE_NAMES[j];
                    ouputTableData = outputTables.get(i)[j].getData();
                    baseResult = getBaseResults(fileName);
                } catch (Exception ex) {
                    table.setValueAt("ERROR", i, 2);
                    regressionOutput[i] += "<br><font color=\"red\">There has been an error comparing data in Regression Tool " + ex + "</font><br>";
                    System.out.println("There has been an error comparing data in Regression Tool " + ex);
                    break;
                }
                //Utils.printArray(ouputTableData);
                if (compareArrays(ouputTableData, baseResult, i)) {
                    regressionOutput[i] += "<br>FINISHED<br><font color=\"green\">PASS</font><br>";
                    table.setValueAt("PASS", i, 2);
                } else {
                    regressionOutput[i] += "<br>FINISHED<br><font color=\"red\">FAIL</font><br>";
                    table.setValueAt("FAIL", i, 2);
                    break;
                }
            }
        }
    }

    /**
     * Set all horizontal bars i.e chords to FIXED_FIXED restraint.
     * @param truss
     */
    private void setChordRestraintsToFIXED_FIXED(TrussModel truss) {
        for (int j = 0; j < truss.getBarModel().size(); j++) {
            if (truss.getBarModel().get(j).isHorizontal()) {
                truss.getBarModel().get(j).setRestraint(Bar.FIXED_FIXED_RESTRAINT);
            }
        }
    }

    /**
     * Set all bars to PINNED_PINNED restraint.
     * @param truss
     */
    private void setChordRestraintsToPINNED_PINNED(TrussModel truss) {
        for (int j = 0; j < truss.getBarModel().size(); j++) {
            truss.getBarModel().get(j).setRestraint(Bar.PINNED_PINNED_RESTRAINT);
        }
    }

    /**
     *
     * @param fileName Name of the csv file you are trying to read
     * @return Object representation of file
     * @throws Exception
     */
    private ArrayList<Object[]> getBaseResults(String fileName) throws Exception {

        ArrayList<Object[]> data = new ArrayList<Object[]>();
        System.out.println("Getting results for " + fileName);
        BufferedReader fileR = null;

        try {
            String n = null;
            InputStream instream = getClass().getResourceAsStream(fileName);
            InputStreamReader instreamReader = new InputStreamReader(instream);
            fileR = new BufferedReader(instreamReader);

            while (true) {
                n = fileR.readLine();
                if (n == null) {
                    break;
                } else {
                    data.add(n.split(","));
                }
            }
        } finally {
            if (fileR != null) {
                fileR.close();
            }
        }
        return data;
    }

    /**
     *
     * @param a Data from output table
     * @param b Base result read from file.
     * @param logIndex
     * @return true if a and b are the same to an accuracy of NO_DECIMAL_PLACES_TO_CHECK_TO
     */
    private boolean compareArrays(Object[][] a, ArrayList<Object[]> b, int logIndex) {

        if (a.length != b.size() || a[0].length != b.get(0).length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] == null && b.get(i)[j].toString().equals("null")) {
                    continue;
                } else if (a[i][j] == null && !b.get(i)[j].toString().equals("null")) {
                    System.out.println("Comparing " + a[i][j] + " and " + b.get(i)[j].toString());
                    regressionOutput[logIndex] += "Comparing " + a[i][j] + " and " + b.get(i)[j].toString() + "<br>";
                    return false;
                }

                String s1 = a[i][j].toString();
                String s2 = b.get(i)[j].toString();

                if (s1.length() > NO_DECIMAL_PLACES_TO_CHECK_TO + 1) {
                    s1 = s1.substring(0, NO_DECIMAL_PLACES_TO_CHECK_TO);
                }

                if (s2.length() > NO_DECIMAL_PLACES_TO_CHECK_TO + 1) {
                    s2 = s2.substring(0, NO_DECIMAL_PLACES_TO_CHECK_TO);
                }
                System.out.println("Comparing " + s1 + " and " + s2);
                regressionOutput[logIndex] += "Comparing " + s1 + " and " + s2 + "<br>";
                if (!s1.equals(s2)) {
                    return false;
                }

            }
        }
        return true;
    }
}
