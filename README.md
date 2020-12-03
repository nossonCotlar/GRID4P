# grid4p
A class (future library) for the use of <b>GRID</b> Midi Modules inside Processing

Currently only supports the <b>EN16</b> module and has <b>themidibus</b> as a dependency for midi communication. 

The presence of "Mknobs" (Midi Knobs) are added to the sketch, which behave as real-time perameter adjusters. 

The "Mgrid" class is the managing class for the Mknobs, which handles the midi communication and other handling
 
 -- Begin by instantiating a Mgrid object by its default constructor. <br>
 -- Then, assign values to the Mknobs via any of the methods: <br>
    - setMknob(int n, float min, float max)<br>
    - setMknob(int n, float min, float max, float def)<br>
    - addMknob(float min, float max)<br>
    - addMknob(float min, float max, float def)<br>
 
 these methods set up an Mknob within the Mgrid, but they also return a pointer to an Mknob. <br>
 
 this means that given an Mgrid m, you can either do something like this: <br>
   m.setMknob(0, 0, 1000);<br>
   float val = m.get(0);<br>
   
 or this:<br>
   Mknob speed;<br>
   speed = m.setMknob(0, 0, 1000);<br>
   float val = speed.get();<br>
