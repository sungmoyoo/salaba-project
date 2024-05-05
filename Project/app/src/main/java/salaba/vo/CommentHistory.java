package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class CommentHistory implements Serializable { // 댓글

  private static final long serialVersionUID = 100L;

  private int no; // 회원번호
  private String cnr; // 댓글인지 답글인지
  private int boardNo; // 게시글 번호
  private int categoryNo; // 카테고리 번호
  private String title; // 게시글 제목
  private int replyNo;  // 답글 번호
  private int commentNo;  // 댓글 번호
  private String content;  // 내용
  private Date createdDate; // 작성일
  private char state; // 상태

}
