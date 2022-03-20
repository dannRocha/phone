package phone.container;

import phone.component.CustomContainer;
import phone.component.ImageLabel;
import phone.repository.LocalStorage;
import phone.window.CallKeyboardWindow;
import phone.window.CallingWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoryPanel extends CustomContainer {
  private List<String> historyCalls;
  private JPanel emptyPanel = new JPanel();
  private JPanel fillPanel = new JPanel();

  private JTabbedPane tab;

  public HistoryPanel(JTabbedPane tabbedPane) {
    tab = tabbedPane;
    loadHistoryCalls();
    setComponents();
    setVisiblePanel();
  }

  private void setVisiblePanel() {
    if(historyCalls.isEmpty()) {
      remove(emptyPanel);
      add(emptyPanel);
      return;
    }
    remove(emptyPanel);
    add(fillPanel);
  }

  private void loadHistoryCalls() {
    historyCalls = LocalStorage.historic;
  }

  private void setComponents() {
    buildHistoryPanelEmpty();
    buildHistoryCallsPanel();
  }

  private Container buildHistoryCallsPanel() {
    var contactListPanel = new JPanel();
    var topPanel = new JPanel();

    var btnClearHistory = new JButton(String.format("Limpar histórico (%s)", historyCalls.size()),
                                      new ImageIcon("resources/icons/trash-32.png"));
    btnClearHistory.addActionListener(e -> {
      LocalStorage.historic.clear();
      reloadTab();
    });

    topPanel.add(btnClearHistory);

    for(var call : historyCalls) {
      var list = new CustomContainer();
      var icon = new ImageLabel("resources/icons/clock-32.png");
      var btnCall = new JButton("", new ImageIcon("resources/icons/cellphone-calling-32.png"));

      btnCall.addActionListener(e -> {
        CallingWindow.run(call, null);
      });

      var title = call.length() < 20
          ? call
          : call.substring(0, 17) + "...";

      var label = new JLabel(String.format(title));

      label.setFont(new Font("Serif", Font.PLAIN, 20));

      list.setLayout(new BorderLayout());
      list.add(BorderLayout.WEST, icon);
      list.add(BorderLayout.CENTER, label);
      list.add(BorderLayout.EAST, btnCall);

      contactListPanel.add(list);
    }
    setLayout(new BorderLayout());

    contactListPanel.setLayout(new BoxLayout(contactListPanel, BoxLayout.PAGE_AXIS));
    add(BorderLayout.NORTH, contactListPanel);
    if(!historyCalls.isEmpty())
      add(BorderLayout.SOUTH, topPanel);

    return this;
  }

  private Container buildHistoryPanelEmpty() {

    var listEmptyIcon = new CustomContainer();

    listEmptyIcon.addAll(new ImageLabel("resources/icons/clock-128.png"));

    var mainPanel = new JPanel();

    mainPanel.setLayout(new GridLayout(2, 1));
    mainPanel.add(listEmptyIcon);

    var lblInfo = new JLabel("Seu histórico de ligações está vazio");
    var btnMakeCall = new JButton("Faça um chamada", new ImageIcon("resources/icons/cellphone-16.png"));

    lblInfo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
    lblInfo.setForeground(Color.GRAY);

    btnMakeCall.addActionListener(e -> {
      CallKeyboardWindow.run(() -> {
        reloadTab();
      });
    });

    var secondaryPanel = new CustomContainer();
    secondaryPanel.setLayout(new BorderLayout());

    secondaryPanel.add(BorderLayout.CENTER, lblInfo);
    secondaryPanel.add(BorderLayout.SOUTH, btnMakeCall);

    mainPanel.add(secondaryPanel);

    emptyPanel.add(mainPanel);

    return this;
  }

  private void reloadTab() {
    tab.removeAll();
    tab.addTab("Favoritos", new ImageIcon("resources/icons/star.png"), new JScrollPane(new FavoritePanel(tab)), "Contatos favoritos");
    tab.addTab("Histórico", new ImageIcon("resources/icons/clock.png"), new JScrollPane(new HistoryPanel(tab)), "Histórico de chamadas");
    tab.addTab("Contatos", new ImageIcon("resources/icons/group.png"), new JScrollPane(new ContactPanel(tab)), "Lista de contados");

    tab.setSelectedIndex(1);
  }
}
