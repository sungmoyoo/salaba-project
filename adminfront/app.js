const express = require('express');
const path = require('path');
const router = express.Router();
const cors = require('cors');
const app = express();
const port = process.env.PORT || 3000;

// 정적 파일 제공
app.use(express.static(path.join(__dirname, 'public')));

// 서버 시작
app.listen(port, () => {
  console.log(`서버가 http://localhost:${port} 에서 실행 중입니다.`);
});


router.get('/', (req, res) => {
    // path.join()을 사용하여 파일 경로를 지정합니다.
    const filePath = path.join(__dirname, '/public/auth/form.html');

    // sendFile() 메서드를 사용하여 파일을 응답으로 보냅니다.
    res.sendFile(filePath);
});

router.get('/home', (req, res) => {
  // path.join()을 사용하여 파일 경로를 지정합니다.
  const filePath = path.join(__dirname, '/public/home/home.html');
  // sendFile() 메서드를 사용하여 파일을 응답으로 보냅니다.
  res.sendFile(filePath);
});

router.get('/rental/list', (req, res) => {
    const filePath = path.join(__dirname, '/public/rental/list.html');
    res.sendFile(filePath);
});

router.get('/report/list', (req, res) => {
  const filePath = path.join(__dirname, '/public/report/list.html');
  res.sendFile(filePath);
});

router.get('/member/list', (req, res) => {
  const filePath = path.join(__dirname, '/public/member/list.html');
  res.sendFile(filePath);
});

router.get('/qna/list', (req, res) => {
  const filePath = path.join(__dirname, '/public/qna/list.html');
  res.sendFile(filePath);
});

router.get('/member/search', (req, res) => {
});

router.get('/rental/search', (req, res) => {
});

router.get('/chart/boardCount', (req, res) => {
});

router.get('/chart/joinCount', (req, res) => {
});

router.get('/chart/gradeCount', (req, res) => {
});

router.get('/chart/rentalCount', (req, res) => {
});

router.get('/chart/unprocessed', (req, res) => {
});


router.post("/qna/update", (req, res) => {
  // 클라이언트에서 전송한 데이터는 req.body에 있습니다.
  const data = req.body;

  // 여기서 data를 사용하여 요청을 처리하고 필요에 따라 응답을 반환합니다.
  // 예를 들어, 데이터베이스에 업데이트를 수행하거나 다른 작업을 수행할 수 있습니다.
});

router.post("/auth/login", (req, res) => {
  const data = req.body;
});

router.post("/auth/info", (req, res) => {
  const data = req.body;
});

router.post("/auth/refreshToken", (req, res) => {
  const data = req.body;
});

router.put("/report/update", (req, res) => {
  const data = req.body;
});

router.put("/member/update", (req, res) => {
  const data = req.body;
});


router.put("/rental/update", (req, res) => {
  const data = req.body;
});

router.delete("/auth/logout", (req, res) => {
  const data = req.body;
});

app.use('/', router);

module.exports = router;
