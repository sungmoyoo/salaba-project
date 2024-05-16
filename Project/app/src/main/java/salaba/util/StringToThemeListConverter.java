package salaba.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import salaba.vo.rental_home.Theme;

@Component
public class StringToThemeListConverter implements Converter<String, List<Theme>> {

  @Override
  public List<Theme> convert(String source) {
    List<Theme> themes = new ArrayList<>();
    String[] strArr = source.split(",");
    for( String str : strArr ){
      Theme theme = new Theme();
      theme.setThemeNo(Integer.parseInt(str));
      themes.add(theme);
    }
    return themes;
  }
}
