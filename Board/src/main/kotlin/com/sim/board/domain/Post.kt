package com.sim.board.domain

import jakarta.persistence.*

@Entity
class Post(
    createBy: String,
    title: String,
    content: String
) : BaseEntity(createBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        private set
    var content: String = content
        private set

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = [CascadeType.ALL])
    var comments: MutableList<Comment> = mutableListOf()
        private set

    fun update(title: String, content: String, updatedBy: String) {
        this.title = title
        this.content = content
        super.update(updatedBy)
    }
}
