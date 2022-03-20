package phone.window;

import phone.component.ImageLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CallingWindow extends JFrame {
  public static void run(String phoneNumber, JFrame backward) {
    if(backward != null)
      backward.setVisible(Boolean.FALSE);
    new CallingWindow(phoneNumber, backward);
  }

  private Boolean muteEnable = Boolean.FALSE;
  private String phoneNumber;
  private JFrame backward;

  public CallingWindow(String phoneNumber, JFrame backward) throws HeadlessException {
    this.phoneNumber = phoneNumber;
    this.backward = backward;
    setDefaultBehavior();
    setComponents();
  }

  private void setComponents() {
    setCallingComponents();
  }

  private void setDefaultBehavior() {
    setTitle("Phone - call");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(380, 500);
    setResizable(Boolean.FALSE);
    setLocationRelativeTo(null);
    setVisible(Boolean.TRUE);
  }

  private void setCallingComponents() {


    var displayCallingPanel = new JPanel();
    var displayButtonCallingPanel = new JPanel();

    var callingInfo = new JLabel(String.format("Chamando %s", phoneNumber));
    var callingIcon = new ImageLabel("resources/icons/cellphone-calling-128.png");


    callingInfo.setBackground(Color.RED);
    callingInfo.setHorizontalAlignment(SwingConstants.CENTER);
    callingInfo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
    callingInfo.setForeground(Color.GRAY);

    displayCallingPanel.setLayout(new BorderLayout());

    displayCallingPanel.add(BorderLayout.CENTER, callingIcon);
    displayCallingPanel.add(BorderLayout.SOUTH, callingInfo);

    var endCallButton = new JButton(new ImageIcon("resources/icons/end-call-64.png"));
    var muteCallButton = new JButton(new ImageIcon("resources/icons/mute-64.png"));

    muteCallButton.addActionListener(e -> {
      muteEnable = !muteEnable;
      if(muteEnable)
        muteCallButton.setIcon(new ImageIcon("resources/icons/sound-64.png"));
      else
        muteCallButton.setIcon(new ImageIcon("resources/icons/mute-64.png"));
    });

    endCallButton.addActionListener(e -> {
      if(backward != null)
        backward.setVisible(Boolean.TRUE);
      dispose();
    });

    endCallButton.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        final int KEY_ENTER_CODE_BUTTON = 10;
        final int KEY_ESC_CODE_BUTTON = 27;
        if(KEY_ESC_CODE_BUTTON == e.getKeyCode() || KEY_ENTER_CODE_BUTTON == e.getKeyCode()) {
          backward.setVisible(Boolean.TRUE);
          dispose();
        }

        System.out.println(e.getKeyCode());
      }

      @Override
      public void keyReleased(KeyEvent e) {}
    });

    displayButtonCallingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    displayButtonCallingPanel.add(endCallButton);
    displayButtonCallingPanel.add(muteCallButton);

    setLayout(new GridLayout(2, 1));

    add(displayCallingPanel);
    add(displayButtonCallingPanel);

  }
}
