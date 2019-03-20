package neuronalnetwork.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**

 @author Carl
 */
class GuiUtil {

    static void centerFrame(Window window) {
        Dimension screenSize = getScreenResolution();
        window.setLocation((int) ((screenSize.getWidth() / 2) - (window.getWidth() / 2)), (int) ((screenSize.getHeight() / 2) - (window.getHeight() / 2)));
    }

    private static Dimension getScreenResolution() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    static Image getResourceImage(String imageResourcePath, int width, int height) {
        return getResourceImage(imageResourcePath).getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private static BufferedImage getResourceImage(String imageResourcePath) {
        try{
            return ImageIO.read(getResourceURL(imageResourcePath));
        }catch(Exception ex){
            System.out.println("Error while reading image file '" + imageResourcePath + "'.");
        }
        return null;
    }

    private static URL getResourceURL(String resourcePath) {
        return GuiUtil.class.getResource("/neuronalnetwork/resources/" + resourcePath);
    }
}
