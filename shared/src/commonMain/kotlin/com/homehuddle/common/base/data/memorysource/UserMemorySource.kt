package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.User

internal class UserMemorySource() : BaseMemorySource<User>() {

    override val parentIdName: String?
        get() = null
}