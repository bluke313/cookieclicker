package cookieclicker;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProjectRunner {

	public static void main(String[] args) {
		CookieWindow window = new CookieWindow();
		ScheduledExecutorService executorService;
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(window::loop, 10, 10, TimeUnit.MILLISECONDS);
	}
}
