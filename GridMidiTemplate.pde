/*
A sketch to illustrate the use of the Mgrid class and Mknobs
*/

Mgrid m;

void setup() {
  fullScreen();
  colorMode(HSB);
  textAlign(CENTER, CENTER);
  textSize(30);
  noFill();
  strokeWeight(5);
  m = new Mgrid();
  m.verbose(true);
  for (int i = 0; i < 32; i++) {
    m.addMknob(0, 1000, 0);
  }
}

void draw() {
  background(0);
  float wsize = width >> 3;
  float hsize = height >> 2;
  float size = min(wsize, hsize) * 0.75;
  textSize(size / 5);

  for (int i = 0; i < 4; i++) {
    float y = hsize * i + hsize / 2;
    for (int j = 0; j < 4; j++) {
      float x = wsize * j + wsize / 2;
      float val = m.get(i * 4 + j);
      if (m.getB(i * 4 + j)) {
        pushStyle();
        noStroke();
        fill(100);
        circle(x, y, size);
        fill(255);
        noFill();
        popStyle();
      }
      strokeWeight(5);
      stroke(map(val, 0, 1000, 0, 150), 255, 255);
      circle(x, y, size);
      strokeWeight(10);
      stroke(255);
      float a = map(val, 0, 1000, 0, TWO_PI);
      point(x + size/2 * cos(a - HALF_PI), y + size/2 * sin(a - HALF_PI));
      text(int(val), x, y);
    }
  }
  translate(width >> 1, 0);
  for (int i = 0; i < 4; i++) {
    float y = hsize * i + hsize / 2;
    for (int j = 0; j < 4; j++) {
      float x = wsize * j + wsize / 2;
      float val = m.get(i * 4 + j + 16);
      if (m.getB(i * 4 + j + 16)) {
        pushStyle();
        noStroke();
        fill(100);
        circle(x, y, size);
        fill(255);
        noFill();
        popStyle();
      }
      strokeWeight(5);
      stroke(map(val, 0, 1000, 0, 150), 255, 255);
      circle(x, y, size);
      strokeWeight(10);
      stroke(255);
      float a = map(val, 0, 1000, 0, TWO_PI);
      point(x + size/2 * cos(a - HALF_PI), y + size/2 * sin(a - HALF_PI));
      text(int(val), x, y);
    }
  }
}
