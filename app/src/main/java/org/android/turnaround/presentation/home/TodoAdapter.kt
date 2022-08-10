package org.android.turnaround.presentation.home

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.R
import org.android.turnaround.databinding.ItemTodoBinding
import org.android.turnaround.domain.entity.Todo
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

class TodoAdapter: ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {
    private lateinit var binding: ItemTodoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setTodoStyle(getItem(position))
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }

        fun setTodoStyle(todo: Todo) {
            val cal =  Calendar.getInstance()
            val today = cal.time

            var diff = Duration.between(today.toInstant(), todo.startTime.toInstant())
            val days = diff.toDays()
            diff = diff.minusDays(days)
            val hours = diff.toHours()

            // D-1 초과
            if(days > 0) setTodoReadyStyle(days+1, binding)
            // D-1 이하
            else {
                if(hours > 0) setTodoTimerStyle(today, todo, binding)
                else setTodoStartStyle(todo, binding)
            }
        }
    }

    private fun setTodoReadyStyle(dDay: Long, binding: ItemTodoBinding) {
        binding.viewContext.setBackgroundResource(R.drawable.bg_todo_gray_r20)
        binding.tvTime.text = "D-$dDay"
    }

    private fun setTodoTimerStyle(today: Date, todo: Todo, binding: ItemTodoBinding) {
        binding.viewContext.setBackgroundResource(R.drawable.bg_todo_purple_r20)

        var diff = Duration.between(today.toInstant(), todo.startTime.toInstant())
        val days = diff.toDays()
        diff = diff.minusDays(days)
        val hours = diff.toHours()
        diff = diff.minusHours(hours)
        val minutes = diff.toMinutes()
        diff = diff.minusMinutes(minutes)
        val seconds = diff.toMillis() / 1000
        val mFormat = SimpleDateFormat("HH:mm:ss")
        val diffTime = hours*60*60*1000 + minutes*60*1000

        val countDown = Calendar.getInstance().apply {
            timeInMillis = 0
            set(Calendar.HOUR_OF_DAY, hours.toInt())
            set(Calendar.MINUTE, minutes.toInt())
            set(Calendar.SECOND, seconds.toInt())
            set(Calendar.MILLISECOND, 0)
        }

        binding.tvTime.text = mFormat.format(countDown.timeInMillis)

        object : CountDownTimer(diffTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDown.add(Calendar.SECOND, -1)
                binding.tvTime.text = mFormat.format(countDown.timeInMillis)
            }
            override fun onFinish() {  }
        }.start()

    }

    private fun setTodoStartStyle(todo: Todo, binding: ItemTodoBinding) {
        binding.viewContext.setBackgroundResource(R.drawable.bg_black_r20)

        val mFormat = SimpleDateFormat("HH시 mm분까지")
        binding.tvTime.text = mFormat.format(todo.endTime)
        binding.tvTitle.setTextColor(ContextCompat.getColor(binding.tvTitle.context, R.color.ta_cb43ff))

        binding.viewContext.setOnClickListener {
            binding.tvStartTodo.visibility = if(binding.tvStartTodo.isVisible) View.INVISIBLE else View.VISIBLE
            binding.ivStartTodo.visibility = if(binding.ivStartTodo.isVisible) View.INVISIBLE else View.VISIBLE
        }
    }
}

class TodoDiffCallback: DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.label == newItem.label
    }
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}