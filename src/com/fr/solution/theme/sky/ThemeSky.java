package com.fr.solution.theme.sky;

import com.fr.fs.fun.impl.AbstractThemeVariousProvider;
import com.fr.plugin.ExtraClassManager;
import com.fr.plugin.PluginLicense;
import com.fr.plugin.PluginLicenseManager;
import com.fr.stable.fun.Authorize;
import com.fr.stable.fun.FunctionHelper;
import com.fr.stable.fun.FunctionProcessor;
import com.fr.stable.fun.impl.AbstractFunctionProcessor;

@Authorize(callSignKey="com.fr.solution.theme.sky")
public class ThemeSky extends AbstractThemeVariousProvider
{
  public static final FunctionProcessor ONEFUNCTION = new AbstractFunctionProcessor()
  {
    public int getId()
    {
      int i = FunctionHelper.generateFunctionID("com.fr.solution.theme.sky");
      return i;
    }

    public String getLocaleKey()
    {
      return "FS-THEME-SKY-TITLE";
    }
  };

  public String name()
  {
    return getText("Sky zuo");
  }

  public String text()
  {
    return getText("西北院自定义主题");
  }

  public String coverPath()
  {
    return getFilePath("/files/cover.png");
  }

  public String scriptPath()
  {
    FunctionProcessor localFunctionProcessor = ExtraClassManager.getInstance().getFunctionProcessor();
    if (localFunctionProcessor != null)
      localFunctionProcessor.recordFunction(ONEFUNCTION);
    return getFilePath("/files/theme.js");
  }

  public String stylePath()
  {
    return getFilePath("/files/style.css");
  }

  private String getFilePath(String paramString)
  {
    PluginLicense localPluginLicense = PluginLicenseManager.getInstance().getPluginLicenseByID("com.fr.solution.theme.sky");
    if (localPluginLicense.isAvailable())
      return Constants.PLUGIN_ROOT + paramString;
    return "";
  }

  private String getText(String paramString)
  {
    PluginLicense localPluginLicense = PluginLicenseManager.getInstance().getPluginLicenseByID("com.fr.solution.theme.sky");
    if (localPluginLicense.isAvailable())
      return paramString;
    return "";
  }
}

/* Location:           F:\FineReport_8.0\WebReport\WEB-INF\lib\plugin-com.fr.solution.theme.sky-0.jar
 * Qualified Name:     com.fr.solution.theme.sky.ThemeSky
 * JD-Core Version:    0.6.2
 */