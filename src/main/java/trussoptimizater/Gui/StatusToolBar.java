package trussoptimizater.Gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;


public class StatusToolBar {
    
    private JToolBar statusToolBar;
    private JLabel toolTipLabel;
    private MouseOverHintManager hintManager;
    public StatusToolBar(){
        toolTipLabel = new JLabel();
        hintManager = new MouseOverHintManager(toolTipLabel);

        statusToolBar = new JToolBar();

    }
    public void createGui(){
        
        statusToolBar.setRollover(false);
        statusToolBar.setFloatable(false);
        statusToolBar.setPreferredSize(new Dimension(statusToolBar.getPreferredSize().width, 20));
        statusToolBar.addSeparator();
        statusToolBar.add(toolTipLabel);
        //setToolTipLabel("\u00a9 2002 - 2004 Truss Analyzer + Optimizer");

    }


    public void setToolTipLabel(String toolTip) {
        this.toolTipLabel.setText(toolTip);
    }

    public JToolBar getStatusToolBar() {
        return statusToolBar;
    }

    public MouseOverHintManager getHintManager() {
        return hintManager;
    }
}






class MouseOverHintManager extends MouseAdapter {
  private Map hintMap;
  private JLabel hintLabel;

  public MouseOverHintManager( JLabel hintLabel ) {
    hintMap = new WeakHashMap();
    this.hintLabel = hintLabel;
  }

  public void addHintFor( Component comp, String hintText ) {
    hintMap.put( comp, hintText );
  }

  public void enableHints( Component comp ) {
    comp.addMouseListener( this );
    if ( comp instanceof Container ) {
      Component[] components = ((Container)comp).getComponents();
      for ( int i=0; i<components.length; i++ ){
          enableHints( components[i] );
      }
       
    }
    if ( comp instanceof JMenu ) {
      Component[] elements = ((JMenu)comp).getMenuComponents();
      for ( int i=0; i<elements.length; i++ ){
            enableHints( elements[i]);
        }
    }
  }

  private String getHintFor( Component comp ) {
    String hint = (String)hintMap.get(comp);
    if ( hint == null ) {
      if ( comp instanceof JLabel )
       hint = (String)hintMap.get(((JLabel)comp).getLabelFor());
      else if ( comp instanceof JTableHeader )
       hint = (String)hintMap.get(((JTableHeader)comp).getTable());
    }
    return hint;
  }


    @Override
  public void mouseEntered( MouseEvent e ) {
    Component comp = (Component)e.getSource();
    String hint;
    do {
      hint = getHintFor(comp);
      comp = comp.getParent();
    } while ( (hint == null) && (comp != null) );
    if ( hint != null )
      hintLabel.setText( hint );
  }

    @Override
  public void mouseExited( MouseEvent e ) {
    hintLabel.setText( " " );
  }

}


