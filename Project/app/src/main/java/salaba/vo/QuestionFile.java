package salaba.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFile implements Serializable {

  private static final long serialVersionUID = 100L;

  private int questionNo; // 질문번호
  private int questionFileNo; // 파일번호
  private String oriFileName; // Original File name
  private String uuidFileName; // UUID File name
}
