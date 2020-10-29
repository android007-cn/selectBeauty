package cn.cxy.selectbeauty

import retrofit2.http.GET

interface NetworkService {
    @GET("cxyzy1/select-beauty/raw/master/imageUrl.json")
    suspend fun query(): List<ImageBean>
}