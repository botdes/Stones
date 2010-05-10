package ru.botland.stones;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Simple HTTP Client, which implementing get and post queries.
 *
 * @author Zhuravskiy Vitaliy
 */
public class Example {

    /**
     * Метод читает из потока данные и преобразует в строку
     *
     * @param in       - входной поток
     * @param encoding - кодировка данных
     * @return - данные в виде строки
     */
    private String readStreamToString(InputStream in, String encoding) throws IOException {
        StringBuffer b = new StringBuffer();
        InputStreamReader r = new InputStreamReader(in, encoding);
        int c;
        while ((c = r.read()) != -1) {
            b.append((char) c);
        }
        return b.toString();
    }

    public void postExample(String url, QueryString query) throws IOException, URISyntaxException {
        //устанавливаем соединение
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//        conn.setRequestMethod("POST");

//        conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
//        conn.setRequestProperty("viewpoint", "user-scalable=yes, width=device-width,maximum-scale=0.6667");
        conn.setRequestProperty("Cookie", "ankiweb=09b51b18fb40b8615c6407fae119d12c966e7b657812fe3ec7c73148665b0be9bb4e11d9; expires=Tue, 19-Jan-2038 04:14:07 GMT; Path=/");
//        conn.setRequestProperty("Cookie", "_utmz=" +
//"193203453.1273491494.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); " +
//"__utmc=" +
//"193203453; " +
//"__utmb=" +
//"193203453.2.10.1273491494; " +
//"__utma=" +
//"193203453.742131716.1273491494.1273491494.1273491494.1; " +
//"ankiweb=" +
//"d8a7093b9be123e6b3a1562b1f12b2ab13213a022906d001a17b01ade08ac44c25b335a1");
        System.out.println(conn.getRequestProperties());
        conn.setDoOutput(true);
        conn.connect();
        

        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(query.toString());
//        out.write("\r\n");
        out.flush();
        out.close();

        System.out.println("getHeaderFields - " + conn.getHeaderFields().toString());
        System.out.println("getRequestMethod - " + conn.getRequestMethod());
        System.out.println("getResponseCode - " + conn.getResponseCode());
        System.out.println("getResponseMessage - " + conn.getResponseMessage());
        System.out.println("getURL - " + conn.getURL().toString());
        System.out.println("cookie - " + conn.getHeaderField("Set-Cookie"));

        //читаем то, что отдал нам сервер
        String html = readStreamToString(conn.getInputStream(), "UTF-8");

        //выводим информацию в консоль
        System.out.println("URL:" + url);
        System.out.println("Html:\n" + html);


    }

    public void getExample(String url, QueryString query) throws IOException, URISyntaxException {
        //устанавливаем соединение
        HttpURLConnection conn = (HttpURLConnection) new URL(url + "?" + query).openConnection();

        conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");

        System.out.println("getHeaderFields - " + conn.getHeaderFields().toString());
        System.out.println("getRequestMethod - " + conn.getRequestMethod());
        System.out.println("getResponseCode - " + conn.getResponseCode());
        System.out.println("getResponseMessage - " + conn.getResponseMessage());
        System.out.println("getURL - " + conn.getURL().toString());
        

        Map<String,List<String>> map = CookieHandler.getDefault().get(new URI(conn.getURL().toString()), conn.getHeaderFields());
        System.out.println(map);

        //читаем то, что отдал нам сервер
        String html = readStreamToString(conn.getInputStream(), "UTF-8");

        //выводим информацию в консоль
        System.out.println("URL:" + url);
//        System.out.println("Html:\n" + html);
    }

    public static void main(String[] args) throws Exception {
        Example e = new Example();
//        e.getExample("http://ankisrs.net/account/login",
//                new QueryString()
//                        .add("username", "mrgrey")
//                        .add("password", "q0dgP78q")
//                        .add("submitted", "1")
//        );
//        e.postExample("http://ankisrs.net/account/login",
//                new QueryString()
//                        .add("username", "botdes")
//                        .add("password", "s1zP9ZdGvvIg")
//                        .add("submitted", "1")
//        );


        e.postExample("http://ankisrs.net/deck/edit",
                new QueryString()
                        .add("Front", "aaaa").add("Back", "bbb")
                        .add("tags", "tag").add("action", "Add")
        );

//        e.postExample("http://translate.google.com", new QueryString()
//        .add("old_sl", "en")
//        .add("old_tl", "ru")
//        .add("text", "text")
//        );
//
    }


//"_utmz=" +
//"193203453.1273491494.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); " +
//"__utmc=" +
//"193203453; " +
//"__utmb=" +
//"193203453.2.10.1273491494; " +
//"__utma=" +
//"193203453.742131716.1273491494.1273491494.1273491494.1; " +
//"ankiweb=" +
//"d8a7093b9be123e6b3a1562b1f12b2ab13213a022906d001a17b01ade08ac44c25b335a1"


//    "__utmz=" +
//    "193203453.1273392902.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); " +
//    "__utmc" +
//    "193203453; " +
//    "__utmb" +
//    "193203453.15.10.1273479663; " +
//    "__utma" +
//    "193203453.1850742068.1273392902.1273444745.1273479663.6; " +
//    "ankiweb" +
//    "35a1db314bea414737f9f8a9cbe1c7ccde9c7787c38d8e0d245a9af782d392056b56cb7d"


}
