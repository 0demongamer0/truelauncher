package truelauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.swt.widgets.Display;


public class Launcher {

public static GUI gui;
	
	public static void main(String[] args) {
		setupSWT();
		loadSWT();
		
	      Display display = new Display();
	      gui = new GUI(display);
	      display.dispose();
	}
	
	private static void setupSWT()
	{
		if (!new File(LauncherUtils.getDir() + "/.true-games.org/SWT/swt.jar").exists())
		{
			try {
			new File(LauncherUtils.getDir() + "/.true-games.org/SWT/").mkdirs();
			InputStream is = LauncherUtils.getSWT();
			OutputStream out = new FileOutputStream(new File(LauncherUtils.getDir() + "/.true-games.org/SWT/swt.jar"));
			byte[] buf = new byte[4096];
	        int len;
	        while ((len = is.read(buf)) > 0){out.write(buf, 0, len);}
	        is.close();
	        out.close();
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	private static void loadSWT()
	{
		try {
		  addFile(LauncherUtils.getDir() + "/.true-games.org/SWT/swt.jar");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private static final Class<?>[] parameters = new Class[]{URL.class};
    public static void addFile(String s) throws IOException {
        File f = new File(s);
        addURL(f.toURI().toURL());
    }

    public static void addURL(URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class<?> sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL",parameters);
            method.setAccessible(true);
            method.invoke(sysloader,new Object[]{ u }); 
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }     
    }

}
