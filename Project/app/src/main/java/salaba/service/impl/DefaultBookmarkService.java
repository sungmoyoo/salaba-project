package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.BookmarkDao;
import salaba.service.BookmarkService;
import salaba.vo.rental_home.RentalHome;

@RequiredArgsConstructor
@Service
public class DefaultBookmarkService implements BookmarkService {

  private final BookmarkDao bookmarkDao;

  @Override
  public List<RentalHome> selectUserBookMark(int memberNo) {
    return bookmarkDao.selectUserBookMark(memberNo);
  }

  @Override
  public int deleteBookMark(int memberNo, int rentalHomeNo) {
    return bookmarkDao.deleteBookMark(memberNo, rentalHomeNo);
  }

  @Override
  public int selectOneBookMark(int memberNo, int rentalHomeNo) {
    return bookmarkDao.selectOneBookMark(memberNo, rentalHomeNo);
  }

  @Override
  public int addBookMark(int memberNo, int rentalHomeNo) {
    return bookmarkDao.addBookMark(memberNo, rentalHomeNo);
  }
}