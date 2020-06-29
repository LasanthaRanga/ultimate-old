package modle.popup;

import java.io.InputStream;

public class BarcodeHolder {
    private String code;
    private String total;
    private String cusname;
    private String subject;
    private InputStream image;

    public BarcodeHolder(String code, String total, String cusname, String subject, InputStream image) {
        this.code = code;
        this.total = total;
        this.cusname = cusname;
        this.subject = subject;
        this.image = image;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }


    public String getCode() {
        return code;
    }

    public String getTotal() {
        return total;
    }

    public String getCusname() {
        return cusname;
    }

    public String getSubject() {
        return subject;
    }

    public InputStream getImage() {
        return image;
    }


}
