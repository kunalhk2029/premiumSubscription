package com.app.premiumsubscription.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.premiumsubscription.MainActivity
import com.app.premiumsubscription.R
import com.app.premiumsubscription.databinding.FragmentFirstBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    lateinit var intentSenderLauncher: ActivityResultLauncher<Intent>
    lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        intentSenderLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try
        {
            val account =  task.getResult(ApiException::class.java)!!
            Log.d("ID=","firebaseAuthWithGoogle"+account.id)
            firebaseAuthWithGoogle(account.idToken!!)

        }
        catch (e: ApiException)
        {
            Log.w("TAG","signInResult failed code=" + e.statusCode)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        firebaseAuth=FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        lifecycleScope.launch{
           firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
               binding.pb.visibility=View.GONE
               (activity as MainActivity).initLoggedInInfo()
               checkLoggedInUserInfo()
           }
        }
    }


    private fun checkLoggedInUserInfo(){
        firebaseAuth=FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser!=null){
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLoggedInUserInfo()
        binding.signinbt.setOnClickListener {
            binding.pb.visibility=View.VISIBLE
            // Configure Google Sign In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1062016098229-3o743rla6shfnegqbom6177ri840g3c7.apps.googleusercontent.com")
                .requestEmail()
                .build()
            GoogleSignIn.getClient(requireContext(),gso).signOut()
            intentSenderLauncher.launch(GoogleSignIn.getClient(requireContext(), gso).signInIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}