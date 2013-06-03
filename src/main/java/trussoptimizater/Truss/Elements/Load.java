package trussoptimizater.Truss.Elements;

import trussoptimizater.Truss.Events.ModelEvent;


/*
 * This class represents a Point Load. A point Load can be positive or negative and horizontal
 * or vertical.
 */
public class Load extends Element {

    /**
     * Vertical orientation
     */
    public static final String VERTICAL_LOAD = "VERTICAL";
    /**
     * Horizontal orientation
     */
    public static final String HORIZOANTAL_LOAD = "HORIZONTAL";
    /**
     * Load orientation, can be either Load.VERTICAL_LOAD or Load.HORIZOANTAL_LOAD
     */
    private String orientation;
    /**
     * Load in KN
     */
    private double load;
    /**
     * Node that load is attached too
     */
    private Node node;
    /**
     * Self weight load type - ie self weight of bars/beams
     */
    public static int SELF_WEIGHT = 2;
    /**
     * User defined load type - ie loads that user has added
     */
    public static int USER_DEFINED = 3;
    /**
     * Load type, can be either Load.SELF_WEIGHT or Load.USER_DEFINED
     */
    private int loadType = -1;

    public Load(int loadNumber, Node node, double load, String direction, int loadType) {
        super(loadNumber);

        this.orientation = direction;
        this.load = load;
        this.node = node;
        this.loadType = loadType;

        if (loadType == Load.USER_DEFINED) {
            node.addLoadIndex(this.getIndex());
        }

    }

    /**
     *
     * @return clone of Load object
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Load loadClone = (Load) super.clone();
        loadClone.node = (Node) this.node.clone();
        return loadClone;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj instanceof Load) {
            Load l = (Load) obj;
            if (this.node.equals(l.node)
                    && this.load == l.load
                    && this.loadType == l.loadType
                    && this.orientation.equals(l.orientation)
                    && this.number == l.number) {
                return true;
            }
        }

        return false;


    }

    @Override
    public int hashCode() {
        return this.node.hashCode() + this.number;
    }

    /**
     *
     * @return String representation of Load
     */
    @Override
    public String toString() {
        return "[Load, node, load value, type]  [" + this.number + ", " + this.node.getNumber() + ", " + this.load + ", " + orientation + " ]";
    }



    /**
     *
     * @return Orientation of Load, either
     * <ul>
     * <li>Load.Vertical </li>
     * <li>Load.Horizontal</li>
     * </ul>
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     *
     * @return Load in KN
     */
    public double getLoad() {
        return load;
    }

    /**
     *
     * @return Node that load is attached too
     */
    public Node getNode() {
        return node;
    }

    /**
     *
     * @return Load type, can be either
     * <ul>
     * <li>Load.SELF_WEIGHT</li>
     * <li>Load.USER_DEFINED</li>
     * </ul>
     */
    public int getLoadType() {
        return loadType;
    }

    /**
     * 
     * @param node - The node that you want to add a load too
     */
    public void setNode(Node node) {
        this.node = node;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     *
     * @param load Load in KN
     */
    public void setLoad(double load) {
        this.load = load;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     *
     * @param orientation Load orientation, use either
     *
     * <ul>
     * <li>Load.VERTICAL_LOAD</li>
     * <li>Load.HORIZOANTAL_LOAD</li>
     * </ul>
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     *
     * @param loadType Type of Load, use either
     *
     * <ul>
     * <li>Load.SELF_WEIGHT </li>
     * <li>Load.USER_DEFINED</li>
     * </ul>
     */
    public void setLoadType(int loadType) {
        this.loadType = loadType;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }
}
