package grid4p;

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
}
