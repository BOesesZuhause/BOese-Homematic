package de.bo.aid.boese.homematic.test.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class IOUtil {
	
	/**
	 * Read dump.
	 *
	 * @return the object
	 */
	public Object readDump(){
		ObjectInputStream o  = null;
		  try {
			o = new ObjectInputStream(new FileInputStream("response"));
			try {
				Object out = o.readObject();
				o.close();
				return  out;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			o.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return null;
	}

}
