package com.rentflow.ui.home

import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rentflow.databinding.FragmentHomeBinding
import com.rentflow.network.RetrofitClient
import com.rentflow.repository.RentFlowRepository
import com.rentflow.util.SessionManager
import com.rentflow.viewmodel.HomeUiState
import com.rentflow.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val sessionManager = SessionManager(requireContext().applicationContext)
                val apiService = RetrofitClient.getApiService(sessionManager)
                val repository = RentFlowRepository(apiService)
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModel()
        updateProfileInitial("Brian Otieno")
        viewModel.loadHomeData()
    }

    private fun updateProfileInitial(name: String) {
        val initials = name.split(" ")
            .filter { it.isNotEmpty() }
            .take(2)
            .map { it[0].uppercaseChar() }
            .joinToString("")
        
        binding.profileInitialText.text = initials
        binding.profileInitialText.visibility = View.VISIBLE
        binding.profileImage.visibility = View.GONE
    }

    private fun setupListeners() {
        binding.copyPaybillButton.setOnClickListener {
            copyToClipboard("Paybill", "400200")
        }

        binding.copyAccountButton.setOnClickListener {
            val accountNum = binding.accountNumberTextView.text.toString()
            copyToClipboard("Account Number", accountNum)
        }

        binding.callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+254700000000"))
            startActivity(intent)
        }

        binding.whatsappButton.setOnClickListener {
            val url = "https://wa.me/254700000000"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.profileIcon.setOnClickListener {
            Toast.makeText(context, "Opening profile settings...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyToClipboard(label: String, text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun observeViewModel() {
        // Mock data injection for UI testing on physical device
        // These IDs now match the refactored high-fidelity layout
        binding.welcomeTextView.text = "Hi, Brian Otieno 👋"
        binding.unitTextView.text = "Kira Plaza · Unit 4A"
        binding.balanceTextView.text = "15,400.00"
        binding.dueDateTextView.text = "05 October 2026"
        binding.accountNumberTextView.text = "KIRA-A1"

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                // Live integration logic can go here
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
