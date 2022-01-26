package fragments;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.UI;

public class EmptyPanel extends JPanel {
	private Font font = UI.Fonts.robotoRegular.deriveFont(20f);

	public EmptyPanel() {
		setBackground(UI.Colors.secondary);
		setLayout(new BorderLayout());
		
		// adding label to the center 
		JLabel label = new JLabel("No Notes");
		label.setIcon(new ImageIcon(getClass().getResource("/Images/empty_note.png")));
		label.setForeground(UI.Colors.font_primary);
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		add(label);

	}

}
