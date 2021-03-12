package com.risingstar.pustakalaya.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.fragments.FavoritesFragment
import com.risingstar.pustakalaya.fragments.HomeFragment
import com.risingstar.pustakalaya.fragments.MyProfileFragment
import com.risingstar.pustakalaya.fragments.ReadHistoryFragment
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {


    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var txtUserNameNav: TextView
    lateinit var imgUserNav : ImageView

    lateinit var mGoogleSignInClient : GoogleSignInClient

    val homeFragment = HomeFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)

        val headerView = navigationView.getHeaderView(0)

        val bundleArgs = intent.getBundleExtra("bundle")
        homeFragment.arguments = bundleArgs
        val userName = bundleArgs?.getString("User name")
        val userPhotoUrl = bundleArgs?.getString("User photo url")

        txtUserNameNav = headerView.findViewById(R.id.txtUserNameNav)
        imgUserNav = headerView.findViewById(R.id.imgUserNav)

        var previousMenuItem : MenuItem? = null

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        txtUserNameNav.text = userName

        Picasso.get().load(userPhotoUrl).error(R.drawable.ic_menu_profile).into(imgUserNav)

        //setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        openHomeFragment()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it


            when(it.itemId){
                R.id.nav_home ->{
                    openHomeFragment()
                }
                R.id.nav_my_profile ->{
                    val myProfileFragment = MyProfileFragment()
                    myProfileFragment.arguments = bundleArgs
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            myProfileFragment
                        )
                        .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_favorites ->{
                    val favoritesFragment = FavoritesFragment()
                    favoritesFragment.arguments = bundleArgs
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            favoritesFragment
                        )
                        .commit()
                    supportActionBar?.title = "Favorites"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_read_history ->{
                    val readHistoryFragment = ReadHistoryFragment()
                    readHistoryFragment.arguments = bundleArgs
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            readHistoryFragment
                        )
                        .commit()
                    supportActionBar?.title = "Read History"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_log_out ->{
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("Sign Out")
                    dialog.setMessage("Confirm Sign Out ??")
                    dialog.setPositiveButton("Sign Out"){text,listener->
                        signOut()
                        this@MainActivity.finish()
                        ActivityCompat.finishAffinity(this@MainActivity)
                        Toast.makeText(this@MainActivity,"Successfully Signed Out",Toast.LENGTH_SHORT).show()
                    }
                    dialog.setNegativeButton("Cancel"){text,listener->
                       openHomeFragment()
                    }
                    dialog.create()
                    dialog.show()

                }

            }
            return@setNavigationItemSelectedListener true
        }

    }

   /* fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openHomeFragment(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                homeFragment
            )
            .commit()
        supportActionBar?.title = "Books"
        drawerLayout.closeDrawers()
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is HomeFragment -> openHomeFragment()

            else -> super.onBackPressed()
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                // ...
                /*val db = Room.databaseBuilder(
                    this@MainActivity,
                    BookDatabase::class.java,
                    "book-db"
                ).build()
                db.bookDao().deleteUser(userEntity)*/
            })
    }

}