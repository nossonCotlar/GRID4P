package grid4p;

import themidibus.*;
import static java.lang.Integer.min;

public class Grid {
    public static final int TOTAL_PARAMS = 64;
    private static final int NOTE_OFFSET = 32;
    private static final int BANK_MULT = 4;
    private static long midiEventCount = 0;
    private static MidiBus bus;
    private static Gknob[] k; //array of Gknobs
    private static int counter; //to keep track of already-used knobs
    private static boolean verbose; //whether to print midi messages to stdout

    public Grid(){


    }
    public static int begin(){
        verbose = false;
        counter = 0;
        k = new Gknob[TOTAL_PARAMS]; //create Gknob array
        for (int i = 0; i < TOTAL_PARAMS; i ++) {
            k[i] = new Gknob(); //initialize each Gknob
        }
        //MidiBus.list();
        int inputIndex = findIntechInputIndex(MidiBus.availableInputs()); //find midi input with "intech"
        bus = new MidiBus(); //instanciate up midi bus
        bus.addInput(inputIndex);
        bus.registerParent(new GridCallbackHandler());
        return 0;
    }


    public static float get(int n) { //returns the value of the specified Mknob
        return k[n].get();
    }
    public static float value(int n) { //alias of get()
        return k[n].get();
    }
    public static boolean getB(int n) { //returns the value of the specified Mknob's button (boolean)
        return k[n].getB();
    }

    public static float getMin(int n) {return k[n].min();}
    public static float getMax(int n) {return k[n].max();}

    public static Gknob set(int n, float min, float max) {
        advanceCounter();
        k[n].min = min;
        k[n].max = max;
        k[n].used = true;
        return k[n];
    }
    public static Gknob set(int n, float min, float max, float def) {
        k[n].val = (int) map(def, min, max, 0, 127);
        k[n].min = min;
        k[n].max = max;
        k[n].used = true;
        return k[n];
    }
    public static Gknob add(float min, float max) {
        advanceCounter();
        k[counter].min = min;
        k[counter].max = max;
        k[counter].used = true;
        return k[counter];
    }
    public static Gknob add(float min, float max, float def) {
        advanceCounter();
        k[counter].val = (int) map(def, min, max, 0, 127);
        k[counter].min = min;
        k[counter].max = max;
        k[counter].used = true;
        return k[counter];
    }

    //Midi Event Handlers
    public static void controllerChange(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].val = value;
        midiEventCount++;
        verboseMessaging("Tweak",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }
    public static void noteOn(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].button = true;
        midiEventCount++;
        verboseMessaging("Note-On",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }
    public static void noteOff(int channel, int number, int value) {
        int n = min(number - NOTE_OFFSET + channel * BANK_MULT, TOTAL_PARAMS - 1);
        k[n].button = false;
        midiEventCount++;
        verboseMessaging("Note-Off",
                Integer.toString(channel),
                Integer.toString(number),
                Integer.toString(value),
                Long.toHexString(midiEventCount));
    }


    public static void verbose(boolean v) {
        verbose = v;
        if(v){
            System.out.println(bus.toString());
            System.out.println(bus.attachedInputs()[0]);
        }
    }
    private static void verboseMessaging(String eventType, String channel, String number, String value, String eventID){
        if(verbose){
            System.out.println(eventType + " | Channel: " +
                    channel + " | Number: " +
                    number + " | Value: " +
                    value + " -- EventID: " + eventID);
        }
    }

    private static int advanceCounter() {
        while (k[counter].used) counter++;
        return min(counter, TOTAL_PARAMS - 1);
    }

    private static float map(float val, float ax, float ay, float bx, float by){
        return bx + (by - bx) * ((val - ax) / (ay - ax));
    }
    private static int findIntechInputIndex(String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].contains("Intech")) return i;
        }

        return -1;
    }
}
