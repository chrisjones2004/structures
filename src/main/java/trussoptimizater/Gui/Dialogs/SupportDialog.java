package trussoptimizater.Gui.Dialogs;



import java.util.logging.Level;
import java.util.logging.Logger;
import trussoptimizater.Truss.TrussModel;
import java.util.Observable;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Elements.Support;
import trussoptimizater.Truss.Events.ModelEvent;


public class SupportDialog implements java.util.Observer{
    
    private TrussModel truss;
    private GUI gui;
    
    private JDialog dialog;
    private JButton cancelButton;
    private JButton addButton;
    private JTextPane textPane;
    private JComboBox uxComboBox;
    private JComboBox uzComboBox;
    private JComboBox ryComboBox;

     
    public SupportDialog(GUI gui, TrussModel truss){
        this.truss = truss;
        this.gui = gui;
        dialog = new JDialog(gui.getFrame(), "Create Supports", false);
        truss.getNodeModel().addObserver(this);
    }
    public void createGui(){

        JPanel supportPanel1 = new JPanel(new GridLayout(1,2,10,5));
        JPanel supportPanel2 = new JPanel(new GridLayout(1,2,10,5));
        JPanel supportPanel3 = new JPanel(new GridLayout(1,2,10,5));
        
        String[] freeorFixed = {Support.FREE,Support.FIXED};
        uxComboBox = new JComboBox(freeorFixed);
        uzComboBox = new JComboBox(freeorFixed);
        ryComboBox = new JComboBox(freeorFixed);
        
        supportPanel1.add(new JLabel("UX: "));
        supportPanel1.add(uxComboBox);
        supportPanel2.add(new JLabel("UZ: "));
        supportPanel2.add(uzComboBox);
        supportPanel3.add(new JLabel("RY: "));
        supportPanel3.add(ryComboBox);
        
        JPanel supportPanel = new JPanel(new GridLayout(3,1,10,5));
        supportPanel.add(supportPanel1);
        supportPanel.add(supportPanel2);
        supportPanel.add(supportPanel3);
        
        
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                addSupport();
            }
        });
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);
        buttonPanel.add(new JButton("Help"));
        
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0) );
        LineBorder lineborder = new LineBorder(Color.black,1,true);



        textPane = new JTextPane();
        //updateTextPane();

        textPane.setPreferredSize(new Dimension(80,40));
        textPane.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(new TitledBorder(lineborder,"Selected Nodes"));
        
        
        dialog.setResizable(false);
        
        dialog.add(supportPanel,BorderLayout.NORTH);
        dialog.add(scrollPane,BorderLayout.CENTER);
        dialog.add(buttonPanel,BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.pack();
        //dialog.setVisible(true);
    }
    
    public void updateTextPane(){
        textPane.setText("");
        String selectedNodes = "";
        for(int i =0;i<truss.getNodeModel().size();i++){
            if(truss.getNodeModel().get(i).isSelected()){
                selectedNodes += Integer.toString(truss.getNodeModel().get(i).getNumber()) + ",";
            }
        }
        textPane.setText(selectedNodes);
    }

    
    public void addSupport(){
        try{
            
            for(int i = 0;i<truss.getNodeModel().size();i++){
                if(!truss.getNodeModel().get(i).isSelected()){
                    continue;
                }
                try {
                    truss.getSupportModel().add(new Support(truss.getSupportModel().size() + 1, truss.getNodeModel().get(i), uxComboBox.getSelectedItem().toString(), uxComboBox.getSelectedItem().toString(), uxComboBox.getSelectedItem().toString()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gui.getFrame(), ex, "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
            //gui.getView().repaint();
        }catch(java.lang.NumberFormatException nfe){
            System.out.println("Load Error "+nfe);
        }
    }//end of addSupport
    
   public JDialog getDialog() {
        return dialog;
    }

    public void update(Observable o, Object arg) {
        if(arg instanceof ModelEvent){
            updateTextPane();
        }
        
    }

}