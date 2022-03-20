package phone.component;

import javax.swing.*;

public class ImageLabel extends JLabel {
  public ImageLabel(String imageURL) {
    super(new ImageIcon(imageURL));
  }
}
