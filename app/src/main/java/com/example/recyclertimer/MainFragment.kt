package com.example.recyclertimer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclertimer.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(R.layout.fragment_main) {
  private lateinit var binding: FragmentMainBinding
  private lateinit var mainAdapter: MainRecyclerAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)
    binding = FragmentMainBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val testList = listOf(
      "1",
      "2",
      "3",
    )

    binding.recyclerView.apply {
      if (itemDecorationCount == 0) {
        addItemDecoration(
          HorizontalMarginItemDecorator(8)
        )
      }

      layoutManager = GridLayoutManager(requireContext(), testList.size)
      setHasFixedSize(true)

      mainAdapter = MainRecyclerAdapter(testList)

      this.adapter = mainAdapter
    }

    /* Fires the [notifyItemChanged(index, payload)] after onClick event
    Result: notifyItemChanged is fired, and payload has been executed
     */
    /*binding.btnTimer.setOnClickListener {
      (binding.recyclerView.adapter as MainRecyclerAdapter)
        .start()
    }*/

    /* Fires the [notifyItemChanged(index, payload)] right onCreateView fired
    Result: notifyItemChanged is not fired, and payload has been not executed
     */
    // (binding.recyclerView.adapter as MainRecyclerAdapter).start()

    /* Fires the [notifyItemChanged(index, payload)] using [Handler().postDelayed]
    Result:
    - notifyItemChanged is not fired, and payload has been not executed.
    - After onStop, when fragment is onResume, notifyItemChanged is fired.
     */
    /*Handler(Looper.getMainLooper()).postDelayed({
      (binding.recyclerView.adapter as MainRecyclerAdapter).start()
    }, 50)*/

    /* Fires the [notifyItemChanged(index, payload)] using [Handler().post]
    Result: notifyItemChanged is not fired, and payload has been not executed.
     */
    /*Handler(Looper.getMainLooper()).post {
      (binding.recyclerView.adapter as MainRecyclerAdapter).start()
    }*/

    /* Fires the [notifyItemChanged(index, payload)] using [RecyclerView.addOnLayoutChangeListener]
    (source : https://stackoverflow.com/q/41992586)
    Result: notifyItemChanged is fired, and payload has been executed.
     */
    binding.recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
      mainAdapter.start()
    }
  }
}