package notes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import fragments.NavPaneButton;
import utils.NoteManager;
import utils.UI;

public class NavigationPane extends JPanel {
	private ArrayList<NavPaneButton> navButtonList;
	private int selectedIndex = 0;

	public NavigationPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, Color.black), new  EmptyBorder(10, 0, 20, 0)));
		setBackground(UI.Colors.primary);
		
		
		// adding navigation buttons to the arraylist
		navButtonList = new ArrayList<NavPaneButton>();
		
		navButtonList.add(new NavPaneButton("All Notes", new ImageIcon(getClass().getResource("/Images/note.png")), 
		new ImageIcon(getClass().getResource("/Images/note_selected.png"))));

		navButtonList.add(new NavPaneButton("Favorites", new ImageIcon(getClass().getResource("/Images/heart.png")), 
		new ImageIcon(getClass().getResource("/Images/heart_filled.png"))));
		
		navButtonList.add(new NavPaneButton("Work", new ImageIcon(getClass().getResource("/Images/work.png")), 
				new ImageIcon(getClass().getResource("/Images/work_selected.png"))));
		

		// adding new note button
		addNewNoteButton();
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		// adding navigation buttons
		addNavigationButtons();
		add(Box.createVerticalGlue());

	}
	
	private void addNewNoteButton() {

		JButton newNote = new JButton("New Note");
		newNote.setIcon(new ImageIcon(getClass().getResource("/Images/plus.png")));
		newNote.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.add(new EditorPane(NoteManager.getNewId(), true));
				MainFrame.contentPane.repaint();
				MainFrame.contentPane.revalidate();
				
			}
		});
		newNote.setPreferredSize(new Dimension(160, 40));
		newNote.setMaximumSize(new Dimension(160, 40));
		newNote.setAlignmentX(CENTER_ALIGNMENT);
		newNote.setHorizontalAlignment(SwingConstants.LEADING);
		newNote.setIconTextGap(10);
		newNote.setBackground(UI.Colors.accent);
		newNote.setForeground(UI.Colors.font_secondary);
		newNote.setFont(new Font("SansSerif", Font.BOLD, 15));
		add(newNote);
	}
	
	
	public void addNavigationButtons() {
		
		for(final NavPaneButton navButton : navButtonList) {
			add(navButton);
			add(Box.createRigidArea(new Dimension(0, 5)));
			
			navButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					// deselecting the previous selected button
					navButtonList.get(selectedIndex).getNormalState();
					
					// selecting the clicked button
					navButton.getSelectedState();
					selectedIndex = navButtonList.indexOf(navButton);
					
				}
			});
		
		}
		
		navButtonList.get(selectedIndex).getSelectedState();
		
		
	}
	

}
