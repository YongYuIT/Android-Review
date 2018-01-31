import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MyServerSide {
	private static ExecutorService pools = Executors.newCachedThreadPool();

	protected int port = -1;

	public MyServerSide(int _port) {
		this.port = _port;
	}

	private boolean isRunning = false;

	public void start() {
		synchronized (this) {
			if (isRunning == true) {
				return;
			}
			isRunning = true;
		}
		pools.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					doJob();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("start server error", e);
				}

			}
		});

	}

	protected abstract void doJob() throws Exception;

}
