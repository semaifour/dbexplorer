package com.silrais.toolkit.util;

public class SimpleStringUtil {

	public static String join(String delimiter, String...tokens) {
		StringBuilder builder = new StringBuilder(tokens[0]);
		int index = 1;
		while (index < tokens.length) {
			builder.append(delimiter).append(tokens[index]);
			index++;
		}
		return builder.toString();
	}
}
