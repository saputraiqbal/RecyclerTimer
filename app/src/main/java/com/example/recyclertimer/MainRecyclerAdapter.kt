package com.example.recyclertimer

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclertimer.databinding.ViewProgressBarBinding

internal class MainRecyclerAdapter(
  private val items: List<String>
): RecyclerView.Adapter<MainProgressBarViewHolder>() {
  private var currentPage = 0

  fun start() {
    notifyItemChanged(0, PAYLOADS_NEXT)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProgressBarViewHolder {
    return MainProgressBarViewHolder(
      ViewProgressBarBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: MainProgressBarViewHolder, position: Int) {
    with(holder.binding) {
      progressBar.max = 15000 * 100
    }
  }

  // article source : https://medium.com/livefront/recyclerview-trick-selectively-bind-viewholders-with-payloads-4b28e3d2cce8
  override fun onBindViewHolder(
    holder: MainProgressBarViewHolder,
    position: Int,
    payloads: MutableList<Any>
  ) {
    if (payloads.isNotEmpty()) {
      with(holder.binding) {
        payloads.forEach {
          println("payload $it at $position")

          val objectAnimator = ObjectAnimator.ofInt(
            progressBar,
            "progress",
            progressBar.progress,
            15000 * 100
          )

          objectAnimator.duration = 15000
          objectAnimator.interpolator = LinearInterpolator()
          objectAnimator.doOnEnd {
            Handler(Looper.getMainLooper()).post {
              currentPage++
              notifyItemChanged(currentPage, PAYLOADS_NEXT)
            }
          }
          objectAnimator.start()
        }
      }
    } else {
      onBindViewHolder(holder, position)
    }
  }

  override fun getItemCount(): Int = items.size

  companion object {
    private const val PAYLOADS_NEXT = "NEXT"
  }
}

internal class HorizontalMarginItemDecorator(
  private val size: Int
): RecyclerView.ItemDecoration() {
  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    super.getItemOffsets(outRect, view, parent, state)

    if (parent.getChildAdapterPosition(view) == 0) {
      outRect.left += size
    }

    outRect.right += size
  }
}

internal class MainProgressBarViewHolder(val binding: ViewProgressBarBinding)
  : RecyclerView.ViewHolder(binding.root)