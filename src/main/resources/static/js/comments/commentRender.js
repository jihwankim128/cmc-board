const CommentRender = {
    renderComments(comments) {
        const parentComments = comments.filter(c => c.depth === 0);

        return parentComments.map(comment => {
            const replies = comments.filter(c => c.parentId === comment.id);
            return `
                <div class="comment-item mb-3" data-comment-id="${comment.id}">
                    ${this.renderCommentContent(comment, false)}
                    
                    ${replies.length > 0 ? `
                        <div class="ms-5 mt-2">
                            ${replies.map(reply => `
                                <div class="comment-item mb-2" data-comment-id="${reply.id}">
                                    ${this.renderCommentContent(reply, true)}
                                </div>
                            `).join('')}
                        </div>
                    ` : ''}
                    
                    <div class="reply-form ms-5 mt-2" id="reply-form-${comment.id}" style="display:none;">
                        ${this.renderReplyForm(comment.id)}
                    </div>
                </div>
            `;
        }).join('');
    },

    renderCommentContent(comment, isReply) {
        const prefix = isReply ? '<i class="bi bi-arrow-return-right me-2"></i>' : '';
        const showReplyButton = comment.depth === 0;

        return `
            <div class="d-flex justify-content-between align-items-start">
                <div class="flex-grow-1">
                    <div class="d-flex align-items-center mb-1">
                        ${prefix}
                        <strong>${this.escapeHtml(comment.author.nickname)}</strong>
                        <small class="text-muted ms-2">${this.formatDate(comment.createdAt)}</small>
                    </div>
                    
                    <div class="comment-content-${comment.id}">
                        <p class="mb-1">${this.escapeHtml(comment.content)}</p>
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
                        ${this.renderEditForm(comment)}
                    </div>
                </div>
                
                ${comment.authorMe ? this.renderDropdown(comment.id) : ''}
            </div>
        `;
    },

    renderReplyForm(commentId) {
        return `
            <form class="reply-submit-form" data-parent-id="${commentId}">
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
        `;
    },

    renderEditForm(comment) {
        return `
            <form class="edit-submit-form" data-comment-id="${comment.id}">
                <div class="mb-2">
                    <textarea class="form-control edit-content" 
                              rows="2" 
                              style="resize: none;">${this.escapeHtml(comment.content)}</textarea>
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
        `;
    },

    renderDropdown(commentId) {
        return `
            <div class="dropdown">
                <button class="btn btn-sm btn-link text-muted" type="button" 
                        data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="bi bi-three-dots-vertical"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li>
                        <a class="dropdown-item edit-comment-btn" href="#" 
                           data-comment-id="${commentId}">
                            <i class="bi bi-pencil"></i> 수정
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item text-danger delete-comment-btn" href="#" 
                           data-comment-id="${commentId}">
                            <i class="bi bi-trash"></i> 삭제
                        </a>
                    </li>
                </ul>
            </div>
        `;
    },

    renderEmpty() {
        return `
            <p class="text-muted text-center py-4">
                <i class="bi bi-chat"></i> 첫 댓글을 작성해보세요!
            </p>
        `;
    },

    renderError() {
        return `
            <p class="text-danger text-center py-4">
                댓글을 불러오는데 실패했습니다.
            </p>
        `;
    },

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    },

    formatDate(dateString) {
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
};