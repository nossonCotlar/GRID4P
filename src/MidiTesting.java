import processing.core.*;
import javax.sound.midi.*;
import java.util.List;
import java.util.ArrayList;

public class MidiTesting extends PApplet{
    Transmitter tx;
    Receiver rx;
    int testing = 1;

    public void settings(){
        size(800, 800);
    }
    public void setup(){
        textAlign(CENTER, CENTER);
        fill(255);
        textSize(20);

        MidiDevice device;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; i++) {
            try {
                device = MidiSystem.getMidiDevice(infos[i]);
                //does the device have any transmitters?
                //if it does, add it to the device list
                System.out.println(infos[i]);

                //get all transmitters
                List<Transmitter> transmitters = device.getTransmitters();
                //and for each transmitter

                Transmitter trans = device.getTransmitter();
                trans.setReceiver(new Receiver() {
                    @Override
                    public void send(MidiMessage message, long timeStamp) {
                        println(message.getMessage());
                    }

                    @Override
                    public void close() {

                    }
                });

                //open each device
                device.open();
                //if code gets this far without throwing an exception
                //print a success message
                System.out.println(device.getDeviceInfo() + " Was Opened");


            } catch (MidiUnavailableException e) {
                println(e.getMessage());
            }
        }
    }
    public void draw(){
        background(0);
        text("Testing: " + testing, width >> 1, height >> 1);

        ++testing;

    }

    public static void main(String... args){
        PApplet.main("MidiTesting");
    }

}
