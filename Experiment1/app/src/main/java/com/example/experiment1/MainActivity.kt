package com.example.experiment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.experiment1.ui.theme.Experiment1Theme

/** 实验二入口：5 个实验界面的导航菜单 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Experiment1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MenuScreen(
                        onOpen = { cls ->
                            startActivity(Intent(this, cls))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuScreen(onOpen: (Class<*>) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "实验二  Android界面布局",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        MenuButton("1. 线性布局 LinearLayout") { onOpen(LinearLayoutActivity::class.java) }
        MenuButton("2. 表格布局 TableLayout") { onOpen(TableLayoutActivity::class.java) }
        MenuButton("3. 约束布局 ConstraintLayout（计算器）") { onOpen(CalculatorActivity::class.java) }
        MenuButton("4. 约束布局 ConstraintLayout（太空订票）") { onOpen(SpaceBookingActivity::class.java) }
        MenuButton("5. Android Compose（课程学习任务）") { onOpen(TaskListActivity::class.java) }
    }
}

@Composable
private fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text, fontSize = 15.sp)
    }
}
