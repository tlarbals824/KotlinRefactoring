package com.sim.board.domain

import jakarta.persistence.*

@Entity
class Comment(
    content: String,
    post: Post,
    createdBy: String
) : BaseEntity(createdBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        private set

    fun update(content: String, updatedBy: String) {
        this.content = content
        this.update(updatedBy)
    }
}
