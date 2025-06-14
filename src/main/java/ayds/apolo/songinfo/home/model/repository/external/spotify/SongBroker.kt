package ayds.apolo.songinfo.home.model.repository.external.spotify

import ayds.apolo.songinfo.home.model.entities.EmptySong
import ayds.apolo.songinfo.home.model.entities.Song
import ayds.apolo.songinfo.home.model.entities.SpotifySong
import ayds.apolo.songinfo.home.model.repository.WikipediaAPI
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

const val url= "https://en.wikipedia.org/w/"

interface wikiResolver{
    fun resolveData(callResponse: Response<String>): SpotifySong
}
class wikiResolverImpl():wikiResolver{
    override fun resolveData(callResponse: Response<String>): SpotifySong {

        val gson = Gson()
        val jobj: JsonObject = gson.fromJson(callResponse.body(), JsonObject::class.java)
        val query = jobj["query"].asJsonObject
        val snippetObj = query["search"].asJsonArray.firstOrNull()
        var res = SpotifySong("", "No info available", " - ", " - ", " - ", "", "") // valor por defecto
        if (snippetObj != null) {
            val snippet = snippetObj.asJsonObject["snippet"]
            var res=SpotifySong("", snippet.asString, " - ", " - ", " - ", "", "")
        }

        return res

    }
}
interface SongBroker{
    fun getSongByTerm(term: String): SpotifySong?

}
class SongBrokerImpl(private val spotifyService:SpotifyTrackService,private val wikiResolver:wikiResolver):SongBroker {

    ///// Wiki
    var retrofit: Retrofit? = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    var wikipediaAPI = retrofit!!.create(WikipediaAPI::class.java)
    //// end wiki

    override fun getSongByTerm(term: String): SpotifySong? {

        var song=spotifyService.getSong(term)

        if(song==null){
            song=getSongFromWiki(term)
        }
        return song
    }

    private fun getSongFromWiki(term: String): SpotifySong? {
        /////// Last chance, get anything from the wiki
        val callResponse: Response<String>
        try {
            callResponse = wikipediaAPI.getInfo(term).execute()
            System.out.println("JSON " + callResponse.body())
            return wikiResolver.resolveData(callResponse)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return null
    }



}