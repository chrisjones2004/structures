package trussoptimizater.Truss.Analysis;

import trussoptimizater.Truss.JMatrix.JMatrix;
import trussoptimizater.Truss.TrussModel;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import trussoptimizater.Truss.Elements.*;

/**
 * This class will analyze a truss using the direct stiffness method and calculate the following
 * <p>
 * <ul>
 * <li>Nodal Deflections
 * <li>Bar Axial Forces
 * <li>Bar Moments
 * <li>Bar Shear Forces
 * <li>Support Reaction
 * </ul>
 * <p>
 * It does this by executing the following procdeure
 * <ul>
 * <li>Calculate local stiffness matrices for all bars
 * <li>Construct a gloabl stiffness matrix from all the local stiffness matrix
 * <li>Calculate Global Force vector
 * <li>Solve the following equation to find unknown displacements    F = ku,
 * <p>
 * where F - Global Force Vector<p>
 *       k - Gloabl Force Vector<p>
 *       u - Displacement vector<p>
 * <li>Post process displacement values to find axials forces, support reaction etc
 * </ul>
 * <p>
 * @author Chris
 */
public class FrameAnalyzer extends Analyzer {

    private TrussModel truss;

    /*
     * The number of points to represent the deflected shape of the bar. The higher the number,
     * the more accurate the representation will be, however over a value of 50 the program
    becomes noticably slow.
     */
    
    public static final int NO_DEFLECTION_POINTS = 11;

    public FrameAnalyzer(TrussModel truss) {
        super();
        this.truss = truss;
    }

    public boolean quickAnalysis() {
        if (analysisModel.isSelfWeightIncluded()) {
            truss.calculateSelfWeightOfTruss();
        }
        JMatrix globalDisplacementMatrix = null;
        JMatrix globalMatrix = null;
        JMatrix gloablDisplacementVector = null;
        JMatrix gloablForceVector = null;

        try {
            globalMatrix = calculateGlobalStiffnessMatrix(truss);
            gloablForceVector = calculateGlobalForceVector(truss);
            int[] rowsToKeep = getArrayofRowsToKeep(truss);
            int[] colsToKeep = {0};
            globalMatrix = globalMatrix.getMatrix(rowsToKeep, rowsToKeep);
            gloablForceVector = gloablForceVector.getMatrix(rowsToKeep, colsToKeep);
            gloablDisplacementVector = globalMatrix.solve(gloablForceVector);

            globalDisplacementMatrix = new JMatrix(truss.getNodeModel().size() * Bar.FIXED_DOF, 1);
            globalDisplacementMatrix = globalDisplacementMatrix.plusEquals(gloablDisplacementVector, rowsToKeep, colsToKeep);
            calculateBarForceMatrix(truss, globalDisplacementMatrix);

            setNodeDeflections(truss, globalDisplacementMatrix);

            calculateBarForces(truss);
            calculateBarDeflectedPoints(truss);


            if (verbose) {
                printAnaysisSteps(globalMatrix, globalDisplacementMatrix, gloablForceVector, gloablDisplacementVector);
            }
            return true;

        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }


    }

    public boolean fullAnalysis() {
        truss.setAnalysed(false);
        System.out.println("Analyzing truss as frame");
        if (!isStructureAnalzable(truss)) {
            System.out.println("Structure is not analyzable");
            return false;
        }

        try {
            if (!quickAnalysis()) {
                System.out.println("Error in analyszing structure ");
                truss.setAnalysed(false);
                JOptionPane.showMessageDialog(null, "Error in analyszing structure\nYou are probably you are trying to "
                        + "analyze a pinned frame with a frame analyzer!", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            calculateReactions(truss);
            truss.setAnalysed(true);
            return true;
        } catch (Exception ex) {
            System.out.println("Error in analyszing structure " + ex);
            truss.setAnalysed(false);
            JOptionPane.showMessageDialog(null, "Error in analyszing structure\nYou are probably you are trying to "
                    + "analyze a pinned frame with a frame analyzer!", "Alert", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Adds all local matrixes together
     * @param truss
     * @return gloabl stiffness matrix of order (3*node count) x (3*node count)
     */
    public JMatrix calculateGlobalStiffnessMatrix(TrussModel truss) {

        JMatrix globalMatrix = new JMatrix(Bar.FIXED_DOF * truss.getNodeModel().size(), Bar.FIXED_DOF * truss.getNodeModel().size());

        for (int i = 0; i < truss.getBarModel().size(); i++) {
            truss.getBarModel().get(i).calculateBeamStiffnessMatrix();

            if (verbose) {
                System.out.println("\nStiffness Matrix for bar" + truss.getBarModel().get(i).getNumber() + "\n");
                truss.getBarModel().get(i).getStiffnessMatrix().print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
            }

            int node1Number = truss.getBarModel().get(i).getNode1().getNumber();
            int node2Number = truss.getBarModel().get(i).getNode2().getNumber();

            int[] x = {Bar.FIXED_DOF * node1Number - Bar.FIXED_DOF, Bar.FIXED_DOF * node1Number - Bar.FIXED_DOF + 1, Bar.FIXED_DOF * node1Number - Bar.FIXED_DOF + 2, Bar.FIXED_DOF * node2Number - Bar.FIXED_DOF, Bar.FIXED_DOF * node2Number - Bar.FIXED_DOF + 1, Bar.FIXED_DOF * node2Number - Bar.FIXED_DOF + 2};
            int[] y = {Bar.FIXED_DOF * node1Number - Bar.FIXED_DOF, Bar.FIXED_DOF * node1Number - Bar.FIXED_DOF + 1, Bar.FIXED_DOF * node1Number - Bar.FIXED_DOF + 2, Bar.FIXED_DOF * node2Number - Bar.FIXED_DOF, Bar.FIXED_DOF * node2Number - Bar.FIXED_DOF + 1, Bar.FIXED_DOF * node2Number - Bar.FIXED_DOF + 2};

            globalMatrix = globalMatrix.plusEquals(truss.getBarModel().get(i).getStiffnessMatrix(), x, y);

        }
        if (verbose) {
            System.out.println("\n***Global Matrix ***");
            globalMatrix.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
        }

        return globalMatrix;
    }

    /**
     * combines selfweight load vector and userdefined load vecotr
     * @param truss
     * @return Global Force Vector of order 3*node count
     */
    public JMatrix calculateGlobalForceVector(TrussModel truss) {


        JMatrix combinedLoadVector = null;
        JMatrix userDefinedLoadVector = calculateGlobalUserDefinedForceVector(truss);

        if (analysisModel.isSelfWeightIncluded()) {
            JMatrix selfWeightLoadVector = calculateGlobalSelfWeightForceVector(truss);
            combinedLoadVector = userDefinedLoadVector.plus(selfWeightLoadVector);
        } else {
            combinedLoadVector = userDefinedLoadVector;
        }


        if (verbose) {
            System.out.println("\n***Load Matrix ***");
            combinedLoadVector.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
        }
        return combinedLoadVector;
    }

    /**
     * Loops through all self weights and set them in there appropriate place in the
     * Force vector. As self weight always acts vertically, there will be no horizontal and
     * rotational forces in this vecotr.
     * @param truss
     * @return Global Force Vector of order 3*node count
     */
    private JMatrix calculateGlobalSelfWeightForceVector(TrussModel truss) {
        JMatrix selfWeightLoadVector = new JMatrix(Bar.FIXED_DOF * truss.getNodeModel().size(), 1);
        //This is used incase their are multiple loads on one node
        double origValue = 0;

        for (int i = 0; i < truss.getSelfWeightLoads().size(); i++) {
            int nodeNumber = truss.getSelfWeightLoads().get(i).getNode().getNumber();

            //not really neccessary as self weight loads should always be applied vertically!!
            if (truss.getSelfWeightLoads().get(i).getOrientation().equals(Load.VERTICAL_LOAD)) {
                origValue = selfWeightLoadVector.get(nodeNumber * Bar.FIXED_DOF - Bar.FIXED_DOF + 1, 0);
                selfWeightLoadVector.set(nodeNumber * Bar.FIXED_DOF - Bar.FIXED_DOF + 1, 0, origValue + truss.getSelfWeightLoads().get(i).getLoad());
            }
        }
        return selfWeightLoadVector;
    }

    /**
     * Loop through all user defined loads and set them in there appropriate place in the
     * Force vector. Currently can not add rotational forces
     * @param truss
     * @return Global Force Vector of order 3*node count
     */
    private JMatrix calculateGlobalUserDefinedForceVector(TrussModel truss) {
        JMatrix userDefinedLoadVector = new JMatrix(Bar.FIXED_DOF * truss.getNodeModel().size(), 1);

        //This is used incase their are multiple loads on one node
        double origValue = 0;
        for (int i = 0; i < truss.getLoadModel().size(); i++) {
            int nodeNumber = truss.getLoadModel().get(i).getNode().getNumber();

            if (truss.getLoadModel().get(i).getOrientation().equals(Load.VERTICAL_LOAD)) {
                origValue = userDefinedLoadVector.get(nodeNumber * Bar.FIXED_DOF - Bar.FIXED_DOF + 1, 0);
                userDefinedLoadVector.set(nodeNumber * Bar.FIXED_DOF - Bar.FIXED_DOF + 1, 0, origValue + truss.getLoadModel().get(i).getLoad());
            } else if (truss.getLoadModel().get(i).getOrientation().equals(Load.HORIZOANTAL_LOAD)) {
                origValue = userDefinedLoadVector.get(nodeNumber * Bar.FIXED_DOF - Bar.FIXED_DOF, 0);
                userDefinedLoadVector.set(nodeNumber * Bar.FIXED_DOF - Bar.FIXED_DOF, 0, origValue + truss.getLoadModel().get(i).getLoad());
            }
        }
        return userDefinedLoadVector;
    }

    /*
     * Used for bar analysis
     * Removes rows and columns where there are fixed supports
     */
    /**
     * Returns an array of rows and columns that should be used to calculate unknown displacements.
     * All rows and columns that correspond to fixed support conditions ie 0 displacement should be
     * deleted.
     *
     * @param truss
     * @return an array of rows and columns that should be kept for solving unknowns
     */
    public int[] getArrayofRowsToKeep(TrussModel truss) {

        ArrayList<Integer> rowsToKeep = new ArrayList<Integer>();

        //Loop through all Nodes
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            if (!truss.getNodeModel().get(i).isSupported()) {
                rowsToKeep.add(truss.getNodeModel().get(i).getNumber() * Bar.FIXED_DOF - Bar.FIXED_DOF);
                rowsToKeep.add(truss.getNodeModel().get(i).getNumber() * Bar.FIXED_DOF - Bar.FIXED_DOF + 1);
                rowsToKeep.add(truss.getNodeModel().get(i).getNumber() * Bar.FIXED_DOF - Bar.FIXED_DOF + 2);
                continue;
            }
            //if support is not fixed then find supports



            int supportIndex = truss.getNodeModel().get(i).getSupportIndex();
            Support support = truss.getSupportModel().get(supportIndex);
            if (support.getUx().equals(Support.FREE)) {
                rowsToKeep.add(truss.getNodeModel().get(i).getNumber() * Bar.FIXED_DOF - Bar.FIXED_DOF);
            }
            if (support.getUz().equals(Support.FREE)) {
                rowsToKeep.add(truss.getNodeModel().get(i).getNumber() * Bar.FIXED_DOF - Bar.FIXED_DOF + 1);
            }
            if (support.getRy().equals(Support.FREE)) {
                rowsToKeep.add(truss.getNodeModel().get(i).getNumber() * Bar.FIXED_DOF - Bar.FIXED_DOF + 2);
            }


        }


        //Converting to int[]
        int[] rowsToKeepArray = new int[rowsToKeep.size()];
        for (int i = 0; i < rowsToKeepArray.length; i++) {
            rowsToKeepArray[i] = rowsToKeep.get(i);
        }
        if (verbose) {
            System.out.println("Rows To Keep -" + rowsToKeep.toString());
        }
        return rowsToKeepArray;
    }

    /**
     * F = T*Fv
     * @param truss
     */
    public void calculateBarForces(TrussModel truss) {
        //+ve axial means it is in compression
        //-ve axial force means it is in tension

        double axialForce = 0;
        double moment1 = 0;
        double moment2 = 0;
        double shear1 = 0;
        double shear2 = 0;
        JMatrix localForceVector = null;

        for (int i = 0; i < truss.getBarModel().size(); i++) {

            localForceVector = truss.getBarModel().get(i).getTransformMatrix().times(truss.getBarModel().get(i).getForceVector());
            axialForce = localForceVector.get(0, 0);
            shear1 = localForceVector.get(1, 0);
            shear2 = localForceVector.get(1, 0);
            moment1 = localForceVector.get(2, 0);
            moment2 = localForceVector.get(5, 0);

            if (moment2 > 0) {
                moment2 = -moment2;
            } else {
                moment2 = -moment2;
            }

            truss.getBarModel().get(i).setAxialForce(axialForce);
            truss.getBarModel().get(i).setShearForce(shear1, shear2);
            truss.getBarModel().get(i).setMomentForce(moment1, moment2);
        }
    }

    public void calculateBarForceMatrix(TrussModel truss, JMatrix globalDisplacementMatrix) {
        //work out axial forces in bars
        JMatrix memberForceVector = null;
        JMatrix barDisplacementVector = new JMatrix(2 * Bar.FIXED_DOF, 1);

        for (int i = 0; i < truss.getBarModel().size(); i++) {

            //removeAllOtherRows
            int node1Index = truss.getBarModel().get(i).getNode1().getIndex();
            int node2Index = truss.getBarModel().get(i).getNode2().getIndex();

            int[] rowsToKeep = {node1Index * Bar.FIXED_DOF, node1Index * Bar.FIXED_DOF + 1, node1Index * Bar.FIXED_DOF + 2, node2Index * Bar.FIXED_DOF, node2Index * Bar.FIXED_DOF + 1, node2Index * Bar.FIXED_DOF + 2};
            int[] colsToKeep = {0};
            barDisplacementVector = globalDisplacementMatrix.getMatrix(rowsToKeep, colsToKeep);

            truss.getBarModel().get(i).setDisplacementVector(barDisplacementVector);


            memberForceVector = truss.getBarModel().get(i).getStiffnessMatrix().times(barDisplacementVector);
            truss.getBarModel().get(i).setForceVector(memberForceVector);

            //double maxCompressonForce = truss.getBarModel().get(i).getMaxCompressionAxialForce();
            //truss.getBarModel().get(i).setMaxCompressionAxialForce(maxCompressonForce);

            if (verbose) {
                System.out.println("\nBarDisplacementMatrix for bar " + truss.getBarModel().get(i).getNumber());
                barDisplacementVector.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
                System.out.println("memberForceMatrix for bar " + truss.getBarModel().get(i).getNumber());
                memberForceVector.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
            }

        }//end of for

    }

    /**
     * For each support, loop through all bars intersecting at support and add up the forces in them
     * @param truss
     */
    public void calculateReactions(TrussModel truss) {
        //System.out.println("Calculating reactions");
        double horizontalReaction = 0;
        double verticalReaction = 0;
        double rotationalReaction = 0;
        for (int i = 0; i < truss.getSupportModel().size(); i++) {
            int supportNodeNumber = truss.getSupportModel().get(i).getNode().getNumber();

            //adding up all horizontal foces in bars
            for (int j = 0; j < truss.getBarModel().size(); j++) {
                if (truss.getBarModel().get(j).getNode1().getNumber() != supportNodeNumber && truss.getBarModel().get(j).getNode2().getNumber() != supportNodeNumber) {
                    continue;
                }
                if (truss.getBarModel().get(j).getNode1().getNumber() == supportNodeNumber) {
                    horizontalReaction += truss.getBarModel().get(j).getForceVector().get(0, 0);
                    verticalReaction += truss.getBarModel().get(j).getForceVector().get(1, 0);
                    rotationalReaction += truss.getBarModel().get(j).getForceVector().get(2, 0);
                } else {
                    horizontalReaction += truss.getBarModel().get(j).getForceVector().get(3, 0);
                    verticalReaction += truss.getBarModel().get(j).getForceVector().get(4, 0);
                    rotationalReaction += truss.getBarModel().get(j).getForceVector().get(5, 0);
                }

            }

            if (truss.getNodeModel().get(supportNodeNumber - 1).isLoaded()) {
                for (int j = 0; j < truss.getNodeModel().get(supportNodeNumber - 1).getLoadIndexes().size(); j++) {
                    int loadIndex = truss.getNodeModel().get(supportNodeNumber - 1).getLoadIndexes().get(j);
                    System.out.println("Load index " + loadIndex);
                    Load load = truss.getLoadModel().get(loadIndex);

                    if (load.getOrientation().equals(Load.VERTICAL_LOAD)) {
                        verticalReaction += -load.getLoad();
                    }

                    if (load.getOrientation().equals(Load.HORIZOANTAL_LOAD)) {
                        horizontalReaction += -load.getLoad();
                    }
                }
            }

            truss.getSupportModel().get(i).setReactionX(horizontalReaction);
            truss.getSupportModel().get(i).setReactionZ(verticalReaction);
            truss.getSupportModel().get(i).setRotationReaction(rotationalReaction);


            horizontalReaction = 0;
            verticalReaction = 0;
            rotationalReaction = 0;

        }
    }

    public void setNodeDeflections(TrussModel truss, JMatrix globalDisplacementMatrix) {

        for (int i = 0; i < globalDisplacementMatrix.getRowDimension(); i += Bar.FIXED_DOF) {
            int nodeIndex = (int) i / Bar.FIXED_DOF;
            truss.getNodeModel().get(nodeIndex).setXDisplacement(globalDisplacementMatrix.get(i, 0) * 1000);
            truss.getNodeModel().get(nodeIndex).setZDisplacement(globalDisplacementMatrix.get(i + 1, 0) * 1000);
            truss.getNodeModel().get(nodeIndex).setRotDisplacement(globalDisplacementMatrix.get(i + 2, 0));
        }
    }

    public void calculateBarDeflectedPoints(TrussModel truss) {
        JMatrix localDisplacementMatrix = new JMatrix(2 * Bar.FIXED_DOF, 1);
        JMatrix localDeflectedPointsAtX = new JMatrix(2 * Bar.FIXED_DOF, 1);
        JMatrix globalDeflectedPointsAtX = new JMatrix(2 * Bar.FIXED_DOF, 1);
        JMatrix aMatrix = new JMatrix(2 * Bar.FIXED_DOF, 2 * Bar.FIXED_DOF);
        Point2D[] deflectedPoints = new Point2D.Double[FrameAnalyzer.NO_DEFLECTION_POINTS + 1];
        //Point2D[] deflectedPoints = new Point2D.Double[FrameAnalyzer.NO_DEFLECTION_POINTS];
        for (int i = 0; i < truss.getBarModel().size(); i++) {
            deflectedPoints = new Point2D.Double[FrameAnalyzer.NO_DEFLECTION_POINTS + 1];
            //deflectedPoints = new Point2D.Double[FrameAnalyzer.NO_DEFLECTION_POINTS ];
            double L = truss.getBarModel().get(i).getLength() / 100;
            double[][] HArray = {{1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0},
                {0, 0, 0, -1, 0, 0},
                {1, L, 0, 0, 0, 0},
                {0, 0, 1, L, Math.pow(L, 2), Math.pow(L, 3)},
                {0, 0, 0, -1, -2 * L, -3 * Math.pow(L, 2)}
            };

            JMatrix HMatrix = new JMatrix(HArray);

            localDisplacementMatrix = truss.getBarModel().get(i).getTransformMatrix().times(truss.getBarModel().get(i).getDisplacementVector());


            for (int j = 0; j <= FrameAnalyzer.NO_DEFLECTION_POINTS; j++) {
                double x = (truss.getBarModel().get(i).getLength() / FrameAnalyzer.NO_DEFLECTION_POINTS) * j / 100;
                aMatrix = HMatrix.inverse().times(localDisplacementMatrix);


                double xDisplacementAtX = aMatrix.get(0, 0) + aMatrix.get(1, 0) * x;
                double zDisplacementAtX = aMatrix.get(2, 0) + aMatrix.get(3, 0) * x + aMatrix.get(4, 0) * Math.pow(x, 2) + aMatrix.get(5, 0) * Math.pow(x, 3);
                double rotDisplacementAtX = -aMatrix.get(3, 0) - 2 * aMatrix.get(4, 0) * x - 3 * aMatrix.get(5, 0) * Math.pow(x, 2);

                localDeflectedPointsAtX.set(0, 0, localDisplacementMatrix.get(0, 0));
                localDeflectedPointsAtX.set(1, 0, localDisplacementMatrix.get(1, 0));
                localDeflectedPointsAtX.set(2, 0, localDisplacementMatrix.get(2, 0));

                localDeflectedPointsAtX.set(3, 0, xDisplacementAtX);
                localDeflectedPointsAtX.set(4, 0, zDisplacementAtX);
                localDeflectedPointsAtX.set(5, 0, rotDisplacementAtX);



                globalDeflectedPointsAtX = truss.getBarModel().get(i).getTransformMatrix().inverse().times(localDeflectedPointsAtX);
                deflectedPoints[j] = new Point2D.Double(globalDeflectedPointsAtX.get(3, 0) * 1000, globalDeflectedPointsAtX.get(4, 0) * 1000);

            }//End of deflectedLoop 

            truss.getBarModel().get(i).setBarDeflections(deflectedPoints);

        }
    }//End of calculate DeflectedPoints

    public void printAnaysisSteps(JMatrix globalMatrix, JMatrix globalDisplacementMatrix, JMatrix loadMatrix, JMatrix displacementMatrix) {

        System.out.println("\n***Reduced Global Matrix ***");
        globalMatrix.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
        System.out.println("\n***Reduced Load Matrix ***");
        loadMatrix.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
        System.out.println("\n***Displacement Matrix ***");
        displacementMatrix.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);
        System.out.println("\n***Gloabl Displacement Matrix ***");
        globalDisplacementMatrix.print(JMatrix.PRINT_WIDTH, JMatrix.DECIMIAL_PLACES);

    }
}
