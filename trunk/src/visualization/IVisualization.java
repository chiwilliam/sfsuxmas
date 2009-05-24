package visualization;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IVisualization {

    public BufferedImage generateGraph() throws IOException;
    
    public String getTitle();

    public String getImageMap();

    public int getHeight();
}
