package com.sim.board.domain

import jakarta.persistence.*

@Entity
class Post(
    createBy: String,
    title: String,
    content: String,
    tags: List<String>
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

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = [CascadeType.ALL])
    var tags: MutableList<Tag> = tags.map { Tag(it, this, createBy) }.toMutableList()
        private set

    fun update(title: String, content: String, updatedBy: String, tags: List<String>) {
        this.title = title
        this.content = content
        super.update(updatedBy)
        updateTag(tags, updatedBy)
    }

    private fun updateTag(tags: List<String>, updatedBy: String) {
        if(this.tags.map { it.name }.toSet() == tags.toSet()) return
        this.tags.clear()
        this.tags.addAll(tags.map { Tag(it, this, updatedBy) })
    }
}
