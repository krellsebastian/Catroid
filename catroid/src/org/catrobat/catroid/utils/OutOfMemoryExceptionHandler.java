/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.utils;

import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class OutOfMemoryExceptionHandler implements Thread.UncaughtExceptionHandler {

	// Code from Stackoverflow to get heap after OutOfMemoryErrors occurs
	// http://stackoverflow.com/questions/6131769/is-there-a-way-to-have-an-android-process-produce-a-heap-dump-on-an-outofmemorye
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e("UncaughtException", "Got an uncaught exception: " + ex.toString());
		if (ex.getClass().equals(OutOfMemoryError.class)) {
			try {
				android.os.Debug.dumpHprofData(Environment.getExternalStorageDirectory().getPath() + "/dump.hprof");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ex.printStackTrace();
	}
}
