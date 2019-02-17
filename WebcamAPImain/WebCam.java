package WebcamAPImain;


import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;

public class WebCam {
    private Webcam webCam;

    public WebCam() {
        System.out.println("Finding Webcam...");
        webCam = Webcam.getDefault();
        if (webCam != null) {
            System.out.println("Webcam Found.");
            System.out.println("Webcam Name: " + webCam.getName());
        } else {
            System.out.println("No webcam detected.");
            return;
        }

        System.out.println("Accessing Webcam.");
        webCam.open();
        System.out.println("Webcam Open.");

    }

    public BufferedImage get() {
        return webCam.getImage();
    }
}
