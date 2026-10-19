package com.example.experiment3

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

/**
 * 任务四：创建上下文操作模式（ActionMode）的上下文菜单。
 * 使用 ListView 展示数据，长按（或选择）列表项进入 ActionMode 多选模式，
 * 顶部显示「已选数量」与删除图标，点击删除按钮移除选中项。
 */
class ActionModeActivity : ComponentActivity() {

    private lateinit var listView: ListView
    private val items = mutableListOf("One", "Two", "Three", "Four", "Five")
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actionmode)

        listView = findViewById(R.id.lvItems)
        adapter = ArrayAdapter(this, R.layout.item_android, R.id.tvLabel, items)
        listView.adapter = adapter

        // 多选模式的上下文操作栏（ActionMode）
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        listView.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                menuInflater.inflate(R.menu.menu_actionmode, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                mode.title = "已选 ${listView.checkedItemCount} 项"
                return true
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                if (item.itemId == R.id.action_delete) {
                    val checked = listView.checkedItemPositions
                    val toRemove = items.indices.filter { checked.get(it) }.map { items[it] }
                    items.removeAll(toRemove.toSet())
                    adapter.notifyDataSetChanged()
                    mode.finish()
                    Toast.makeText(
                        this@ActionModeActivity,
                        "已删除 ${toRemove.size} 项",
                        Toast.LENGTH_SHORT
                    ).show()
                    return true
                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) = Unit

            override fun onItemCheckedStateChanged(
                mode: ActionMode,
                position: Int,
                id: Long,
                checked: Boolean
            ) {
                // 刷新 ActionBar 中已选数量的标题
                mode.title = "已选 ${listView.checkedItemCount} 项"
            }
        })
    }
}
