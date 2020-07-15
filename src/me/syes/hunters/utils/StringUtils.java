package me.syes.hunters.utils;

public class StringUtils {
	
	public static String getFixedCaseName(String name) {
		String fullname = name.toLowerCase().replace("_", " ");
		String[] split1 = fullname.split(" ");
		String finalname = "";
		for(int i = 0; i < split1.length; i++) {
			String[] split2 = split1[i].split("");
			for(int z = 0; z < split2.length; z++) {
				if(z == 0) {
					finalname = finalname + split2[z].toUpperCase();
					continue;
				}
				finalname = finalname + split2[z];
			}
			if(i < split1.length-1)
				finalname = finalname + " ";
		}
		return finalname;
	}
	
}

