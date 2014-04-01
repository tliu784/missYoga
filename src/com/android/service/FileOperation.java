package com.android.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.os.Environment;

public class FileOperation {
	
	public static final String MY_PATH="/myhealthmate";
	/**
	 * save object into file
	 * 
	 * @param obj
	 * @param dest
	 * @return object can be type casted
	 */
	public static boolean save(Object obj, String dest, Context context) {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = context.openFileOutput(dest, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject((Object) obj);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	
	public static File createExternalFile(String filename){
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + MY_PATH);
		myDir.mkdirs();
		File file = new File(myDir, filename);
		return file;
	}

	public static File copytoExternal(String internalFile, String externalFile, Context context) {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + MY_PATH);
		myDir.mkdirs();
		File file = new File(myDir, externalFile);
		if (file.exists())
			file.delete();
		try {
			FileInputStream fis = context.openFileInput(internalFile);
			
			FileOutputStream fos = new FileOutputStream(file);
            byte[] buff=new byte[1024];
            int len;
            while((len=fis.read(buff))>0){
                fos.write(buff,0,len);
            }
            fis.close();
            fos.close();

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
		return file;

	}

	/**
	 * read object from a file
	 * 
	 * @param src
	 * @return object, can be type casted
	 */
	public static Object read(String src, Context context) {
		Object obj;
		FileInputStream fis;
		ObjectInputStream ois;
		try {
			fis = context.openFileInput(src);
			ois = new ObjectInputStream(fis);
			obj = (Object) ois.readObject();
			ois.close();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException ex) {
			return null;
		}
		return obj;
	}

	public static boolean delete(String src, Context context) {
		return context.deleteFile(src);
	}
}
