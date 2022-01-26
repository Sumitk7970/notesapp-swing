package utils;

import java.awt.Container;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import fragments.Note;

public class NoteManager {
	
	// method to get a new id when new note is created 
	// This helps to recognize notes
	public static int getNewId() {
		String query = "SELECT id FROM notes ORDER BY id DESC LIMIT 1";
		Connection con = DBConnection.connect();
		int id = 0;
		try {
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			if(!rs.isClosed())	id = rs.getInt(1)+1;
			rs.close();
			st.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	// get the data of a note
	public static Note getNote(int id) {
		String query = "SELECT * FROM notes WHERE id = "+id;
		Connection con = DBConnection.connect();
		try {
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			rs.next();
			
			Note note = new Note(rs.getInt(1), rs.getInt(4), rs.getString(2), rs.getString(3), rs.getLong(5), rs.getInt(6));
			
			rs.close();
			st.close();
			con.close();
			
			return note;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// get data of all notes
	public static ArrayList<Note> getAllNotes(int category) {
		String query;
		if(category==0) query = "SELECT * FROM notes ORDER BY date_modified DESC";
		else query = "SELECT * FROM notes WHERE category = "+category + " ORDER BY date_modified DESC";
		Connection con = DBConnection.connect();
		try {
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			ArrayList<Note> notes = new ArrayList<Note>();
			while(rs.next()) {
				notes.add(new Note(rs.getInt(1), rs.getInt(4), rs.getString(2), rs.getString(3), rs.getLong(5), rs.getInt(6)));
			}
			
			rs.close();
			st.close();
			con.close();
			
			return notes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void insertNote(int id, String title, String text, int colorIndex, long date_modified, int category) {
		String query = "INSERT INTO notes(id, title, text, color, date_modified, category) VALUES(?,?,?,?,?,?)";
		Connection con = DBConnection.connect();
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			st.setString(2, title);
			st.setString(3, text);
			st.setInt(4, colorIndex);
			st.setLong(5, date_modified);
			st.setInt(6, category);
			st.execute();
			
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updateNote(int id, String title, String text, int colorIndex, long date_modified) {
		String query = "UPDATE notes SET title=?, text=?, color=?, date_modified=? WHERE id = "+id;
		Connection con = DBConnection.connect();
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, title);
			ps.setString(2, text);
			ps.setInt(3, colorIndex);
			ps.setLong(4, date_modified);
			ps.executeUpdate();
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Update note unsuccessful\n" + e.getMessage());
		}
		
	}
	
	public static void addToCategory(int id, int category) {
		String query = "UPDATE notes SET category=? WHERE id = "+id;
		Connection con = DBConnection.connect();
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, category);
			ps.executeUpdate();
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Add to starred unsuccesful\n" + e.getMessage());
		}
	}
	
	public static void deleteNote(Container panel, int id) {
		String query = "DELETE FROM notes WHERE id = "+id;
		Connection con = DBConnection.connect();
		try {
			Statement st = con.createStatement();
			st.execute(query);
			
			st.close();
			con.close();
			
			panel.repaint();
			JOptionPane.showMessageDialog(panel, "Note deleted");
		} catch (Exception e) {
			System.out.println("Delete unsuccessful\n" + e.getMessage());
		}
		
	}


}
