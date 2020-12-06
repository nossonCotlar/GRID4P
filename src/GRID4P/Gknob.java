package grid4p;

/**
 * A Gknob (GRID Knob) is a representation of a modifiable physical GRID parameter.
 *
 * A static Gknob array is handled by the {@link Grid} class,
 * and as such, Gknob-s needn't be explicitly declared or defined by the user.
 * That being said, it is welcome and encouraged for the user to explicitly
 * use Gknob objects, as it makes the program more readable.
 *
 * @see Grid
 * @example BasicWithGknobs
 */
public class Gknob {
    protected int val, def;
    protected float min, max;
    protected boolean used, button;
    protected Gaction gaction;

    /**
     * Constructor for a Gknob, intended to be called when populating
     * an addressable list or array of Gknobs.
     *
     * The values for a Gknob default constructor are:
     *      value: 0
     *      minimum: 0
     *      maximum: 127
     *      button: false
     */
    Gknob() {
        val = 0;
        min = 0;
        max = 127;
        button = false;
    }

    /**
     * Constructor for a Gknob, intended to be called when populating
     * an addressable list or array of Gknobs.
     *
     * @param val default value that the Gknob will start off with
     * @param min minimum value
     * @param max maximum value
     */
    Gknob(int val, float min, float max) {
        this.val = val;
        this.min = min;
        this.max = max;
        button = false;
        used = true;
    }

    /**
     * Returns the held value, which is the raw integer value mapped
     * to the range specified by {@link Gknob#min} and {@link Gknob#max}
     *
     * @return the value held by the Gknob
     */
    public float get() {
        return map(val, 0, 127, min, max);
    }
    /**
     * Returns the held value, which is the raw integer value mapped
     * to the range specified by {@link Gknob#min} and {@link Gknob#max}
     * This is alias of {@link Gknob#get()}
     *
     * @return the value held by the Gknob
     */
    public float value() {
        return this.get();
    }
    /**
     * Returns the boolean button value indicating whether or not
     * the Gknob's button is pushed down.
     *
     * @return the button status of the Gknob
     */
    public boolean getB() {
        return button;
    }

    /**
     * Returns the <strong>unmapped</strong> value held by the Gknob.
     * @return the unmapped Gknob value
     */
    public int getRaw(){ return this.val;}

    /**
     * Returns the minimum value that the Gknob can hold.
     * This normally does not change during runtime and is
     * primarily for reference and sketch-wide consistency.
     *
     * @return the Gknob's maximum value
     */
    public float min() {return this.min;}
    /**
     * Returns the maximum value that the Gknob can hold.
     * This normally does not change during runtime and is
     * primarily for reference and sketch-wide consistency.
     *
     * @return the Gknob's maximum value
     */
    public float max() {return this.max;}

    /**
     * Sets the {@link Gknob#used} flag to false,
     * indicating this Gknob as vacant for configuration
     */
    public void free() {
        used = true;
    }

    /**
     * Method for mapping a value in an existing range to a value in a newly specified range.
     * This behaves identically to the map() method in the Processing Library.
     *
     * @param val value to map
     * @param ax initial range start point
     * @param ay initial range end point
     * @param bx new range start point
     * @param by new range end point
     * @return the mapped value
     */
    private float map(float val, float ax, float ay, float bx, float by){
        return bx + (by - bx) * ((val - ax) / (ay - ax));
    }

    /**
     * Method which accepts a user created Gaction object, with user defined gaction methods.
     * The methods in the Gaction are to be called when the appropriate midi event occurs.
     *
     * @param passedAction user defined implementation of the Gaction interface
     */
    public void setAction(Gaction passedAction){
        this.gaction = passedAction;
    }
}

