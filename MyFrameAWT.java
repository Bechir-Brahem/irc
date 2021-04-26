import java.awt.*;
import java.io.*;
import java.awt.event.*;

class MyFrameAWT extends Frame {
    Panel pNorth = new Panel();
    Button bQuit = new Button("Exit");
    Button bSearch = new Button("Send");
    Panel pSouth = new Panel();
    TextArea message = new TextArea("");
    TextArea convo = new TextArea("");
    int line;
    private void  helper(DataOutputStream out,boolean is_server) {
        pNorth.setBackground(Color.gray);
        pNorth.add(bSearch);
        pNorth.add(bQuit);
        pSouth.setBackground(Color.black);
        pSouth.add(message);
        pNorth.add(convo);
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(2, 0));
        this.add(pSouth);
        this.add(pNorth);
        this.setBounds(10, 10, 500, 400);
        this.setVisible(true);
        this.convo.setEditable(false);
        this.message.setText("[write your message here]");
        if(is_server)
        this.setTitle("server interface");
        else
        this.setTitle("client interface");

        MyWindowListener x1 = new MyWindowListener();
        this.addWindowListener(x1);

        bQuit.addActionListener(new MyActionListenerForExit(this));
        bSearch.addActionListener(new MyActionListenerForSearch(this, out,is_server));
    }
    public MyFrameAWT (DataOutputStream out,boolean is_server) {
        helper(out,is_server);
    }
    public MyFrameAWT(boolean is_server) {
        helper(null,is_server);
    }

}
