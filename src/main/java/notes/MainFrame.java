package notes;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fragments.EmptyPanel;
import utils.DBConnection;
import utils.UI;


public class MainFrame extends JFrame {

	public static JPanel contentPane;
	public static CardLayout cardLayout;
	public static JPanel centerPanel;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new UI();
					new DBConnection();
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public MainFrame() {
		setTitle("Notes");
		ArrayList<Image> app_icons = new ArrayList<Image>();
		try {
			app_icons.add(ImageIO.read(getClass().getResource("/Images/icon_20.png")));
			app_icons.add(ImageIO.read(getClass().getResource("/Images/icon_40.png")));
			app_icons.add(ImageIO.read(getClass().getResource("/Images/icon_60.png")));
			app_icons.add(ImageIO.read(getClass().getResource("/Images/icon_100.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIconImages(app_icons);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new CardLayout());
		setContentPane(contentPane);
		
		contentPane.add("MainPanel", new MainPanel());
		
	}
	
	public static class MainPanel extends JPanel{

		public MainPanel() {
			setLayout(new BorderLayout());
			
			/* adding center panel to mainpanel */
			cardLayout = new CardLayout();
			
			centerPanel = new JPanel();
			centerPanel.setLayout(cardLayout);
			add(centerPanel, BorderLayout.CENTER);
			
			// adding notes pane for different note categories
			centerPanel.add(new NotesPane(0), "All Notes");
			centerPanel.add(new NotesPane(1), "Favorites");
			centerPanel.add(new NotesPane(2), "Work");
			
			// adding empty panel to the centerpanel to show when there are no notes
			centerPanel.add(new EmptyPanel(), "EmptyPane");
			
			/* adding navigation pane to mainpanel */
			NavigationPane navigationPane = new NavigationPane();
			navigationPane.setPreferredSize(new Dimension(200, 0));
			add(navigationPane, BorderLayout.WEST);
			
		}
	}
}
