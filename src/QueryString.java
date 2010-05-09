import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
* Created by IntelliJ IDEA.
* User: botdes
* Date: 09.05.2010
* Time: 18:13:05
* To change this template use File | Settings | File Templates.
*/
public class QueryString {
    private StringBuffer query;

    public QueryString() {
        query = new StringBuffer();
    }

    public synchronized QueryString add(Object name, Object value)
            throws UnsupportedEncodingException {
        if (!"".equals(query.toString().trim())) {
            query.append("&");
        }
        query.append(URLEncoder.encode(name.toString(), "UTF-8"));
        query.append("=");
        query.append(URLEncoder.encode(value.toString(), "UTF-8"));
        return this;
    }

    public String toString() {
        return query.toString();
    }

}
