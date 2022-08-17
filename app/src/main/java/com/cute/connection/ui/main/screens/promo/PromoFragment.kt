package com.cute.connection.ui.main.screens.promo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.cute.connection.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PromoFragment : Fragment() {

    //private val viewModel: MainViewModel by activityViewModels()
    //private  var loadingAnimator: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_promo, container, false)
        //loadingAnimator =view.findViewById(R.id.loading_animator)
        view.findViewById<AppCompatButton>(R.id.copy_btn).setOnClickListener{
            findNavController().navigate(R.id.url_listing_fragment)
        }

        return view
    }

}