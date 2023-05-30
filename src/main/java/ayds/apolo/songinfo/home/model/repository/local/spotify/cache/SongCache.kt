package ayds.apolo.songinfo.home.model.repository.local.spotify.cache

import ayds.apolo.songinfo.home.model.entities.SpotifySong

interface SongCache {
    fun insertSong(query: String, song: SpotifySong)

    fun getSongByTerm(term: String): SpotifySong?

}

internal class SongCacheImpl: SongCache {

    private val songCache = mutableMapOf<String, SpotifySong>()

    override fun insertSong(query: String, song: SpotifySong) {
        songCache[query] = song
    }

    override fun getSongByTerm(term: String) = songCache[term]
}