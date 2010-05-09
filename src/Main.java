/**
 * Created by IntelliJ IDEA.
 * User: botdes
 * Date: 09.05.2010
 * Time: 19:16:04
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        ClipboardListener listener = new ClipboardListener(frame);
        listener.run();

    }
}
