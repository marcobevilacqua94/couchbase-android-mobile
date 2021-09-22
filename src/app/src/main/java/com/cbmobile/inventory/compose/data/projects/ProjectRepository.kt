package com.cbmobile.inventory.compose.data.projects

import com.cbmobile.inventory.compose.models.Location
import com.cbmobile.inventory.compose.models.Project
import com.couchbase.lite.DocumentChange
import com.couchbase.lite.QueryChange
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    suspend fun saveProject(project: Project)

    fun getProjectsFlow(): Flow<List<Project>>?

    suspend fun getProject(projectId: String): Project

    suspend fun deleteProject(projectId: String) : Boolean

    suspend fun completeProject(projectId: String)

    suspend fun initializeDatabase()
}