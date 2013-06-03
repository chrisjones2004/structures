package trussoptimizater.Truss.Elements;

import java.io.Serializable;
import trussoptimizater.Truss.Events.*;

/**
 * This is a super class for all elements and can not be instatatized.
 * @author Chris
 */
public abstract class Element extends java.util.Observable implements Serializable , Cloneable{

    /**
     * <p>Element number, should be between 0 and the elementArray.size. Each element has
     * to be assigned a number as it is used in the analysis part of the program. Elements numbers
     * have to be updated when elements are removed in the element arrays.</p>
     * 
     * <p>Perhaps a better way to do this would be to scrap this attribute and just use the element
     * index in the array as this is updated automatically.</p>
     */
    protected int number;

    /**
     * <p>Boolean representing whether the element is currently highlighted</p>
     * <p>Probably should be moved to GUI side, but currently convienant to have it here</p>
     */
    protected boolean highLighted;

   /**
     * Boolean representing whether the element is currently selected
    * <p>
     * Probably should be moved to GUI side, but currently convienant to have it here</p>
     */
    protected boolean selected;

    public Element(){

    }

    public Element(int number) {
        this.number = number;
    }

    /**
     *
     * @return true if element is highlighted, else return false
     */
    public boolean isHighLighted() {
        return highLighted;
    }

    /**
     *
     * @return true if element is highlighted, else returns false
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     *
     * @return element number
     */
    public int getNumber() {
        return number;
    }

    /**
     *
     * @return element index ie element number - 1
     */
    public int getIndex() {
        return number -1;
    }

    /**
     *
     * @param highLighted True if element is currently highlighted for example mouse is hovering over 2D highlight
     * Shape.
     */
    public void setHighLighted(boolean highLighted) {
        this.highLighted = highLighted;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.HIGHLIGHT_ELEMENTS));
    }

    /**
     *
     * @param selected True if element is currently selected for example element has been clicked on
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.SELECT_ELEMENTS));
    }

    /* Should get rid of this method*/
    public void setNumber(int number) {
        this.number = number;
        setChanged();
        notifyObservers(new ModelEvent(this, ModelEvent.INDEX_CHANGES));
    }


    @Override
    public abstract boolean equals(Object obj);


    @Override
    public abstract int hashCode() ;


    public String toSimpleString() {
        return this.getClass().getSimpleName() +" "+ this.number;
    }



}
