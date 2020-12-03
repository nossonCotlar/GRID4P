package GRID4P;

public class Gknob {
    public int val, def;
    public float min, max;
    public boolean used, button;

    Gknob() {
        val = 0;
        min = 0;
        max = 127;
        button = false;
    }
    Gknob(int val, float min, float max) {
        this.val = val;
        this.min = min;
        this.max = max;
        button = false;
        used = true;
    }

    public float get() {
        return map(val, 0, 127, min, max);
    }
    public float value() {
        return this.get();
    }
    public boolean getB() {
        return button;
    }
    public float min() {return this.min;}
    public float max() {return this.max;}
    public void free() {
        used = true;
    }
    private float map(float val, float ax, float ay, float bx, float by){
        return bx + (by - bx) * ((val - ax) / (ay - ax));
    }
}

