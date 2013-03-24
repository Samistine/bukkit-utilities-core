/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ResourceBundleLoader.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.localisation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleLoader {

  /**
   * Load a resource bundle
   *
   * @param loader the ClassLoader of the plugin
   * @param name the name of the bundle to load.
   * @return the bundle
   */
  public static ResourceBundle getBundle(ClassLoader loader, final String name) {
    return ResourceBundle.getBundle(name + "-localisation", Locale.getDefault(), loader);
  }

  /**
   * Load any overridden bundles in the plugin data folder.
   *
   * @param loader the ClassLoader of the plugin
   * @param name the name of the bundle to load
   * @param dataFolder the data folder
   * @return the bundle
   */
  public static ResourceBundle getBundle(ClassLoader loader, final String name, final File dataFolder) {
    try {
      final String path = dataFolder.getAbsolutePath() + File.separator + "localisation.properties";
      final File customBundle = new File(path);
      if (customBundle.exists()) {
        final FileInputStream stream = new FileInputStream(customBundle);
        final PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        stream.close();
        return bundle;
      } else {
        return ResourceBundle.getBundle(name + "-localisation", Locale.getDefault(), loader);
      }
    } catch (final IOException exception) {
      exception.printStackTrace();
      return ResourceBundle.getBundle(name + "-localisation", Locale.getDefault(), loader);
    }
  }

}
