package ru.botland.stones;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class ClipboardListener extends Thread implements ClipboardOwner {
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
        System.out.println("Listening to board...");
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
                final String transtale = Translate.execute(data, Language.ENGLISH, Language.RUSSIAN);
                createFrame(data, transtale);
                System.out.println("Processing: " + data + " -> " + transtale);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

//        new ru.botland.stones.ClipboardListener().postExample("http://ankisrs.net/account/login", new ru.botland.stones.QueryString().
//                        add("username",  "botdes").add("password", "s1zP9ZdGvvIg"));

        Translate.setHttpReferrer("http://google.com");
        String s = Translate.execute("derive", Language.ENGLISH, Language.RUSSIAN);
        System.out.println(s);

    }


    public void postExample(String url, QueryString query) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("Cookie", "ankiweb=6ff452838757f007626324a6d7ca07baf940877cd3df743eba32b9b2e9c3841e85ea71b6; " +
                "__utmc=193203453; " +
                "__utma=193203453.1806882451.1269288361.1273392587.1273394508.5; " +
                "__utmz=193203453.1273311586.2.2.utmcsr=ichi2.net|utmccn=(referral)|utmcmd=referral|utmcct=/anki/");
        conn.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "ASCII");
        out.write(query.toString());
        out.write("\r\n");
        out.flush();
        out.close();

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