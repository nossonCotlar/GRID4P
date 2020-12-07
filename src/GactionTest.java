import grid4p.*;
import processing.core.PApplet;

public class GactionTest extends PApplet{
    Gknob a, b, c;
    float size = 0;

    public void settings(){
        size(500, 500);
    }
    public void setup(){

        Grid.begin();
        a = Grid.add(0, 1000);
        b = Grid.add(0, height);
        c = Grid.add(0, 5, 1);
        a.setGaction(new Gaction(){
            public void onpush(){
                size = b.get();
            }
            public void onlimitreached(){
                background(255, 0, 0);
            }
        });


    }
    public void draw(){
    background(0);
    circle(width >> 1, height >> 1, size);
    if(size > 0) size -= c.get();

    }

    public static void main(String[] args){
        PApplet.main("GactionTest");
    }


}
