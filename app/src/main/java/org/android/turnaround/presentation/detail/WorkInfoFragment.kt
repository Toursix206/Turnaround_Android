package org.android.turnaround.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentWorkInfoBinding
import org.android.turnaround.presentation.base.BaseFragment

@AndroidEntryPoint
class WorkInfoFragment : BaseFragment<FragmentWorkInfoBinding>(R.layout.fragment_work_info) {

    private val workInfoRvAdapter: WorkInfoRvAdapter by lazy { WorkInfoRvAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWorkInfoImageAdapter()
    }

    private fun initWorkInfoImageAdapter() {
        binding.rvWork.adapter = workInfoRvAdapter
        binding.rvWork.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        workInfoRvAdapter.notifyDataSetChanged()
    }

}