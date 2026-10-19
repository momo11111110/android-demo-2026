package com.example.experiment3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private data class Animal(val name: String, @DrawableRes val imgRes: Int)

/**
 * 任务五：使用 RecyclerView 实现列表视图效果。
 * 列表项包含动物图片与名称，单击名称后增加一条记录，列表立即更新。
 */
class RecyclerViewActivity : ComponentActivity() {

    private val data = mutableListOf(
        Animal("Lion", R.drawable.lion),
        Animal("Tiger", R.drawable.tiger),
        Animal("Monkey", R.drawable.monkey),
        Animal("Dog", R.drawable.dog),
        Animal("Cat", R.drawable.cat),
        Animal("Elephant", R.drawable.elephant)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val recyclerView = findViewById<RecyclerView>(R.id.rvAnimals)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AnimalAdapter(data) { position ->
            val animal = data[position]
            Toast.makeText(this, "点击了：${animal.name}", Toast.LENGTH_SHORT).show()

            // 增加一条记录，并立即通知列表更新（避免刷新整表造成错位）
            data.add(position + 1, Animal("${animal.name}（新增）", animal.imgRes))
            recyclerView.adapter?.notifyItemInserted(position + 1)
        }
    }
}

private class AnimalAdapter(
    private val items: List<Animal>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAnimal: ImageView = view.findViewById(R.id.ivAnimal)
        val tvName: TextView = view.findViewById(R.id.tvAnimalName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = items[position]
        holder.ivAnimal.setImageResource(animal.imgRes)
        holder.tvName.text = animal.name
        holder.itemView.setOnClickListener {
            // 使用绑定位置，保证点击对象正确、更新后不错位
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onClick(pos)
        }
    }

    override fun getItemCount(): Int = items.size
}
