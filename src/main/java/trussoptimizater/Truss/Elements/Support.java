package trussoptimizater.Truss.Elements;

import trussoptimizater.Truss.Events.ModelEvent;

public class Support extends Element {

    /**
     * Node that support is attached to
     */
    private Node node;
    /**
     * A free support condition means that support is free to move in a particulur direction
     */
    public static final String FREE = "FREE";
    /**
     * A fixed support condition means that support can not move in a particulur direction
     */
    public static final String FIXED = "FIXED";
    /**
     * Horizontal support condition
     */
    private String ux;
    /**
     * Vertical support condition
     */
    private String uz;
    /**
     * Rotatinal support condition
     */
    private String ry;
    /**
     * Horizontal reaction in KN
     */
    private double reactionX = 0;
    /**
     * Vertical reaction in KN
     */
    private double reactionZ = 0;
    /**
     * Rotation Reaction in KNm
     */
    private double rotationReaction = 0;

    public Support(int supportNumber, Node node, String ux, String uz, String ry) throws Exception {
        super(supportNumber);
        this.node = node;
        if (this.node.getSupportIndex() != Node.NO_SUPPORT) {
            throw new Exception("Node is already supported!");
        }

        this.node.setSupportIndex(this.getIndex());
        this.ux = ux;
        this.uz = uz;
        this.ry = ry;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Support supportClone = (Support) super.clone();
        supportClone.node = (Node) this.node.clone();
        return supportClone;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this){
            return true;
        }

        if(obj instanceof Support){
            Support s = (Support)obj;
            if(this.node.equals(s.node) &&
                    this.ry.equals(s.ry) &&
                    this.ux.equals(s.ux) &&
                    this.uz.equals(s.uz) &&
                    this.number == s.number){
                return true;
            }
        }

        return false;


    }

    @Override
    public int hashCode() {
        return this.node.hashCode() + this.number;
    }

    @Override
    public String toString() {
        return "[Support, node, ux, uy, ry]  [" + this.number + ", " + this.node.getNumber() + ", " + this.ux + ", " + this.uz + ", " + this.ry + " ]";
    }

    /**
     *
     * @return Node that support is attached to
     */
    public Node getNode() {
        return node;
    }

    /**
     *
     * @return Horizontal support condition
     */
    public String getUx() {
        return ux;
    }

    /**
     *
     * @return Vertical support condition
     */
    public String getUz() {
        return uz;
    }

    /**
     * 
     * @return Rotational support condition
     */
    public String getRy() {
        return ry;
    }

    /**
     *
     * @return Hozintal reaction in KN
     */
    public double getReactionX() {
        return reactionX;
    }

    /**
     *
     * @return Vertical reaction in KN
     */
    public double getReactionZ() {
        return reactionZ;
    }

    /**
     *
     * @return Rotational reaction in KNm
     */
    public double getRotationReaction() {
        return rotationReaction;
    }

    //Used to check if the truss is stable or not
    public int getDOFLost() {
        int supportCount = 0;
        if (this.ux.equals(Support.FIXED)) {
            supportCount++;
        }
        if (this.uz.equals(Support.FIXED)) {
            supportCount++;
        }
        if (this.ry.equals(Support.FIXED)) {
            supportCount++;
        }
        return supportCount;
    }

    /**
     * Should be either Support.FIXED or Support.FREE
     * @param ux Horizontal suport condition
     */
    public void setUx(String ux) {
        this.ux = ux;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     * Should be either Support.FIXED or Support.FREE
     * @param uz Vertical suport condition
     */
    public void setUz(String uz) {
        this.uz = uz;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     * Should be either Support.FIXED or Support.FREE
     * @param ry Rotational suport condition
     */
    public void setRy(String ry) {
        this.ry = ry;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     * Set node that support is attached too
     * @param node
     */
    public void setNode(Node node) {
        this.node = node;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.ELEMENT_MODIFICATION));
    }

    /**
     * Set hoirontal reaction
     * @param reactionX Horizontal reaction in KN
     */
    public void setReactionX(double reactionX) {
        this.reactionX = reactionX;
    }

    /**
     * Set vertical reaction
     * @param reactionZ Vertical Reaction in KN
     */
    public void setReactionZ(double reactionZ) {
        this.reactionZ = reactionZ;
    }

    /**
     * Set rotational reaction
     * @param reactionRY Rotational reaction in KNm
     */
    public void setRotationReaction(double reactionRY) {
        this.rotationReaction = reactionRY;
    }
}//end of Support class

