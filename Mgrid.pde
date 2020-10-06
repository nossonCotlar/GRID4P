/*
- A class to handle the ultra rudimentary implementation of the GRID MIDI modules into Processing sketches. 
 - This depends on TheMidiBus library
 
 - The presence of "Mknobs" (Midi Knobs) are added to the sketch, which behave as real-time perameter adjusters. 
 - The "Mgrid" class is the managing class for the Mknobs, which handles the midi communication and other handling
 
 -- Begin by instantiating a Mgrid object by its default constructor. 
 -- Then, assign values to the Mknobs via any of the methods:
 setMknob(int n, float min, float max)
 setMknob(int n, float min, float max, float def)
 addMknob(float min, float max)
 addMknob(float min, float max, float def)
 
 these methods set up an Mknob within the Mgrid, but they also return a pointer to an Mknob. 
 this means that given an Mgrid m, you can either do something like this: 
   m.setMknob(0, 0, 1000);
   float val = m.get(0);
 or this:
   Mknob speed;
   speed = m.setMknob(0, 0, 1000);
   float val = speed.get();
 
 */

import themidibus.*; //midi communication library

public class Mgrid {
  private MidiBus bus;
  private Mknob[] k; //array of Mknobs
  private int counter; //to keep track of already-used knobs
  private boolean verbose; //whether to print midi messages to stdout

  Mgrid() {
    verbose = false;
    counter = 0;
    k = new Mknob[32]; //create Mknob array
    for (int i = 0; i < 32; i ++) {
      k[i] = new Mknob(); //initialize each Mknob
    }
    //MidiBus.list();
    int inputIndex = findIntechInputIndex(MidiBus.availableInputs()); //find midi input with "intech"
    bus = new MidiBus(this, inputIndex, -1); //set up midi bus
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

  public Mknob setMknob(int n, float min, float max) {
    this.advanceCounter();
    this.k[n].min = min;
    this.k[n].max = max;
    this.k[n].used = true;
    return this.k[n];
  }
  public Mknob setMknob(int n, float min, float max, float def) {
    this.k[n].val = (int) map(def, min, max, 0, 127);
    this.k[n].min = min;
    this.k[n].max = max;
    this.k[n].used = true;
    return this.k[n];
  }
  public Mknob addMknob(float min, float max) {
    this.advanceCounter();
    this.k[counter].min = min;
    this.k[counter].max = max;
    this.k[counter].used = true;
    return this.k[counter];
  }
  public Mknob addMknob(float min, float max, float def) {
    this.advanceCounter();
    this.k[counter].val = (int) map(def, min, max, 0, 127);
    this.k[counter].min = min;
    this.k[counter].max = max;
    this.k[counter].used = true;
    return this.k[counter];
  }
  public void controllerChange(int channel, int number, int value) {
    int n = min(number - 32 + channel * 2, 31);
    k[n].val = value;
    if (verbose) println("Tweak | Channel: " + channel + " | Number: " + number + " | Value: " + value);
  }
  public void noteOn(int channel, int number, int value) {
    int n = min(number - 32 + channel * 2, 31);
    k[n].button = true;
    if (verbose) println("Note-On | Channel: " + channel + " | Number: " + number + " | Value: " + value);
  }
  public void noteOff(int channel, int number, int value) {
    int n = min(number - 32 + channel * 2, 31);
    k[n].button = false;
    if (verbose) println("Note-Off | Channel: " + channel + " | Number: " + number + " | Value: " + value);
  }
  

  public void verbose(boolean v) {
    this.verbose = v;
  }

  private int findIntechInputIndex(String[] list) {
    for (int i = 0; i < list.length; i++) {
      if (list[i].indexOf("Intech") >= 0) return i;
    }

    return -1;
  }

  private int advanceCounter() {
    while (k[this.counter].used) this.counter++;
    return min(this.counter, 31);
  }
}

public class Mknob {
  public int val, def;
  public float min, max;
  public boolean used, button;

  Mknob() {
    val = 0;
    min = 0;
    max = 127;
    button = false;
  }
  Mknob(int val, float min, float max) {
    this.val = val;
    this.min = min;
    this.max = max;
    button = false;
    used = true;
  }

  public float get() {
    return map(val, 0, 127, min, max);
  }
  public float value() {
    return this.get();
  }
  public boolean getB() {
    return button;
  }
  public float min() {return this.min;}
  public float max() {return this.max;}
  public void free() {
    used = true;
  }
}
