package phone.container;

import phone.component.CustomContainer;
import phone.component.ImageLabel;
import phone.entity.Person;
import phone.repository.LocalStorage;
import phone.window.CallingWindow;
import phone.window.ContactDetailsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class FavoritePanel extends CustomContainer {

  private List<Person> contacts;
  private JTabbedPane tab;

  public FavoritePanel(JTabbedPane tabbedPane) {
    loadFavoriteContacts();
    setVisiblePanel();
//    redraw();
    this.tab = tabbedPane;
  }


  public void redraw() {

    Timer timer = new Timer(0, e -> {
        loadFavoriteContacts();
        setVisiblePanel();
    });

    timer.setRepeats(true);
    timer.setDelay(1000 * 1);
    timer.start();
  }

  private void setVisiblePanel() {


    if(contacts.isEmpty()) {
      buildFavoritePanelEmpty();
      return;
    }

    buildFavoriteContactList();
  }

  private void loadFavoriteContacts() {
    contacts = LocalStorage.favorites
        .parallelStream()
        .collect(Collectors.toList());
  }

  private Container buildFavoriteContactList() {
    removeAll();

    var contactListPanel = new JPanel();

    var scrollPanel = new JScrollPane(contactListPanel);
    scrollPanel.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);

    scrollPanel.setLayout(new ScrollPaneLayout());
    scrollPanel.setBorder(null);

    for (Person person : contacts) {
      var list = new CustomContainer();
      var icon = new ImageLabel("resources/icons/clock-32.png");
      var btnTrash = new JButton("", new ImageIcon("resources/icons/trash-32.png"));
      var btnCall = new JButton("", new ImageIcon("resources/icons/cellphone-calling-32.png"));

      btnCall.addActionListener(e -> {
        LocalStorage.historic.add(person.getPhoneNumber());
        reloadTab();
        CallingWindow.run(person.getPhoneNumber(), null);

      });

      btnTrash.addActionListener(e -> {
        LocalStorage.favorites.remove(person);
        contacts.remove(person);
        setVisiblePanel();

        reloadTab();

      });

      var title = person.getName().length() < 20
          ? person.getName()
          : person.getName().substring(0, 17) + "...";

      var label = new JLabel(String.format(title));

      label.setFont(new Font("Serif", Font.PLAIN, 20));

      list.addMouseListener(new EventClickContactList(list, person));

      var buttonsPanel = new JPanel();
      buttonsPanel.setLayout(new GridLayout(1, 2));
      buttonsPanel.add(btnTrash);
      buttonsPanel.add(btnCall);

      list.setLayout(new BorderLayout());
      list.add(BorderLayout.WEST, icon);
      list.add(BorderLayout.CENTER, label);
      list.add(BorderLayout.EAST, buttonsPanel);

      contactListPanel.add(list);
    }

    contactListPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    contactListPanel.setLayout(new BoxLayout(contactListPanel, BoxLayout.Y_AXIS));
    contactListPanel.requestFocus();

    add(scrollPanel);

    return this;
  }

  private void reloadTab() {
    tab.removeAll();
    tab.addTab("Favoritos", new ImageIcon("resources/icons/star.png"), new JScrollPane(new FavoritePanel(tab)), "Contatos favoritos");
    tab.addTab("Histórico", new ImageIcon("resources/icons/clock.png"), new JScrollPane(new HistoryPanel(tab)), "Histórico de chamadas");
    tab.addTab("Contatos", new ImageIcon("resources/icons/group.png"), new JScrollPane(new ContactPanel(tab)), "Lista de contados");

    tab.setSelectedIndex(0);
  }

  private Container buildFavoritePanelEmpty() {

    removeAll();

    var listEmptyIcon = new CustomContainer();

    listEmptyIcon.addAll(
        new ImageLabel("resources/icons/star-32.png"),
        new ImageLabel("resources/icons/cellphone-128.png"),
        new ImageLabel("resources/icons/clock-32.png")
    );

    var mainPanel = new JPanel();

    mainPanel.setLayout(new GridLayout(2, 1));
    mainPanel.add(listEmptyIcon);

    var lblInfo = new JLabel("Não há contatos favoritos ...");

    var btnAddFavoriteContact = new JButton("Adicionar contato ", new ImageIcon("resources/icons/plus-16.png"));

    btnAddFavoriteContact.addActionListener(e -> {
      final int TAB_CONTACTS = 2;
      tab.setSelectedIndex(TAB_CONTACTS);
    });


    lblInfo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
    lblInfo.setForeground(Color.GRAY);
    var secondaryPanel = new CustomContainer();
    secondaryPanel.setLayout(new BorderLayout());

    secondaryPanel.add(BorderLayout.CENTER, lblInfo);
    secondaryPanel.add(BorderLayout.SOUTH, btnAddFavoriteContact);

    mainPanel.add(secondaryPanel);

    add(mainPanel);

    return this;
  }

  private static class EventClickContactList implements MouseListener {
    private Component target;
    private Color targetColorDefault;
    private Person person;

    public EventClickContactList(Component target, Person person) {
      this.target = target;
      this.person = person;
      targetColorDefault = target.getBackground();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      target.setBackground(targetColorDefault);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      target.setBackground(Color.LIGHT_GRAY);
      System.out.println(person);
      ContactDetailsWindow.run(person, null);
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
