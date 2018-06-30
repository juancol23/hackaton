package weekendfever.riva.com.api.service;

import retrofit2.Call;
import retrofit2.http.GET;
import weekendfever.riva.com.model.ArticleResponse;
import weekendfever.riva.com.util.Contanst;

public interface ServiceApiNew {

    @GET(Contanst.ROOT_URL)
    Call<ArticleResponse> obtenerListaAnunciosApi();
}
