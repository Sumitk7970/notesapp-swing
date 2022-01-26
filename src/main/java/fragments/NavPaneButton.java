package fragments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import notes.MainFrame;
import utils.UI;

public class NavPaneButton extends JButton {
	private ImageIcon defaultIcon, selectedIcon;
	private Font defaultFont = UI.Fonts.robotoRegular.deriveFont(13f);
	private Font selectedFont = UI.Fonts.robotoBold.deriveFont(13f);
	String text;
	
	public NavPaneButton(String text, ImageIcon defaultIcon, ImageIcon selectedIcon) {
		super(text);
		this.text = text;
		this.defaultIcon = defaultIcon;
		this.selectedIcon = selectedIcon;
		setPreferredSize(new Dimension(160, 40));
		setMinimumSize(new Dimension(150, 40));
		setMaximumSize(new Dimension(160, 40));
		setAlignmentX(CENTER_ALIGNMENT);
		setBackground(null);
		setForeground(new Color(0x7E8285));
		setIcon(defaultIcon);
		setHorizontalAlignment(SwingConstants.LEADING);
		setIconTextGap(10);
		setFont(defaultFont);
		
	}
	
	public void getSelectedState() {
		setBackground(UI.Colors.color_on_primary);
		setForeground(UI.Colors.font_selected);
		setIcon(selectedIcon);
		setFont(selectedFont);

		MainFrame.cardLayout.show(MainFrame.centerPanel, text);
		
	}
	
	public void getNormalState() {
		setBackground(null);
		setForeground(UI.Colors.font_primary);
		setIcon(defaultIcon);
		setFont(defaultFont);
	}

}
