package com.exozet.threehundredsixty.player

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ExozetService {

    @GET("{file}")
    fun exteriorJson(@Path("file") file: String): Observable<Exterior>
}