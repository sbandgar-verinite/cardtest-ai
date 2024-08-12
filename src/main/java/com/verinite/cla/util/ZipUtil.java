package com.verinite.cla.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {
	public static void unzip(File zipFile, File destDir) throws IOException {
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		try (ZipFile zip = new ZipFile(zipFile)) {
			Enumeration<? extends ZipEntry> entries = zip.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				File file = new File(destDir, entry.getName());

				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					file.getParentFile().mkdirs();

					try (InputStream in = zip.getInputStream(entry); OutputStream out = new FileOutputStream(file)) {
						byte[] buffer = new byte[1024];
						int len;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
					}
				}
			}
		}
	}
}
