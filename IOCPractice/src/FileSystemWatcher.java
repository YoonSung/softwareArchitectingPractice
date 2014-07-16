import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileSystemWatcher extends Thread{
	
	private String WATCH_DIRECTORY = "." + File.separator + "etc";
	private String WATCH_FILE = "setting.xml";
	
	private boolean isOperation = true;
	
	private static FileSystemWatcher instance = new FileSystemWatcher();
	
	private FileSystemWatcher(){}
	
	static FileSystemWatcher getInstance() {
		return instance;
	}
	
	void watchStop() {
		isOperation = false;
	}
	
	boolean isOperation() {
		return isOperation;
	}
	
	boolean watchStart() {
		boolean isSuccess = true;
		
		Path path = Paths.get(WATCH_DIRECTORY);
		
		WatchService watchService;
		try {
			watchService = FileSystems.getDefault().newWatchService();
			//final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			
			while (isOperation) {
				final WatchKey wk = watchService.take();
				for (WatchEvent<?> event : wk.pollEvents()) {
					
					final Path changed = (Path) event.context();
					System.out.println("Modify detected!! : " + changed);
					if (changed.endsWith(WATCH_FILE)) {
						System.out.println("\n\n\n============================\n\n\n");
						System.out.println(WATCH_DIRECTORY + File.separator + WATCH_FILE + " Changed");
						System.out.println("============================\n\n\n");
						//TODO
						Main.changeSortInstance();
					}
				}
				
				// Reset the key
				boolean valid = wk.reset();
				if (!valid) {
					System.out.println("Key has been unregisterede");
				}
			}
			
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		} 
		
		isOperation = isSuccess;
		return isSuccess;
	}
	
	@Override
	public void run() {
		watchStart();
	}
}
