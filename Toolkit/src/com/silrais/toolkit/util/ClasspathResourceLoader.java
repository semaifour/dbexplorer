package com.silrais.toolkit.util;

public class ClasspathResourceLoader extends ClassLoader {
	public ClasspathResourceLoader(Object caller) {
		super(caller.getClass().getClassLoader());
	}
}
