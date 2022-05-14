package com.assessment.soonet.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.soonet.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private var auth: FirebaseAuth? = null

    private val viewModel by viewModels<DashboardViewModel>()

    private val adapter by lazy {
        DashboardAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ((auth!!.currentUser != null)) {
            initObserver()
            initRecyclerView()
            initListeners()
            initViewBinding()
        }
    }

    private fun initObserver() {
        viewModel.pagingEvent.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }

        adapter.addLoadStateListener {
            if (it.refresh is LoadState.NotLoading || it.refresh is LoadState.Error) {
                if (adapter.itemCount == 0) {
                    binding.layoutEmpty.emptyRoot.visibility = View.VISIBLE
                } else {
                    binding.layoutEmpty.emptyRoot.visibility = View.GONE
                }
            }

        }
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerViewPhotos.layoutManager = LinearLayoutManager(activity)
            recyclerViewPhotos.adapter = adapter
        }
    }

    private fun initListeners() {
        binding.apply {
            layoutEmpty.buttonTry.setOnClickListener {
                adapter.retry()
            }
        }
    }

    private fun initViewBinding() {
        binding.apply {
            layoutEmpty.buttonTry.transformationMethod = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.jobCancel()
    }
}