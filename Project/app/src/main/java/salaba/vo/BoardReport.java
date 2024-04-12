package salaba.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import lombok.Data;

@Data // 클래스 내의 모든 필드에 대한 Getter와 Setter 메서드를 생성
public class BoardReport implements Serializable { // 게시판 신고

  private static final long serialVersionUID = 100L;

  private int reportNo;  // 신고 번호
  private int categoryNo; // 신고유형
  private Member writer;  // 신고자
  private String content; // 내용
  private Date reportDate; // 신고 접수일시
  private char state;  // 상태
  private int targetNo; // 신고대상 번호
  private char targetType; // 신고대상 타입
  private List<BoardFile> fileList; // 게시글 (신고) 첨부파일

}
