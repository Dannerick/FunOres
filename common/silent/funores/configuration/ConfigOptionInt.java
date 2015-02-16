package silent.funores.configuration;

import net.minecraftforge.common.config.Configuration;

public class ConfigOptionInt extends ConfigOption {

  public int value;
  public final int defaultValue;

  public ConfigOptionInt(String name, int defaultValue) {

    this.name = name;
    value = 0;
    this.defaultValue = defaultValue;
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category) {

    value = c.get(category, name, defaultValue).getInt(defaultValue);
    return this;
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category, String comment) {

    value = c.get(category, name, defaultValue, comment).getInt(defaultValue);
    return this;
  }

  @Override
  public ConfigOption validate() {

    return this;
  }

}
