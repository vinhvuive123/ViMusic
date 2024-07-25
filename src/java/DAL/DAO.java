/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.*;
import org.checkerframework.checker.units.qual.g;

/**
 *
 * @author admin
 */
public class DAO {
    private Connection con;
	private String status;
	private List<Artist> art;
	private List<Album> alb;
	private List<Genre> gen;
	private List<Song> songList;

	public DAO() {
		try {
			con = new DBContext().getConnection();
		} catch (Exception e) {
			status = "Erorr connection " + e.getMessage();
		}
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Artist> getArt() {

		return art;
	}

	public void setArt(List<Artist> art) {
		this.art = art;
	}

	public List<Album> getAlb() {
		return alb;
	}

	public void setAlb(List<Album> alb) {
		this.alb = alb;
	}

	public List<Genre> getGen() {
		// genre
               
		gen = new ArrayList<>();
		String sql = "select * from Genres_HE173311";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
                       
			while (rs.next()) {
				gen.add(new Genre(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}
		return gen;
	}

	public void setGen(List<Genre> gen) {
		this.gen = gen;
	}

	public List<Song> getSongList() {
		return songList;
	}

	public void setSongList(List<Song> song) {
		this.songList = song;
	}

	public void loadDB() {
		//artist
		art = new ArrayList<>();
		String sql = "select * from Artists_HE173311";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				art.add(new Artist(rs.getInt("Id"), rs.getString("Name"), rs.getString("Biography"), rs.getInt("Use_id")));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_Artist: " + e.getMessage();
		}
		//album
		alb = new ArrayList<>();
		sql = "select * from Albums_HE173311";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				alb.add(new Album(rs.getInt("Id"), rs.getString("Title"), rs.getString("Img"), rs.getDate("Relase_date"), rs.getString("Playlist_type"), getArtistById(rs.getInt("Artist_Id")), rs.getString("Description")));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_Albums: " + e.getMessage();
		}
		// genre
		gen = new ArrayList<>();
		sql = "select * from Genres_HE173311";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				gen.add(new Genre(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description")));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}
		//song
		songList = new ArrayList<>();
		sql = "select * from Songs_HE173311 order by Title";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				songList.add(new Song(rs.getInt("Id"), rs.getString("Title"), rs.getInt("Duration"), rs.getInt("Likes"), rs.getString("Src"), rs.getInt("Listens"), rs.getString("Description"), getGenreById(rs.getInt("Genre_id")), getAlbumById(rs.getInt("Album_Id"))));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}

	}

	public List<Album> getNext3Albums(int amount) {
		//album
		List<Album> alb = new ArrayList<>();
		String sql = "select * from Albums_HE173311 order by Id offset 0 rows fetch next ? rows only";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, amount);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				alb.add(new Album(rs.getInt("Id"), rs.getString("Title"), rs.getString("Img"), rs.getDate("Relase_date"), rs.getString("Playlist_type"), getArtistById(rs.getInt("Artist_Id")), rs.getString("Description")));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_Albums: " + e.getMessage();
		}
		return alb;
	}

	public Artist getArtistById(int id) {
		//artist
		String sql = "select * from Artists_HE173311 where Id=?";
		Artist a;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				a = new Artist(rs.getInt("Id"), rs.getString("Name"), rs.getString("Biography"), rs.getInt("Use_id"));
				return a;
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_Artist By Id: " + e.getMessage();
		}
		return null;
	}

	public Genre getGenreById(int id) {
		// genre
		Genre gen;
		String sql = "select * from Genres_HE173311 where Id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				gen = new Genre(rs.getInt("Id"), rs.getString("Title"), rs.getString("Description"));
				return gen;
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}
		return null;
	}

	public Album getAlbumById(int id) {
		Album a;
		String sql = "select * from Albums_HE173311 where Id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				a = new Album(rs.getInt("Id"), rs.getString("Title"), rs.getString("Img"), rs.getDate("Relase_date"), rs.getString("Playlist_type"), getArtistById(rs.getInt("Artist_Id")), rs.getString("Description"));
				return a;
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_Albums: " + e.getMessage();
		}
		return null;
	}

	public List<Song> getListSongByGenId(int id) {
		//song
		List<Song> list = new ArrayList<>();
		if (id != 0) {

			String sql = "select * from Songs_HE173311 where Genre_id=? ";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					list.add(new Song(rs.getInt("Id"), rs.getString("Title"), rs.getInt("Duration"), rs.getInt("Likes"), rs.getString("Src"), rs.getInt("Listens"), rs.getString("Description"), getGenreById(rs.getInt("Genre_id")), getAlbumById(rs.getInt("Album_Id"))));
				}

			} catch (Exception e) {
				status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
			}

		} else {
			list = getSongList();
		}
		return list;
	}

	public List<Song> getListSongByAlbumId(int id) {
		//song
		List<Song> list = new ArrayList<>();

		String sql = "select * from Songs_HE173311 where Album_Id=? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Song(rs.getInt("Id"), rs.getString("Title"), rs.getInt("Duration"), rs.getInt("Likes"), rs.getString("Src"), rs.getInt("Listens"), rs.getString("Description"), getGenreById(rs.getInt("Genre_id")), getAlbumById(rs.getInt("Album_Id"))));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}
		return list;
	}

	public void updateFavoritesSong(int type, int userId, int songId) {
		//insert
		if (type == 1) {
			String sql = "insert into Favorites_HE173311 values (?,?)";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, songId);
				ps.execute();

			} catch (Exception e) {
				status = "Erorr connection at loadDB_updateFaSong/insert: " + e.getMessage();

			}

		} //delete
		else if (type == 2) {
			String sql = "delete from Favorites_HE173311 where User_id = ? and Song_id = ?";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, songId);
				ps.execute();

			} catch (Exception e) {
				status = "Erorr connection at loadDB_updateFaSong/delete: " + e.getMessage();
			}
		}
	}

	public List<Integer> getListSongByFavorites(int userId) {
		//song
		List<Integer> list = new ArrayList<>();
		String sql = "select Song_id from Favorites_HE173311 where User_id =?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("Song_id"));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}
		return list;
	}

	public List<Song> getListSongFavoritesByUserId(int userId) {
		//song
		List<Song> list = new ArrayList<>();

		String sql = "SELECT s.Id,s.Title,s.Duration,s.Likes,s.Src,s.Listens,s.Description,s.Genre_id,s.Album_Id\n"
			+ "FROM Songs_HE173311 s\n"
			+ "Join Favorites_HE173311 f on f.Song_id = s.Id\n"
			+ "where User_id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Song(rs.getInt("Id"), rs.getString("Title"), rs.getInt("Duration"), rs.getInt("Likes"), rs.getString("Src"), rs.getInt("Listens"), rs.getString("Description"), getGenreById(rs.getInt("Genre_id")), getAlbumById(rs.getInt("Album_Id"))));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}
		return list;
	}

	public void searchByKey(String key) {
		//artist
		art = new ArrayList<>();
		String sql = "select * from Artists_HE173311 where Name like CONCAT('%',?,'%')";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				art.add(new Artist(rs.getInt("Id"), rs.getString("Name"), rs.getString("Biography"), rs.getInt("Use_id")));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_Artist: " + e.getMessage();
		}
		//album
		alb = new ArrayList<>();
		sql = "select * from Albums_HE173311 where Title like CONCAT('%',?,'%')";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				alb.add(new Album(rs.getInt("Id"), rs.getString("Title"), rs.getString("Img"), rs.getDate("Relase_date"), rs.getString("Playlist_type"), getArtistById(rs.getInt("Artist_Id")), rs.getString("Description")));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_Albums: " + e.getMessage();
		}

		//song
		songList = new ArrayList<>();
		sql = "select * from Songs_HE173311 where Title like CONCAT('%',?,'%')";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				songList.add(new Song(rs.getInt("Id"), rs.getString("Title"), rs.getInt("Duration"), rs.getInt("Likes"), rs.getString("Src"), rs.getInt("Listens"), rs.getString("Description"), getGenreById(rs.getInt("Genre_id")), getAlbumById(rs.getInt("Album_Id"))));
			}

		} catch (Exception e) {
			status = "Erorr connection at loadDB_ASongs: " + e.getMessage();
		}

	}

	public String getImgSrcByArtId(int artId) {
		String src = "";
		String sql = "select a.img\n"
			+ "from Accounts_HE173311 a\n"
			+ "join Artists_HE173311 ar on ar.Use_id =a.Id\n"
			+ "where ar.Id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, artId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				src = rs.getString("img");
			}

		} catch (Exception e) {
			status = "Erorr connection at getImg: " + e.getMessage();
		}
		return src;

	}
     
}
