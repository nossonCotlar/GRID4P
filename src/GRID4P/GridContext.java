package GRID4P;

public class GridContext {
    protected void controllerChange(int channel, int number, int value){
        System.out.println("Tweaking works :)");
    }
    protected void noteOn(int channel, int number, int value){
        System.out.println("Note-On works :)");
    }
    protected void noteOff(int channel, int number, int value){
        System.out.println("Note-Off works :)");
    }
}
