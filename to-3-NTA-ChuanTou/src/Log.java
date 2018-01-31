import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Log {
	private static Logger logger = Logger.getLogger(Log.class.getName());
	static {
		try {
			FileHandler fileHandler = null;
			fileHandler = new FileHandler("./log_" + System.currentTimeMillis() + ".log");
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fileHandler.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord arg0) {
					return String.format("%-8s", arg0.getLevel().getLocalizedName())
							+ sdf.format(new Date(arg0.getMillis())) + "  : " + arg0.getMessage() + "\n";
				}
			});
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void w(String title, String message) {
		logger.log(Level.WARNING, title + "-->" + message);
	}

	public static void i(String title, String message) {
		logger.info(title + "-->" + message);
	}

	public static void e(String title, Throwable error) {
		String error_msg = "";
		error_msg += error.getLocalizedMessage() + "\n";
		StackTraceElement[] msgs = error.getStackTrace();
		for (int i = 0; i < msgs.length; i++) {
			error_msg += msgs[i].toString() + "\n";
		}
		logger.log(Level.WARNING, title + "-->" + error_msg);
	}
}
