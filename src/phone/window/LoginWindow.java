package phone.window;

import phone.component.CustomContainer;
import phone.component.ImageLabel;
import phone.entity.User;
import phone.repository.LocalStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class LoginWindow extends JFrame {

  private User user = new User();

  public static void run() {
    new LoginWindow();
  }

  public LoginWindow() {
    setComponents();
    setDefaultBehavior();
  }

  private void setDefaultBehavior() {
    setTitle("Phone - Login");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(380, 500);
    setResizable(Boolean.FALSE);
    setLocationRelativeTo(null);
    setVisible(Boolean.TRUE);
  }

  private  void setComponents() {

    var logoIcon = new ImageLabel("resources/icons/cellphone-128.png");
    logoIcon.setFont(new Font("Serif", Font.PLAIN, 64));
    var logoContainer = new CustomContainer(logoIcon);

    var labelInfo = new JLabel("Usuário ou senha inválido");
    labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
    labelInfo.setFont(new Font("Serif", Font.BOLD, 16));
    labelInfo.setForeground(Color.RED);
    labelInfo.setVisible(Boolean.FALSE);

    var loginField = new JTextField();
    loginField.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        labelInfo.setVisible(Boolean.FALSE);
      }
      @Override
      public void focusLost(FocusEvent e) {}
    });

    var labelLoginField = new JLabel("login");

    loginField.setFont(new Font("Serif", Font.BOLD, 16));

    var lebalPasswordField = new JLabel("senha");
    var passwordField = new JPasswordField();

    passwordField.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        labelInfo.setVisible(Boolean.FALSE);
      }
      @Override
      public void focusLost(FocusEvent e) {}
    });

    passwordField.setFont(new Font("Serif", Font.BOLD, 16));

    var btnLogin = new JButton("Entrar", new ImageIcon("resources/icons/lock-16.png"));
    btnLogin.addActionListener(e -> {
      var user = new User(loginField.getText(), passwordField.getText());

      if(LocalStorage.users.contains(user)) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dispose();
        MainWindow.run();
        return;
      }

      passwordField.setText("");
      labelInfo.setVisible(Boolean.TRUE);

    });



    var mainContainer = new CustomContainer();

    mainContainer.setLayout(new GridLayout(6, 1));

    setLayout(new GridLayout(3, 1));

    mainContainer.addAll(
        labelInfo,
        labelLoginField,
        loginField,
        lebalPasswordField,
        passwordField,
        btnLogin
    );
    add(logoContainer);
    add(mainContainer);
  }
}
