package entity;

public class Exhibit {
	private int ex_id;
	private String name;
	private String music_addr;
	private int type_id;
	private int hall_id;
	private int favorites;
	private String cover_addr;
	
	public int getex_id(){
		return ex_id;
	}
	public void setex_id(int ex_id){
		this.ex_id=ex_id;
	}
	public String getname(){
		return name;
	}
	public void setname(String name){
		this.name= name;
	}
	public String getmusic_addr(){
		return music_addr;
	}
	public void setmusic_addr(String music_addr){
		this.music_addr=music_addr;
	}
	public int gettype_id(){
		return type_id;
	}
	public void settype_id(int type_id){
		this.type_id = type_id;
	}
	public int gethall_id(){
		return hall_id;
	}
	public void sethall_id(int hall_id){
		this.hall_id= hall_id;
	}
	public int getfavorites(){
		return favorites;
	}
	public void setfavorites(int favorites){
		this.favorites=favorites;
	}
	public String getcover_addr(){
		return cover_addr;
	}
	public void setcover_addr(String cover_addr){
		this.cover_addr = cover_addr;
	}
}
