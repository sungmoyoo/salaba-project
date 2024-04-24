const express = require('express');
const app = express();
const server = require('http').createServer(app);
const io = require('socket.io')(server);
const bodyParser = require('body-parser');

const PORT = 3000;

const chatRooms = new Map();
const loginUsers = new Map();
var loginUser = null;
let reservationNo = null;
let messageList = [];

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.post('/receiveData', (req, res) => {
  const loginUser = req.body.loginUser;
  console.log('Received loginUser:', loginUser);
  loginUsers.set(req.body.socketId, loginUser);

  res.sendStatus(200);
});

app.get('/:reservationNo', (req, res) => {
  reservationNo = req.params.reservationNo;
  if (!chatRooms.has(reservationNo)) {
    chatRoomId = generateChatRoomId();
    chatRooms.set(reservationNo, chatRoomId);
    console.log(`채팅방이 생성되었습니다. (번호: ${reservationNo})`);
  } else {
    chatRoomId = chatRooms.get(reservationNo)
    console.log(`채팅방에 접속합니다. (번호: ${reservationNo}`);
  }

  res.sendFile(__dirname + '/room.html');
});

server.listen(PORT, () => {
  console.log(`서버가 http://localhost:${PORT} 에서 실행 중입니다.`);
});

io.on('connection', (socket) => {
  console.log('새로운 사용자가 연결되었습니다.');
  let loginUser = null;

  loginUser = loginUsers.get(socket.socketId);
  console.log('사용자: ' + loginUser);

  // 해당 채팅방에 조인
  socket.join(reservationNo);

  io.to(reservationNo).emit('chat message', loginUser + "님이 입장했습니다.");

  // 클라이언트로부터 채팅 메시지 수신
  socket.on('chat message', (data) => {
    console.log('수신한 메시지:', data);

    const currentTime = new Date().toISOString().replace(/T/, ' ').replace(/\..+/, '');
    const message = {writer: loginUser, message: data.message, time: currentTime}
    messageList.push(message)

    // 채팅방에 메시지 전송
    io.to(reservationNo).emit('chat message', loginUser + ": " + data.message);
  });

  // 클라이언트가 연결을 종료할 때
  socket.on('disconnect', () => {
    console.log('사용자가 연결을 종료했습니다.');
    io.to(reservationNo).emit('chat message', loginUser + "님이 퇴장했습니다.");
    // 연결 종료 처리 (필요하다면 추가)

    console.log(messageList);
  });
});


function generateChatRoomId() {
  // 여기서는 간단히 랜덤한 숫자를 채팅방 ID로 생성합니다.
  return Math.floor(Math.random() * 1000).toString();
}

