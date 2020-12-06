import grid4p.*;
import processing.core.PApplet;


public class GridSandbox extends PApplet {
    Gknob test;
     public void settings(){
        size(500, 500);
    }
    public void setup(){
         Grid.begin();
         test = new Gknob(0, 500, 250);
         Grid.add(test);
    }
    public void draw(){
        background(0);
        circle(width >> 1, height >> 1, test.get());
    }

    public static void main(String... args){
        PApplet.main("GridSandbox");
    }
}
