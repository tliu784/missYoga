package com.android.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class FileOperation {

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
	
	public static boolean delete(String src, Context context){
		return context.deleteFile(src);
	}
}
