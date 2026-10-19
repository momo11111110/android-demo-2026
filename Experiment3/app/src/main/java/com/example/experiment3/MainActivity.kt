package com.example.experiment3

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
import com.example.experiment3.ui.theme.Experiment3Theme

/** 实验三入口：5 个界面组件任务的导航菜单 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Experiment3Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MenuScreen(onOpen = { cls -> startActivity(Intent(this, cls)) })
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
            text = "实验三  Android界面组件",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        MenuButton("1. ListView 与 SimpleAdapter") { onOpen(ListViewActivity::class.java) }
        MenuButton("2. 自定义布局的 AlertDialog") { onOpen(CustomDialogActivity::class.java) }
        MenuButton("3. XML 定义菜单") { onOpen(XmlMenuActivity::class.java) }
        MenuButton("4. ActionMode 上下文菜单") { onOpen(ActionModeActivity::class.java) }
        MenuButton("5. RecyclerView 列表视图") { onOpen(RecyclerViewActivity::class.java) }
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
