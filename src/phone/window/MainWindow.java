package phone.window;


import phone.component.PlaceholderTextField;
import phone.container.ContactPanel;
import phone.container.FavoritePanel;
import phone.container.HistoryPanel;


import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

  public static void run() {
    new MainWindow();
  }

  private JTabbedPane tab;

  public MainWindow() {
    setComponents();
    setDefaultBehavior();
  }

  private  void setComponents() {
    setInputSearchComponent();
    setTabbedPanel();
    setTopMenu();
  }

  private void setDefaultBehavior() {
    setTitle("Phone");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(380, 500);
    setResizable(Boolean.FALSE);
    setLocationRelativeTo(null);
    setVisible(Boolean.TRUE);
  }

  private void setInputSearchComponent() {

    var searchInput = new PlaceholderTextField();
    var defaultFont = searchInput.getFont();

    searchInput.setPlaceholder("Procurar contato");
    searchInput.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), 25));
    searchInput.setHorizontalAlignment(JTextField.LEFT);

    add(BorderLayout.NORTH, searchInput);

  }

  private void setTabbedPanel() {
    tab = new JTabbedPane();

    tab.addTab("Favoritos", new ImageIcon("resources/icons/star.png"), new JScrollPane(new FavoritePanel(tab)), "Contatos favoritos");
    tab.addTab("Histórico", new ImageIcon("resources/icons/clock.png"), new JScrollPane(new HistoryPanel(tab)), "Histórico de chamadas");
    tab.addTab("Contatos", new ImageIcon("resources/icons/group.png"), new JScrollPane(new ContactPanel(tab)), "Lista de contados");

    tab.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
    tab.setSize(getWidth(), 30);
    tab.setSelectedIndex(1);
    add(tab);
  }

  private void setTopMenu() {
    var newCall = new JMenuItem("Nova chamada");
    var newContactItem = new JMenuItem("Novo contato");
    var logoutItem = new JMenuItem("Sair");


    newCall.addActionListener(e -> {
      CallKeyboardWindow.run(() -> {
        reloadTab();
      });
    });

    newContactItem.addActionListener(e -> {
      RegisterContactWindow.run(() -> {
        reloadTab();
      });
    });

    logoutItem.addActionListener(e -> {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.dispose();
    });

    var start = new JMenu("usuário");
    start.add(newCall);
    start.add(newContactItem);
    start.add(logoutItem);

    var menuBar = new JMenuBar();
    menuBar.add(start);
    setJMenuBar(menuBar);
  }

  private void reloadTab() {
    var currentTab = tab.getSelectedIndex();

    tab.removeAll();

    tab.addTab("Favoritos", new ImageIcon("resources/icons/star.png"), new JScrollPane(new FavoritePanel(tab)), "Contatos favoritos");
    tab.addTab("Histórico", new ImageIcon("resources/icons/clock.png"), new JScrollPane(new HistoryPanel(tab)), "Histórico de chamadas");
    tab.addTab("Contatos", new ImageIcon("resources/icons/group.png"), new JScrollPane(new ContactPanel(tab)), "Lista de contados");

    tab.setSelectedIndex(currentTab);

  }

}
