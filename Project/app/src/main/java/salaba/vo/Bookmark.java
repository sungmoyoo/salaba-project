package salaba.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Bookmark implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no; // 회원번호
  private int rentalHomeNo; // 숙소번호
  private String name; // 숙소명
  private String uuidPhotoName; // 사진파일명
  private int photoOrder; // 사진파일 정렬순서
  private int photoNo; // 사진파일 번호
  private List<Bookmark> photoList; //숙소사진파일 리스트
  private String address; // 주소
  private String price; // 숙소가격

  private String myInfoMenuId;//좌측 메뉴 클릭 상태 표시를 위한 변수

}
