package startup.softflix.com.firebasedemoproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //to see all firebase analytics for this project
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    //to work with firebase authentication
    private var mAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //also for analytics part
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //authentication var should be initialized
        mAuth= FirebaseAuth.getInstance()

    }

    fun buLoginEvent(view:View)
    {
        //fired when clicked login button
        val email= etEmail.text.toString()
        val password=etPassword.text.toString()
        //called from this function
        loginToFirebase(email,password)

    }

    //func for login to firebase
    fun loginToFirebase(email:String, password: String)
    {
        //creating user, on completelistener that when complete then should get some result(task will have result)
        mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task->
                    run {

                        //if registratio is successfull
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, "Login successfull", Toast.LENGTH_LONG).show()
                            //unique id for every user to track down etc
                            var currentUser = mAuth!!.currentUser
                            Log.d("Login", currentUser!!.uid)
                        } else {
                            Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
                        }
                    }

                }


    }

//will override if activity started to not show login page if already logged in
    override fun onStart() {
        super.onStart()
    //if there is user,
    var currentUser = mAuth!!.currentUser
    if(currentUser!=null)
    {
        //should go to second activity if already logged in, no need to see login again after logged in successfull
        val intent=Intent(this,Control::class.java)
        //we need to start activity also otherwise will not go to another only by using above line
        startActivity(intent)
    }
    }
}
