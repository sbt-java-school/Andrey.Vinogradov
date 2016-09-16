package ru.sbt.home.task03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
	public static void main(String[] args) {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
			for (String content = bufferedReader.readLine(); content != null; content = bufferedReader.readLine()) {
				try {
					System.out.println(readContent(content));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readContent(String url) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		
		for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
			stringBuilder.append(s).append("\n");
		}
		
		bufferedReader.close();
		
		return stringBuilder.toString();
	}
}