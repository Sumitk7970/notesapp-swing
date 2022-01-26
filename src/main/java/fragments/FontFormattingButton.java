package fragments;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import utils.UI;

public class FontFormattingButton extends JCheckBox{
	private ImageIcon defaultIcon, selectedIcon;
	
	public FontFormattingButton(String toolTipText, String defaultIcon, String selectedIcon, Action action) {
		this.defaultIcon = new ImageIcon(getClass().getResource("/Images/" + defaultIcon + ".png"));
		this.selectedIcon = new ImageIcon(getClass().getResource("/Images/" + selectedIcon + ".png"));
		
		
		setBackground(UI.Colors.transparent);
		setIcon(this.defaultIcon);
		setSelectedIcon(this.selectedIcon);
		setToolTipText(toolTipText);
		setAction(action);
		setText("");
		// default icon is 24 so setting pressed icon size 23
		setPressedIcon(new ImageIcon(this.defaultIcon.getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH)));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(UI.Colors.color_on_primary.brighter());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(UI.Colors.transparent);
			}
		});
		
	}
	
	

}