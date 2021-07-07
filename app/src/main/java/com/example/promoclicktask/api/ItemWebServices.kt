package  com.example.promoclicktask.api

import com.example.promoclicktask.pojo.home.DataItem
import com.example.promoclicktask.pojo.details.DetailsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ItemWebServices {

    @GET("home-page")
    suspend fun getData(): Response<DataItem>

    @POST("product-detalis")
    suspend fun getDetails(@Query("product_id") productId : Int) : Response<DetailsItem>

}