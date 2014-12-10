package test.plugin;

import com.marine.plugins.Plugin;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginMain extends Plugin {

    @Override
    public void onEnable() {
        getLogger().log(getName() + " is enabled");
    }

}
