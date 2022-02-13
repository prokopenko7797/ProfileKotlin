package com.example.profilekot.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.profilekot.R
import com.example.profilekot.databinding.SignInFragmentBinding
import com.example.profilekot.databinding.SignUpFragmentBinding
import com.example.profilekot.viewModels.SignInViewModel
import com.example.profilekot.viewModels.SignUpViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: SignUpFragmentBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sign_up_fragment,
            container,
            false)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}