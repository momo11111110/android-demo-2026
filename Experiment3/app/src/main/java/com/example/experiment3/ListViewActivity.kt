package com.example.experiment3

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

/**
 * 任务一：使用 SimpleAdapter 实现 ListView。
 * 列表项为「名称 + 图片」，单击显示 Toast，并发送一条通知
 * （通知 Icon 为应用图标，Title 为列表项内容）。
 */
class ListViewActivity : ComponentActivity() {

    private val animals = listOf(
        "Lion" to R.drawable.lion,
        "Tiger" to R.drawable.tiger,
        "Monkey" to R.drawable.monkey,
        "Dog" to R.drawable.dog,
        "Cat" to R.drawable.cat,
        "Elephant" to R.drawable.elephant
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)

        val listView = findViewById<ListView>(R.id.lvAnimals)

        // SimpleAdapter：Map 中的 image 值为资源 id，会自动绑定到 ImageView
        val data: List<Map<String, Any>> = animals.map {
            mapOf("name" to it.first, "image" to it.second)
        }
        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.item_animal,
            arrayOf("name", "image"),
            intArrayOf(R.id.tvName, R.id.ivImage)
        )
        listView.adapter = adapter

        createNotificationChannel()
        requestNotificationPermission()

        listView.setOnItemClickListener { _, _, position, _ ->
            val name = animals[position].first
            // 使用 Toast 显示选中的列表项信息
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
            // 单击后发送一条通知
            sendNotification(name)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "列表项通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE
            )
        }
    }

    private fun sendNotification(name: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)   // 通知 Icon 为应用程序图标
            .setContentTitle(name)                // Title 为列表项内容
            .setContentText("你单击了列表项：$name") // 通知内容自拟
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        getSystemService(NotificationManager::class.java).notify(name.hashCode(), notification)
    }

    companion object {
        private const val CHANNEL_ID = "animal_list_channel"
        private const val REQUEST_CODE = 100
    }
}
