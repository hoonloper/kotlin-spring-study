package com.example.simplecrud.domain.post

import com.example.simplecrud.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Post (
    @Id
    var id: Long,
    @Column
    var title: String,
    @Column
    var description: String,
): BaseEntity() {}