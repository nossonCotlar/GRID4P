import grid4p.*;
import processing.core.PApplet;

public class GactionTest extends PApplet{
    Gknob myGknob;
    float size = 0;
    int bg = 0;

    public void settings(){
        size(500, 500);
    }
    public void setup(){
        size(500, 500);
        Grid.begin();  //set up Grid
        myGknob = Grid.add(0, 500, 250); //add Gknob configuration to the Grid
        myGknob.setGaction(new Gaction(){ //pass implemented Gaction into the Gknob's setGaction method
            public void onpush(){ //define whichever methods you'd like
                size = myGknob.get();
            }
        });
    }
    public void draw(){
        background(0);
        circle(width >> 1, height >> 1, size);
        if(size > 0) --size;
    }

    public static void main(String[] args){
        PApplet.main("GactionTest");
    }


}
