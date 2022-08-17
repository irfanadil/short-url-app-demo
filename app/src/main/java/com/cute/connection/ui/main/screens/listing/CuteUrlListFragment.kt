package com.cute.connection.ui.main.screens.listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cute.connection.R
import com.cute.connection.api.GenericApiResponse
import com.cute.connection.extensions.hideKeyboard
import com.cute.connection.extensions.showToast
import com.cute.connection.ui.main.MainViewModel
import com.cute.connection.ui.main.model.UrlResultEntity
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CuteUrlListFragment : Fragment() , AdapterClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    private  var loadingAnimator: ProgressBar? = null
    private lateinit var historyRecycler: RecyclerView
    private lateinit var longUrlEditText:EditText
    private var urlEntityList: ArrayList<UrlResultEntity> = ArrayList()
    private lateinit var cuteUrlAdapter: CuteUrlAdapter
    private val linearLayoutManager = LinearLayoutManager(activity , LinearLayoutManager.VERTICAL, false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.e("hello")
        val view = inflater.inflate(R.layout.fragment_listing, container, false)
        longUrlEditText = view.findViewById(R.id.long_url_edit_text)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
        view.findViewById<AppCompatButton>(R.id.copy_btn).setOnClickListener{
            callApiToShortenUrl()
        }
        //viewModel.getAllStoredUrl()
        observeResponse()
        loadingAnimator = view.findViewById(R.id.loading_animator)
        historyRecycler = view.findViewById(R.id.history_recycler)

        cuteUrlAdapter = CuteUrlAdapter(urlEntityList, this@CuteUrlListFragment)
        historyRecycler.apply {
            this.layoutManager = linearLayoutManager
            this.setHasFixedSize(true)
            cuteUrlAdapter = CuteUrlAdapter(urlEntityList, this@CuteUrlListFragment)
            this.adapter = cuteUrlAdapter
        }

        return view
    }


    private fun callApiToShortenUrl(){
        hideKeyboard()
        viewModel.getShortUrl(longUrlEditText.text.toString())
    }

    private fun observeResponse(){
        viewModel.shortUrlResponse.observe(viewLifecycleOwner) { shortUrlResponse ->
            when (shortUrlResponse) {
                is GenericApiResponse.Failure -> {
                    shortUrlResponse.message?.let { requireContext().showToast(it) }
                }
                else -> {}
            }
        }

        viewModel.urlEntities.observe(viewLifecycleOwner) { liveUrlEntities ->
            urlEntityList.clear()
            urlEntityList.addAll( liveUrlEntities)
            cuteUrlAdapter.notifyDataSetChanged()
        }
    }

    

    override fun triggerCopyUrlClickEvent(position: Int) {
        //for(item in urlEntityList)
           // item.recycleItemState = true
        //urlEntityList[position].recycleItemState = false

    }

    override fun triggerDeleteUrlClickEvent(position: Int) {
        Logger.e("Delete event triggered "+urlEntityList[position].autoId)
        viewModel.deleteStoredUrlById(urlEntityList[position].autoId)
    }

}