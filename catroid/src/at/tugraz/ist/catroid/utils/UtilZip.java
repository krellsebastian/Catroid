/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.tugraz.ist.catroid.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class UtilZip {
	private static final int BUFFER = 2048;
	private static final int QUICKEST_COMPRESSION = 0;

	private static ZipOutputStream zipOutputStream;

	public static boolean writeToZipFile(String[] filePaths, String zipFile) {

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			zipOutputStream = new ZipOutputStream(fileOutputStream);
			zipOutputStream.setLevel(QUICKEST_COMPRESSION);

			for (String filePath : filePaths) {
				File file = new File(filePath);
				if (file.isDirectory()) {
					writeDirToZip(file, file.getName() + "/");
				} else {
					writeFileToZip(file, "");
				}
			}

			zipOutputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private static void writeDirToZip(File dir, String zipEntryPath) throws IOException {
		for (String dirListEntry : dir.list()) {
			File file = new File(dir, dirListEntry);
			if (file.isDirectory()) {
				writeDirToZip(file, zipEntryPath + file.getName() + "/");
				continue;
			}
			writeFileToZip(file, zipEntryPath);
		}
	}

	private static void writeFileToZip(File file, String zipEntryPath) throws IOException {
		byte[] readBuffer = new byte[BUFFER];
		int bytesIn = 0;

		FileInputStream fis = new FileInputStream(file);
		ZipEntry anEntry = new ZipEntry(zipEntryPath + file.getName());
		zipOutputStream.putNextEntry(anEntry);

		while ((bytesIn = fis.read(readBuffer)) != -1) {
			zipOutputStream.write(readBuffer, 0, bytesIn);
		}
		zipOutputStream.closeEntry();
		fis.close();
	}

	public static boolean unZipFile(String zipFile, String outDir) {
		try {
			FileInputStream fin = new FileInputStream(zipFile);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry zipEntry = null;

			BufferedOutputStream dest = null;
			byte data[] = new byte[BUFFER];
			while ((zipEntry = zin.getNextEntry()) != null) {

				if (zipEntry.isDirectory()) {
					File f = new File(outDir + zipEntry.getName());
					f.mkdir();
					zin.closeEntry();
					continue;
				}
				File f = new File(outDir + zipEntry.getName());
				f.getParentFile().mkdirs();
				FileOutputStream fout = new FileOutputStream(f);

				int count;
				dest = new BufferedOutputStream(fout, BUFFER);
				while ((count = zin.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();

			}
			zin.close();

			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}