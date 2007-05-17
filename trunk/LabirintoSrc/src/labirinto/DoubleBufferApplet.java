// $Id$

package labirinto;


import java.awt.*;
import java.applet.*;

public class DoubleBufferApplet extends Applet
{
  // The width and height of our offscreen image
  private int width=-1;
  private int height=-1;

  // The offscreen image
  private Image offscreen;

  // switch: are we double buffering or not?
  private boolean dbon = true;

  // Use this to turn double buffering on and off
  protected void setDoubleBuffering( boolean dbon ) {
    this.dbon = dbon;
    if (!dbon) {
      offscreen = null;
    }
  }

  // Depending on the value of our switch, we either call our
  // special code, or just call the default code
  public void update( Graphics g ) {
    if (dbon) {
      updateDoubleBufffered( g );
    } else { 
      super.update( g );
    }
  }

  // Do the drawing to an offscreen buffer -- maybe
  private void updateDoubleBufffered( Graphics g ) {

    // Let's make sure we have an offscreen buffer, and that
    // it's the right size.  If the applet has been resized,
    // our buffer will be the wrong size and we need to make
    // a new one
    Dimension d = getSize();
    if (offscreen == null ||
        width!=d.width || height!=d.height || offscreen==null) {
      width = d.width;
      height = d.height;
      if (width>0 || height>0) {
        offscreen = createImage( width, height );
      } else offscreen = null;
    }

    // If we still don't have one, give up
    if (offscreen == null) return;

    // Get the off-screen graphics object
    Graphics gg = offscreen.getGraphics();

    // Clear the off-screen graphics object
    gg.setColor( getBackground() );
    gg.fillRect( 0, 0, width, height );
    gg.setColor( getForeground() );

    // Draw to the off-screen graphics object
    paint( gg );

    // We don't need this Graphics object anymore
    gg.dispose();

    // Finally, we transfer the newly-drawn stuff right to the
    // screen
    g.drawImage( offscreen, 0, 0, null );
  }
}
