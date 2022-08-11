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

private data class DateTime(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)

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
            val diff = getDiffDateTime(todo.startTime)

            // D-1 초과
            if(diff.days > 0) setTodoReadyStyle(diff, todo, binding)
            // D-1 이하
            else {
                if(diff.hours > 0 || diff.minutes > 0 || diff.seconds > 0) setTodoTimerStyle(diff, todo, binding)
                else setTodoStartStyle(todo, binding)
            }
        }
    }

    private fun setTodoReadyStyle(diff: DateTime, todo: Todo, binding: ItemTodoBinding) {
        binding.viewContext.setBackgroundResource(R.drawable.bg_todo_gray_r20)
        binding.tvTime.text = "D-${diff.days + 1}"

        // 카운트 다운 시간 설정
        val countDown = getCountDownDateTime(diff)
        // 카운트 다운 시작
        val tickCount = diff.hours*60*60*1000 + diff.minutes*60*1000 + diff.seconds*1000
        object : CountDownTimer(tickCount, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDown.add(Calendar.SECOND, -1)
            }
            override fun onFinish() { setTodoTimerStyle(getDiffDateTime(todo.startTime), todo, binding) }
        }.start()
    }

    private fun setTodoTimerStyle(diff: DateTime, todo: Todo, binding: ItemTodoBinding) {
        binding.viewContext.setBackgroundResource(R.drawable.bg_todo_purple_r20)
        // 카운트 다운 시간 설정
        val mFormat = SimpleDateFormat("HH:mm:ss")
        val countDown = getCountDownDateTime(diff)
        // 카운트 다운 시작
        val tickCount = diff.days*24*60*60*1000 + diff.hours*60*60*1000 + diff.minutes*60*1000 + diff.seconds*1000
        object : CountDownTimer(tickCount, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDown.add(Calendar.SECOND, -1)
                binding.tvTime.text = mFormat.format(countDown.timeInMillis)
            }
            override fun onFinish() { setTodoStartStyle(todo, binding) }
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

    private fun getDiffDateTime(startTime: Date): DateTime {
        val cal =  Calendar.getInstance()
        val today = cal.time
        var diff = Duration.between(today.toInstant(), startTime.toInstant())
        val days = diff.toDays()
        diff = diff.minusDays(days)
        val hours = diff.toHours()
        diff = diff.minusHours(hours)
        val minutes = diff.toMinutes()
        diff = diff.minusMinutes(minutes)
        val seconds = diff.toMillis() / 1000
        return DateTime(days, hours, minutes, seconds)
    }
    private fun getCountDownDateTime(diff: DateTime): Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = 0
            set(Calendar.DATE, diff.days.toInt())
            set(Calendar.HOUR_OF_DAY, diff.hours.toInt())
            set(Calendar.MINUTE, diff.minutes.toInt())
            set(Calendar.SECOND, diff.seconds.toInt())
            set(Calendar.MILLISECOND, 0)
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