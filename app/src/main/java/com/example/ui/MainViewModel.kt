package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AdminMetrics
import com.example.data.model.PlatformType
import com.example.data.model.TrendingTopic
import com.example.data.model.User
import com.example.data.model.VideoScriptConcept
import com.example.data.model.ViralReport
import com.example.data.repository.ViralRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ViralRepository(application)

    val currentUser: StateFlow<User?> = repository.currentUser

    // Platform Context Switcher [ 🎵 TikTok ] vs [ 🔵 Facebook Reels & Pages ]
    private val _selectedPlatform = MutableStateFlow(PlatformType.TIKTOK)
    val selectedPlatform: StateFlow<PlatformType> = _selectedPlatform.asStateFlow()

    // Smart Input Engine
    private val _inputUrlOrHandle = MutableStateFlow("")
    val inputUrlOrHandle: StateFlow<String> = _inputUrlOrHandle.asStateFlow()

    // Loading & Glow State
    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _analysisError = MutableStateFlow<String?>(null)
    val analysisError: StateFlow<String?> = _analysisError.asStateFlow()

    // Current Analysis Post-Report & Scripts
    private val _currentReport = MutableStateFlow<ViralReport?>(null)
    val currentReport: StateFlow<ViralReport?> = _currentReport.asStateFlow()

    private val _currentScripts = MutableStateFlow<List<VideoScriptConcept>>(emptyList())
    val currentScripts: StateFlow<List<VideoScriptConcept>> = _currentScripts.asStateFlow()

    // Trending Category Filter
    private val _selectedTrendingCategory = MutableStateFlow("All")
    val selectedTrendingCategory: StateFlow<String> = _selectedTrendingCategory.asStateFlow()

    // Billing / Subscription Modal State
    private val _isUpgradeModalOpen = MutableStateFlow(false)
    val isUpgradeModalOpen: StateFlow<Boolean> = _isUpgradeModalOpen.asStateFlow()

    // Admin Dashboard State
    val customAdminApiKey: StateFlow<String> = repository.customAdminApiKey
    private val _adminMetrics = MutableStateFlow<AdminMetrics?>(null)
    val adminMetrics: StateFlow<AdminMetrics?> = _adminMetrics.asStateFlow()

    val allUsers: StateFlow<List<User>> = repository.getAllUsersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedScripts: StateFlow<List<VideoScriptConcept>> = repository.getAllSavedScriptsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Auto-login default super admin for instant preview if no session exists
        viewModelScope.launch {
            repository.loginOrRegister("landolyrics209@gmail.com", "Lando Admin")
            refreshAdminMetrics()
        }
    }

    fun onPlatformSelected(platform: PlatformType) {
        _selectedPlatform.value = platform
    }

    fun onInputUrlChanged(input: String) {
        _inputUrlOrHandle.value = input
        _analysisError.value = null
    }

    fun onTrendingCategorySelected(category: String) {
        _selectedTrendingCategory.value = category
    }

    fun toggleUpgradeModal(isOpen: Boolean) {
        _isUpgradeModalOpen.value = isOpen
    }

    fun runViralAnalysis() {
        val input = _inputUrlOrHandle.value.trim()
        if (input.isBlank()) {
            _analysisError.value = "Veuillez entrer un lien de vidéo, de profil ou un nom d'utilisateur."
            return
        }

        val user = currentUser.value
        if (user != null && !user.isPro && !user.isSuperAdmin && user.credits <= 0) {
            _isUpgradeModalOpen.value = true
            return
        }

        viewModelScope.launch {
            _isAnalyzing.value = true
            _analysisError.value = null
            try {
                val (report, scripts) = repository.generateViralReport(
                    platform = _selectedPlatform.value,
                    urlOrHandle = input
                )
                _currentReport.value = report
                _currentScripts.value = scripts
            } catch (e: Exception) {
                _analysisError.value = "Erreur d'analyse: ${e.message}"
            } finally {
                _isAnalyzing.value = false
            }
        }
    }

    fun loginWithEmail(email: String, name: String = "") {
        viewModelScope.launch {
            repository.loginOrRegister(email, name)
            refreshAdminMetrics()
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun upgradeCurrentToPro() {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            repository.upgradeToPro(user.email)
            _isUpgradeModalOpen.value = false
        }
    }

    fun toggleUserTierAdmin(email: String, makePro: Boolean) {
        viewModelScope.launch {
            if (makePro) {
                repository.upgradeToPro(email)
            } else {
                repository.updateUserCredits(email, 5)
            }
        }
    }

    fun updateUserCreditsAdmin(email: String, credits: Int) {
        viewModelScope.launch {
            repository.updateUserCredits(email, credits)
        }
    }

    fun saveAdminApiKey(key: String) {
        viewModelScope.launch {
            repository.setCustomAdminApiKey(key)
        }
    }

    fun refreshAdminMetrics() {
        viewModelScope.launch {
            _adminMetrics.value = repository.getAdminMetrics()
        }
    }

    fun analyzeTrendTopic(topic: TrendingTopic) {
        _selectedPlatform.value = if (topic.platform.contains("Facebook")) PlatformType.FACEBOOK else PlatformType.TIKTOK
        _inputUrlOrHandle.value = "@${topic.title.replace(" ", "").lowercase()}"
        runViralAnalysis()
    }
}
