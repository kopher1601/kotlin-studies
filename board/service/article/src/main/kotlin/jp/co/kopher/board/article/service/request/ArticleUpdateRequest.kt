package jp.co.kopher.board.article.service.request

data class ArticleUpdateRequest(
    val title: String,
    val content: String,
)