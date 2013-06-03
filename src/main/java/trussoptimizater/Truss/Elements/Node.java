package trussoptimizater.Truss.Elements;

import java.awt.geom.Point2D;
import java.awt.*;
import java.util.ArrayList;
import trussoptimizater.Truss.Events.ModelEvent;

/**
 * This class represents a node
 * @author Chris
 */
public class Node extends Element {

    /**
     * X cordinate - cm
     */
    public double x;
    /**
     * Z cordinate - cm
     */
    public double z;
    /**
     * Horiznotal displacement in mm
     */
    private double XDisplacement;
    /**
     * Vertical displacement in mm
     */
    private double ZDisplacement;
    /**
     * Rotational displacement in mm
     */
    private double rotDisplacement;


    /**
     * Is used for setting supportIndex when there are no supports attached
     */
    public static final int NO_SUPPORT = -1;

    /*
     * Index of the support that is attached to node. If no supports are attached to this node then
     * it should be set to Node.NO_SUPPORT
     */
    private int supportIndex = NO_SUPPORT;
    /**
     * Index of the load that is attached to node. If no loads are attached to this node then
     * it should be set to Node.NO_LOAD
     */
    private ArrayList<Integer> loadIndexs = new ArrayList<Integer>();
    private Node symmetryNode = null;

    public Node(int nodeNumber) {
        this(nodeNumber, 0, 0);
    }

    public Node(int nodeNumber, double x, double z) {
        super(nodeNumber);
        this.x = x;
        this.z = z;
    }

    /**
     *
     * @return clone of node object
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Node) super.clone();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this){
            return true;
        }

        if(obj instanceof Node){
            Node n = (Node)obj;
            if(this.x == n.x && this.z == n.z && this.number == n.number){
                return true;
            }
        }

        return false;

        
    }

    @Override
    public int hashCode() {
        return (int)this.x+ (int)this.z + this.number;
    }

    /**
     *
     * @return string representation of Node object
     */
    @Override
    public String toString() {
        return "[Node, x, z, Support, Load]  -  [" + this.number + ", " + x + ", " + z + ", " + this.supportIndex + ", " + this.loadIndexs.toString() + "]";
    }

    /**
     *
     * @return true if a load is attached to this node, else returns false
     */
    public boolean isLoaded() {
        if (this.loadIndexs.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @return true if a support is attached to this node, else returns false
     */
    public boolean isSupported() {
        if (this.supportIndex == Node.NO_SUPPORT) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @return Z cordinate in cm
     */
    public Point2D.Double getPoint2D() {
        return new Point2D.Double(x, z);
    }

    /**
     *
     * @return point cordinate in cm
     */
    public Point getPoint() {
        return new Point((int) x, (int) z);
    }

    /**
     *
     * @return X cordinate in cm
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return Z cordinate in cm
     */
    public double getZ() {
        return z;
    }

    /**
     *
     * @param deflectionScale
     * @return deflected point in cm
     */
    public Point2D.Double getDeflectedPoint(double deflectionScale) {
        return new Point2D.Double(this.x + (XDisplacement / 10) * deflectionScale, this.z + (ZDisplacement / 10) * deflectionScale);
    }

    /**
     * 
     * @return displacement in z direction in mm
     */
    public double getZDisplacement() {
        //return ZDisplacement * 1000;
        return ZDisplacement;
    }

    /**
     * 
     * @return displacement in x direction in mm
     */
    public double getXDisplacement() {
        //return XDisplacement * 1000;
        return XDisplacement;
    }

    /**
     * 
     * @return displacement magnitude in  mm
     */
    public double getResultantDisplacement() {
        return Math.sqrt(Math.pow(ZDisplacement, 2) + Math.pow(XDisplacement, 2));
    }

    /**
     *
     * @return rotational displacement in radians
     */
    public double getRotDisplacement() {
        return rotDisplacement;
    }

    /**
     * Nodes should only have one load
     * @return the number of the attached load. If node has no load returns -1;
     */
    public ArrayList<Integer> getLoadIndexes() {
        return loadIndexs;
    }

    /**
     * Nodes should only have one Support
     * @return the number of the attached support. If node has no support returns -1;
     */
    public int getSupportIndex() {
        return supportIndex;
    }

    /**
     * Set the horiznontal displacement
     * @param XDisplacement in mm
     */
    public void setXDisplacement(double XDisplacement) {
        this.XDisplacement = XDisplacement;
    }

    public Node getSymmetryNode() {
        return symmetryNode;
    }

    /**
     * Set vertical disaplcement
     * @param ZDisplacement in mm
     */
    public void setZDisplacement(double ZDisplacement) {
        this.ZDisplacement = ZDisplacement;
    }

    /**
     * Set rotational displacement
     * @param rotDisplacement in radians
     */
    public void setRotDisplacement(double rotDisplacement) {
        this.rotDisplacement = rotDisplacement;
    }

    /**
     * Set node point
     * @param point - point corinate in cm
     */
    public void setPoint(Point2D point) {
        this.x = point.getX();
        this.z = point.getY();
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     * Set node point
     * @param x cordinate in cm
     * @param z cordinate in cm
     */
    public void setPoint(double x, double z) {
        this.x = x;
        this.z = z;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     * Set Z cordinate
     * @param z in cm
     */
    public void setZ(double z) {
        this.z = z;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     * Set X cordinate
     * @param x in cm
     */
    public void setX(double x) {
        this.x = x;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     *
     * @param loadNumber - index of load attached to node
     */
    public void addLoadIndex(int loadIndex) {
        this.loadIndexs.add(loadIndex);
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     *
     * @param loadNumber - index of load attached to node
     */
    public void setLoadIndexs(ArrayList<Integer> loadIndexs) {
        this.loadIndexs = loadIndexs;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     *
     * @param supportNumber index of support attached to support
     */
    public void setSupportIndex(int supportIndex) {
        this.supportIndex = supportIndex;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    public void setSymmetryNode(Node symmetryNode) {
        this.symmetryNode = symmetryNode;
    }
}//end of Node

