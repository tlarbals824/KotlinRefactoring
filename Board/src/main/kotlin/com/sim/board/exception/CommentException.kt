package com.sim.board.exception

open class CommentException(message: String) : RuntimeException(message)

class CommentNotFoundException : CommentException("해당 댓글을 찾을 수 없습니다.")

class CommentNotUpdatableException : CommentException("수정할 수 없는 댓글 입니다.")

class CommentNotDeletableException : CommentException("삭제할 수 없는 댓글 입니다.")