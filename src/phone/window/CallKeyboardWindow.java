package phone.window;

import phone.component.CustomContainer;
import phone.component.ImageLabel;
import phone.repository.LocalStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

public class CallKeyboardWindow extends JFrame {


  public static void run() {
    new CallKeyboardWindow();
  }

  public static void run(Runnable runnable) {
    new CallKeyboardWindow(runnable);
  }

  private Runnable runnable;

  public CallKeyboardWindow() {
    setComponents();
    setDefaultBehavior();
  }

  public CallKeyboardWindow(Runnable runnable) {
    this();
    this.runnable = runnable;
  }

  private void setDefaultBehavior() {
    setTitle("Phone - call");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(380, 500);
    setResizable(Boolean.FALSE);
    setLocationRelativeTo(null);
    setVisible(Boolean.TRUE);
  }

  private void setComponents() {
    setKeyboardComponent();
  }


  private void setKeyboardComponent() {

    var displayPanel = new JPanel();
    var keyboardPanel = new JPanel();

    var displayTextField = new JTextField();
    var clearButton = new JButton("C");
    var callButton = new JButton(new ImageIcon("resources/icons/cellphone-16.png"));

    displayTextField.setFocusable(Boolean.FALSE);
    displayPanel.setLayout(new BorderLayout());
    displayTextField.setFont(new Font("Serif", Font.BOLD, 32));
    displayTextField.setForeground(Color.BLACK);

    callButton.setEnabled(Boolean.FALSE);

    clearButton.addActionListener(e -> {
      displayTextField.setText("");
      callButton.setEnabled(Boolean.FALSE);
    });

    callButton.addActionListener(e -> {
      CallingWindow.run(displayTextField.getText(), this);
      LocalStorage.historic.add(displayTextField.getText());

      if(runnable != null)
        runnable.run();
    });

    var secundaryPanel = new CustomContainer(clearButton, callButton);
    secundaryPanel.setLayout(new GridLayout());

    displayPanel.add(BorderLayout.CENTER, displayTextField);
    displayPanel.add(BorderLayout.EAST, secundaryPanel);

    keyboardPanel.setLayout(new GridLayout(4, 3));

    final String[] keyboard = {
        "7", "8", "9",
        "4", "5", "6",
        "1", "2", "3",
        "#", "0", "*"
    };

    for(var key : keyboard) {
      var numericButton = new JButton(key);
      numericButton.setFont(new Font("Serif", Font.BOLD, 18));

      numericButton.addActionListener(e -> {
        displayTextField.setText(displayTextField.getText() + numericButton.getText());

        if(displayTextField.getText().trim().length() > 0) {
          callButton.setEnabled(Boolean.TRUE);
          return;
        }
        callButton.setEnabled(Boolean.FALSE);
      });

      keyboardPanel.add(numericButton);
    }

    setLayout(new BorderLayout());

    add(BorderLayout.NORTH, displayPanel);
    add(BorderLayout.CENTER, keyboardPanel);

  }
}
