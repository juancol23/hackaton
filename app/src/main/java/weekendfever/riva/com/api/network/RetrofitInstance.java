package weekendfever.riva.com.api.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import weekendfever.riva.com.util.Contanst;

public class RetrofitInstance {
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofitInstance(){
        if(mRetrofit == null){
            mRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Contanst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
