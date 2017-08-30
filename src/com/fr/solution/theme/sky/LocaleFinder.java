package com.fr.solution.theme.sky;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

public class LocaleFinder extends AbstractLocaleFinder
{
  private String path = LocaleFinder.class.getPackage().getName().replaceAll("\\.", "/");

  public int currentAPILevel()
  {
    return 1;
  }

  public String find()
  {
    return this.path + "/i18n";
  }
}

/* Location:           F:\FineReport_8.0\WebReport\WEB-INF\lib\plugin-com.fr.solution.theme.sky-0.jar
 * Qualified Name:     com.fr.solution.theme.sky.LocaleFinder
 * JD-Core Version:    0.6.2
 */