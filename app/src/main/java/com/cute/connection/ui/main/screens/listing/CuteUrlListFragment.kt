package com.cute.connection.ui.main.screens.listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.api.UrlViewState
import com.cute.connection.databinding.FragmentListingBinding
import com.cute.connection.extensions.hide
import com.cute.connection.extensions.hideKeyboard
import com.cute.connection.extensions.show
import com.cute.connection.extensions.showToast
import com.cute.connection.ui.main.MainViewModel
import com.cute.connection.ui.main.model.UrlResultEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CuteUrlListFragment : Fragment(), AdapterClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var cuteUrlAdapter: CuteUrlAdapter
    private val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

    private lateinit var _binding: FragmentListingBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListingBinding.inflate(inflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
        binding.copyBtn.setOnClickListener {
            callApiToShortenUrl()
        }
        setupUI()
        observeResponse()
        return binding.root
    }

    private fun setupUI() {
        binding.historyRecycler.apply {
            this.layoutManager = linearLayoutManager
            this.setHasFixedSize(true)
            cuteUrlAdapter = CuteUrlAdapter(this@CuteUrlListFragment)
            this.adapter = cuteUrlAdapter
        }
    }

    private fun observeResponse() {
        // Using LiveData to get remote shorten url data....
        viewModel.shortUrlResponse.observe(viewLifecycleOwner) { shortUrlResponse ->
            binding.progress.hide()
            when (shortUrlResponse) {
                is GenericApiResponse.Failure -> {
                    shortUrlResponse.message?.let { requireContext().showToast("error = $it") }
                }
                is GenericApiResponse.Success -> {
                    requireContext().showToast("Success block")
                    if (shortUrlResponse.value.urlResultEntity == null)
                        shortUrlResponse.value.responseErrors.errorMessage?.let {
                            requireContext().showToast(it)
                        }
                }
                else -> {}
            }
        }

        // Using Flow to receive the data from the room data...
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect() { uiState ->
                    when (uiState) {
                        is UrlViewState.Loading -> binding.progress.show()
                        is UrlViewState.Success -> {
                            binding.progress.hide()
                            onUrlLoaded(uiState.urls)
                        }
                        is UrlViewState.Error -> {
                            binding.progress.hide()
                            requireActivity().showToast("Error...")
                        }
                        is UrlViewState.Empty -> {
                            binding.progress.hide()
                            showEmptyState()
                        }
                    }
                }
            }
        }
    }

    private fun callApiToShortenUrl() {
        hideKeyboard()
        binding.progress.show()
        viewModel.getShortUrl(binding.longUrlEditText.text.toString()) // Method-1  Using LiveData....
    }

    private fun showEmptyState() {
        cuteUrlAdapter.differ.submitList(emptyList())
    }

    private fun onUrlLoaded(notes: List<UrlResultEntity>) {
        cuteUrlAdapter.differ.submitList(notes)
    }

    override fun copyUrlEvent(position: Int) {
        for (holder in cuteUrlAdapter.differ.currentList)
            holder.recycleItemState = false
        cuteUrlAdapter.differ.currentList[position].recycleItemState = true
        binding.historyRecycler.post {
            cuteUrlAdapter.notifyDataSetChanged()
        }
    }

    override fun deleteUrlEvent(position: Int) {
        viewModel.deleteStoredUrlById(cuteUrlAdapter.differ.currentList[position].autoId)
    }

}