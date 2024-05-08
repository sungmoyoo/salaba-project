"use strict";


  (function(){
    axiosInstance.get(`${RESTAPI_HOST}/chart/unprocessed`)
    .then((response) => {
      const unprocessedData = response.data.data;
      let unprcsTemplate = Handlebars.compile($("#unprcs-template").html());
      $('#firstLine').html(unprcsTemplate(unprocessedData));
      return axiosInstance.get(`${RESTAPI_HOST}/chart/boardCount`);
    })
    axiosInstance.get(`${RESTAPI_HOST}/chart/boardCount`)
    .then((response) => {
        const boardsData = response.data.data;
        var barCtx = document.getElementById('barChart').getContext('2d');
        var barChart = new Chart(barCtx, {
          type: 'bar',
          data: {
            labels: boardsData.map(item => item.createdDate), // 최근 1달간의 월을 표시
            datasets: [{
              label: '게시물 수',
              backgroundColor: 'rgba(54, 162, 235, 0.2)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1,
              data: boardsData.map(item => item.boardCount)
            }]
          },
          options: {
            scales: {
              y: {
                beginAtZero: true
              }
            }
          }
        });

        return axiosInstance.get(`${RESTAPI_HOST}/chart/joinCount`);
    })


    .then((response) => {
       const membersData = response.data.data;
       console.log(membersData);
       var regionCtx = document.getElementById('regionChart').getContext('2d');
        var regionChart = new Chart(regionCtx, {
          type: 'bar',
          data: {
            labels: membersData.map(item => `${item.joinYear}-${item.joinMonth}`),
            datasets: [{
              label:'가입자 수',
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              borderColor: 'rgba(255, 99, 132, 1)',
              borderWidth: 1,
              data: membersData.map(item => item.memberCount) 
            }]
          },
          options: {
            scales: {
              y: {
                beginAtZero: true
              }
            }
          }
        });
        return axiosInstance.get(`${RESTAPI_HOST}/chart/gradeCount`);
    })


    .then((response) => {
        const gradeData = response.data.data;
        var gradeCtx = document.getElementById('gradeChart').getContext('2d');
        var gradeChart = new Chart(gradeCtx, {
          type: 'bar',
          data: {
            labels: gradeData.map(item => item.gradeName), // 등급 표시
            datasets: [{
              label: '회원 수',
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1,
              data: gradeData.map(item => item.memberCount) // 등급별 회원 수
            }]
          },
          options: {
            scales: {
              y: {
                beginAtZero: true
              }
            }
          }
        });
        return axiosInstance.get(`${RESTAPI_HOST}/chart/rentalCount`);
    })


    .then((response) => {
        const regionData = response.data.data;
        var doughnutCtx = document.getElementById('doughnutChart').getContext('2d');
        var doughnutChart = new Chart(doughnutCtx, {
          type: 'doughnut',
          data: {
            labels: regionData.map(item => item.regionName),
            datasets: [{
              label: '지역별 숙소 분포',
              data: regionData.map(item => item.rentalCount),
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)',
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
              ],
              borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)'
              ],
              borderWidth: 1
            }]
          }
        });
    })
  })();

  // 막대 그래프 생성


  // 원 그래프 생성


  // 등급별 회원 분포 차트 생성


  // 지역별 숙소 분포 차트 생성
