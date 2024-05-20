$(document).ready(function(){
  sideMenuActiveQuestion();
  setQuestionPage();
});


// SideMenu Active
function sideMenuActiveQuestion(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-Question');
  $('#sideMenu-collapse-HelpCenter').collapse('show');
  sideMenu.addClass('active');
}

function setQuestionPage(){
  const tableBody = $('#question-table tbody');
  const questionList = questionModel;
  questionList.forEach(function(question) {
    const registerDate = new Date(question.registerDate).toISOString().split('T')[0].replace(/-/g, '.');

    const row = $('<tr class="table-light"></tr>');
    const titleCell = $('<td class="table-light"></td>');
    const titleLink = $('<a id="question-title"></a>')
      .attr('href', '#')
      .text(question.title);
    titleCell.append(titleLink);

    const stateCell = $('<td class="table-light question-state"></td>').text(question.state);
    const dateCell = $('<td class="table-light"></td>').text(registerDate);

    row.append(titleCell, stateCell, dateCell);
    tableBody.append(row);

    // 답변이 있는 경우에만 collapse를 추가
    if (question.qna && question.qna.answer) {
      const collapseId = 'collapse-' + question.questionNo;
      const answerRow = $('<tr class="table-light"></tr>');
      const answerCell = $('<td colspan="3"></td>');
      const collapseDiv = $('<div class="collapse"></div>')
        .attr('id', collapseId);

      const collapseContent = $('<div class="card card-body"></div>')
        .html('<table class="table-light"><thead><tr class="table-light"><th>답변</th><th>답변일</th></tr></thead><tbody><td class="table-light">' + question.qna.answer + '</td><td class="table-light">'+ question.qna.answerDate +'</td></tbody></table>');

      collapseDiv.append(collapseContent);
      answerCell.append(collapseDiv);
      answerRow.append(answerCell);
      tableBody.append(answerRow);

      // 질문 제목을 클릭하면 collapse를 토글
      titleLink.attr('data-bs-toggle', 'collapse');
      titleLink.attr('data-bs-target', '#' + collapseId);
      titleLink.attr('aria-expanded', 'false');
      titleLink.attr('aria-controls', collapseId);
    }
  });
}