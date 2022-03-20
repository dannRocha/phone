package phone.component;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CustomContainer extends JPanel {

  public CustomContainer() {
  }

  public CustomContainer(Component ...components) {
    addAll(components);
  }

  public CustomContainer addAll(Component ...components) {
    for(var component : components) {
      add(component);
    }

    return this;
  }
}
