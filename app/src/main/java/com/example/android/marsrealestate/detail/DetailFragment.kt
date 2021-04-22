package com.example.android.marsrealestate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.marsrealestate.databinding.FragmentDetailBinding

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentDetailBinding.inflate(inflater)

        val application = requireNotNull(activity).application

        val arguments = DetailFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = DetailViewModelFactory(arguments.selectedMarsProperty, application)

        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(DetailViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }
}