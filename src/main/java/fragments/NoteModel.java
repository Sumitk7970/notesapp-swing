package fragments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import notes.EditorPane;
import notes.MainFrame;
import utils.NoteManager;
import utils.UI;

public class NoteModel extends JPanel {
	private int id;
	private String noteTitle, noteText;
	private long time_modified;
	private final Font titleFont = UI.Fonts.robotoBold.deriveFont(16f), textFont = UI.Fonts.robotoRegular.deriveFont(14f),
			timeFont = UI.Fonts.robotoRegular.deriveFont(12f);
	public Color defaultColor, hoverColor, bgColor;
	private int cornerRadius = 20;
	private MouseAdapter adapter;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics2d.setColor(bgColor);
		graphics2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
		
	}

	public NoteModel(Note note, final JPanel parent) {
		this.id = note.getId();
		this.noteTitle = note.getTitle();
		this.noteText = note.getText();
		this.time_modified = note.getTime_modified();

		hoverColor = UI.Colors.noteColors[note.getColorIndex()];
		defaultColor = new Color(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), 200);
		setBackground(defaultColor);
		
		// adding popup menu 
		final JPopupMenu popupMenu = new JPopupMenu();
		
		// adding add to favorites button to popup menu
		JMenuItem fav = new JMenuItem("Add to favorites");
		fav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NoteManager.addToCategory(NoteModel.this.id, 1);
			}
		});
		popupMenu.add(fav);
		
		// adding add to work category button to popup menu
		JMenuItem work = new JMenuItem("Add to Work category");
		work.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NoteManager.addToCategory(NoteModel.this.id, 2);
			}
		});
		popupMenu.add(work);
		
		// adding delete button to the popup menu
		popupMenu.addSeparator();
		JMenuItem delete = new JMenuItem("Delete note");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NoteModel.this.setVisible(false);
				NoteManager.deleteNote(parent, NoteModel.this.id);
			}
		});
		popupMenu.add(delete);
		
		
		setOpaque(false);
		setPreferredSize(new Dimension(200, 200));
		setMaximumSize(new Dimension(200, 200));
		setLayout(new BorderLayout(0, 5));
		setBorder(new EmptyBorder(12, 12, 12, 12));
		
		// creating mouselistener
		adapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1) {
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.add(new EditorPane(NoteModel.this.id, false));
					MainFrame.contentPane.repaint();
					MainFrame.contentPane.revalidate();
				} else if(e.getButton()==MouseEvent.BUTTON3) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(hoverColor);
				repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(defaultColor);
				repaint();
			}
		};
		
		addMouseListener(adapter);
		
		
		// adding jlabel to display title of note
		addTitleLabel();
		
		
		// adding Jtextarea to display preview of note
		addTextArea();
		
		// adding bottom panel to display time modified and some buttons
		addBottomPanel();

	}
	
	public String getTitle() {
		return noteTitle;
	}
	
	public String getText() {
		return noteText;
	}
	
		private void addTitleLabel() {
			JLabel titleLabel = new JLabel("Title");
			titleLabel.setOpaque(false);
			titleLabel.setBackground(UI.Colors.transparent);
			titleLabel.setForeground(UI.Colors.font_secondary);
			titleLabel.setFont(titleFont);
			titleLabel.setText(noteTitle);
			titleLabel.setHorizontalAlignment(SwingConstants.LEADING);
			add(titleLabel, BorderLayout.NORTH);
		}
	
	private void addTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(noteText);
		textArea.setForeground(UI.Colors.font_selected);
		textArea.setBackground(UI.Colors.transparent);
		textArea.setBorder(null);
		textArea.setHighlighter(null);
		textArea.setEditable(false);
		textArea.setFont(textFont);
		add(textArea, BorderLayout.CENTER);
		textArea.addMouseListener(adapter);
	}
	
	private void addBottomPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		add(panel, BorderLayout.SOUTH);
		
		
		// adding time_modified 
		LocalDateTime localDateTime = Instant.ofEpochMilli(time_modified).atZone(ZoneId.systemDefault()).toLocalDateTime();

		String timeModified;
		if(localDateTime.getDayOfMonth() == LocalDate.now().getDayOfMonth())
			timeModified = localDateTime.format(DateTimeFormatter.ofPattern("h:mm a")).toUpperCase();
		else
			timeModified = localDateTime.format(DateTimeFormatter.ofPattern("MMM d"));
			
		JLabel label = new JLabel(timeModified);
		label.setFont(timeFont);
		panel.add(label);

		// creating a horizontal glue to add new items to the right side
		panel.add(Box.createHorizontalGlue());
		
	}

	@Override
	public void setBackground(Color bgColor) {
		this.bgColor = bgColor;
	}
	
	
	public Color getNoteColor() {
		return defaultColor;
	}

	public void setNoteColor(int colorIndex) {
		hoverColor = UI.Colors.noteColors[colorIndex];
		defaultColor = new Color(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), 200);
		setBackground(defaultColor);
	}

	public int getCornerRadius() {
		return cornerRadius;
	}

	public void setCornerRadius(int cornerRadius) {
		this.cornerRadius = cornerRadius;
	}

	public int getId() {
		return id;
	}

}
