package notes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.StyledEditorKit;

import fragments.FontFormattingButton;
import fragments.Note;
import utils.ColorChooser;
import utils.NoteManager;
import utils.UI;

public class EditorPane extends JPanel {
	int id;
	boolean isNew;
	private ArrayList<FontFormattingButton> formatButtonList;
	private final Font titleFont = UI.Fonts.robotoBold.deriveFont(16f), textFont = UI.Fonts.robotoRegular.deriveFont(14f);
	private JPanel editorPanel;
	private JTextField titleField;
	private JTextPane textArea;
	private TopPanel topPanel;
	private ColorChooser colorChooser;
	private Note note;

	public EditorPane(int id, boolean isNew) {
		this.id = id;
		this.isNew = isNew;
		
		setLayout(new BorderLayout(0, 0));
		setBackground(UI.Colors.secondary);
		
		/* adding top panel */
		topPanel = new TopPanel();
		add(topPanel, BorderLayout.NORTH);
		
		
		/* adding editor panel */
		editorPanel = new JPanel();
		editorPanel.setBackground(UI.Colors.secondary);
		editorPanel.setLayout(new BorderLayout(0, 10));
		editorPanel.setBorder(new EmptyBorder(0, 0, 2, 2));
		add(editorPanel, BorderLayout.CENTER);
		
		
		// adding title panel to editor panel for note title
		addTitlePanel();
		
		
		// adding textarea to editor panel for text
		addTextPanel();
		
		// getting note data 
		if(!isNew) {
			note = NoteManager.getNote(id);
			titleField.setText(note.getTitle());
			textArea.setText(note.getText());
			colorChooser.setColor(note.getColorIndex());
			
		}
		
	}

	private void addTitlePanel() {
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(editorPanel.getBackground());
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.setBorder(new CompoundBorder(new MatteBorder(2, 0, 0, 0, UI.Colors.color_on_primary),
				new EmptyBorder(5, 15, 0, 15)));
		editorPanel.add(titlePanel, BorderLayout.NORTH);
		
		titleField = new JTextField();
		titleField.setOpaque(false);
		titleField.setPreferredSize(new Dimension(300, 35));
		titleField.setMaximumSize(new Dimension(300, 35));
		titleField.setMinimumSize(new Dimension(300, 35));
		titleField.setBorder(new CompoundBorder(new MatteBorder(new Insets(0, 0, 1, 0), UI.Colors.color_on_primary), 
				new EmptyBorder(10, 2, 2, 10)));
		titleField.setFont(titleFont);
		titleField.setForeground(UI.Colors.font_secondary);
		titlePanel.add(titleField);
		
		titlePanel.add(Box.createHorizontalGlue());
		
		// adding color chooser
		colorChooser = new ColorChooser(titlePanel);
		titlePanel.add(colorChooser);
		
	}

	private void addTextPanel() {
		textArea = new JTextPane();
		textArea.setBorder(new EmptyBorder(0, 0, 10, 10));
		textArea.setBackground(editorPanel.getBackground());
		textArea.setForeground(UI.Colors.font_selected);
		textArea.setFocusable(true);
		textArea.setFont(textFont);
		
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(10, 15, 2, 2));
		scrollPane.setBackground(null);
		editorPanel.add(scrollPane, BorderLayout.CENTER);
		
		final JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setOpaque(false);
		
	}


	public class TopPanel extends JPanel{
		
		public TopPanel() {
			setBorder(new EmptyBorder(5, 10, 5, 10));
			setBackground(UI.Colors.primary);
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			
			// adding font formatting buttons to the arraylist
			formatButtonList = new ArrayList<FontFormattingButton>();
			
			formatButtonList.add(new FontFormattingButton("Bold", "bold", "bold_selected", new StyledEditorKit.BoldAction()));
			formatButtonList.add(new FontFormattingButton("Italic", "italic", "italic_selected", new StyledEditorKit.ItalicAction()));
			formatButtonList.add(new FontFormattingButton("Underline", "underline", "underline_selected", new StyledEditorKit.UnderlineAction()));

			// adding font formatting buttons
			addFontFormattingButtons();
			
			add(Box.createHorizontalGlue());
			
			// adding cancel button 
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new MainFrame.MainPanel());
					MainFrame.contentPane.repaint();
					MainFrame.contentPane.revalidate();
					
				}
			});
			cancelButton.setBackground(UI.Colors.color_on_primary);
			cancelButton.setForeground(UI.Colors.accent);
			cancelButton.setMaximumSize(new Dimension(70, 28));
			add(cancelButton);
			
			add(Box.createRigidArea(new Dimension(8, 0)));
			
			// adding save button
			JButton saveButton = new JButton("SAVE");
			saveButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String title = titleField.getText();
					String text = textArea.getText();
					int colorIndex = colorChooser.getColorIndex();
					
					if(text!=null && !text.equals(""))	{
						if(isNew)	NoteManager.insertNote(id, title, text, colorIndex, System.currentTimeMillis(), 0);
						else	NoteManager.updateNote(id, title, text, colorIndex, System.currentTimeMillis());
					} 
					else {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								JOptionPane.showMessageDialog(EditorPane.this, "Empty note discarded");
							}
						});
					}
					
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new MainFrame.MainPanel());
					MainFrame.contentPane.repaint();
					MainFrame.contentPane.revalidate();
				}
			});
			saveButton.setBackground(UI.Colors.accent);
			saveButton.setForeground(UI.Colors.font_secondary);
			saveButton.setMaximumSize(new Dimension(70, 28));
			add(saveButton);
			
		}
		
		
		private void addFontFormattingButtons() {
			
			for(FontFormattingButton formatButton : formatButtonList) {
				add(formatButton);
				add(Box.createRigidArea(new Dimension(5, 0)));
			}
			
		}
		
		
	}

}
