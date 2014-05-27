/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author james8
 */
public class ImageProducer {

    public int imgName;
    public String dir;

    public ImageProducer() {
        Date now = new Date();
        this.imgName = 1;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy_hh:mm:ss");
        this.dir = sdf.format(date);
        File theDir = new File(dir);
        if (!theDir.exists()) {
            boolean result = theDir.mkdir();

            if (result) {
                //System.out.println("DIR created");
            }
        }
    }

    public void createImage(int[][] t, int dim) {
        try {
            int width = t.length * dim, height = t[0].length * dim;

            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();
            for (int j = 0; j < t.length; j++) {
                for (int i = 0; i < t[j].length; i++) {
                    if (t[j][i] == 1) {
                        ig2.setColor(Color.yellow);
                        ig2.drawRect(i, j, 1, 1);
                    } else if (t[j][i] == 2) {
                        ig2.setColor(Color.red);
                        ig2.drawRect(i, j, 1, 1);
                    } else if (t[j][i] == 3) {
                        ig2.setColor(Color.blue);
                        ig2.drawRect(i, j, 1, 1);
                    } else {
                        ig2.setColor(Color.black);
                        ig2.drawRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(bi, "PNG", new File(dir + "/" + Integer.toString(imgName) + ".PNG"));
            imgName++;
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    public void saveGif(File outputGif) throws IOException {
        if (imgName > 1) {
            // grab the output image type from the first image in the sequence
            BufferedImage firstImage = ImageIO.read(new File(dir + "/" + Integer.toString(1) + ".PNG"));

            // create a gif sequence with the type of the first image, 1 second
            // between frames, which loops continuously
            try ( // create a new BufferedOutputStream with the last argument
                    ImageOutputStream output = new FileImageOutputStream( outputGif )) {
                // create a gif sequence with the type of the first image, 1 second
                // between frames, which loops continuously
                GifSequenceWriter writer
                        = new GifSequenceWriter(output, firstImage.getType(), 1, true);

                // write out the first image to our sequence...
                writer.writeToSequence(firstImage);
                for (int i = 2; i < imgName; i++) {
                    BufferedImage nextImage = ImageIO.read(new File(dir + "/" + Integer.toString(i) + ".PNG"));
                    writer.writeToSequence(nextImage);
                }

                writer.close();
            }
        } else {
            System.out.println(
                    "Usage: java GifSequenceWriter [list of gif files] [output file]");
        }
    }
}
