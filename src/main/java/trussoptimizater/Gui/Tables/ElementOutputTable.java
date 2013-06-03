/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trussoptimizater.Gui.Tables;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import trussoptimizater.Truss.TrussModel;

public abstract class ElementOutputTable {
    
    protected TrussModel truss;
    protected JTable table;
    
    public ElementOutputTable(TrussModel truss){
        this.truss = truss;
    }

    public abstract Object[][] getData();
    public abstract JScrollPane getScrollTable();
    public abstract JTable getTable();
    protected abstract void setColoumnWidths();


}
