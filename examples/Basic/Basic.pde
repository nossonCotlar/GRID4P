import grid4p.*;

void setup(){
  size(400, 400);

  Grid.begin(); //start the Grid

  //add a Knob that has a minimum value of 0,
  //maximum value of 400,
  //and starting value of 200
  //the knob's position will be at the first available spot on the grid
  Grid.add(0, 400, 200);

  //set the knob at index 3 to have a minimum value of 0,
  //maximum value of 255,
  //and starting value of 255
  Grid.set(3, 0, 255, 255);


  fill(255);
}

void draw(){
  background(0);
  fill(Grid.get(3)); //use the Grid.get() method to retrieve the value of the knob at index 3
  circle(200, 200, Grid.get(0)); //use the Grid.get() method to retrieve the value of the knob at index 0
}