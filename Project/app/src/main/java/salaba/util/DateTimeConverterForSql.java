package salaba.util;

import java.sql.Date;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateTimeConverterForSql implements Converter<DateTime, Date> {

  @Override
  public Date convert(DateTime dataTime) {
    return new Date(dataTime.getMillis());
  }
}
