package salaba.util;

import java.util.HashMap;
import java.util.Map;

public final class Translator {
  private static Map<Integer, String> ncpFolder = new HashMap<>();

  // ncpFolder 초기화
  static {
    ncpFolder.put(0, "board/review/");
    ncpFolder.put(1, "board/community/");
    ncpFolder.put(2, "board/community/");
  }

  private Translator() {}

  public static String getNcpFolderPath(int categoryNo) {
    return ncpFolder.get(categoryNo);
  }
}
