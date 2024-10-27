package jp.co.kopher1601.kotlinpractice.domain.repository

import jp.co.kopher1601.kotlinpractice.domain.entity.HttpInterface
import org.springframework.data.jpa.repository.JpaRepository

interface HttpInterfaceRepository: JpaRepository<HttpInterface, Long>