package grid4p;

import processing.core.*;

/**
 * A runnable class that extends a Processing PApplet, to test GRID4P in a normal IDE.
 */
public class GridTemplate extends PApplet{
    public void settings(){
        size(1000, 1000);
    }
    public void setup(){
        //fullScreen();
        colorMode(HSB);
        textAlign(CENTER, CENTER);
        textSize(30);
        noFill();
        strokeWeight(5);

        Grid.begin();
        Grid.verbose(true); //set verbose flag
        for (int i = 0; i < Grid.TOTAL_PARAMS; i++) {
            //initialize each knob
            Grid.add(0, 1000, map(i, 0, Grid.TOTAL_PARAMS, 0, 1000));
        }
    }
    public void draw(){
        background(0);
        float wsize = width >> 3;
        float hsize = height >> 3;
        float size = min(wsize, hsize) * 0.75f;
        textSize(size / 5);

        //draw lines separating the banks
        strokeWeight(2);
        stroke(100);
        line(width >> 1, 0, width >> 1, height);
        line(0, height >> 1, width, height >> 1);


        //quadruple loop :( sorry fellas
        for(int h = 0; h < 2; h++){
            float py = h * hsize * 4 + (hsize / 2); //initial Y position
            for(int k = 0; k < 2; k++){
                float px = k * wsize * 4 + (wsize / 2); //initial X position
                for(int j = 0; j < 4; j++){
                    float y = py + j * hsize; //update Y position
                    for(int i = 0; i < 4; i++){
                        float x = px + i * wsize; //update X position

                        int knobIndex = (h << 5) + (k << 4) + (j << 2) + i; //calculate index for Gknob Array
                        float val = Grid.get(knobIndex); //retrieve Gknob value at calculated index
                        if (Grid.getB(knobIndex)) { //execute if button is pushed down
                            //pretty much just fills the background of the circle with gray
                            pushStyle();
                            noStroke();
                            fill(100);
                            circle(x, y, size);
                            popStyle();
                        }
                        //make the outline of the circle correspond to the Gknob's value
                        strokeWeight(5);
                        stroke(map(val, 0, 1000, 0, 150), 255, 255);
                        circle(x, y, size);

                        //draw a point for the Gknob value "position"
                        strokeWeight(10);
                        stroke(255);
                        float a = map(val, 0, 1000, 0, TWO_PI);
                        point(x + size/2 * cos(a - HALF_PI), y + size/2 * sin(a - HALF_PI));

                        //display the value in text form
                        pushStyle();
                        fill(255);
                        text((int)val, x, y);
                        popStyle();

                    }
                }
            }
        }
    }

    public static void main(String[] args){
        PApplet.main("grid4p.GridTemplate");
    }

}
