package jp.co.kopher1601.kopherlog.request

import jakarta.validation.constraints.NotBlank

data class PostCreate(
    @field:NotBlank(message = "타이틀을 입력하세요")
    val title: String,

    @field:NotBlank(message = "내용을 입력하세요")
    val content: String,
)
