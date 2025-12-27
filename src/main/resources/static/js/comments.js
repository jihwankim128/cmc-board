function checkAuthAndProceed(callback) {
    if (isAuthenticated) {
        callback();
    } else {
        redirectToLogin();
    }
}

function redirectToLogin() {
    if (confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
        location.href = `/login?redirect=${encodeURIComponent(location.pathname)}`;
    } else {
        $('#comment-content').blur();
        bindCommentFocus();
    }
}

function bindCommentFocus() {
    $('#comment-content').one('focus', function () {
        checkAuthAndProceed(function () {
            $('#comment-form-actions').show();
        });
    });
}

function renderComments(comments) {
    const parentComments = comments.filter(c => c.depth === 0);

    return parentComments.map(comment => {
        const replies = comments.filter(c => c.parentId === comment.id);

        return `
            <div class="comment-item mb-3" data-comment-id="${comment.id}">
                ${renderCommentContent(comment, false)}
                
                ${replies.length > 0 ? `
                    <div class="ms-5 mt-2">
                        ${replies.map(reply => `
                            <div class="comment-item mb-2" data-comment-id="${reply.id}">
                                ${renderCommentContent(reply, true)}
                            </div>
                        `).join('')}
                    </div>
                ` : ''}
                
                <div class="reply-form ms-5 mt-2" id="reply-form-${comment.id}" style="display:none;">
                    <form class="reply-submit-form" data-parent-id="${comment.id}">
                        <div class="mb-2">
                            <textarea class="form-control reply-content" 
                                      placeholder="답글을 입력하세요" 
                                      rows="2" 
                                      style="resize: none;"></textarea>
                        </div>
                        <div class="text-end">
                            <button type="button" class="btn btn-sm btn-secondary reply-cancel-btn">
                                취소
                            </button>
                            <button type="submit" class="btn btn-sm btn-primary">
                                <i class="bi bi-send"></i> 답글 작성
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        `;
    }).join('');
}

function renderCommentContent(comment, isReply) {
    const prefix = isReply ? '<i class="bi bi-arrow-return-right me-2"></i>' : '';
    const showReplyButton = comment.depth === 0;

    return `
        <div class="d-flex justify-content-between align-items-start">
            <div class="flex-grow-1">
                <div class="d-flex align-items-center mb-1">
                    ${prefix}
                    <strong>${escapeHtml(comment.author.nickname)}</strong>
                    <small class="text-muted ms-2">${formatCommentDate(comment.createdAt)}</small>
                </div>
                
                <div class="comment-content-${comment.id}">
                    <p class="mb-1">${escapeHtml(comment.content)}</p>
                    ${showReplyButton ? `
                        <div class="comment-actions">
                            <a href="#" class="text-muted text-decoration-none me-3 reply-btn" 
                               data-comment-id="${comment.id}" 
                               style="font-size: 0.875rem;">
                                답글
                            </a>
                        </div>
                    ` : ''}
                </div>
                
                <div class="comment-edit-${comment.id}" style="display:none;">
                    <form class="edit-submit-form" data-comment-id="${comment.id}">
                        <div class="mb-2">
                            <textarea class="form-control edit-content" 
                                      rows="2" 
                                      style="resize: none;">${escapeHtml(comment.content)}</textarea>
                        </div>
                        <div class="text-end">
                            <button type="button" class="btn btn-sm btn-secondary edit-cancel-btn">
                                취소
                            </button>
                            <button type="submit" class="btn btn-sm btn-primary">
                                <i class="bi bi-check"></i> 수정
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            
            ${comment.authorMe ? `
                <div class="dropdown">
                    <button class="btn btn-sm btn-link text-muted" type="button" 
                            data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-three-dots-vertical"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li>
                            <a class="dropdown-item edit-comment-btn" href="#" 
                               data-comment-id="${comment.id}">
                                <i class="bi bi-pencil"></i> 수정
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item text-danger delete-comment-btn" href="#" 
                               data-comment-id="${comment.id}">
                                <i class="bi bi-trash"></i> 삭제
                            </a>
                        </li>
                    </ul>
                </div>
            ` : ''}
        </div>
    `;
}

function renderEmptyComments() {
    $('#comments-list').html(`
        <p class="text-muted text-center py-4">
            <i class="bi bi-chat"></i> 첫 댓글을 작성해보세요!
        </p>
    `);
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function formatCommentDate(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diff = now - date;

    if (diff < 60 * 1000) {
        return '방금 전';
    }
    if (diff < 60 * 60 * 1000) {
        return `${Math.floor(diff / (60 * 1000))}분 전`;
    }
    if (diff < 24 * 60 * 60 * 1000) {
        return `${Math.floor(diff / (60 * 60 * 1000))}시간 전`;
    }
    if (diff < 7 * 24 * 60 * 60 * 1000) {
        return `${Math.floor(diff / (24 * 60 * 60 * 1000))}일 전`;
    }

    return date.toLocaleDateString('ko-KR');
}

// ========== 댓글 목록 로드 ==========
function loadComments() {
    $.get(`/api/comments/posts/${postId}`, function (response) {
        const comments = response.data;
        $('#comment-count').text(comments.length);

        if (comments.length === 0) {
            renderEmptyComments();
            return;
        }

        $('#comments-list').html(renderComments(comments));
    }).fail(function () {
        $('#comments-list').html(`
            <p class="text-danger text-center py-4">
                댓글을 불러오는데 실패했습니다.
            </p>
        `);
    });
}

// ========== 이벤트 핸들러 ==========
$(document).ready(function () {
    // 댓글 작성 폼 - 포커스 시 인증 체크
    $('#comment-content').one('focus', function () {
        checkAuthAndProceed(function () {
            $('#comment-form-actions').show();
        });
    });

    // 댓글 작성 취소
    $('#comment-cancel-btn').click(function () {
        $('#comment-content').val('');
        $('#comment-form-actions').hide();
        $('#comment-content').blur();
    });

    // 댓글 작성 제출
    $('#comment-form').submit(function (e) {
        e.preventDefault();

        $.ajax({
            url: `/api/comments`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                postId: postId,
                content: $('#comment-content').val()
            }),
            success: function () {
                $('#comment-content').val('');
                $('#comment-form-actions').hide();
                loadComments();
            },
            error: function (xhr) {
                alert('댓글 작성 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
            }
        });
    });

    // 답글 버튼
    $(document).on('click', '.reply-btn', function (e) {
        e.preventDefault();
        const commentId = $(this).data('comment-id');

        checkAuthAndProceed(function () {
            $('.reply-form').hide();
            $(`#reply-form-${commentId}`).show();
            $(`#reply-form-${commentId} .reply-content`).focus();
        });
    });

    // 답글 취소
    $(document).on('click', '.reply-cancel-btn', function () {
        $(this).closest('.reply-form').hide().find('.reply-content').val('');
    });

    // 답글 제출
    $(document).on('submit', '.reply-submit-form', function (e) {
        e.preventDefault();
        const parentId = $(this).data('parent-id');
        const content = $(this).find('.reply-content').val();

        $.ajax({
            url: `/api/comments/${parentId}/reply`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                content: content
            }),
            success: function () {
                $(`#reply-form-${parentId}`).hide().find('.reply-content').val('');
                loadComments();
            },
            error: function (xhr) {
                alert('답글 작성 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
            }
        });
    });

    // 수정 버튼
    $(document).on('click', '.edit-comment-btn', function (e) {
        e.preventDefault();
        const commentId = $(this).data('comment-id');

        $('[class^="comment-content-"]').show();
        $('[class^="comment-edit-"]').hide();

        $(`.comment-content-${commentId}`).hide();
        $(`.comment-edit-${commentId}`).show().find('.edit-content').focus();
    });

    // 수정 취소
    $(document).on('click', '.edit-cancel-btn', function () {
        const commentId = $(this).closest('form').data('comment-id');
        $(`.comment-content-${commentId}`).show();
        $(`.comment-edit-${commentId}`).hide();
    });

    // 수정 제출
    $(document).on('submit', '.edit-submit-form', function (e) {
        e.preventDefault();
        const commentId = $(this).data('comment-id');
        const content = $(this).find('.edit-content').val();

        $.ajax({
            url: `/api/comments/${commentId}`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({content: content}),
            success: function () {
                loadComments();
            },
            error: function (xhr) {
                alert('댓글 수정 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
            }
        });
    });

    // 삭제
    $(document).on('click', '.delete-comment-btn', function (e) {
        e.preventDefault();
        const commentId = $(this).data('comment-id');

        if (!confirm('댓글을 삭제하시겠습니까?')) {
            return;
        }

        $.ajax({
            url: `/api/comments/${commentId}`,
            method: 'DELETE',
            success: function () {
                loadComments();
            },
            error: function (xhr) {
                alert('댓글 삭제 실패: ' + (xhr.responseJSON?.message || '알 수 없는 오류'));
            }
        });
    });
});