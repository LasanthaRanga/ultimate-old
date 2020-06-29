package modle.other;


import com.lowagie.text.pdf.parser.Matrix;
import modle.popup.BarcodeHolder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.batik.ext.awt.geom.Linear;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrix;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import static modle.other.WarantFix2.getData;

public class Print {


    public static void main(String[] args) {
//        Print.createImage( "999999999");
//        System.out.println("finished");
        print();
    }

    public static ByteArrayInputStream createImage( String myString) {
        ByteArrayInputStream byteArrayInputStream = null;
        try {









            Code128Bean code128 = new Code128Bean();
            code128.setHeight(15f);
            code128.setModuleWidth(0.3);
            code128.setQuietZone(10);
            code128.doQuietZone(true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            code128.generateBarcode(canvas, myString);
            canvas.finish();
            //write to png file
//            FileOutputStream fos = new FileOutputStream("D:\\" + image_name);
            byteArrayInputStream = new ByteArrayInputStream(baos.toByteArray());

//            fos.write(baos.toByteArray());
//            fos.flush();
//            fos.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return byteArrayInputStream;
    }


    public static void print() {
        try {
            JasperReport report = JasperCompileManager.compileReport("C:\\Ultimate\\Report\\barcode\\barcode.jrxml");
            JRBeanCollectionDataSource jcd = new JRBeanCollectionDataSource(getData());
            JasperPrint print = JasperFillManager.fillReport(report, null, jcd);
            if (false) {
                JasperPrintManager.printReport(print, true);    //  print auto
            } else {
                JasperViewer.viewReport(print, false);  //  view
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Collection getData() throws Exception {
        ArrayList<modle.popup.BarcodeHolder> arr = new ArrayList<>();

        arr.add(new BarcodeHolder("1234", "2000.00", "asdf asdf asdf ", "adsf", createImage( "asdf")));

        return arr;
    }


}
