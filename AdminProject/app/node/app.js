const express = require('express');
const app = express();
const port = 3000;

// 정적 파일을 제공할 디렉토리 설정
app.use(express.static('public'));

app.get('/', (req, res) => {
    // HTML 파일을 응답으로 보냅니다.
    res.sendFile("/public/auth/form.html");
});

app.listen(port, () => {
    console.log('서버가 실행됩니다.');
});