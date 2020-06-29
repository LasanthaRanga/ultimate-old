package modle.popup;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PrintBarcode {



    public static ByteArrayInputStream createImage(String myString) {
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


    public static void print(String cus_name, String code, String price, String subject) {
        try {
            JasperReport report = JasperCompileManager.compileReport("C:\\Ultimate\\Report\\barcode\\barcode.jrxml");
            JRBeanCollectionDataSource jcd = new JRBeanCollectionDataSource(getData(cus_name, code, price, subject));
            JasperPrint print = JasperFillManager.fillReport(report, null, jcd);
            if (true) {
                JasperPrintManager.printReport(print, false);    //  print auto
                modle.Allert.notificationGood("Printed " + code, "complete");
            } else {
                JasperViewer.viewReport(print, false);  //  view
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Collection getData(String cus_name, String code, String price, String subject) throws Exception {
        ArrayList<BarcodeHolder> arr = new ArrayList<>();
        arr.add(new BarcodeHolder(code, price, cus_name, subject, createImage(code)));
        return arr;
    }


}
