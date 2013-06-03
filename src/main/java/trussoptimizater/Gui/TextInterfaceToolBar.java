package trussoptimizater.Gui;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import trussoptimizater.Gui.Actions.*;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.*;
import trussoptimizater.Truss.Materials.Steel;
import trussoptimizater.Truss.TrussModel;

/*Useful function*/
//maybe should have print class

//print bar 8 - just get bar and print
//print bars
//print all

public class TextInterfaceToolBar extends OptionPanel implements KeyListener { 

    private JTextArea textArea;
    //private JPanel textPanel;
    private JTextField commandTextField;
    private ArrayList<String> commandHistory = new ArrayList<String>();
    private int commandIndex = 0;
    private static final String TITLE = "Command Line Interface";
    private static final String COMMAND_PROMPT = "Command: ";

    /*Commands*/
    public static final String NODE_COMMAND = "node";
    public static final String BAR_COMMAND = "bar";
    public static final String LOAD_COMMAND = "load";
    public static final String SUPPORT_COMMAND = "support";
    public static final String HELP_COMMAND = "help";
    public static final String SELECT_COMMAND = "select";
    public static final String UNSELECT_COMMAND = "unselect";
    public static final String DELETE_COMMAND = "delete";

    /*Usages*/
    public static final String USAGE_INDENT = "      ";
    public static final String NODE_USAGE = "**Node <x> <z>\n"
            + USAGE_INDENT + "x                 - xcord in (m)\n"
            + USAGE_INDENT + "z                 - zcord in (m)\n";
    public static final String BAR_USAGE = "**Bar <N1> <N2>  <Restraint>\n"
            + USAGE_INDENT + "N1                - Node 1 number\n"
            + USAGE_INDENT + "N2                - Node 2 number\n"
            + USAGE_INDENT + "Restraint         - (F)ixed or (P)inned\n";
    public static final String LOAD_USAGE = "**Load <Node> <Load> <Orientation>\n"
            + USAGE_INDENT + "Node              - Node number\n"
            + USAGE_INDENT + "Load              - Load in (KN)\n"
            + USAGE_INDENT + "Orientation       - (V)ertival or (H)orizontal\n";
    public static final String SUPPORT_USAGE = "**Support <Node> <UX><UY><RY>\n"
            + USAGE_INDENT + "Node              - Node number\n"
            + USAGE_INDENT + "UX                - (F)ixed or (x)Free\n"
            + USAGE_INDENT + "UZ                - (F)ixed or (x)Free\n"
            + USAGE_INDENT + "RY                - (F)ixed or (x)Free\n";
    public static final String SELECT_USAGE = "**(Un)Select <Element Type> <Element Numbers>\n"
            + "***Example Usage:\n"
            + USAGE_INDENT + "select all bars    - (un)select bars\n"
            + USAGE_INDENT + "select bars 1      - (un)select bars 1\n"
            + USAGE_INDENT + "select bars 1 2 3  - (un)select bars 1 2 3\n"
            + USAGE_INDENT + "select bars 1-5 9  - (un)select bars 1-5 9\n";
    public static final String DELETE_USAGE = "**Delete <Element Type> <Element Numbers>\n"
            + "***Example Usage:\n"
            + USAGE_INDENT + "delete bars        - delete all bars\n"
            + USAGE_INDENT + "delete bars 1      - delete bars 1\n"
            + USAGE_INDENT + "delete bars 1 2 3  - delete bars 1 2 3\n"
            + USAGE_INDENT + "delete bars 1-5 9  - delete bars 1-5 9\n";
    private GUI gui;
    private TrussModel truss;

    public TextInterfaceToolBar(GUI gui, TrussModel truss) {
        super();
        this.gui = gui;
        this.truss = truss;
    }

    public void parseSelectCommand(String command, boolean select) {
        String[] commandArr = command.split(" ");
        SelectAction selectAction = (SelectAction) MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ACTION_KEY);


        try {

            String elementToBeSelected = commandArr[1].toLowerCase().trim();
            String selectedIndexes = Arrays.toString(commandArr);
            selectedIndexes.replace(commandArr[0], "");
            selectedIndexes.replace(commandArr[1], "");

            System.out.println("Elements to be selected "+elementToBeSelected);


            if ("all".equals(commandArr[1].toLowerCase()) && commandArr.length == 2) {
                selectAction.selectAll(select);
            } else if ("bars".equals(elementToBeSelected) && commandArr.length == 2) {
                //selectAction.selectElementArray((ElementModel)truss.getBarModel(), select);
                truss.getBarModel().selectAll(select);
            } else if ("nodes".equals(elementToBeSelected) && commandArr.length == 2) {
                //selectAction.selectElementArray((ElementModel)truss.getNodeModel(), select);
                truss.getNodeModel().selectAll(select);
            } else if ("supports".equals(elementToBeSelected) && commandArr.length == 2) {
                //selectAction.selectElementArray((ElementModel)truss.getSupportModel(), select);
                truss.getSupportModel().selectAll(select);
            } else if ("loads".equals(elementToBeSelected) && commandArr.length == 2) {
                //selectAction.selectElementArray((ElementModel)truss.getLoadModel(), select);
                truss.getLoadModel().selectAll(select);
            } else if ("bars".equals(elementToBeSelected)) {
                selectAction.selectSpecificElements((ElementModel)truss.getBarModel(), select, selectedIndexes);
            } else if ("loads".equals(elementToBeSelected)) {
                selectAction.selectSpecificElements((ElementModel)truss.getLoadModel(), select, selectedIndexes);
            } else if ("nodes".equals(elementToBeSelected)) {
                selectAction.selectSpecificElements((ElementModel)truss.getNodeModel(), select, selectedIndexes);
            } else if ("supports".equals(elementToBeSelected)) {
                selectAction.selectSpecificElements((ElementModel)truss.getSupportModel(), select, selectedIndexes);
            } else {
                textArea.insert("**USAGE: " + SELECT_USAGE, 0);
                textArea.select(0, 0);
            }
        } catch (Exception e) {
            textArea.insert("**USAGE: " + SELECT_USAGE, 0);
            textArea.select(0, 0);
        }
    }

    public void parseDeleteCommand(String command) {
        String[] commandArr = command.split(" ");
        DeleteAction deleteAction = (DeleteAction) MyActionMap.ACTION_MAP.get(MyActionMap.DELETE_ACTION_KEY);
        try {
            if (commandArr.length == 1) {
                MyActionMap.ACTION_MAP.get(MyActionMap.DELETE_KEY_BINDING_KEY).actionPerformed(null);
                return;
            }

            String elementToBeDeleted = commandArr[1].toLowerCase().trim();
            String selectedIndexes = Arrays.toString(commandArr);
            selectedIndexes.replace(commandArr[0], "");
            selectedIndexes.replace(commandArr[1], "");

            if ("bars".equals(elementToBeDeleted) && commandArr.length == 2) {
                truss.getBarModel().clear();
            } else if ("nodes".equals(elementToBeDeleted) && commandArr.length == 2) {
                truss.getNodeModel().clear();
            } else if ("supports".equals(elementToBeDeleted) && commandArr.length == 2) {
                truss.getSupportModel().clear();
            } else if ("loads".equals(elementToBeDeleted) && commandArr.length == 2) {
                truss.getLoadModel().clear();
            } else if ("bars".equals(elementToBeDeleted) && commandArr.length > 2) {
                deleteAction.deleteSpecificElements(truss.getBarModel(),selectedIndexes);
            } else if ("loads".equals(elementToBeDeleted) && commandArr.length > 2) {
                deleteAction.deleteSpecificElements(truss.getLoadModel(),selectedIndexes);
            } else if ("nodes".equals(elementToBeDeleted) && commandArr.length > 2) {
                deleteAction.deleteSpecificElements(truss.getNodeModel(),selectedIndexes);
            } else if ("supports".equals(elementToBeDeleted) && commandArr.length > 2) {
                deleteAction.deleteSpecificElements(truss.getSupportModel(),selectedIndexes);
            } else {
                textArea.insert("**USAGE: " + DELETE_USAGE, 0);
                textArea.select(0, 0);
            }
        } catch (Exception e) {
            //System.out.println("Printing usage " + e);
            textArea.insert("**USAGE: " + DELETE_USAGE, 0);
            textArea.select(0, 0);
        }
    }

    public void parseNodeCommand(String command) {
        String[] commandArr = command.split(" ");
        try {
            double x = Double.parseDouble(commandArr[1]);
            double z = Double.parseDouble(commandArr[2]);
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1,x * 100, z * 100));
        } catch (Exception e) {
            textArea.insert("**USAGE: " + NODE_USAGE, 0);
            textArea.select(0, 0);
        }
    }

    public void parseBarCommand(String command) {
        String[] commandArr = command.split(" ");
        if (truss.getNodeModel().size() < 2) {
            textArea.insert("**There must be at least 2 nodes to create a bar\n", 0);
            return;
        }
        try {
            int node1Index = Integer.parseInt(commandArr[1]) - 1;
            int node2Index = Integer.parseInt(commandArr[2]) - 1;
            Node n1 = truss.getNodeModel().get(node1Index);
            Node n2 = truss.getNodeModel().get(node2Index);
            char restraint = commandArr[3].charAt(0);
            switch (restraint) {
                case 'F':
                case 'f':
                    truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1,n1, n2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.FIXED_FIXED_RESTRAINT));
                    break;
                case 'P':
                case 'p':
                    truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1,n1, n2, truss.getMaterials().get(Steel.MATERIAL_NAME), Bar.PINNED_PINNED_RESTRAINT));
                    break;
                default:
                    textArea.insert("**USAGE: " + BAR_USAGE, 0);
                    break;
            }
        } catch (Exception e) {
            textArea.insert("**USAGE: " + BAR_USAGE, 0);
            textArea.select(0, 0);
        }
    }

    public void parseLoadCommand(String command) {
        String[] commandArr = command.split(" ");
        if (truss.getNodeModel().size() < 1) {
            textArea.insert("**There must be at least 1 nodes to create a load\n", 0);
            return;
        }
        try {
            int nodeIndex = Integer.parseInt(commandArr[1]) - 1;
            Node node = truss.getNodeModel().get(nodeIndex);
            double load = Double.parseDouble(commandArr[2]);
            char orientation = commandArr[3].charAt(0);
            switch (orientation) {
                case 'V':
                case 'v':
                    truss.getLoadModel().add(new Load(truss.getLoadModel().size() + 1, node, load, Load.VERTICAL_LOAD, Load.USER_DEFINED));
                    break;
                case 'H':
                case 'h':
                    truss.getLoadModel().add(new Load(truss.getLoadModel().size() + 1, node, load, Load.HORIZOANTAL_LOAD, Load.USER_DEFINED));
                    break;
                default:
                    textArea.insert("**USAGE: " + LOAD_USAGE, 0);
                    break;
            }
        } catch (Exception e) {
            textArea.insert("**USAGE: " + LOAD_USAGE, 0);
            textArea.select(0, 0);
        }
    }

    private String freeOrFixed(char supportRestraint) {
        switch (supportRestraint) {
            case 'X':
            case 'x':
                return Support.FREE;
            case 'F':
            case 'f':
                return Support.FIXED;
            default:
                return null;
        }
    }

    public void parseSupportCommand(String command) {
        String[] commandArr = command.split(" ");
        if (truss.getNodeModel().size() < 1) {
            textArea.insert("**There must be at least 1 nodes to create a support\n", 0);
            return;
        }
        try {
            int nodeIndex = Integer.parseInt(commandArr[1]) - 1;
            Node node = truss.getNodeModel().get(nodeIndex);
            char UX = commandArr[2].charAt(0);
            char UZ = commandArr[2].charAt(1);
            char RY = commandArr[2].charAt(2);
            truss.getSupportModel().add(new Support( truss.getSupportModel().size() + 1, node, freeOrFixed(UX), freeOrFixed(UZ), freeOrFixed(RY)));
        } catch (Exception e) {
            textArea.insert("**USAGE: " + SUPPORT_USAGE, 0);
            textArea.select(0, 0);
        }
    }

    public void printHelpMenu() {
        String helpMenu = "*************\n"
                + "Help Menu\n"
                + "*************\n"
                + NODE_USAGE + "\n"
                + BAR_USAGE + "\n"
                + LOAD_USAGE + "\n"
                + SUPPORT_USAGE + "\n"
                + "*************\n\n";
        textArea.insert(helpMenu, 0);
        textArea.select(0, 0);
    }

    public void processCommand(String command) {
        command = command.trim();

        if (command.toLowerCase().startsWith(SELECT_COMMAND)) {
            parseSelectCommand(command, true);
        } else if (command.toLowerCase().startsWith(UNSELECT_COMMAND)) {
            parseSelectCommand(command, false);
        } else if (command.toLowerCase().startsWith(HELP_COMMAND)) {
            printHelpMenu();
        } else if (command.toLowerCase().startsWith(NODE_COMMAND)) {
            parseNodeCommand(command);
        } else if (command.toLowerCase().startsWith(LOAD_COMMAND)) {
            parseLoadCommand(command);
        } else if (command.toLowerCase().startsWith(SUPPORT_COMMAND)) {
            parseSupportCommand(command);
        } else if (command.toLowerCase().startsWith(BAR_COMMAND)) {
            parseBarCommand(command);
        } else if (command.toLowerCase().startsWith(DELETE_COMMAND)) {
            parseDeleteCommand(command);
        } else if (MyActionMap.ACTION_MAP.get(command) != null) {
            MyActionMap.ACTION_MAP.get(command).actionPerformed(null);
        } else {
            textArea.insert("**Unknown command \"" + command + "\"  use \"Help\" for more information", 0);
            textArea.select(0, 0);
        }
    }


    public void traverseUpCommandHistory() {
        if (commandIndex < commandHistory.size() - 1) {
            commandIndex++;
            commandTextField.setText(commandHistory.get(commandIndex)); ///.substring(COMMAND_PROMPT.length()
        } else if (commandIndex == commandHistory.size() - 1) {
            commandIndex++;
            commandTextField.setText("");
        }
        textArea.select(0, 0);
    }

    public void traverseDownCommandHistroy() {
        if (commandIndex > 0) {
            commandIndex--;
            commandTextField.setText(commandHistory.get(commandIndex));
        }
    }

    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            traverseUpCommandHistory();
            return;
        }

        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            traverseDownCommandHistroy();
            return;
        }

        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            textArea.insert(COMMAND_PROMPT + commandTextField.getText() + "\n", 0);
            commandHistory.add(commandTextField.getText());
            processCommand(commandTextField.getText());
            commandTextField.setText("");
            commandIndex = commandHistory.size();
            //System.out.println(commandIndex);

            return;
        }
    }

    @Override
    protected JPanel getMainPanel() {
        commandTextField = new JTextField(10);
        commandTextField.setText("");
        commandTextField.setBorder(new LineBorder(Color.DARK_GRAY));
        commandTextField.addKeyListener(this);

        textArea = new JTextArea(5, 20);
        textArea.append("Text Interface\n");
        textArea.append("Sections Loaded\n");
        textArea.append("Images Loaded\n");

        textArea.setEditable(false);
        textArea.setBorder(new LineBorder(Color.DARK_GRAY));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JPanel commandPanel = new JPanel(new BorderLayout());
        JLabel commandLabel = new JLabel(COMMAND_PROMPT);
        commandLabel.setBackground(Color.WHITE);
        commandPanel.setBackground(Color.WHITE);
        commandPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        commandPanel.add(commandLabel, BorderLayout.WEST);
        commandTextField.setBorder(new EmptyBorder(1, 1, 1, 1));
        commandPanel.add(commandTextField, BorderLayout.CENTER);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(commandPanel, BorderLayout.NORTH);
        textPanel.add(scrollPane, BorderLayout.CENTER);
        return textPanel;
    }

    @Override
    protected String getPanelTitle() {
        return TITLE;
    }

    @Override
    protected Action getCloseAction() {
        return new closeAction();
    }

    class closeAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gui.getGuiPanelModel().setCLIPanelVisible(false);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
