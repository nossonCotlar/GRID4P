

import themidibus.*;
import static java.lang.Integer.min;

public class Grid {
    public static final int TOTAL_PARAMS = 64;
    private static final int NOTE_OFFSET = 32;
    private static final int BANK_MULT = 4;
    private static long midiEventCount = 0;
    private static MidiBus bus;
    private Gknob[] k; //array of Gknobs
    private int counter; //to keep track of already-used knobs
    private boolean verbose; //whether to print midi messages to stdout

    public Grid(){
        verbose = false;
        counter = 0;
        k = new Gknob[TOTAL_PARAMS]; //create Gknob array
        for (int i = 0; i < TOTAL_PARAMS; i ++) {
            k[i] = new Gknob(); //initialize each Gknob
        }
        //MidiBus.list();
        int inputIndex = findIntechInputIndex(MidiBus.availableInputs()); //find midi input with "intech"
        bus = new MidiBus(this, inputIndex, -1); //set up midi bus

    }

    public static Grid begin(){
        Grid grid = new Grid();


        return grid;
    }

    public float get(int n) { //returns the value of the specified Mknob
        return k[n].get();
    }
    public float value(int n) { //alias of get()
        return k[n].get();
    }
    public boolean getB(int n) { //returns the value of the specified Mknob's button (boolean)
        return k[n].getB();
    }

    public float getMin(int n) {return k[n].min();}
    public float getMax(int n) {return k[n].max();}

    public Gknob setGknob(int n, float min, float max) {
        this.advanceCounter();
        this.k[n].min = min;
        this.k[n].max = max;
        this.k[n].used = true;
        return this.k[n];
    }
    public Gknob setGknob(int n, float min, float max, float def) {
        this.k[n].val = (int) map(def, min, max, 0, 127);
        this.k[n].min = min;
        this.k[n].max = max;
        this.k[n].used = true;
        return this.k[n];
    }
    public Gknob addGknob(float min, float max) {
        this.advanceCounter();
        this.k[counter].min = min;
        this.k[counter].max = max;
        this.k[counter].used = true;
        return this.k[counter];
    }
    public Gknob addGknob(float min, float max, float def) {
        this.advanceCounter();
        this.k[counter].val = (int) map(def, min, max, 0, 127);
        this.k[counter].min = min;
        this.k[counter].max = max;
        this.k[counter].used = true;
        return this.k[counter];
    }
    public void controllerChange(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].val = value;
        midiEventCount++;
        verboseMessaging("Tweak",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }
    public void noteOn(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].button = true;
        midiEventCount++;
        verboseMessaging("Note-On",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }
    public void noteOff(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].button = false;
        midiEventCount++;
        verboseMessaging("Note-Off",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }


    public void verbose(boolean v) {
        this.verbose = v;
    }
    private void verboseMessaging(String eventType, String channel, String number, String value, String eventID){
        if(this.verbose){
            System.out.println(eventType + " | Channel: " +
                    channel + " | Number: " +
                    number + " | Value: " +
                    value + " -- EventID: " + eventID);
        }
    }

    private int advanceCounter() {
        while (k[this.counter].used) this.counter++;
        return min(this.counter, TOTAL_PARAMS - 1);
    }

    private float map(float val, float ax, float ay, float bx, float by){
        return bx + (by - bx) * ((val - ax) / (ay - ax));
    }
    private int findIntechInputIndex(String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].contains("Intech")) return i;
        }

        return -1;
    }
}
