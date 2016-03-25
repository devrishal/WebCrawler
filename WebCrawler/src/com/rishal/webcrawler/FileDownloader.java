package com.rishal.webcrawler;

/*
 * File to process the url and download the files and store it in created folder.
 * 
 */

import java.io.*;
import java.net.*;

public class FileDownloader {

	/*
	 * This method
	 * 
	 * @param url -String value which will be a url
	 * 
	 * @param count -Value which will be used for the file names if they are
	 * same on the web page. So that they can be of unique type.
	 */
	public void storingInFile(String url, int count) {
		/*
		 * Defining variables which will be used for handling the input url and
		 * processing it for creating folders and files.
		 */
		URL inputurl;
		InputStream inputStream = null;
		DataInputStream dataInputStream;
		String data;

		if (!url.equals(null) && !url.equals("")) {
			if (!url.endsWith("/")) {
				url = url + "/";
			}

			try {
				inputurl = new URL(url);
				String hostName = inputurl.getHost();
				String pathName = inputurl.getPath();
				int start = pathName.indexOf("/");
				int stop = pathName.lastIndexOf("/");
				String end = pathName.substring(start, stop);
				// create directory
				boolean folder = (new File(hostName + end)).mkdirs();

				int fileStart = stop + 1;
				int fileEnd = pathName.length();
				// create fileName
				String fileName = pathName.substring(fileStart, fileEnd);

				String dir = hostName + end;
				String information = dir + "/" + fileName;
				//Creating input stream which will connect and download the data and later will use the same for storing it into file
				inputStream = inputurl.openStream();
				dataInputStream = new DataInputStream(new BufferedInputStream(
						inputStream));
				//Creating file
				File file = new File(information);
				//Checking if the file already exists in the path. If yes then rename it using the count variable.
				if (file.exists() && !file.isDirectory()) {
					file = new File(information + count);
				}
				PrintWriter printWriter = null;
				try {
					// Storing the data into the file
					printWriter = new PrintWriter(file);
					
				} catch (FileNotFoundException e) {
					try{
					file = new File(information + count);
					printWriter = new PrintWriter(file);
					}
					catch(FileNotFoundException ex)
					{
						ex.printStackTrace();
					}

				}

				while ((data = dataInputStream.readLine()) != null) {

					printWriter.write(data);

				}
			} catch (MalformedURLException me) {
				System.err.println("Faulty- a MalformedURLException happened.");
				me.printStackTrace();
				// System.exit(2);
			} catch (IOException ie) {
				System.err.println("Faulty- an IOException happened.");
				ie.printStackTrace();
				// System.exit(3);
			}
			catch(Exception e)
			{
				//do nothing 
			}
			finally {
				try {
					inputStream.close();
				} catch (IOException ioe) {
				}
			}

		}
	}
}