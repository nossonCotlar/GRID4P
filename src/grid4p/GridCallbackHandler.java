package grid4p;

import javax.sound.midi.*;

/**
 * A Class to handle callbacks from the Midi Bus within {@link Grid}
 * <strong>Don't try using it</strong>
 */
public class GridCallbackHandler {
    public void controllerChange(int channel, int number, int value){
        Grid.controllerChange(channel, number, value);
    }
    public void noteOn(int channel, int number, int value){
        Grid.noteOn(channel, number, value);
    }
    public void noteOff(int channel, int number, int value){
        Grid.noteOff(channel, number, value);
    }
    public void print(String s){
        System.out.println(s);
    }
}
