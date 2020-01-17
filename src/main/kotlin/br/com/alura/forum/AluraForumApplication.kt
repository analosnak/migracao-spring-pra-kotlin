package br.com.alura.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableAsync
@EnableSpringDataWebSupport
@SpringBootApplication
class AluraForumApplication

fun main(args: Array<String>) {
    runApplication<AluraForumApplication>(*args)
}