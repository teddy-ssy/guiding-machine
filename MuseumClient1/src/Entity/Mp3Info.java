package Entity;

import java.io.Serializable;

public class Mp3Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String str;
	private int ex_id;
	private String name;
	public String getname(){
		return name;
	}
	public void setname(String name){
		this.name = name;
	}
	public String getStr(){
		return str;
	}
	public void setStr(String str){
		this.str = str;
	}
	public void setex_id(int ex_id){
		this.ex_id=ex_id;
	}
	public int getex_id(){
		return ex_id;
	}
}
