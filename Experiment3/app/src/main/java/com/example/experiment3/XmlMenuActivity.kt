package com.example.experiment3

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

/**
 * 任务三：使用 XML 定义菜单。
 * - 字体大小：小 / 中 / 大，分别对应 10 号字、16 号字和 20 号字；
 * - 普通菜单项：点击后弹出 Toast；
 * - 字体颜色：红色 / 黑色，点击后设置测试文本的字体颜色。
 */
class XmlMenuActivity : ComponentActivity() {

    private lateinit var etTest: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menutest)
        etTest = findViewById(R.id.etTest)
        title = "MenuTest"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.size_small -> etTest.setTextSize(10f)   // 小：10 号字
            R.id.size_medium -> etTest.setTextSize(16f)  // 中：16 号字
            R.id.size_large -> etTest.setTextSize(20f)   // 大：20 号字
            R.id.menu_normal -> Toast.makeText(this, "你点击了普通菜单项", Toast.LENGTH_SHORT).show()
            R.id.color_red -> etTest.setTextColor(Color.RED)
            R.id.color_black -> etTest.setTextColor(Color.BLACK)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
