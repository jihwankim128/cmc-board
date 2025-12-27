const CommentApi = {
    getComments(postId) {
        return $.get(`/api/comments/posts/${postId}`);
    },
    createComment(postId, content) {
        return $.ajax({
            url: '/api/comments',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                postId: postId,
                content: content
            })
        });
    },
    updateComment(commentId, content) {
        return $.ajax({
            url: `/api/comments/${commentId}`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({content: content})
        });
    },
    deleteComment(commentId) {
        return $.ajax({
            url: `/api/comments/${commentId}`,
            method: 'DELETE'
        });
    },
    createReply(parentId, content) {
        return $.ajax({
            url: `/api/comments/${parentId}/reply`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                content: content
            })
        });
    }
};