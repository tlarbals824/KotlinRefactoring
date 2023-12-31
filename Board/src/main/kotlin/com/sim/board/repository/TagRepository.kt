package com.sim.board.repository

import com.sim.board.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
    fun findByPostId(postId: Long): List<Tag>
}