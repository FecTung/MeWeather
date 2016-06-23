package com.fec.me.meweather.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: FecT
 * Time  : 22:19, 06.22.2016
 */
public class MenuUtils {

	private Context context;
	private String fileName;
	private String filesPath;

	public MenuUtils(Context context, String fileName) {
		this.context = context;
		this.fileName = fileName;
		filesPath = FileHelper.getFilesPath(context);
	}

	/**
	 * codeAndName[0] CityName, codeAndName[1] CityCode
	 * @param codeAndName
	 */
	public void addMenuItem(String [] codeAndName){
		FileHelper.writeFile(filesPath, codeAndName[0]+","+codeAndName[1]+";", fileName);
	}

	/**
	 * 读取files文件下的fileName文件中的内容，并由List<Map<String, String>>返回
	 * @return
	 */
	public List<String[]> readMenu(){
		List<String[]> menuList = new ArrayList<String[]>();
		String[] menuItemStr;
		String menuStr = FileHelper.readFile(filesPath, fileName);

		if (!TextUtils.isEmpty(menuStr)){
			menuItemStr = menuStr.split(";");
			for (String item : menuItemStr){
				String[] codeAndName = item.split(",");
				menuList.add(codeAndName);
			}
		}
		return menuList;
	}
}
