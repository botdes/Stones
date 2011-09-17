package ru.botland.stones;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class ClipboardListener extends Thread implements ClipboardOwner {
    private static final Logger log = Logger.getLogger(ClipboardListener.class.toString());

    private final Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
    private Allower allower;
    private AddtionalFrame addtionalFrame;

    public ClipboardListener(Allower frame) {
        Translate.setHttpReferrer("http://google.com");
        this.allower = frame;
        addtionalFrame = new AddtionalFrame();
    }

    public void run() {
        final Transferable trans = sysClip.getContents(this);
        regainOwnership(trans);
        log.info("Listening to clipboard...");
    }

    public void lostOwnership(Clipboard c, Transferable t) {
        Transferable contents = sysClip.getContents(this); //EXCEPTION
        processContents(contents);
        regainOwnership(contents);
    }

    private void processContents(Transferable t) {
        if (allower.allow()) {
            try {
                String data = (String) t.getTransferData(DataFlavor.stringFlavor);
                final String translation = Translate.execute(data, Language.ENGLISH, Language.RUSSIAN);
                createFrame(data, translation);
                System.out.println("Processing: " + data + " -> " + translation);
            } catch (Exception e) {
                log.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void regainOwnership(Transferable t) {
        sysClip.setContents(t, this);
    }

    private void createFrame(final String data, final String transtale) {
        addtionalFrame.setValues(data, transtale);
        addtionalFrame.setVisible(true);
//        addtionalFrame.setFo
    }

    public static void main(String[] args) throws Exception {
//        ru.botland.stones.ClipboardListener b = new ru.botland.stones.ClipboardListener();
//        b.start();
//        new JFrame().setVisible(true);
//        new ru.botland.stones.ClipboardListener().postExample("http://ankisrs.net/deck/edit/Add", new ru.botland.stones.QueryString().
//                add("Front",  "fftest").add("Back", "btest"));

        QueryString queryString = new QueryString().
                add("username", "botdes").add("password", "s1zP9ZdGvvIg").add("submitted", "1");
        System.out.println(queryString.toString());
        new ClipboardListener(null).postExample("http://ankisrs.net/account/login/", queryString);

         
        
//        Translate.setHttpReferrer("http://google.com");
//        String s = Translate.execute("derive", Language.ENGLISH, Language.RUSSIAN);
//        System.out.println(s);

    }


    public void postExample(String url, QueryString query) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Cookie", "ankiweb=6ff452838757f007626324a6d7ca07baf940877cd3df743eba32b9b2e9c3841e85ea71b6; " +
//                "__utmc=193203453; " +
//                "__utma=193203453.1806882451.1269288361.1273392587.1273394508.5; " +
//                "__utmz=193203453.1273311586.2.2.utmcsr=ichi2.net|utmccn=(referral)|utmcmd=referral|utmcct=/anki/");
        conn.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(query.toString());
        out.writeBytes("\r\n");
        out.flush();
        out.close();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //читаем то, что отдал нам сервер
        String html = readStreamToString(conn.getInputStream(), "UTF-8");

        //выводим информацию в консоль
        System.out.println("URL:" + url);
        System.out.println("Html:\n" + html);
    }

    private String readStreamToString(InputStream in, String encoding) throws IOException {
        StringBuffer b = new StringBuffer();
        InputStreamReader r = new InputStreamReader(in, encoding);
        int c;
        while ((c = r.read()) != -1) {
            b.append((char) c);
        }
        return b.toString();
    }

//    Cookie

}