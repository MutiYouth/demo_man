package com.weng.demo_man.git_stats.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.weng.demo_man.git_stats.toolWindow.StatsTableModel
import com.weng.demo_man.git_stats.utils.GitUtils
import com.weng.demo_man.git_stats.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

@Service(Service.Level.PROJECT)
class GitStatsService(p: Project) {
    private var project: Project

    init {
        project = p
//        thisLogger().info(MyBundle.message("projectService", project.name))
    }

//    fun getRandomNumber() = (1..100).random()

    fun getUserStats(startTime: Date, endTime: Date): StatsTableModel {
        if (!Utils.checkDirectoryExists(project.basePath)) {
            return StatsTableModel(arrayOf(), arrayOf())
        }
        val gitUtils = GitUtils(project)
        val userStats = gitUtils.getUserStats(
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime),
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime)
        )
        val data = userStats.map { item ->
            arrayOf(
                item.author,
                item.commitCount.toString(),
                item.addedLines.toString(),
                item.deletedLines.toString(),
                item.modifiedFileCount.toString()
            )
        }.toTypedArray()
        return StatsTableModel(
            data,
            arrayOf("Author", "CommitCount", "AddedLines", "DeletedLines", "ModifiedFileCount")
        )
    }

    fun getTopSpeedUserStats(startTime: Date, endTime: Date): StatsTableModel {
        if (!Utils.checkDirectoryExists(project.basePath)) {
            return StatsTableModel(arrayOf(), arrayOf())
        }
        val gitUtils = GitUtils(project)
        val userStats = gitUtils.getTopSpeedUserStats(
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime),
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime)
        )
        val data = userStats.map { item ->
            arrayOf(
                item.author,
                item.addedLines.toString(),
                item.deletedLines.toString(),
                item.modifiedFileCount.toString()
            )
        }.toTypedArray()
        return StatsTableModel(
            data,
            arrayOf("Author", "AddedLines", "DeletedLines", "ModifiedFileCount")
        )
    }
}
