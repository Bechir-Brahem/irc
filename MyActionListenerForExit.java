import java.awt.event.*;
import java.io.*;
import java.awt.*;
public class MyActionListenerForExit implements ActionListener {
    MyFrameAWT f;
    public MyActionListenerForExit(MyFrameAWT f) {
        this.f = f;
    }
    public void actionPerformed(ActionEvent e) {
        System.exit(0);

    }
}

