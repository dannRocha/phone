package phone.container;

import phone.component.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainPanel extends JPanel {

  private JFrame frame;

  public MainPanel(JFrame frame) {
    this.frame = frame;
    setComponents();
    setDefaultBehavior();
  }

  private  void setComponents() {
    setInputSearchComponent();
    setTabbedPanel();
    setTopMenu();
  }

  private void setDefaultBehavior() {
    frame.setTitle("Phone");
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(380, 500);
    frame.setResizable(Boolean.FALSE);
    frame.setLocationRelativeTo(null);
    setVisible(Boolean.TRUE);
    requestFocus();
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
    var tab = new JTabbedPane();

    tab.addTab("Favoritos", new ImageIcon("resources/icons/star.png"), new JScrollPane(new FavoritePanel(tab)), "Contatos favoritos");
    tab.addTab("Histórico", new ImageIcon("resources/icons/clock.png"), new JScrollPane(new HistoryPanel(tab)), "Histórico de chamadas");
    tab.addTab("Contatos", new ImageIcon("resources/icons/group.png"), new JScrollPane(new ContactPanel(tab)), "Lista de contados");

    tab.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
    tab.setSize(getWidth(), 30);
//    tab.setSelectedIndex(2);
    add(tab);

  }


  private void setTopMenu() {
    var newContactItem = new JMenuItem("Novo contato");
    var logoutItem = new JMenuItem("Sair");
    logoutItem.addActionListener(e -> {
      frame.dispose();
    });

    var start = new JMenu("arquivo");

    start.add(newContactItem);
    start.add(logoutItem);

    var menuBar = new JMenuBar();
    menuBar.add(start);
    frame.setJMenuBar(menuBar);
  }
}
