package weekendfever.riva.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import weekendfever.riva.com.view.menu.Dashboard;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseUser;
    private static final String TAG = "Login Sign";

    //Login Facebook
    @BindView(R.id.accesoFacebook)
    LoginButton mAccessRelatoFacebook;

    private CallbackManager mCallbackManager;

    //Login Google
    FrameLayout mAccessRelatoGoogle;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        //facebook
        mAccessRelatoFacebook.setReadPermissions("email", "public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        facebookInit();
        googleInit();

    }

    private void facebookInit() {
        mAccessRelatoFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInWithFacebook(loginResult.getAccessToken());
                showSnackBar(" Autenticación satisfactoria");
                Log.v("TAG_FACEBOOK","SUCCESS");
            }
            @Override
            public void onCancel() {
                showSnackBar(" Autenticación cancelada");
                mProgress.dismiss();
                Log.v("TAG_FACEBOOK","onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                showSnackBar("Error en la autenticación");
                mProgress.dismiss();
                Log.v("TAG_FACEBOOK","onError");
            }
        });
    }

    private void googleInit() {
        mAccessRelatoGoogle = (FrameLayout) findViewById(R.id.accesoGoogle);
        //-------------------GOOGLE-----------------------//
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        showSnackBar("Conexion fallo");
                    }

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mAccessRelatoGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut (mGoogleApiClient);
                signIn();
            }
        });
        //-------------------FIN GOOGLE-----------------------//

    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signInWithFacebook(AccessToken accessToken) {
        Log.d("TAG_FACEBOOK", "firebaseAuthWithGoogle:" + accessToken.getUserId());
        mProgress.setMessage("Accediento...");
        mProgress.show();

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            accesoPermitido();
                            Log.v("TAG_FACEBOOK","Permitido");

                        }
                    }
                });
    }


    public void accesoPermitido(){
        Intent i = new Intent(this,Dashboard.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        mProgress.dismiss();
        //finish();
        Log.v("TAG_FACEBOOK","entrando");

    }

    public void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.relative_linear), msg, Snackbar.LENGTH_SHORT)
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            mProgress.setMessage("Accediendo ...");
            mProgress.show();
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                mProgress.dismiss();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            showSnackBar("Error en la Autenticación Google");
                        }else {
                            mProgress.dismiss();
                            checkUserExist();
                        }
                    }
                });
    }


    private void checkUserExist() {
        if (mAuth.getCurrentUser() != null) {

            if (mAuth.getCurrentUser() != null) {
                accesoPermitido();

                final String user_id = mAuth.getCurrentUser().getUid();
                mDatabaseUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(user_id)) {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }


    }
}