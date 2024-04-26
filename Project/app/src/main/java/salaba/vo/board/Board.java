package salaba.vo.board;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.vo.Member;
import salaba.vo.Region;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board implements Serializable {  // 게시판

  private static final long serialVersionUID = 100L;

  private int boardNo; // 게시판 번호
  private Member writer; // 작성자
  private int categoryNo; // 카테고리: 0 - 후기 게시판 / 1 - 정보공유 게시판 / 2 - 자유 게시판
<<<<<<< HEAD:Project/app/src/main/java/salaba/vo/board/Board.java
=======
  private String categoryNm; // 카테고리 이름
>>>>>>> origin/dev:Project/app/src/main/java/salaba/vo/Board.java
  private int headNo; // 말머리 번호: 1 - 공지 / 2 - 이벤트 / 3 - 홍보 / 4 - 꿀팁 / 5 - 질문 / 6 - 잡담
  private String headContent; // 말머리 이름
  private String title;  // 제목
  private String content; // 내용
  private int likeCount; // 추천수
  private int myLikeCount; // 로그인 회원 추천수
  private Date createdDate; // 작성일
  private int viewCount; // 조회수
  private String state; // 상태
  private int scopeNo; // 공개범위 번호: 0 - 전체 공개 / 1 - 회원 공개 / 2 - 비공개
  private String scopeName; // 공개범위 이름
  private List<BoardFile> fileList; // 첨부파일
  private List<Comment> commentList; // 댓글
  private int fileCount; // 파일수
  private Region region; // 지역
<<<<<<< HEAD:Project/app/src/main/java/salaba/vo/board/Board.java
=======

  private int no; // 회원 번호

>>>>>>> origin/dev:Project/app/src/main/java/salaba/vo/Board.java
}