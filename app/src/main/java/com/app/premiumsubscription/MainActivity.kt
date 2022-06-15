package com.app.premiumsubscription

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.app.premiumsubscription.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var onDestinationChangedListener: NavController.OnDestinationChangedListener
     val viewModel by viewModels<MainViewModel>()
    var boolean = false
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.navifationView.getHeaderView(0).findViewById<CardView>(R.id.signoutbt)
            .setOnClickListener {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("1062016098229-3o743rla6shfnegqbom6177ri840g3c7.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
                GoogleSignIn.getClient(this, gso).signOut()
                firebaseAuth.signOut()
                initLoggedInInfo()
            }

        binding.navifationView.layoutParams?.width =
            ((resources.displayMetrics.widthPixels) / 100) * 85
        initLoggedInInfo()


        binding.topAppBar.setNavigationOnClickListener {
            if (boolean) {
                onBackPressedDispatcher.onBackPressed()
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        onDestinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.FirstFragment ||
                    destination.id == R.id.SecondFragment
                ) {
                    binding.topAppBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.openicon)
                    boolean = false
                } else {
                    boolean = true
                    binding.topAppBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.backicon)
                }
                if (destination.id == R.id.FirstFragment) {
                    binding.topAppBar.title = "Sign-In"
                }

                if (destination.id == R.id.SecondFragment) {
                    binding.topAppBar.title = "Happy Shopping \uD83D\uDE0A"
                }
                if (destination.id == R.id.premiumSubscriptionDetailFragment) {
                    binding.topAppBar.title = "Premium Subscription"
                }
                if (destination.id == R.id.vouchersDetailFragment) {
                    binding.topAppBar.title = "Voucher"
                }
            }
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    @SuppressLint("SetTextI18n")
    fun initLoggedInInfo() {
        val im = binding.navifationView.getHeaderView(0).findViewById<ImageView>(R.id.profile)
        val userName = binding.navifationView.getHeaderView(0).findViewById<TextView>(R.id.username)
        firebaseAuth.currentUser?.let {
            binding.navifationView.getHeaderView(0)
                .findViewById<CardView>(R.id.signoutbt).visibility = View.VISIBLE
            Glide.with(im).load(it.photoUrl).circleCrop().into(im)
            userName.text = it.displayName
        } ?: kotlin.run {
            binding.navifationView.getHeaderView(0)
                .findViewById<CardView>(R.id.signoutbt).visibility = View.GONE
            Glide.with(im).load(R.drawable.img).circleCrop().into(im)
            userName.text = "Sign-In to use the app"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}