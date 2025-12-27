const CommentAuth = {
    checkAuthAndProceed(callback) {
        if (isAuthenticated) {
            callback();
        } else {
            this.redirectToLogin();
        }
    },

    redirectToLogin() {
        if (confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
            location.href = `/login?redirect=${encodeURIComponent(location.pathname)}`;
        } else {
            $('#comment-content').blur();
            CommentUI.bindCommentFocus();
        }
    }
};