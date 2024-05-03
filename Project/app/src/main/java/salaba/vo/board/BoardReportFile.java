package salaba.vo.board;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardReportFile implements Serializable {
  private static final long serialVersionUID = 100L;
  private int reportNo; // 신고 번호
  private String oriFileName; // 신고파일 원본
  private String uuidFileName; // 신고 uuid 파일
}
