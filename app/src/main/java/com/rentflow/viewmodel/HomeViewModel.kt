package com.rentflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentflow.model.Bill
import com.rentflow.model.Tenant
import com.rentflow.repository.RentFlowRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(
        val tenant: Tenant,
        val totalBalance: Double,
        val nextDueDate: String,
        val statusStanding: String // "Paid Up", "Due Soon", "Overdue"
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel(private val repository: RentFlowRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun loadHomeData() {
        _uiState.update { HomeUiState.Loading }
        viewModelScope.launch {
            try {
                val profileDeferred = async { repository.getTenantProfile() }
                val billsDeferred = async { repository.getBills() }

                val profileResult = profileDeferred.await()
                val billsResult = billsDeferred.await()

                if (profileResult.isSuccess && billsResult.isSuccess) {
                    val tenant = profileResult.getOrThrow()
                    val bills = billsResult.getOrThrow()

                    // Calculate total outstanding balance (bills that are unpaid/overdue)
                    val unpaidBills = bills.filter { it.status.lowercase() != "paid" }
                    val totalBalance = unpaidBills.sumOf { it.amountDue }

                    // Determine nearest due date and status standing
                    val nextDueDate = unpaidBills.minByOrNull { it.dueDate }?.dueDate ?: "N/A"
                    
                    val statusStanding = when {
                        unpaidBills.any { it.status.lowercase() == "overdue" } -> "Overdue"
                        unpaidBills.isNotEmpty() -> "Due Soon"
                        else -> "Paid Up"
                    }

                    _uiState.update {
                        HomeUiState.Success(
                            tenant = tenant,
                            totalBalance = totalBalance,
                            nextDueDate = nextDueDate,
                            statusStanding = statusStanding
                        )
                    }
                } else {
                    val errorMsg = profileResult.exceptionOrNull()?.message 
                        ?: billsResult.exceptionOrNull()?.message 
                        ?: "Failed to load dashboard parameters."
                    _uiState.update { HomeUiState.Error(errorMsg) }
                }
            } catch (e: Exception) {
                _uiState.update { HomeUiState.Error(e.localizedMessage ?: "An unexpected error occurred.") }
            }
        }
    }
}
