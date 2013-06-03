package trussoptimizater.Gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import jxl.*;
import jxl.write.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import jxl.write.WritableWorkbook;
import trussoptimizater.Gui.Tables.*;

/**
 * This class is used to write table data to an excel file. The excel file is saved in the same directory
 * as your project file. If you have not saved your project, the excel file will be name unknown and will be saved
 * in the same directory as the jar file you have run it from.
 * @author Chris
 */
public class ExcellWriter {

    private GUI gui;
    private WritableSheet writeableSheet;
    private WritableWorkbook workbook;
    /**
     * Cell format with Bold text,light grey background and thin border. It is used for all headings
     */
    private WritableCellFormat headerFormat;
    /**
     * Cell format with normal text, white backgound and thin border. It is used for everything except headers.
     */
    private WritableCellFormat numberFormat;
    /**
     * Number of cells spacing to seperate tables horizontally
     */
    public static final int horizontalTableSpacing = 2;
    /**
     * Number of cells spacing to seperate tables vertically
     */
    public static final int verticalTableSpacing = 3;

    public ExcellWriter(GUI gui) {
        this.gui = gui;
        initExcellAttributes();
    }

    /**
     * Initilize all excell attributes
     */
    private void initExcellAttributes() {

        try {

            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableFont standardFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
            headerFormat = new WritableCellFormat(headerFont);
            numberFormat = new WritableCellFormat(standardFont);

            headerFormat.setWrap(true);
            headerFormat.setBackground(Colour.GRAY_25);
            headerFormat.setAlignment(Alignment.CENTRE);
            headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);



            numberFormat.setAlignment(Alignment.CENTRE);
            numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            //System.out.println("Create excell file called " + filename);
            WorkbookSettings ws = new WorkbookSettings();
            ws.setLocale(new Locale("en", "EN"));
            workbook = Workbook.createWorkbook(new File(getFilePath()), ws);
        } catch (Exception ex) {
            System.out.println("Excell initilization error " + ex);
            JOptionPane.showMessageDialog(gui.getFrame(), "Excell initilization error " + ex, "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    /**
     * If you have saved your project the excel file is saved in the same directory as your project file.
     * If you have not saved your project, the excel file will be called "unknown" and will be saved
     * in the same directory as the jar file you have run it from.
     * @return
     */
    private String getFilePath() {
        if (gui.getSavePath() == null) {
            return "untitled.xls";
        } else {
            return gui.getSavePath().substring(0, gui.getSavePath().length() - 4) + ".xls";
        }
    }

    /**
     * Write Image and table data to an Excel file for further numerical analysis
     */
    public void writeDataToExcell() {

        try {


            //Write all structure info to excell

            writeableSheet = workbook.createSheet("TRUSS INFO", 0);
            writeImageSheet(0, 0);
            int tableXstart = 1;
            transferTable(tableXstart, 3 * gui.getView().getHeight() / 100 + verticalTableSpacing, gui.getNodeTable().getTable());

            tableXstart += gui.getNodeTable().getTable().getModel().getColumnCount() + horizontalTableSpacing;
            transferTable(tableXstart, 3 * gui.getView().getHeight() / 100 + verticalTableSpacing, gui.getBarTable().getTable());

            tableXstart += gui.getBarTable().getTable().getModel().getColumnCount() + horizontalTableSpacing;
            transferTable(tableXstart, 3 * gui.getView().getHeight() / 100 + verticalTableSpacing, gui.getSupportTable().getTable());

            tableXstart += gui.getSupportTable().getTable().getModel().getColumnCount() + horizontalTableSpacing;
            transferTable(tableXstart, 3 * gui.getView().getHeight() / 100 + verticalTableSpacing, gui.getLoadTable().getTable());


            //Write results to excell
            writeableSheet = workbook.createSheet("OUTPUT", 1);
            tableXstart = 1;

            transferTable(tableXstart, 0, new NodeOutputTable(gui.getTruss(), null).getTable());
            tableXstart += NodeOutputTable.COLUMN_COUNT + horizontalTableSpacing;

            transferTable(tableXstart, 0, new BarOutputTable(gui.getTruss(), gui).getTable());
            tableXstart += BarOutputTable.COLUMN_COUNT + horizontalTableSpacing;

            transferTable(tableXstart, 0, new StressOutputTable(gui.getTruss()).getTable());
            tableXstart += StressOutputTable.COLUMN_COUNT + horizontalTableSpacing;

            transferTable(tableXstart, 0, new SupportOutputTable(gui.getTruss(), null).getTable());
            tableXstart += SupportOutputTable.COLUMN_COUNT + horizontalTableSpacing;

            //transferTable(tableXstart, 0, new JointOutputTable(gui.getTruss()).getTable());

            //Write parameters to excell
            writeableSheet = workbook.createSheet("PARAMETERS", 2);
            writeGAParameters();


            workbook.write();
            workbook.close();

        } catch (IOException ex) {
            System.out.println("Excell creation error " + ex);
        } catch (Exception ex) {
            System.out.println("Excell creation error " + ex);

        }
    }

    /**
     * This method will loop through all rows and columns and store each cells object in
     * a 2D object array.
     * @param table Table you want to get all data from
     * @return 2D object array containing all data in table
     */
    private Object[][] getTableData(JTable table) {
        Object[][] data = new Object[table.getRowCount()][table.getColumnCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                data[i][j] = table.getValueAt(i, j);
            }

        }
        return data;
    }

    private void writeGAParameters() throws Exception {
        writeableSheet.addCell(new Label(0, 0, "Penalties", headerFormat));
        writeableSheet.addCell(new Label(0, 1, "Deflection", numberFormat));
        writeableSheet.addCell(new Label(0, 2, "Weight", numberFormat));
        writeableSheet.addCell(new Label(0, 3, "Stress", numberFormat));
        writeableSheet.addCell(new Label(0, 4, "Lengths", numberFormat));
        writeableSheet.addCell(new Label(0, 5, "Compression", numberFormat));
        writeableSheet.addCell(new Label(1, 1, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getDeflectionPenatly()), numberFormat));
        writeableSheet.addCell(new Label(1, 2, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getWeightPenalty()), numberFormat));
        writeableSheet.addCell(new Label(1, 3, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getStressPenalty()), numberFormat));
        writeableSheet.addCell(new Label(1, 4, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getLengthPenatly()), numberFormat));
        writeableSheet.addCell(new Label(1, 5, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getCompressionPenatly()), numberFormat));

        writeableSheet.addCell(new Label(3, 0, "Sections", headerFormat));
        writeableSheet.addCell(new Label(3, 1, "Enabled", numberFormat));
        writeableSheet.addCell(new Label(3, 2, "Min", numberFormat));
        writeableSheet.addCell(new Label(3, 3, "Max", numberFormat));
        writeableSheet.addCell(new Label(4, 1, java.lang.Boolean.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSections()), numberFormat));
        writeableSheet.addCell(new Label(4, 2, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getMinSectionIndex()), numberFormat));
        writeableSheet.addCell(new Label(4, 3, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getMaxSectionIndex()), numberFormat));


        writeableSheet.addCell(new Label(5, 0, "Lengths", headerFormat));
        writeableSheet.addCell(new Label(5, 1, "Min", numberFormat));
        writeableSheet.addCell(new Label(5, 2, "Max", numberFormat));
        writeableSheet.addCell(new Label(6, 1, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getMinNodeSpacing()), numberFormat));
        writeableSheet.addCell(new Label(6, 2, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getMaxBarLength()), numberFormat));
        writeableSheet.addCell(new Label(7, 1, "m", numberFormat));
        writeableSheet.addCell(new Label(7, 2, "m", numberFormat));

        writeableSheet.addCell(new Label(9, 0, "Deflactions", headerFormat));
        writeableSheet.addCell(new Label(9, 1, "Max", numberFormat));
        writeableSheet.addCell(new Label(10, 1, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getMaxDeflection()), numberFormat));
        writeableSheet.addCell(new Label(11, 1, "mm", numberFormat));

        writeableSheet.addCell(new Label(13, 0, "Nodes", headerFormat));
        writeableSheet.addCell(new Label(13, 1, "Nodal Grid", numberFormat));
        writeableSheet.addCell(new Label(13, 2, "X Tolerance", numberFormat));
        writeableSheet.addCell(new Label(13, 3, "Z Tolerance", numberFormat));
        writeableSheet.addCell(new Label(13, 4, "Free Nodes X", numberFormat));
        writeableSheet.addCell(new Label(13, 5, "Free Nodes Z", numberFormat));
        writeableSheet.addCell(new Label(13, 6, "Loaded Nodes X", numberFormat));
        writeableSheet.addCell(new Label(13, 7, "Loaded Nodes Z", numberFormat));
        writeableSheet.addCell(new Label(13, 8, "Supported Nodes X", numberFormat));
        writeableSheet.addCell(new Label(13, 9, "Supported Nodes Z", numberFormat));
        writeableSheet.addCell(new Label(14, 1, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getNodalGrid()), numberFormat));
        writeableSheet.addCell(new Label(14, 2, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalXDisplacement()), numberFormat));
        writeableSheet.addCell(new Label(14, 3, Double.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().getMaxNodalYDisplacement()), numberFormat));
        writeableSheet.addCell(new Label(14, 4, java.lang.Boolean.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeFreeNodesX()), numberFormat));
        writeableSheet.addCell(new Label(14, 5, java.lang.Boolean.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeFreeNodesY()), numberFormat));
        writeableSheet.addCell(new Label(14, 6, java.lang.Boolean.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeLoadedNodesX()), numberFormat));
        writeableSheet.addCell(new Label(14, 7, java.lang.Boolean.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeLoadedNodesY()), numberFormat));
        writeableSheet.addCell(new Label(14, 8, java.lang.Boolean.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSupportedNodesX()), numberFormat));
        writeableSheet.addCell(new Label(14, 9, java.lang.Boolean.toString(gui.getTruss().getOptimizeMethods().getGAOptimizer().getGAModel().isOptimizeSupportedNodesY()), numberFormat));
        writeableSheet.addCell(new Label(15, 1, "cm", numberFormat));
        writeableSheet.addCell(new Label(15, 2, "cm", numberFormat));
        writeableSheet.addCell(new Label(15, 3, "cm", numberFormat));


        writeableSheet.addCell(new Label(17, 0, "Materials", headerFormat));
        writeableSheet.addCell(new Label(17, 1, "E", numberFormat));
        writeableSheet.addCell(new Label(17, 2, "gravity", numberFormat));
        writeableSheet.addCell(new Label(17, 3, "Density", numberFormat));
        writeableSheet.addCell(new Label(17, 4, "Yield strength", numberFormat));

        writeableSheet.addCell(new Label(18, 1, Double.toString(gui.getTruss().getMaterials().get("STEEL").getYoungsModulus()), numberFormat));
        writeableSheet.addCell(new Label(18, 2, Double.toString(gui.getTruss().getMaterials().get("STEEL").getGravity()), numberFormat));
        writeableSheet.addCell(new Label(18, 3, Double.toString(gui.getTruss().getMaterials().get("STEEL").getDensity()), numberFormat));
        writeableSheet.addCell(new Label(18, 4, Double.toString(gui.getTruss().getMaterials().get("STEEL").getYieldStrength()), numberFormat));

        writeableSheet.addCell(new Label(19, 1, "KN/m^2", numberFormat));
        writeableSheet.addCell(new Label(19, 2, "m/s^2", numberFormat));
        writeableSheet.addCell(new Label(19, 3, "Kg/m^3", numberFormat));
        writeableSheet.addCell(new Label(19, 4, "KN/mm^2", numberFormat));

    }

    /**
     * Transfer a table data to Excel at a specific location on the sheet
     * @param startx Column that top left of table should be placed at
     * @param starty Row that top left of table should be placed at
     * @param table Table to write to excell
     * @throws Exception WriteException if fails to write data to cell
     */
    private void transferTable(int startx, int starty, JTable table) throws Exception {
        Object[][] data = getTableData(table);

        for (int j = 0; j < data[0].length; j++) {
            Label l = new Label(j + startx, starty + verticalTableSpacing, table.getModel().getColumnName(j), headerFormat);
            writeableSheet.addCell(l);
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                try {
                    jxl.write.Number number = new jxl.write.Number(j + startx, i + starty + verticalTableSpacing + 1, Double.parseDouble(data[i][j].toString()), numberFormat);
                    writeableSheet.addCell(number);
                } catch (NumberFormatException ex) {
                    System.out.println("excell NFE " + ex);
                    Label l = new Label(j + startx, i + starty + verticalTableSpacing + 1, data[i][j].toString(), numberFormat);
                    writeableSheet.addCell(l);
                } catch (NullPointerException ex) {
                    System.out.println("excell NPE " + ex);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("excell AIOBE " + ex);
                }
            }
        }
    }

    /**
     * Transfer an image to Excel at a specific location on the sheet
     * @param startx Column that top left of image should be placed at
     * @param starty Row that top left of image should be placed at
     * @throws Exception WriteException if fails to write data to cell
     */
    private void writeImageSheet(int startx, int starty) throws Exception {

        /*Picture Label*/

        Label l = new Label(startx, starty, "Image", headerFormat);
        writeableSheet.addCell(l);

        /*Picture*/
        BufferedImage bi = new BufferedImage(gui.getView().getWidth(), gui.getView().getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        //gui.getView().paintComponent(g);
        gui.getView().paint(g);
        ImageIO.write(bi, "png", new File("image.png"));
        File imageFile = new File("image.png");
        WritableImage wi = new WritableImage(startx, starty + 1, gui.getView().getWidth() / 100, 3 * gui.getView().getHeight() / 100, imageFile);

        writeableSheet.addImage(wi);
    }
}
