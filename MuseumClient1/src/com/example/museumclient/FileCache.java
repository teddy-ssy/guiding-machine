package com.example.museumclient;

import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileCache {
    
    private File cacheDir;
    public static String FirstFolder = "MuseumClient";// һ��Ŀ¼
	public static String SecondFolder = "ListViewImage";// ����Ŀ¼
    private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ FirstFolder
			+ File.separator;
	private final static String Second_PATH = ALBUM_PATH + SecondFolder
			+ File.separator;
    public FileCache(Context context){
    	//��һ����������ͼƬ��·��
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
        	File dirFirstFile = new File(ALBUM_PATH);// �½�һ����Ŀ¼
			if (!dirFirstFile.exists()) {// �ж��ļ���Ŀ¼�Ƿ����
				dirFirstFile.mkdir();// ����������򴴽�
			}
			cacheDir = new File(Second_PATH);// �½�������Ŀ¼
			if (!cacheDir.exists()) {// �ж��ļ���Ŀ¼�Ƿ����
				cacheDir.mkdir();// ����������򴴽�

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
