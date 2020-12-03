import grid4p.*;

//create two Gknob (Grid Knob) objects, which corespond to parameters on the Grid
//using Gknob-s helps the variables be more memorable, and make the code more readable.
Gknob circleSize;
Gknob circleBrightness;

void setup(){
  size(400, 400);

  Grid.begin(); //start the Grid

  //add a Knob that has a minimum value of 0,
  //maximum value of 400,
  //and starting value of 200
  //the knob's position will be at the first available spot on the grid
  //Grid.add() returns a Gknob, so we can assign the returned Gknob to circleSize
  circleSize = Grid.add(0, 400, 200);

  //set the knob at index 3 to have a minimum value of 0,
  //maximum value of 255,
  //and starting value of 255
  //Grid.set() returns a Gknob, so we can assign the returned Gknob to circleBrightness
  circleBrightness = Grid.set(3, 0, 255, 255);


  fill(255);
}

void draw(){
  background(0);

  //use the Gknob.get() method to retrieve the value of circleBrightness
  fill(circleBrightness.get());

  //the Gknob.getB() function returns a true if the button is pushed, and false if it is not
  //so we'll set the fill to red if the button is pushed. For fun.
  if(circleBrightness.getB()) fill(255, 0, 0);

  //use the Gknob.get() method to retrieve the value of the circleSize
  circle(200, 200, circleSize.get());
}