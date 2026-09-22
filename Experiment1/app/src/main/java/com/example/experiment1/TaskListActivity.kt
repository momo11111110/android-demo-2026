package com.example.experiment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.experiment1.ui.theme.Experiment1Theme

/** 实验任务五：使用 Android Compose 实现可增删、可勾选的课程学习任务清单 */
class TaskListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Experiment1Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    TaskListScreen()
                }
            }
        }
    }
}

private data class Task(val id: Int, val name: String, val done: Boolean)

private val Red = Color(0xFFC62828)
private val TitleRed = Color(0xFF8A1F1F)
private val GrayText = Color(0xFF777777)

@Composable
private fun TaskListScreen() {
    // 初始状态：3 项任务，完成 1 项
    val tasks: SnapshotStateList<Task> = remember {
        listOf(
            Task(1, "学习 Column 和 Row", true),
            Task(2, "学习状态管理", false),
            Task(3, "完成 Compose 实验", false)
        ).toMutableStateList()
    }
    var input by remember { mutableStateOf("") }
    var nextId by remember { mutableIntStateOf(4) }

    val doneCount = tasks.count { it.done }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Text(
            text = "课程学习任务",
            color = TitleRed,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 18.dp, end = 16.dp, bottom = 14.dp)
        )

        // 输入框 + 添加按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                placeholder = { Text("请输入学习任务", fontSize = 13.sp) },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    val name = input.trim()
                    if (name.isNotEmpty()) {
                        tasks.add(Task(nextId, name, false))
                        nextId++
                        input = ""
                    }
                },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("添加")
            }
        }

        Text(
            text = "已完成：$doneCount / ${tasks.size}",
            color = GrayText,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 4.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(tasks, key = { it.id }) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { checked ->
                            val index = tasks.indexOfFirst { it.id == task.id }
                            if (index >= 0) {
                                tasks[index] = task.copy(done = checked)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Red,
                            uncheckedColor = Color(0xFF888888),
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = task.name,
                        modifier = Modifier.weight(1f),
                        color = if (task.done) GrayText else Color(0xFF333333),
                        fontSize = 14.sp,
                        // 勾选后文字同步出现删除线
                        textDecoration = if (task.done) TextDecoration.LineThrough else null
                    )
                    TextButton(
                        onClick = { tasks.removeAll { it.id == task.id } }
                    ) {
                        Text("删除", color = Red, fontSize = 12.sp)
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    color = Color(0xFFEEEEEE),
                    thickness = 0.8.dp
                )
            }
        }
    }
}
