import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class FileSystemWatcher {
	
	private String WATCH_DIRECTORY = "." + File.separator + "etc";
	private String WATCH_FILE = "setting.xml";
	
	public FileSystemWatcher() throws IOException, InterruptedException {
		Path path = Paths.get(WATCH_DIRECTORY);
		
		final WatchService watchService = FileSystems.getDefault().newWatchService();
		final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
		while (true) {
		    final WatchKey wk = watchService.take();
		    for (WatchEvent<?> event : wk.pollEvents()) {

		        final Path changed = (Path) event.context();
		        System.out.println("Path modify detected!! : " + changed);
		        if (changed.endsWith(WATCH_DIRECTORY + File.separator + WATCH_FILE)) {
		            System.out.println(WATCH_DIRECTORY + File.separator + WATCH_FILE + " Changed");
		            //TODO
		        }
		    }
		    
		    // Reset the key
		    boolean valid = wk.reset();
		    if (!valid) {
		        System.out.println("Key has been unregisterede");
		    }
		}
	}
}
