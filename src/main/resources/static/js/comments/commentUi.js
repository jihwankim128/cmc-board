const CommentUI = {
    loadComments() {
        CommentApi.getComments(postId)
            .done((response) => {
                const comments = response.data;
                $('#comment-count').text(comments.length);

                if (comments.length === 0) {
                    $('#comments-list').html(CommentRender.renderEmpty());
                } else {
                    $('#comments-list').html(CommentRender.renderComments(comments));
                }
            })
            .fail(() => {
                $('#comments-list').html(CommentRender.renderError());
            });
    },

    bindCommentFocus() {
        $('#comment-content').one('focus', function () {
            CommentAuth.checkAuthAndProceed(function () {
                $('#comment-form-actions').show();
            });
        });
    },

    initEvents() {
        this.bindCommentFocus();
        $('#comment-cancel-btn').click(() => {
            $('#comment-content').val('');
            $('#comment-form-actions').hide();
            $('#comment-content').blur();
            this.bindCommentFocus();
        });

        $('#comment-form').submit((e) => {
            e.preventDefault();
            const content = $('#comment-content').val();

            CommentApi.createComment(postId, content)
                .done(() => {
                    $('#comment-content').val('');
                    $('#comment-form-actions').hide();
                    this.loadComments();
                })
                .fail((xhr) => {
                    alert('댓글 작성 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
                });
        });

        $(document).on('click', '.reply-btn', (e) => {
            e.preventDefault();
            const commentId = $(e.currentTarget).data('comment-id');

            CommentAuth.checkAuthAndProceed(() => {
                $('.reply-form').hide();
                $(`#reply-form-${commentId}`).show();
                $(`#reply-form-${commentId} .reply-content`).focus();
            });
        });

        $(document).on('click', '.reply-cancel-btn', function () {
            $(this).closest('.reply-form').hide().find('.reply-content').val('');
        });

        $(document).on('submit', '.reply-submit-form', (e) => {
            e.preventDefault();
            const form = $(e.currentTarget);
            const parentId = form.data('parent-id');
            const content = form.find('.reply-content').val();

            CommentApi.createReply(parentId, content)
                .done(() => {
                    $(`#reply-form-${parentId}`).hide().find('.reply-content').val('');
                    this.loadComments();
                })
                .fail((xhr) => {
                    alert('답글 작성 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
                });
        });

        $(document).on('click', '.edit-comment-btn', (e) => {
            e.preventDefault();
            const commentId = $(e.currentTarget).data('comment-id');

            $('[class^="comment-content-"]').show();
            $('[class^="comment-edit-"]').hide();

            $(`.comment-content-${commentId}`).hide();
            $(`.comment-edit-${commentId}`).show().find('.edit-content').focus();
        });

        $(document).on('click', '.edit-cancel-btn', function () {
            const commentId = $(this).closest('form').data('comment-id');
            $(`.comment-content-${commentId}`).show();
            $(`.comment-edit-${commentId}`).hide();
        });

        $(document).on('submit', '.edit-submit-form', (e) => {
            e.preventDefault();
            const form = $(e.currentTarget);
            const commentId = form.data('comment-id');
            const content = form.find('.edit-content').val();

            CommentApi.updateComment(commentId, content)
                .done(() => {
                    this.loadComments();
                })
                .fail((xhr) => {
                    alert('댓글 수정 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
                });
        });

        $(document).on('click', '.delete-comment-btn', (e) => {
            e.preventDefault();
            const commentId = $(e.currentTarget).data('comment-id');
            if (!confirm('댓글을 삭제하시겠습니까?')) {
                return;
            }

            CommentApi.deleteComment(commentId)
                .done(() => {
                    this.loadComments();
                })
                .fail((xhr) => {
                    alert('댓글 삭제 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
                });
        });
    }
};

$(document).ready(() => {
    CommentUI.initEvents();
    CommentUI.loadComments();
});