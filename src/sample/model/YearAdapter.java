package sample.model;

import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class YearAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

    @Override
    public String marshal(Date v) {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            return (Date) dateFormat.parse(v);
        }
    }

}
