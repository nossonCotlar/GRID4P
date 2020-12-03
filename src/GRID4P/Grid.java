package grid4p;

import themidibus.*;
import static java.lang.Integer.min;

/**
 * This is the main class for managing communication with the GRID modules.
 *
 * The Grid class <strong>can not</strong> be instantiated, and instead only provides static methods
 * for assigning parameters, setting configurations, and other functionality.
 *
 * The class works hand-in-hand with the Gknob class, which can either be
 * explicitly or implicitly used by the user.
 *
 * @see Gknob
 * @example Basic
 */

public abstract class Grid {
    public static final int TOTAL_PARAMS = 64;
    private static final int NOTE_OFFSET = 32;
    private static final int BANK_MULT = 4;
    private static long midiEventCount = 0;
    private static MidiBus bus;
    private static Gknob[] k; //array of Gknobs
    private static int counter; //to keep track of already-used knobs
    private static boolean verbose; //whether to print midi messages to stdout

    /**
     * The method to initialize the GRID setup.
     * Does some important tasks like creating an array of Gknobs and
     * starting the Midi Bus for the actual communication.
     * This method should be called in the setup() method of a Processing sketch as Grid.begin()
     *
     * @example Basic
     */
    public static void begin(){
        verbose = false;
        counter = 0;
        k = new Gknob[TOTAL_PARAMS]; //create Gknob array
        for (int i = 0; i < TOTAL_PARAMS; i ++) {
            k[i] = new Gknob(); //initialize each Gknob
        }
        //MidiBus.list();
        int inputIndex = findIntechInputIndex(MidiBus.availableInputs()); //find midi input with "intech"
        bus = new MidiBus(); //instantiate up midi bus
        bus.addInput(inputIndex);
        bus.registerParent(new GridCallbackHandler());
    }

    /**
     * Returns the value currently held by the specified Gknob.
     * not often called, because it is more readable to call it from
     * the specific Gknob object, but it works just as well.
     *
     * @param GknobIndex the index of the GRID's Gknob you wish to access
     * @return the specified Gknob's value
     */
    public static float get(int GknobIndex) { //returns the value of the specified Gknob
        return k[GknobIndex].get();
    }

    /**
     * this is an alias of the {@link Grid#get(int) get()} method
     * Returns the value currently held by the specified Gknob.
     * not often called, because it is more readable to call it from
     * the specific Gknob object, but it works just as well.
     *
     * @param GknobIndex the index of the GRID's Gknob you wish to access
     * @return the specified Gknob's value
     */
    public static float value(int GknobIndex) { //alias of get()
        return k[GknobIndex].get();
    }

    /**
     * Returns true or false depending on if the Gknob's button
     * is currently pushed down.
     *
     * @param GknobIndex the index of the GRID's Gknob you wish to access
     * @return the specified Gknob's button value
     */
    public static boolean getB(int GknobIndex) { //returns the value of the specified Gknob's button (boolean)
        return k[GknobIndex].getB();
    }

    /**
     * Returns the minimum value that the specified Gknob can hold.
     * This normally does not change during runtime and is
     * primarily for reference and sketch-wide consistency.
     *
     * @param GknobIndex the index of the GRID's Gknob you wish to access
     * @return the specified Gknob's minimum value
     */
    public static float getMin(int GknobIndex) {return k[GknobIndex].min();}

    /**
     * Returns the maximum value that the specified Gknob can hold.
     * This normally does not change during runtime and is
     * primarily for reference and sketch-wide consistency.
     *
     * @param GknobIndex the index of the GRID's Gknob you wish to access
     * @return the specified Gknob's maximum value
     */
    public static float getMax(int GknobIndex) {return k[GknobIndex].max();}

    /**
     * Sets the minimum and maximum configuration for the specified Gknob.
     * Returns the Gknob instance that was configured so that it
     * may be handled by the user if desired.
     *
     * @param GknobIndex the index of the GRID's Gknob you wish to access
     * @param min the minimum value that the Gknob will hold
     * @param max the maximum value that the Gknob will hold
     * @return the newly configured Gknob
     */
    public static Gknob set(int GknobIndex, float min, float max) {
        advanceCounter();
        k[GknobIndex].min = min;
        k[GknobIndex].max = max;
        k[GknobIndex].used = true;
        return k[GknobIndex];
    }

    /**
     * Sets the minimum, maximum, and default configuration for the specified Gknob.
     * Returns the Gknob instance that was configured so that it
     * may be handled by the user if desired.
     *
     * @param GknobIndex the index of the GRID's Gknob you wish to access
     * @param min the minimum value that the Gknob will hold
     * @param max the maximum value that the Gknob will hold
     * @param def the default value that the Gknob will begin with
     * @return the newly configured Gknob
     */
    public static Gknob set(int GknobIndex, float min, float max, float def) {
        k[GknobIndex].val = (int) map(def, min, max, 0, 127);
        k[GknobIndex].min = min;
        k[GknobIndex].max = max;
        k[GknobIndex].used = true;
        return k[GknobIndex];
    }

    /**
     * Adds a Gknob configuration to the next vacant Gknob position on the GRID.
     * Sets the minimum and maximum configuration for said Gknob.
     * Returns the Gknob instance that was added so that it
     * may be handled by the user if desired.
     *
     * @param min the minimum value that the Gknob will hold
     * @param max the maximum value that the Gknob will hold
     * @return the newly added and configured Gknob
     */
    public static Gknob add(float min, float max) {
        advanceCounter();
        k[counter].min = min;
        k[counter].max = max;
        k[counter].used = true;
        return k[counter];
    }

    /**
     * Adds a Gknob configuration to the next vacant Gknob position on the GRID.
     * Sets the minimum, maximum, and default configuration for said Gknob.
     * Returns the Gknob instance that was added so that it
     * may be handled by the user if desired.
     *
     * @param min the minimum value that the Gknob will hold
     * @param max the maximum value that the Gknob will hold
     * @param def the default value that the Gknob will begin with
     * @return the newly added and configured Gknob
     */
    public static Gknob add(float min, float max, float def) {
        advanceCounter();
        k[counter].val = (int) map(def, min, max, 0, 127);
        k[counter].min = min;
        k[counter].max = max;
        k[counter].used = true;
        return k[counter];
    }

    //Midi Event Handlers

    /**
     * The controllerChange callback for the GridCallbackHandler class
     * to call once it receives midi input.
     * Locates the intended Gknob based on the midi channel and
     * midi number, then inserts the received midi value into {@link Gknob#val}.
     * Increments the Event Counter,
     * and calls the {@link Grid#verboseMidiEventMessaging(String, String, String, String, String) verboseMidiEventMessaging} method
     * providing the appropriate details.
     *
     * @param channel midi channel
     * @param number midi number
     * @param value midi value
     */
    protected static void controllerChange(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].val = value;
        midiEventCount++;
        verboseMidiEventMessaging("Tweak",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }

    /**
     * The noteOn callback for the GridCallbackHandler class
     * to call once it receives midi input.
     * Locates the intended Gknob based on the midi channel and
     * midi number, then sets the {@link Gknob#button} to true.
     * Increments the Event Counter,
     * and calls the {@link Grid#verboseMidiEventMessaging(String, String, String, String, String) verboseMidiEventMessaging} method
     * providing the appropriate details.
     *
     * @param channel midi channel
     * @param number midi number
     * @param value midi value
     */
    protected static void noteOn(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].button = true;
        midiEventCount++;
        verboseMidiEventMessaging("Note-On",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }
    /**
     * The noteOff callback for the GridCallbackHandler class
     * to call once it receives midi input.
     * Locates the intended Gknob based on the midi channel and
     * midi number, then sets the {@link Gknob#button} to false.
     * Increments the Event Counter,
     * and calls the {@link Grid#verboseMidiEventMessaging(String, String, String, String, String) verboseMidiEventMessaging} method
     * providing the appropriate details.
     *
     * @param channel midi channel
     * @param number midi number
     * @param value midi value
     */
    protected static void noteOff(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].button = false;
        midiEventCount++;
        verboseMidiEventMessaging("Note-Off",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }

    /**
     * Sets the {@link Grid#verbose} flag to the supplied value.
     * If set to true, it also prints the Midi Bus information.
     *
     * @param v value for the verbose flag
     */
    public static void verbose(boolean v) {
        verbose = v;
        if(v){
            System.out.println(bus.toString());
            try{
                System.out.println(bus.attachedInputs()[0]);
            } catch (Exception e){
                System.out.println("Couldn't find any inputs");
            }

        }
    }

    /**
     * A generic method for printing the verbosity of Midi Event Messages.
     * This is intended to be called within the Midi Callback methods.
     *
     * @param eventType type of midi event
     * @param channel midi channel
     * @param number midi number
     * @param value midi value
     * @param eventID midi event ID
     */
    private static void verboseMidiEventMessaging(String eventType, String channel, String number, String value, String eventID){
        if(verbose){
            System.out.println(eventType + " | Channel: " +
                    channel + " | Number: " +
                    number + " | Value: " +
                    value + " -- EventID: " + eventID);
        }
    }

    /**
     * Method to advance the vacant Gknob index counter.
     * Should be called whenever a Gknob is added to an automatically assigned index,
     * in order not to overwrite previously assigned configurations.
     * Returns the new counter index position, capped by the {@link Grid#TOTAL_PARAMS}
     *
     * @return the new Gknob counter index
     */
    private static int advanceCounter() {
        while (k[counter].used) counter++;
        return min(counter, TOTAL_PARAMS - 1);
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
    private static float map(float val, float ax, float ay, float bx, float by){
        return bx + (by - bx) * ((val - ax) / (ay - ax));
    }

    /**
     * Auxiliary method for determining the index of the Intech GRID
     * within the recognized midi inputs
     *
     * @param list the list of String identifiers for Midi Inputs
     * @return the index that corresponds to the Intech GRID
     */
    private static int findIntechInputIndex(String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].contains("Intech")) return i;
        }

        return -1;
    }
}
