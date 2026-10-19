package com.example.experiment3

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

/**
 * 任务二：创建自定义布局的 AlertDialog。
 * 通过 AlertDialog.Builder 的 setView() 将自定义布局（橙色标题栏 +
 * Username / Password 输入框）添加到对话框，并附带 Cancel / Sign in 按钮。
 */
class CustomDialogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        findViewById<Button>(R.id.btnShowDialog).setOnClickListener { showCustomDialog() }

        // 进入页面即展示一次对话框
        showCustomDialog()
    }

    private fun showCustomDialog() {
        val contentView = layoutInflater.inflate(R.layout.dialog_login, null)

        AlertDialog.Builder(this)
            .setView(contentView)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Sign in") { _, _ ->
                val username = contentView.findViewById<EditText>(R.id.etUsername).text.toString()
                val password = contentView.findViewById<EditText>(R.id.etPassword).text.toString()
                Toast.makeText(
                    this,
                    "Sign in：$username / $password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }
}
