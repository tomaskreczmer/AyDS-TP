package ayds.apolo.songinfo.home.model.repository.local.spotify.cache

import ayds.apolo.songinfo.home.model.entities.SpotifySong


interface SongCache{
    fun insertSong(term: String, song: SpotifySong)
    fun getSongByTerm(term: String): SpotifySong?
}

class SongCacheImpl: SongCache {
    private val cache = mutableMapOf<String, SpotifySong>()

    override fun insertSong(term: String, song: SpotifySong) {
        cache[term] = song

    }

    override fun getSongByTerm(term: String) = cache[term]

}