package trussoptimizater.Gui.GUIModels;


import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.TrussModel;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Sections.TubularSection;

public class BarTableModel extends ElementTableModel<Bar>  {

    private final String[] COLUMN_NAMES = {
        "Bar",
        "Node 1",
        "Node 2",
        "<HTML><CENTER>Length<BR>(m)</CENTER></HTML>",
        "<HTML><CENTER>Angle<BR>(degrees)</CENTER></HTML>",
        "Restraints",
        "Section",
        "<HTML><CENTER>Area<BR>(cm^2)</CENTER></HTML>",
        "Material"};
    private boolean[] editableColumns = {false, true, true, false, false, true, true, false, true};
    private final int COLUMN_COUNT = COLUMN_NAMES.length;
    public static final int BAR_NUMBER_COLOUMN_INDEX = 0;
    public static final int NODE1_COLUMN_INDEX = 1;
    public static final int NODE2_COLUMN_INDEX = 2;
    public static final int LENGTH_COLUMN_INDEX = 3;
    public static final int ANGLE_COLUMN_INDEX = 4;
    public static final int RESTRAINTS_COLUMN_INDEX = 5;
    public static final int SECTION_COLUMN_INDEX = 6;
    public static final int AREA_COLUMN_INDEX = 7;
    public static final int MATERIAL_COLUMN_INDEX = 8;


    public BarTableModel(TrussModel truss) {
        super(truss, truss.getBarModel());
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return editableColumns[col];

    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (model.size() == 0) {
            return new Object();
        }

        switch (columnIndex) {
            case BarTableModel.BAR_NUMBER_COLOUMN_INDEX:
                return model.get(rowIndex).getNumber();
            case BarTableModel.NODE1_COLUMN_INDEX:
                return model.get(rowIndex).getNode1().getNumber();
            case BarTableModel.NODE2_COLUMN_INDEX:
                return model.get(rowIndex).getNode2().getNumber();
            case BarTableModel.LENGTH_COLUMN_INDEX:
                return model.get(rowIndex).getLength() / 100;
            case BarTableModel.ANGLE_COLUMN_INDEX:
                return Math.toDegrees(model.get(rowIndex).getAngle());
            case BarTableModel.SECTION_COLUMN_INDEX:
                //System.out.println("getting bar to "+bars.get(rowIndex).getSection().getName());
                return model.get(rowIndex).getSection().getName();
            case BarTableModel.AREA_COLUMN_INDEX:
                return model.get(rowIndex).getSection().getArea();
            case BarTableModel.RESTRAINTS_COLUMN_INDEX:
                return model.get(rowIndex).getRestraint();
            case BarTableModel.MATERIAL_COLUMN_INDEX:
                return model.get(rowIndex).getMaterial().getMaterialName();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if (rowIndex > getRowCount() || columnIndex > COLUMN_COUNT) {
            throw new IndexOutOfBoundsException();
        }

        switch (columnIndex) {
            case BarTableModel.BAR_NUMBER_COLOUMN_INDEX:
                //Not ediablte
                break;
            case BarTableModel.NODE1_COLUMN_INDEX:
                if (aValue == null) {
                    model.get(rowIndex).setNode2(truss.getNodeModel().get(0));
                    return;
                }
                int nodeNumber = Integer.parseInt(aValue.toString());
                Node node = truss.getNodeModel().get(nodeNumber - 1);
                model.get(rowIndex).setNode1(node);
                break;
            case BarTableModel.NODE2_COLUMN_INDEX:
                if (aValue == null) {
                    model.get(rowIndex).setNode2(truss.getNodeModel().get(0));
                    return;
                }
                nodeNumber = Integer.parseInt(aValue.toString());
                node = truss.getNodeModel().get(nodeNumber - 1);
                model.get(rowIndex).setNode2(node);
                break;
            case BarTableModel.LENGTH_COLUMN_INDEX:
                //Not ediablte
                break;
            case BarTableModel.ANGLE_COLUMN_INDEX:
                //Not ediablte
                break;
            case BarTableModel.SECTION_COLUMN_INDEX:
                if (aValue != null) {
                    TubularSection section = truss.getSectionModel().get(aValue.toString());
                    //System.out.println("setting bar to "+section);
                    model.get(rowIndex).setSection(section);
                } else {
                    System.out.println("Trying to set section to null");
                }
                break;
            case BarTableModel.AREA_COLUMN_INDEX:
                //Not ediablte
                break;
            case BarTableModel.RESTRAINTS_COLUMN_INDEX:
                model.get(rowIndex).setRestraint(aValue.toString());
                break;
            case BarTableModel.MATERIAL_COLUMN_INDEX:
                model.get(rowIndex).setMaterial(truss.getMaterials().get(aValue.toString()));
                break;
            default:
                break;
        }

        this.fireTableDataChanged();

    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean[] getEditableColumns() {
        return editableColumns;
    }

}//end of NodeTableModel

