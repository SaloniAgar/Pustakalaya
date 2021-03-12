package com.risingstar.pustakalaya.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.risingstar.pustakalaya.R


class SignInActivity : AppCompatActivity() {

    lateinit var signInButton : SignInButton
    lateinit var mGoogleSignInClient : GoogleSignInClient
    val RC_SIGN_IN = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestEmail()
                                        .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener {
            signIn()
        }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.

            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl

                val bundle = Bundle()
                bundle.putString("User Id",personId)
                bundle.putString("User name",personName)
                bundle.putString("User photo url",personPhoto.toString())
                bundle.putString("User Mail",personEmail)

                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("bundle",bundle)
                Toast.makeText(this,personGivenName,Toast.LENGTH_LONG).show()
                startActivity(intent)
            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message",e.toString())
        }
    }

    override fun onStart() {
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if(account!=null){
            val personName = account.displayName
            val personGivenName = account.givenName
            val personFamilyName = account.familyName
            val personEmail = account.email
            val personId = account.id
            val personPhoto: Uri? = account.photoUrl

            val bundle = Bundle()
            bundle.putString("User Id",personId)
            bundle.putString("User name",personName)
            bundle.putString("User photo url",personPhoto.toString())
            bundle.putString("User Mail",personEmail)

            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("bundle",bundle)
            Toast.makeText(this,personGivenName,Toast.LENGTH_LONG).show()
            startActivity(intent)
            this@SignInActivity.finish()
        }

        super.onStart()
    }

    /*override fun onPause() {
        super.onPause()
        finish()
    }*/
}