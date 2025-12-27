// csrf 설정
$(document).ready(function () {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    if (token && header) {
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
});

// 전역 예외 처리 - 로그인 관련
$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
    if (jqxhr.status === 401) {
        alert('로그인이 필요합니다.');
        location.href = '/login';
    } else if (jqxhr.status === 403) {
        alert('권한이 없습니다.');
    }
});

// 날짜 포맷팅 함수
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR');
}

// 로딩 스피너 표시/숨김
function showLoading() {
    // TODO: 로딩 스피너 구현
}

function hideLoading() {
    // TODO: 로딩 스피너 구현
}
