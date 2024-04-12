package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Board implements Serializable {  // 게시판

  private static final long serialVersionUID = 100L;

  private int boardNo; // 게시판 번호
  private Member writer; // 작성자
  private int categoryNo; // 카테고리: 0 - 후기 게시판 / 1 - 정보공유 게시판 / 2 - 자유 게시판
  private int headNo; // 말머리 번호
  private String headContent; // 말머리 이름
  private String title;  // 제목
  private String content; // 내용
  private int likeCount; // 추천수
  private Date createdDate; // 작성일
  private int viewCount; // 조회수
  private char state; // 상태
  private int scopeNo; // 공개범위 번호: 0 - 전체 공개 / 1 - 회원 공개 / 2 - 비공개
  private String scopeName; // 공개범위 이름
  private List<BoardFile> fileList; // 첨부파일
  private List<Comment> commentList; // 댓글
  private List<Reply> replyList; // 답글
  private int fileCount; // 파일수
  private Region region;
}