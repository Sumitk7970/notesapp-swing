package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;

import javax.swing.UIManager;

public class UI {
	
	public UI() {
		new Colors();
		new Fonts();
		
		try {
			UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
			UIManager.put("Button.arc", 20);
			UIManager.put("TitlePane.background", Colors.color_on_primary);
			UIManager.put("RootPane.background", Colors.color_on_primary);
			UIManager.put("Button.borderWidth", 0);
			UIManager.put("Button.focusedBorderColor", null);
			UIManager.put("Button.innerFocusWidth", 0);
			UIManager.put("ScrollBar.thumb", Colors.color_on_primary.brighter());
			UIManager.put("ScrollBar.track", Colors.primary.brighter());
			UIManager.put("ScrollBar.thumbArc", 10);
			UIManager.put("ScrollBar.trackArc", 10);
			UIManager.put("ScrollBar.thumbInsets", new Insets(0, 1, 0, 1));
			UIManager.put("ToolTip.background", Colors.color_on_primary);
			UIManager.put("PopupMenu.background", Colors.color_on_primary);
			UIManager.put("PopupMenu.borderColor", Colors.secondary);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class Colors {
		
		public static Color primary, secondary, color_on_primary,transparent, accent, 
		font_primary, font_selected, font_secondary;
		public static Color[] noteColors = new Color[10];
		
		public Colors() {
				
				// background colors
				primary = new Color(26, 28, 30);
				secondary = new Color(17, 19, 21);
				color_on_primary = new Color(39, 42, 48);
				transparent = new Color(0, 0, 0, 0);
				accent = new Color(0x2A86FF);
				
				// font colors
				font_primary = new Color(126, 130, 133);
				font_selected = new Color(218, 220, 222);
				font_secondary = new Color(253, 253, 253);
				
				
				// note colors
				noteColors[0] = new Color(39, 42, 48);
				noteColors[1] = new Color(124, 33, 26);
				noteColors[2] = new Color(22, 101, 44);
				noteColors[3] = new Color(124, 104, 5);
				noteColors[4] = new Color(123, 77, 2);
				noteColors[5] = new Color(123, 39, 58);
				noteColors[6] = new Color(103, 61, 120);
				noteColors[7] = new Color(58, 95, 123);
				noteColors[8] = new Color(115, 87, 83);
				noteColors[9] = new Color(98, 81, 58);
				
		}
	}
	
	public static class Fonts {
		public static Font robotoRegular, robotoBold;
		
		public Fonts() {
			try {
				robotoRegular = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().
						getResourceAsStream("font/Roboto-Regular.ttf"));
				
				robotoBold = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().
						getResourceAsStream("font/Roboto-Bold.ttf"));
				
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(robotoRegular);
				ge.registerFont(robotoBold);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}
}
