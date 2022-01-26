package notes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import fragments.EmptyPanel;
import fragments.Note;
import fragments.NoteModel;
import utils.NoteManager;
import utils.UI;
import utils.WrapLayout;

public class NotesPane extends JPanel {
	private Font searchFont = UI.Fonts.robotoRegular.deriveFont(16f);
	private ArrayList<Note> noteList;
	private ArrayList<NoteModel> noteModelList = new ArrayList<NoteModel>();
	private JPanel notesPanel;
	
	public NotesPane(int category) {
		setBackground(UI.Colors.secondary);
		setLayout(new BorderLayout());
		

		// getting note data from database
		noteList = NoteManager.getAllNotes(category);
		if(noteList==null || noteList.size()==0) {
			add(new EmptyPanel());
			return;
		}
		
		/* adding top panel */
		add(new TopPanel(), BorderLayout.NORTH);
		
		
		/* adding notes panel */
		notesPanel = new JPanel();
		notesPanel.setBackground(getBackground());
		notesPanel.setLayout(new WrapLayout(FlowLayout.LEADING, 15, 15));
		
		final JScrollPane scrollPane = new JScrollPane(notesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		scrollPane.getVerticalScrollBar().setBackground(getBackground());
		add(scrollPane, BorderLayout.CENTER);
		
		
		
		// adding notes to the notes pane
		for(Note note : noteList) {
			NoteModel noteModel = new NoteModel(note, notesPanel);
			noteModelList.add(noteModel);
			notesPanel.add(noteModel);
		}
		
		
		// setting the position of scrollpane to top
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(0);
				
			}
		});

	}
	
	
	private void search(String keyword) {
		for(NoteModel noteModel : noteModelList) {
			if(!noteModel.getTitle().contains(keyword) && !noteModel.getText().contains(keyword)) {
				noteModel.setVisible(false);
			} else {
				noteModel.setVisible(true);
			}
			notesPanel.revalidate();
			notesPanel.repaint();
		}
	}
	
	
	private class TopPanel extends JPanel{
		
		public TopPanel() {
			setOpaque(false);
			setBorder(new EmptyBorder(10, 15, 5, 15));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			// adding search field to the panel
			add(new SearchField());
			
		}
	}
	
	
	private class SearchField extends JTextField {
		private Color bgColor = UI.Colors.color_on_primary, borderColor = UI.Colors.color_on_primary;
		private int cornerRadius = 15;
		
		@Override
		protected void paintComponent(Graphics g) {
			
			Graphics2D graphics2d = (Graphics2D) g;
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// filling rectangle
			graphics2d.setColor(bgColor);
			graphics2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);

			// drawing border
			graphics2d.setColor(borderColor);
			graphics2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);

			super.paintComponent(g);
		}
		
		public SearchField() {
			addFocusListener(new FocusListener() {
				
				public void focusLost(FocusEvent e) {
					bgColor = UI.Colors.color_on_primary;
					borderColor = UI.Colors.color_on_primary;
					if(getText().equals("")) setText("Search notes...");
				}
				
				public void focusGained(FocusEvent e) {
					bgColor = UI.Colors.secondary;
					borderColor = UI.Colors.color_on_primary.brighter();
					if(getText().equals("Search notes...")) setText("");
				}
			});
			addKeyListener(new KeyListener() {
				
				public void keyTyped(KeyEvent e) {
					search(getText());
				}

				public void keyPressed(KeyEvent e) {
					
				}

				public void keyReleased(KeyEvent e) {
					
				}
			});
			setOpaque(false);
			setPreferredSize(new Dimension(220, 40));
			setMaximumSize(new Dimension(220, 40));
			setFont(searchFont);
			setText("Search notes...");
			setCaretPosition(0);
			setBorder(new EmptyBorder(6, 10, 4, 10));
			setForeground(UI.Colors.font_primary);
		}
	}

}
