package com.sim.board.repository

import com.sim.board.domain.QTag
import com.sim.board.domain.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface TagRepository: JpaRepository<Tag, Long>, CustomTagRepository {
    fun findByPostId(postId: Long): List<Tag>
}

interface CustomTagRepository {
    fun findPageBy(pageRequest: Pageable, tagName: String) : Page<Tag>
}

class CustomTagRepositoryImpl : CustomTagRepository, QuerydslRepositorySupport(Tag::class.java) {
    override fun findPageBy(pageRequest: Pageable, tagName: String): Page<Tag> {
        return from(QTag.tag)
            .join(QTag.tag.post).fetchJoin()
            .where(
                QTag.tag.name.eq(tagName)
            )
            .orderBy(QTag.tag.post.createdAt.desc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .fetchResults()
            .let { return PageImpl(it.results, pageRequest, it.total) }
    }
}