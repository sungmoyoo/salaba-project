package salaba.vo.board;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.vo.Member;

@Data // 클래스 내의 모든 필드에 대한 Getter와 Setter 메서드를 생성
@NoArgsConstructor
@AllArgsConstructor
public class BoardReport implements Serializable { // 게시판 신고

  private static final long serialVersionUID = 100L;

  private int reportNo;  // 신고 번호
  private int categoryNo; // 신고유형: 1- 스팸홍보/도배 / 2 - 음란물입니다. / 3 - 불법정보를 포함하고 있습니다. / 4 - 욕설/혐오적 표현입니다.
  private Member writer;  // 신고자
  private String content; // 내용
  private Date reportDate; // 신고 접수일시
  private String state;  // 상태
  private int targetNo; // 신고대상 번호 = 게시글, 댓글, 답글 번호
  private String targetType; // 신고대상 타입: 0 - 게시판 / 1 - 댓글 / 2 - 답글
  private List<BoardReportFile> reportFileList; // 게시글 첨부파일

}
