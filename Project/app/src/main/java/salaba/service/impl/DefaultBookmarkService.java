package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.BookmarkDao;
import salaba.dao.ReservationDao;
import salaba.service.BookmarkService;
import salaba.service.ReservationService;
import salaba.vo.Bookmark;
import salaba.vo.Reservation;

@RequiredArgsConstructor
@Service
public class DefaultBookmarkService implements BookmarkService {

  private final BookmarkDao bookmarkDao;

  @Override
  public List<Bookmark> bookmark(Bookmark bookmark) {
    return bookmarkDao.findAll(bookmark);
  }

}