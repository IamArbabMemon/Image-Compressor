import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImagingParameters;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("PLEASE INSERT THE PATH OF YOUR IMAGE : ");
            String imagePath = scanner.nextLine();
            System.out.print("PLEASE INSERT QUALITY YOU WANT  : ");
            float quality = scanner.nextFloat();
            imageCompressionMethod(imagePath,quality);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void imageCompressionMethod(String imagePath, float quality) {
        try {
            Random random = new Random();
            BufferedImage image = ImageIO.read(new File(imagePath));
            String formatName = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();

            Iterator<ImageWriter> imageWriterIterator = ImageIO.getImageWritersByFormatName(formatName);
            ImageWriter imageWriter = imageWriterIterator.next();
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(new File("./compressedImage"+random.nextInt()+"." + formatName));
            imageWriter.setOutput(outputStream);
            ImageWriteParam QualityParameters = imageWriter.getDefaultWriteParam();

            if (QualityParameters.canWriteCompressed()) {
                QualityParameters.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                if (formatName.equals("jpeg") || formatName.equals("jpg"))
                    QualityParameters.setCompressionQuality(quality);
            }

            imageWriter.write(null, new IIOImage(image, null, null), QualityParameters);
            imageWriter.dispose();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
