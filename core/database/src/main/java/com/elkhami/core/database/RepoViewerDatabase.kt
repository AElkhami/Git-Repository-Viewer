package com.elkhami.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elkhami.core.database.dao.GitRepoDao
import com.elkhami.core.database.entity.GitRepoEntity

@Database(
    entities = [GitRepoEntity::class],
    version = 1
)
abstract class RepoViewerDatabase : RoomDatabase() {
    abstract val gitRepoDao: GitRepoDao
}