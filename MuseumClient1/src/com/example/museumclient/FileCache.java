package com.example.museumclient;

import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileCache {
    
    private File cacheDir;
    public static String FirstFolder = "MuseumClient";// 一级目录
	public static String SecondFolder = "ListViewImage";// 二级目录
    private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ FirstFolder
			+ File.separator;
	private final static String Second_PATH = ALBUM_PATH + SecondFolder
			+ File.separator;
    public FileCache(Context context){
    	//找一个用来缓存图片的路径
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
        	File dirFirstFile = new File(ALBUM_PATH);// 新建一级主目录
			if (!dirFirstFile.exists()) {// 判断文件夹目录是否存在
				dirFirstFile.mkdir();// 如果不存在则创建
			}
			cacheDir = new File(Second_PATH);// 新建二级主目录
			if (!cacheDir.exists()) {// 判断文件夹目录是否存在
				cacheDir.mkdir();// 如果不存在则创建

			}
			//cacheDir=new File(Environment.getExternalStorageDirectory(),"LazyList");
        }
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getFile(String url){
        
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}
