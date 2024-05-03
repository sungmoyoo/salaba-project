const express = require('express');
const app = express();
const AWS = require('aws-sdk');
require('dotenv').config();
const { v4: uuidv4 } = require('uuid');

// 웹소켓 모듈
const server = require('http').createServer(app);
const io = require('socket.io')(server);

// json 변환 모듈
const bodyParser = require('body-parser');

// 파일 관련 모듈
const fs = require('fs');
const path = require('path');


// ncp 환경변수
const bucketName = process.env.NCP_BUCKET_NAME;
const endpoint = new AWS.Endpoint(process.env.NCP_ENDPOINT);
const region = process.env.NCP_REGION_NAME;
const accessKey = process.env.NCP_ACCESS_KEY;
const secretKey = process.env.NCP_SECRET_KEY;

var uploadDir = "chat/"

// Amazon S3 인스턴스 생성
const S3FileManager = require('./S3FileManager');
const storageService = new S3FileManager(bucketName, endpoint, region, accessKey, secretKey);

const PORT = 3000;

const loginUsers = new Map();
const reservationNos = new Map();
const rooms = new Map();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.post('/receiveData', (req, res) => {
  loginUsers.set(req.body.socketId, req.body.loginUser);
  reservationNos.set(req.body.socketId, req.body.reservationNo);

  res.sendStatus(200);
});

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/room.html');
});

server.listen(PORT, () => {
  console.log(`서버가 http://localhost:${PORT} 에서 실행 중입니다.`);
});




io.on('connection', (socket) => {
  console.log('새로운 사용자가 연결되었습니다.');
  let reservationNo = reservationNos.get(socket.socketId);
  let loginUser = loginUsers.get(socket.socketId);

  console.log('사용자: ' + loginUser);
  console.log('예약번호: ' + reservationNo);

  let messageList = rooms.get(reservationNo);
  if (!messageList) {
    messageList = []
    rooms.set(reservationNo, messageList);
  }

  // 해당 채팅방에 조인
  socket.join(reservationNo);

  let fileName = 'chat-'+ reservationNo +'.json';
  let filePath = path.join(__dirname, 'tmp', fileName);

  // 기존 파일 읽어서 자바스크립트 객체배열로 변환
  if (fs.existsSync(filePath)) {
    messageList = JSON.parse(fs.readFileSync(filePath, 'utf8'));
    // 기존 채팅 내역 출력
    messageList.forEach(messageObj => {
      io.to(reservationNo).emit('chat message', `${messageObj.writer}: ${messageObj.message}`);
    });
  }

  // 입장 메시지
  io.to(reservationNo).emit('chat message', loginUser + "님이 입장했습니다.");

  // 클라이언트로부터 채팅 메시지 수신
  socket.on('chat message', (data) => {
    console.log('수신한 메시지:', data);

    // 메시지 배열에 담기
    const currentTime = new Date().toISOString().replace(/T/, ' ').replace(/\..+/, '');
    const message = {writer: loginUser, message: data.message, time: currentTime}

    messageList.push(message);
    // 메시지 파일에 추가
    // flag: 'a'를 사용하여 기존 데이터 덮어씌우지 않고 추가
    fs.writeFile(filePath, JSON.stringify(messageList) + '\n', { flag: 'w' }, (err) => {
      if (err) {
        console.error(err);
        return;
      }
      console.log("채팅 파일 저장");
    });

    console.log(messageList);

    // 채팅방에 메시지 전송
    io.to(reservationNo).emit('chat message', loginUser + ": " + data.message);
  });

  // 클라이언트가 연결을 종료할 때
  socket.on('disconnect', () => {

    // 파일 업로드
    // async => 비동기 함수 선언
    if (fs.existsSync(filePath)) {
      (async () => {
        await storageService.uploadFile(uploadDir, fileName, filePath); // await => 작업이 끝날때까지 대기
      })();
    }

    console.log('사용자가 연결을 종료했습니다.');
    io.to(reservationNo).emit('chat message', loginUser + "님이 퇴장했습니다.");
    // 연결 종료 처리 (필요하다면 추가)
  });
});
