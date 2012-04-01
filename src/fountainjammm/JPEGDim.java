/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fountainjammm;

import java.awt.Dimension;
import java.io.*;


/**
 *
 * @author Mike
 */
public class JPEGDim {

public static Dimension getJPEGDimension(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);

        // check for SOI marker
        if (fis.read() != 255 || fis.read() != 216)
                throw new RuntimeException("SOI (Start Of Image) marker 0xff 0xd8 missing");

        Dimension d = null;

        while (fis.read() == 255) {
                int marker = fis.read();
                int len = fis.read() << 8 | fis.read();

                if (marker == 192) {
                        fis.skip(1);

                        int height = fis.read() << 8 | fis.read();
                        int width = fis.read() << 8 | fis.read();

                        d = new Dimension(width, height);
                        break;
                }

                fis.skip(len - 2);
        }

        fis.close();
        
        return d;
}

public static void main(String[] args) throws IOException {
        //System.out.println(getJPEGDimension(new File(args[0])));
        System.out.println(getJPEGDimension(new File("two.jpg")));
}
}
