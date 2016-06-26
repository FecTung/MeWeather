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
	 * 增加菜单项，并排除重复项
	 * codeAndName[0] CityName, codeAndName[1] CityCode
	 * @param codeAndName
	 */
	public void addMenuItem(String [] codeAndName) {
		boolean flag = false;
		String codeName = codeAndName[0]+","+codeAndName[1];
		String menuStr = FileHelper.readFile(filesPath, fileName);

		if (!TextUtils.isEmpty(menuStr)){
			String[]	menuItemStr = menuStr.split(";");
			for (String item : menuItemStr) {
				if (item.equals(codeName)){
					flag = true;
				} else {
					continue;
				}
			}
			if (!flag){FileHelper.writeFile(filesPath, codeAndName[0] + "," + codeAndName[1] + ";", fileName);}
		} else {
			FileHelper.writeFile(filesPath, codeAndName[0] + "," + codeAndName[1] + ";", fileName);
		}
	}

	/**
	 * 读取菜单内容，在APP运行时动态配置
	 * 读取files文件下的fileName文件中的内容，并由List<String[]>返回
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

	public String findCodeByName(String cityName){
		String cityCode = null;
		String menuStr = FileHelper.readFile(filesPath, fileName);
		if (!TextUtils.isEmpty(menuStr)){
			String[]	menuItemStr = menuStr.split(";");
			for (String item : menuItemStr) {
				if (item.contains(cityName)){
					//101221702 example
					cityCode = item.substring(0,9);
				}
			}
		}
		return cityCode;
	}

}
