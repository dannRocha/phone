package phone.container;

import phone.component.CustomContainer;
import phone.component.ImageLabel;
import phone.entity.Person;
import phone.repository.LocalStorage;
import phone.window.CallingWindow;
import phone.window.ContactDetailsWindow;
import phone.window.RegisterContactWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class ContactPanel extends CustomContainer {

  private List<Person> contacts;
  private JTabbedPane tab;

  public ContactPanel(JTabbedPane tabbedPane) {
    this.tab = tabbedPane;
    loadContacts();
    setVisiblePanel();
  }

  private void loadContacts() {

    contacts = LocalStorage.contacts.parallelStream().collect(Collectors.toList());

    contacts.sort((personA, personB) ->
      personA.getName().compareToIgnoreCase(personB.getName()));
  }

  private void setVisiblePanel() {
    if(contacts.isEmpty()) {
      buildContactPanelEmpty();
      return;
    }

    buildContactListPanel();
  }

  private Container buildContactListPanel() {
    removeAll();
    var contactListPanel = new JPanel();

    var scrollPanel = new JScrollPane(contactListPanel);
    scrollPanel.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);

    scrollPanel.setLayout(new ScrollPaneLayout());
    scrollPanel.setBorder(null);

    for (Person person : contacts) {
      var list = new CustomContainer();
      var icon = new ImageLabel("resources/icons/clock-32.png");
      var btnFavorite = new JButton("", new ImageIcon("resources/icons/star-32.png"));
      var btnCall = new JButton("", new ImageIcon("resources/icons/cellphone-calling-32.png"));

      if(LocalStorage.favorites.contains(person)) {
        btnFavorite.setEnabled(Boolean.FALSE);
      }

      btnCall.addActionListener(e -> {
        LocalStorage.historic.add(person.getPhoneNumber());
        reloadTab();
        CallingWindow.run(person.getPhoneNumber(), null);
      });

      btnFavorite.addActionListener(e -> {
        LocalStorage.favorites.add(person);
        btnFavorite.setEnabled(Boolean.FALSE);

        reloadTab();
      });

      var title = person.getName().length() < 20
          ? person.getName()
          : person.getName().substring(0, 17) + "...";

      var label = new JLabel(String.format(title));

      label.setFont(new Font("Serif", Font.PLAIN, 20));

      list.addMouseListener(new EventClickContactList(list, person, () -> {
        reloadTab();
      }));

      var buttonsPanel = new JPanel();
      buttonsPanel.setLayout(new GridLayout(1, 2));
      buttonsPanel.add(btnFavorite);
      buttonsPanel.add(btnCall);

      list.setLayout(new BorderLayout());
      list.add(BorderLayout.WEST, icon);
      list.add(BorderLayout.CENTER, label);
      list.add(BorderLayout.EAST, buttonsPanel);

      contactListPanel.add(list);
    }

    contactListPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    contactListPanel.setLayout(new BoxLayout(contactListPanel, BoxLayout.Y_AXIS));
    add(scrollPanel);

    return this;
  }

  private void reloadTab() {
    tab.removeAll();

    tab.addTab("Favoritos", new ImageIcon("resources/icons/star.png"), new JScrollPane(new FavoritePanel(tab)), "Contatos favoritos");
    tab.addTab("Histórico", new ImageIcon("resources/icons/clock.png"), new JScrollPane(new HistoryPanel(tab)), "Histórico de chamadas");
    tab.addTab("Contatos", new ImageIcon("resources/icons/group.png"), new JScrollPane(new ContactPanel(tab)), "Lista de contados");
    tab.setSelectedIndex(2);
  }


  private Container buildContactPanelEmpty() {
    removeAll();

    var listEmptyIcon = new CustomContainer();

    listEmptyIcon.addAll(new ImageLabel("resources/icons/user-128.png"));

    var mainPanel = new JPanel();

    mainPanel.setLayout(new GridLayout(2, 1));
    mainPanel.add(listEmptyIcon);

    var lblInfo = new JLabel("Lista vazia. Adiciona seus contatos aqui");
    var btnNewCall = new JButton("Adicionar contato", new ImageIcon("resources/icons/plus-16.png"));

    lblInfo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 16));
    lblInfo.setForeground(Color.GRAY);

    btnNewCall.addActionListener(e -> {
      RegisterContactWindow.run(() -> {
        reloadTab();
      });
    });

    var secondaryPanel = new CustomContainer();
    secondaryPanel.setLayout(new BorderLayout());

    secondaryPanel.add(BorderLayout.CENTER, lblInfo);
    secondaryPanel.add(BorderLayout.SOUTH, btnNewCall);

    mainPanel.add(secondaryPanel);
    add(mainPanel);
    return this;
  }

  private static class EventClickContactList implements MouseListener {
    private Component target;
    private Color targetColorDefault;
    private Person person;
    private Runnable runnable;

    public EventClickContactList(Component target, Person person) {
        this.target = target;
        this.person = person;
        targetColorDefault = target.getBackground();
    }
    public EventClickContactList(Component target, Person person, Runnable runnable) {
      this.target = target;
      this.person = person;
      this.runnable = runnable;
      targetColorDefault = target.getBackground();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      target.setBackground(targetColorDefault);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      target.setBackground(Color.LIGHT_GRAY);
      ContactDetailsWindow.run(person, runnable);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      target.setBackground(targetColorDefault);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
  }
}
