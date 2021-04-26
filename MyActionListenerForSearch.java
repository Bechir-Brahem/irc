import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.awt.*;
public class MyActionListenerForSearch implements ActionListener {
    MyFrameAWT f;
    DataOutputStream out = null;
    boolean is_server = false;
    public MyActionListenerForSearch(MyFrameAWT f, DataOutputStream out, boolean x) {
        this.f = f;
        this.out = out;
        is_server = x;
    }
    public void actionPerformed(ActionEvent e) {

        System.out.println("send button activated");
        String s = f.message.getText();
        f.message.setText("");
        if (!is_server) {
            try {
                out.writeUTF(s);

            } catch (Exception ex) {
                //
            }
        } else {
            for (Socket socket : Server.table) {
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("[message from server]: " + s);
                } catch (Exception ex) {
                    ;
                }
            }

        }
    }
}
