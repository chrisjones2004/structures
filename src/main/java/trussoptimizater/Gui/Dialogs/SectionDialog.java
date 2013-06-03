package trussoptimizater.Gui.Dialogs;

import trussoptimizater.Truss.TrussModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import trussoptimizater.Gui.*;
import trussoptimizater.Gui.GUIModels.SectionComboBoxModel;
import trussoptimizater.Truss.SectionLibrary;
import trussoptimizater.Truss.Sections.CHS;
import trussoptimizater.Truss.Sections.RHS;
import trussoptimizater.Truss.Sections.SHS;
import trussoptimizater.Truss.Sections.TubularSection;





public class SectionDialog  implements java.util.Observer{
    
    private TrussModel truss;
    private GUI gui;

    private JDialog dialog;
    private final String[] picturePaths = {"/Pictures/Sections/CHS.bmp","/Pictures/Sections/RHS.bmp","/Pictures/Sections/SHS.bmp"};
    private final String[] sectionTypes = {"CHS","RHS","SHS"};
    private JPanel pictures;
    private JPanel bottomPanel;
    
    private JList list;
    
    private JComboBox sectionTypesComboBox;    
    private JComboBox sectionNamesComboBox;   
    
    private static ArrayList<ArrayList<String>> sectionNames = new ArrayList<ArrayList<String>>();
    
    private static ArrayList<CHS> CHSData = new ArrayList<CHS>();
    private static ArrayList<SHS> SHSData = new ArrayList<SHS>();
    private static ArrayList<RHS> RHSData = new ArrayList<RHS>();
    
    private JTextPane textPane;
    
    private final JLabel[] CHSlabels = new JLabel[4];
    private final JLabel[] RHSlabels = new JLabel[6];
    private final JLabel[] SHSlabels = new JLabel[4];
    private static final LineBorder lineborder = new LineBorder(Color.black,1,true);
    
    private JPanel sectionInfoPanel;
    
    public SectionDialog(GUI gui, TrussModel truss){
        this.truss = truss;
        this.gui = gui;
        this.list = new JList(new SectionComboBoxModel(truss.getSectionModel()));
        textPane = new JTextPane();
        initArrayLists();
        truss.addObserver(this);

    }
    
    public void initArrayLists(){
        ArrayList<String> CHSNames = new ArrayList<String>();
        ArrayList<String> RHSNames = new ArrayList<String>();
        ArrayList<String> SHSNames = new ArrayList<String>();
        
        for(int i=0;i<SectionLibrary.SECTIONS.size();i++){
            if(SectionLibrary.SECTIONS.get(i) instanceof CHS){
                CHSData.add((CHS)SectionLibrary.SECTIONS.get(i));
                CHSNames.add(SectionLibrary.SECTIONS.get(i).getName());
            }else if(SectionLibrary.SECTIONS.get(i) instanceof RHS){
                RHSData.add((RHS)SectionLibrary.SECTIONS.get(i));
                RHSNames.add(SectionLibrary.SECTIONS.get(i).getName());
            }else if(SectionLibrary.SECTIONS.get(i) instanceof SHS){
                SHSData.add((SHS)SectionLibrary.SECTIONS.get(i));
                SHSNames.add(SectionLibrary.SECTIONS.get(i).getName());
            }
        }
        sectionNames.add(CHSNames);
        sectionNames.add(RHSNames);
        sectionNames.add(SHSNames);
        
    }
    
    //Constructor
    public void createGui(){

        //test();
        ImagePanel[] picturePanels = makePicturePanels();
        
        pictures = new JPanel(new CardLayout());
        pictures.add(picturePanels[0],sectionTypes[0]);
        pictures.add(picturePanels[1],sectionTypes[1]);
        pictures.add(picturePanels[2],sectionTypes[2]);
        

        
        
        sectionTypesComboBox = new JComboBox(sectionTypes);
        sectionTypesComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Setting picture in top right
                CardLayout cl = (CardLayout)(pictures.getLayout());
                cl.show(pictures,sectionTypesComboBox.getSelectedItem().toString());
                
                //Setting sectionName combo box
                sectionNamesComboBox.setModel( new DefaultComboBoxModel( sectionNames.get(sectionTypesComboBox.getSelectedIndex()).toArray()));
                
                //setting bottom panel
                CardLayout c2 = (CardLayout)(sectionInfoPanel.getLayout());
                c2.show(sectionInfoPanel,sectionTypesComboBox.getSelectedItem().toString());
                setCHSLables();
                setRHSLables();
                setSHSLables(); 
                       
            }
        });

        sectionNamesComboBox = new JComboBox(sectionNames.get(0).toArray());
        sectionNamesComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                if(sectionTypesComboBox.getSelectedItem().toString().equals(sectionTypes[0])){
                    setCHSLables();
                } 
                else if(sectionTypesComboBox.getSelectedItem().toString().equals(sectionTypes[1])){
                    setRHSLables();
                }
                else if(sectionTypesComboBox.getSelectedItem().toString().equals(sectionTypes[2])){
                    setSHSLables(); 
                }         
            }
        });
        
        
        

        JButton addSectionButton = new JButton("Add to List");
        addSectionButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                addSection();
            }
        });
        JPanel bottomControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        bottomControlPanel.add(sectionTypesComboBox);
        bottomControlPanel.add(sectionNamesComboBox);
        bottomControlPanel.add(addSectionButton);
        
        
        sectionInfoPanel = new JPanel(new CardLayout());
        sectionInfoPanel.add(makeCHSPanel(),sectionTypes[0]);
        sectionInfoPanel.add(makeRHSPanel(),sectionTypes[1]);
        sectionInfoPanel.add(makeSHSPanel(),sectionTypes[2]);
        
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(bottomControlPanel,BorderLayout.NORTH);
        bottomPanel.add(sectionInfoPanel,BorderLayout.CENTER);
        bottomPanel.setBorder(new TitledBorder(lineborder,"Properties"));
        
        
        list.setLayoutOrientation(JList.VERTICAL);
        //list.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(150, 130));


        
        
        textPane.setPreferredSize(new Dimension(80,40));
        textPane.setEditable(false);  
        updateTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        
        scrollPane.setBorder(new TitledBorder(lineborder,"Selected Bars"));
        
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.PAGE_AXIS));
        JLabel addedSectionLabel = new JLabel("Section List:");
        topLeftPanel.add(addedSectionLabel);
        topLeftPanel.add(listScroller);
        topLeftPanel.add(scrollPane);
        //topLeftPanel.add(sectionTypesComboBox);
        
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.PAGE_AXIS));
        topRightPanel.add(pictures);
        
        
        JButton addSectionToNodesButton = new JButton("Add Section to Bar(s)");
        addSectionToNodesButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(list.getSelectedIndex()==-1){
                    JOptionPane.showMessageDialog(dialog, "You have not selected a section to add to bars", "Alert", JOptionPane.ERROR_MESSAGE);
                    return;
                }                
                
                
                for(int i=0;i<truss.getBarModel().size();i++){
                    if(truss.getBarModel().get(i).isSelected()){
                        //gui.getBarTable().getModel().setValueAt(list.getSelectedValue(), truss.getBarModel().get(i).getIndex(), 5);
                        for(int j =0;j<SectionLibrary.SECTIONS.size();j++){
                            if(SectionLibrary.SECTIONS.get(j).getName().equals(list.getSelectedValue().toString())){
                                truss.getBarModel().get(i).setSection(SectionLibrary.SECTIONS.get(j));
                            }
                        }
                    }
                }
            
            }
        });
        JButton removeButton = new JButton("Remove from List");
        removeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                removeSection();
            }
        });
        
        
        JPanel buttonPanel = new JPanel(new GridLayout(2,1));
        buttonPanel.add(addSectionToNodesButton);
        buttonPanel.add(removeButton);
        topRightPanel.add(buttonPanel);
        
        
        JPanel topPanel = new JPanel();
        topPanel.add(topLeftPanel);
        topPanel.add(topRightPanel);
        
        //JSplitPane splitPane = new JSplitPane();
        //splitPane.setTopComponent(topPanel);
        //splitPane.setBottomComponent(bottomPanel);
        //splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        dialog = new JDialog(gui.getFrame(),"Section Properties",false);
        dialog.setLayout(new BorderLayout());
        dialog.add(topPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        //dialog.setAlwaysOnTop(true);
        dialog.pack();
        //dialog.setVisible(true);
    }
    
    
    
    public void setListModel(SectionComboBoxModel scbm){
        this.list.setModel(scbm);
        //this.setChanged();
        //this.notifyObservers(this);
    }
    
    public ImagePanel[] makePicturePanels(){        
        ImagePanel[] pictures = new ImagePanel[sectionTypes.length];
        BufferedImage image = null;
        try{
            for(int i = 0;i<sectionTypes.length;i++){
                image = ImageIO.read(getClass().getResource(picturePaths[i]));
                pictures[i] = new ImagePanel(image);
            }
        }catch(IOException  e){
            System.err.println("\nTrying to read in image "+e);
        }catch(NullPointerException f){
            System.err.println("\nTrying to read in image "+f);
        }
        return pictures;
    }
    

    public void addSection(){
        addSection(sectionNamesComboBox.getSelectedItem().toString());
    }

    public void addSection(String sectionName){
        TubularSection section = SectionLibrary.get(sectionName);
        if(truss.getSectionModel().contains(section)){
            System.out.println("Trying to add a section that is already added");
            JOptionPane.showMessageDialog(dialog, "Section has already been added to list", "Alert", JOptionPane.ERROR_MESSAGE);
        }else{
            truss.getSectionModel().add(section);
        }
    }
    
    public void removeSection(){
        if(list.getSelectedIndex()==-1){
            JOptionPane.showMessageDialog(dialog, "You have not selected a section to remove", "Alert", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sectionToRemove = this.list.getSelectedValue().toString();
        TubularSection section = SectionLibrary.get(sectionToRemove);
        truss.getSectionModel().remove(section);
       // this.setChanged();
        //this.notifyObservers(this);

    }
    
    public void updateTextPane(){
        textPane.setText("");
        String selectedBars = "";
        for(int i =0;i<truss.getBarModel().size();i++){
            if(truss.getBarModel().get(i).isSelected()){
                selectedBars += Integer.toString(truss.getBarModel().get(i).getNumber()) + ",";
            }
        }
        textPane.setText(selectedBars);
    }
    
    public void setCHSLables(){
                CHSlabels[0].setText("D:    "+CHSData.get(sectionNamesComboBox.getSelectedIndex()).getDiameter()+"  mm");
                CHSlabels[1].setText("A:    "+CHSData.get(sectionNamesComboBox.getSelectedIndex()).getArea()+"  cm^2");
                CHSlabels[2].setText("t:    "+CHSData.get(sectionNamesComboBox.getSelectedIndex()).getThickness()+"  mm");
                CHSlabels[3].setText("Ixx:  "+CHSData.get(sectionNamesComboBox.getSelectedIndex()).getIxx()+"  cm^4");
    }
    public void setRHSLables(){
                RHSlabels[0].setText("B:    "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getBredth()+"  mm");
                RHSlabels[1].setText("t:    "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getThickness()+"  mm");
                RHSlabels[2].setText("D:    "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getDepth()+"  mm");
                RHSlabels[3].setText("A:    "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getArea()+"  cm^2");
                RHSlabels[4].setText("Ixx:  "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getIxx()+"  cm^4");
                RHSlabels[5].setText("Iyy:  "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getIyy()+"  cm^4");
    }
    public void setSHSLables(){
                SHSlabels[0].setText("D:     "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getDepth()+"  mm");
                SHSlabels[1].setText("A:     "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getThickness()+"  cm^2");
                SHSlabels[2].setText("t:     "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getArea()+"  mm");
                SHSlabels[3].setText("Ixx:   "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getIxx()+"  cm^4");
    }
    
    public JPanel makeCHSPanel(){
        JPanel CHSpanel = new JPanel(new GridLayout(3,2));
        
        String[] CHSSections = new String[CHSData.size()];
        for(int i =0;i<CHSData.size();i++){
            CHSSections[i] = CHSData.get(i).getSectionSize();
        }
        
        //initilize labels
        CHSlabels[0]= new JLabel("D:    "+CHSData.get(0).getDiameter()+"  mm");
        CHSlabels[1]= new JLabel("A:    "+CHSData.get(0).getArea()+"  cm^2");
        CHSlabels[2]= new JLabel("t:    "+CHSData.get(0).getThickness()+"  mm");
        CHSlabels[3]= new JLabel("Ixx:  "+CHSData.get(0).getIxx()+"  cm^4");

        for(int i = 0;i<CHSlabels.length;i++){
            CHSpanel.add(CHSlabels[i]);
        }
        return CHSpanel;
    }
    
    public JPanel makeRHSPanel(){
        JPanel RHSpanel = new JPanel(new GridLayout(4,2));
        String[] RHSSections = new String[RHSData.size()];
        for(int i =0;i<RHSData.size();i++){
            RHSSections[i] = RHSData.get(i).getSectionSize();
        }

        RHSlabels[0]= new JLabel("B:    "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getBredth()+"  mm");
        RHSlabels[1]= new JLabel("t:     "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getThickness()+"  mm");
        RHSlabels[2]= new JLabel("D:    "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getDepth()+"  mm");
        RHSlabels[3]= new JLabel("A:    "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getArea()+"  cm^2");
        RHSlabels[4]= new JLabel("Ixx:  "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getIxx()+"  cm^4");
        RHSlabels[5]= new JLabel("Iyy:  "+RHSData.get(sectionNamesComboBox.getSelectedIndex()).getIyy()+"  cm^4");

        for(int i = 0;i<RHSlabels.length;i++){
            RHSpanel.add(RHSlabels[i]);
        }
        return RHSpanel;
    }
    
    public JPanel makeSHSPanel(){
        JPanel SHSpanel = new JPanel(new GridLayout(3,2));

        String[] SHSSections = new String[SHSData.size()];
        for(int i =0;i<SHSData.size();i++){
            SHSSections[i] = SHSData.get(i).getSectionSize();
        }

        SHSlabels[0] = new JLabel("D:     "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getDepth()+"  mm");
        SHSlabels[1] = new JLabel("A:     "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getThickness()+"  cm^2");
        SHSlabels[2] = new JLabel("t:     "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getArea()+"  mm"); 
        SHSlabels[3] = new JLabel("Ixx:   "+SHSData.get(sectionNamesComboBox.getSelectedIndex()).getIxx()+"  cm^4");

        for(int i = 0;i<SHSlabels.length;i++){
            SHSpanel.add(SHSlabels[i]);
        }
        return SHSpanel;
    }
    
    public JDialog getDialog() {
        return dialog;
    }

    public void update(Observable o, Object arg) {
        /*if(!(arg instanceof Bar)){
            return;
        }*/
        updateTextPane();
    }
    

}//end of class










