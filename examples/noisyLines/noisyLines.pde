import grid4p.*;
Gknob m_amt, m_grain, m_amp, m_nxdense, m_nydense,
      m_nzinc, m_nxinc, m_nyinc, m_weight, m_gap,
      m_hue1, m_hue2, m_sat;
float nz = 0, nxoff = 0, nyoff = 0;

void setup(){
  fullScreen();
  colorMode(HSB);

  Grid.begin();
  Grid.verbose(true);
  m_amt = Grid.add(1, 100, 20);
  m_grain = Grid.add(1, 200, 50);
  m_amp = Grid.add(0, 700, 40);
  m_gap = Grid.add(0, 1, 0.7);

  m_nxdense = Grid.add(0, 0.5, 0.02);
  m_nydense = Grid.add(0, 0.5, 0.02);
  m_nxinc = Grid.add(0, 0.1);
  m_nyinc = Grid.add(0, 0.1, 0.005);

  m_nzinc = Grid.add(0, 0.1);

  m_weight = Grid.set(15, 0, 15, 3);
  m_sat = Grid.set(14, 0, 255, 175);
  m_hue1 = Grid.set(13, 0, 255, 100);
  m_hue2 = Grid.set(12, 0, 255, 200);

  noFill();
}

void draw(){
  if(!Grid.getB(0)) background(0);
  float inc = width / m_grain.get();
  float gap = (height / m_amt.get()) * m_gap.get();
  strokeWeight(m_weight.get());

  float ny = nyoff;
  for(int n = 0; n < m_amt.get(); n++){
    float py = height / 2 + (n - m_amt.get() / 2) * gap;
    //float py = height / 2;
    float nx = nxoff;
    stroke(map(n, 0, m_amt.get(), m_hue1.get(), m_hue2.get()), m_sat.get(), 255);
    beginShape();
    for(float i = 0; i < width; i += inc){
      float val = map(noise(nx, ny, nz), 0, 1, -1, 1);
      float y = py + val * m_amp.get();
      float x = i;

      vertex(x, y);

      nx += m_nxdense.get();
    }
    endShape();
    ny += m_nydense.get();
  }

  nxoff += m_nxinc.get();
  nyoff += m_nyinc.get();
  nz += m_nzinc.get();

}