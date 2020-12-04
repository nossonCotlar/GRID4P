# Getting Started
GRID4P is all about interacting with your sketch in real time, and not settling for constant values. 
Perhaps the most important aspect of this library is Gknob (GRID Knob) class; that';'s the one you'll use as your "Knobs" to control different aspects of your code in real time. 
To get the GRID ready, you must call <b>Grid.begin();</b> from your sketch's setup method. 
<pre>void setup(){
    size(500, 500);
    Grid.begin();
}</pre>
Let's say you're drawing a circle in the middle of your sketch. 
Instead of keeping the circle the same size all the time, it would be more exciting to control its size with a <b>Gknob</b>!<br>
We'll start by creating a Gknob and naming it mySize with <code>Gknob mySize;</code><br>
In order to attach the Gknob we just created to the GRID, we have to assign it a place on the GRID. 
This is done with the <code>Grid.add()</code> method, which we will supply with the details we want the Gknob to have. 
<pre>Gknob mySize;

void setup(){
    size(500, 500);
    Grid.begin();
    mySize = Grid.add(100, 500);
}</pre>
The first number passed into <code>Grid.add()</code> indicates the minimum value that the Gknob will hold, and 
the second number indicates the maximum. The Library adds the Gknob configuration to the first available Knob on the GRID, 
and you're ready to use it! Simply call the <code>.get()</code> method on the Gknob, and it will give you the value 
that it's at right now. 
<pre>import grid4p.*;
Gknob mySize;
     
 void setup(){
     size(500, 500);
     Grid.begin();
     mySize = Grid.add(100, 500);
 }
 
 void draw(){
    background(255);
    circle(250, 250, mySize.get());
 }</pre>
 Start twisting the Knob and watch the magic happen!

## How to install GRID4P

### Install with the Contribution Manager

Add contributed Libraries by selecting the menu item _Sketch_ → _Import Library..._ → _Add Library..._ This will open the Contribution Manager, where you can browse for GRID4P, or any other Library you want to install.

Not all available Libraries have been converted to show up in this menu. If a Library isn't there, it will need to be installed manually by following the instructions below.

### Manual Install

Contributed Libraries may be downloaded separately and manually placed within the `libraries` folder of your Processing sketchbook. To find (and change) the Processing sketchbook location on your computer, open the Preferences window from the Processing application (PDE) and look for the "Sketchbook location" item at the top.

By default the following locations are used for your sketchbook folder: 
  * For Mac users, the sketchbook folder is located inside `~/Documents/Processing` 
  * For Windows users, the sketchbook folder is located inside `My Documents/Processing`

Download GRID4P from https://github.com/nossonCotlar/GRID4P/releases/tag/GRID4Pv0.1.1

Unzip and copy the contributed Library's folder into the `libraries` folder in the Processing sketchbook. You will need to create this `libraries` folder if it does not exist.

The folder structure for Library GRID4P should be as follows:

```
Processing
  libraries
    GRID4P
      examples
      library
        GRID4P.jar
      reference
      src
```
             
Some folders like `examples` or `src` might be missing. After Library GRID4P has been successfully installed, restart the Processing application.

### Troubleshooting

If you're having trouble, have a look at the [Processing Wiki](https://github.com/processing/processing/wiki/How-to-Install-a-Contributed-Library) for more information, or contact the author [Nosson Cotlar](https://rood.systems).
