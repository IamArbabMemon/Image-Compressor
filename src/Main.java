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

public class Main {

    public static void main(String[] args) {

        try {
            imageCompressionMethod("./photo2.png",0.75f);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void imageCompressionMethod(String imagePath, float quality) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            String formatName = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();

            Iterator<ImageWriter> imageWriterIterator = ImageIO.getImageWritersByFormatName(formatName);
            ImageWriter imageWriter = imageWriterIterator.next();
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(new File("./compressedImage." + formatName));
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
