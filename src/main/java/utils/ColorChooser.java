package utils;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ColorChooser extends JLabel{
	private int size = 20, colorIndex=0;
	private JPopupMenu popupMenu;
	private Dimension dimension = new Dimension(22, 22);
	private JPanel panel;
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// filling circle
		graphics2d.setColor(UI.Colors.noteColors[colorIndex]);
		graphics2d.fillRoundRect(0, 0, size, size, size, size);
		
		// drawing border around the circle
		graphics2d.setColor(UI.Colors.noteColors[colorIndex].brighter());
		graphics2d.drawRoundRect(0, 0, size, size, size, size);
		
		super.paintComponent(g);
	}
	
	public ColorChooser(JPanel panel) {
		this.panel = panel;
		
		setOpaque(false);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		
		popupMenu = new JPopupMenu();
		popupMenu.setLayout(new FlowLayout());
		popupMenu.setBackground(UI.Colors.primary);
		popupMenu.setBorder(null);
		popupMenu.setPreferredSize(new Dimension(150, 70));
		
		for(int i=0;i<10;i++) {
			popupMenu.add(new NoteColor(i));
		}
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX()-150, e.getY());
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		
	}
	
	public int getColorIndex() {
		return colorIndex;
	}
	
	public void setColor(int index) {
		ColorChooser.this.colorIndex = index;
		ColorChooser.this.repaint();
		
		panel.setBorder(new CompoundBorder(new MatteBorder(2, 0, 0, 0, UI.Colors.noteColors[index]),
				new EmptyBorder(5, 15, 0, 15)));
		panel.repaint();
		
		popupMenu.setVisible(false);
	}
	
	
	private class NoteColor extends JLabel {
		private int size = 20, colorIndex=0;
		
		@Override
		protected void paintComponent(Graphics g) {
			
			Graphics2D graphics2d = (Graphics2D) g;
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			graphics2d.setColor(UI.Colors.noteColors[colorIndex]);
			graphics2d.fillRoundRect(0, 0, size, size, size, size);
			
			super.paintComponent(g);
		}
		
		public NoteColor(int colorIndex) {
			this.colorIndex = colorIndex;
			setPreferredSize(dimension);
			setMaximumSize(dimension);
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setColor(getColorIndex());
				}
			});
		}
		
		private int getColorIndex() {
			return colorIndex;
		}
		
	}

}
