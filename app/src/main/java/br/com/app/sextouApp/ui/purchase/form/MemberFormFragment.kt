package br.com.app.sextouApp.ui.purchase.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentMemberFormBinding
import br.com.app.sextouApp.repository.ListenerCrudFirebase
import br.com.app.sextouApp.ui.purchase.PurchaseViewModel

class MemberFormFragment : Fragment() {
    private lateinit var binding: FragmentMemberFormBinding
    val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(PurchaseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemberFormBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.memberFormButton.setOnClickListener { submitForm() }
        return binding.root
    }

    fun submitForm(){
        if (viewModel.validateMemberForm()){
            viewModel.submitMemberForm(::onSearchError, onAddMemberListener)
        }else{
            binding.memberFormEmail.error = getString(R.string.insert_valid_value)
        }
    }

    fun onSearchError(){
        binding.memberFormEmail.error = getString(R.string.user_not_found)
    }

    val onAddMemberListener = object : ListenerCrudFirebase{
        override fun onSuccess() {
            findNavController().navigateUp()
        }

        override fun onError() {
            Toast.makeText(requireContext(), "Couldn't add member to list", Toast.LENGTH_LONG).show()
        }

    }

}