package ru.botland.stones;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: botdes
 * Date: 10.05.2010
 * Time: 19:33:06
 * To change this template use File | Settings | File Templates.
 */
public class Connector {

    private String cookie;


    public void sendData(final String word, final String translation) {
        try {
            postData("http://ankisrs.net/deck/edit",
                   new QueryString()
                           .add("Front", word).add("Back", translation)
                           .add("tags", "").add("action", "Add"), false
           );
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private String postData(String url, QueryString query, boolean getCookie) throws IOException, URISyntaxException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setInstanceFollowRedirects(!getCookie);
        if (getCookie) {
            conn.setRequestProperty("Cookie", getCookie());
        }
        System.out.println(conn.getRequestProperties());
        System.out.println("");
        conn.setDoOutput(true);
        conn.connect();
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(query.toString());
        out.flush();
        out.close();
        conn.getInputStream();
        return conn.getHeaderField("Set-Cookie");
    }

    private String getCookie() throws IOException, URISyntaxException {
        if (cookie == null) {
            cookie = postData("http://ankisrs.net/account/login",
                    new QueryString()
                            .add("username", "botdes")
                            .add("password", "s1zP9ZdGvvIg")
                            .add("submitted", "1"), true
            );
        }
        return cookie;
    }

    public static void main(String[] args) {
        Connector connector = new Connector();
        connector.sendData("one", "two");
    }

}
