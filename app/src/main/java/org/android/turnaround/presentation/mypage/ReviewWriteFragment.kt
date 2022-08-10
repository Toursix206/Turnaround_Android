package org.android.turnaround.presentation.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentReviewWriteBinding
import org.android.turnaround.presentation.base.BaseFragment

@AndroidEntryPoint
class ReviewWriteFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentReviewWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewWriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}