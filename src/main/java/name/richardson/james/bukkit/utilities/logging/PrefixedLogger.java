package name.richardson.james.bukkit.utilities.logging;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.*;

import org.bukkit.Bukkit;

import name.richardson.james.bukkit.utilities.formatters.localisation.ResourceBundles;

public class PrefixedLogger extends Logger {

	private static final Logger BUKKIT_LOGGER = Logger.getLogger("Minecraft");
	private static final String RESOURCE_BUNDLE_NAME = ResourceBundles.MESSAGES.getBundleName();

	private static String PREFIX;

	protected PrefixedLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		LogManager.getLogManager().addLogger(this);
		if ((this.getParent() == null) || this.getParent().getName().isEmpty()) {
			this.setLevel(Level.INFO);
			for (final Handler handler : BUKKIT_LOGGER.getHandlers()) {
				handler.setLevel(Level.ALL);
			}
			this.setUseParentHandlers(true);
			this.setParent(BUKKIT_LOGGER);
		}
	}

	public static Logger getLogger(Class<?> object) {
		final String name = object.getPackage().getName();
		final java.util.logging.Logger logger = LogManager.getLogManager().getLogger(name);
		if (logger == null) {
			return new PrefixedLogger(name, RESOURCE_BUNDLE_NAME);
		} else {
			return logger;
		}
	}

	public static String getPrefix() {
		return PREFIX;
	}

	public static void setPrefix(final String prefix) {
		PREFIX = prefix;
	}

	@Override
	public void log(final LogRecord record) {
		if (this.getResourceBundle() != null) {
			ResourceBundle bundle = this.getResourceBundle();
			String key = record.getMessage();
			if (bundle.containsKey(key)) {
				record.setMessage(MessageFormat.format(bundle.getString(key), record.getParameters()));
			} else {
				record.setMessage(MessageFormat.format(key, record.getParameters()));
			}
		}
		if (this.getLevel() == Level.FINEST) {
			record.setMessage("<" + this.getName() + "> " + record.getMessage());
		} else {
			record.setMessage(PREFIX + record.getMessage());
		}
		super.log(record);
	}

}
