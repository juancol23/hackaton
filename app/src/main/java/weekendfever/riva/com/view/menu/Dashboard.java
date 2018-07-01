package weekendfever.riva.com.view.menu;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import weekendfever.riva.com.LoginActivity;
import weekendfever.riva.com.R;
import weekendfever.riva.com.adapter.ViewPagerAdapter;
import weekendfever.riva.com.view.activity.detalle.mapa.MapsActivity;
import weekendfever.riva.com.view.fragmentos.FragmenEventos;
import weekendfever.riva.com.view.fragmentos.FragmentBar;
import weekendfever.riva.com.view.fragmentos.FragmentCercaMi;
import weekendfever.riva.com.view.fragmentos.FragmentCiudad;
import weekendfever.riva.com.view.fragmentos.FragmentDiscotecas;
import weekendfever.riva.com.view.fragmentos.FragmentDiscotecasBackup;
import weekendfever.riva.com.view.fragmentos.FragmentKaraoke;
import weekendfever.riva.com.view.fragmentos.FragmentMapa;
import weekendfever.riva.com.view.fragmentos.FragmentSetting;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView mImageViewProfile;
    TextView mUserName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static Typeface Pacifico,Nightmare,Double,BloodLust;

    NavigationView mNavigationView;
    //a list to store all the products

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initNavigation();
        init();
        initBottom();

    }

    private void initBottom() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.action_cercademi) {
                            menu_eventos();

                            Toast.makeText(getApplicationContext(),"Ordenado",Toast.LENGTH_LONG).show();
                        } else if (id == R.id.action_mapa) {
                            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                            Toast.makeText(getApplicationContext(),"mapa",Toast.LENGTH_LONG).show();

                        }

                        return true;
                    }
                });
    }

    private void initNavigation() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {

        setToolbar("Weekend Fever");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDiscotecas(), "Discotecas");
        adapter.addFragment(new FragmentBar(), "Bares");
        adapter.addFragment(new FragmentKaraoke(), "Karaoke");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        mImageViewProfile = findViewById(R.id.mImageViewProfile);
        mUserName = findViewById(R.id.mUserName);

        if(user != null) {

            Uri photoUrl = user.getPhotoUrl();
            String name = user.getDisplayName();
            String email = user.getEmail();
            String userId = user.getUid();
            mUserName.setText(name);
            Glide.with(Dashboard.this)
                    .load(photoUrl)
                    .thumbnail(Glide.with(Dashboard.this).load(R.drawable.splash_background))
                    .into(mImageViewProfile);

            Log.v("sesion","Inciado");
        }else{
            //needAccess();
            Log.v("sesion","Sin Iniciar");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            setToolbar("Weekend Fever");
        } else if (id == R.id.nav_eventos) {
            setToolbar("Eventos");
            menu_eventos();
        } else if (id == R.id.nav_ciudad) {
            setToolbar("Ciudad");
            menu_ciudad();
        } else if (id == R.id.nav_configuracion) {
            setToolbar("Configuración");
            menu_setting();
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            needAccess();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void needAccess(){
        Intent i = new Intent(Dashboard.this,LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        finish();

    }

    private void setToolbar(String title) {
        // Añadir la Toolbar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void menu_eventos() {
        FragmenEventos eventos = new FragmenEventos();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenidoTotal, eventos)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("")
                .commit();
    }

    private void menu_ciudad() {
        FragmentCiudad ciudad = new FragmentCiudad();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenidoTotal, ciudad)
                .setTransition(FragmentTransaction.TRANSIT_EXIT_MASK)
                .addToBackStack("")
                .commit();
    }

    private void menu_setting() {
        FragmentSetting setting = new FragmentSetting();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenidoTotal, setting)
                .setTransition(FragmentTransaction.TRANSIT_UNSET)
                .addToBackStack("")
                .commit();
    }


    private void navigation_cercademi() {
        FragmentCercaMi cerca = new FragmentCercaMi();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenidoTotal, cerca)
                .setTransition(FragmentTransaction.TRANSIT_UNSET)
                .addToBackStack("")
                .commit();
    }

    private void navigation_vermapa() {
        FragmentMapa mapa = new FragmentMapa();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenidoTotal, mapa)
                .setTransition(FragmentTransaction.TRANSIT_UNSET)
                .addToBackStack("")
                .commit();
    }




}
