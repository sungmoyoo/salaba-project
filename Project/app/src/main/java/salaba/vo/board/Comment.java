package salaba.vo.board;

import java.io.Serializable;
import java.util.Date;
import javax.servlet.ServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.vo.Member;

@Builder
@Data // 클래스 내의 모든 필드에 대한 Getter와 Setter 메서드를 생성
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable { // 댓글

  private static final long serialVersionUID = 100L;

  private int commentNo;  // 댓글 번호
  private Board board; // 게시판 번호
  private String content;  // 내용
  private Member writer;  // 댓글 작성자
  private Date createdDate; // 작성일
  private String state; // 상태

}
