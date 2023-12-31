package com.sim.board.domain

import jakarta.persistence.*

@Entity
class Tag(
    name: String,
    post: Post,
    createdBy: String
): BaseEntity(createdBy){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var name: String = name
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        private set
}