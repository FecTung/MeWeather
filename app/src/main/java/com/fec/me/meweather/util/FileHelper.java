package com.fec.me.meweather.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileHelper {

	private FileHelper(){
		throw new UnsupportedOperationException("FileHelper cannot be instantiated!");
	}

	/**
	 * 判断SDCard是否可用
	 *
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取当前程序的存储地址
	 * @param context
	 * @return
	 */
	public static String getFilesPath(Context context){
		return context.getFilesDir().getPath();
	}

	/**
	 * 获取SD卡路径
	 *
	 * @return
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}

	/**
	 * 在{path}下创建{fileName}文件
	 * @param path
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String path, String fileName) throws IOException {
		File file = new File(path+"//"+ fileName);
		if (!file.exists()){
			file.createNewFile();
		} else {
			Log.w("warning : ", fileName + "is already existed!");
		}
		return file;
	}

	/**
	 * 删除{path}下的{filename}文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static boolean deleteSDFile(String path, String fileName){
		File file = new File(path+"//"+fileName);
		if (file == null || !file.exists() || file.isDirectory()) {
			return false;
		}
		return file.delete();
	}

	/**
	 * 在{path}路径下的{fileName}中写入{str}
	 * @param path
	 * @param str
	 * @param fileName
	 */
	public static void writeFile(String path, String str,String fileName){
		try {
//			FileWriter fw = new FileWriter(SD_PATH+"//"+fileName);
//			File file = new File(SD_PATH+"//"+fileName);
//			fw.write(str);
//			FileOutputStream fos = new FileOutputStream(file);
//			DataOutputStream dos = new DataOutputStream(fos);
//			dos.writeShort(2);
//			dos.writeUTF("UTF-8");
//			dos.close();
//			fos.close();
//			fw.flush();
//			fw.close();

			//使用BufferedWriter实现
			File file = new File(path+"//"+fileName);
//			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");		// 覆盖文件
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"); // 追加文件
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(str);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取{path}路径下{fileName}中的内容
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String readFile(String path, String fileName){
		String fileContent = null;
		try {
			File file = new File(path+"//"+fileName);
			if (file.isFile() && file.exists()) {
				fileContent = "";
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), "UTF-8");
				BufferedReader reader = new BufferedReader(read);
				String line;
				while ((line = reader.readLine()) != null) {
					fileContent += line;
				}
				read.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
}
