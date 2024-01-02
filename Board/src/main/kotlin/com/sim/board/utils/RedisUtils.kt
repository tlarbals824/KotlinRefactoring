package com.sim.board.utils

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component


@Component
class RedisUtils (
    private val redisTemplate: RedisTemplate<String, Any>
){
    fun setData(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun getData(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    fun deleteData(key: String) {
        redisTemplate.delete(key)
    }

    fun increment(key: String) {
        redisTemplate.opsForValue().increment(key, 1L)
    }
}