package ayds.apolo.songinfo.home.model.repository

import ayds.apolo.songinfo.home.model.entities.EmptySong
import ayds.apolo.songinfo.home.model.entities.Song
import ayds.apolo.songinfo.home.model.entities.SpotifySong
import ayds.apolo.songinfo.home.model.repository.external.spotify.SongBroker
import ayds.apolo.songinfo.home.model.repository.local.spotify.SpotifyLocalStorage
import ayds.apolo.songinfo.home.model.repository.local.spotify.cache.SongCache

interface ISongRepository {
    fun getSongByTerm(term: String): Song
}

class SongRepository(private val songLocalStorage:SpotifyLocalStorage,
                     private val songCache:SongCache,
                     private val songBroker:SongBroker): ISongRepository {




    override fun getSongByTerm(term: String): Song {
        var song = songCache.getSongByTerm(term)


        if (song != null) {
            song.setSongIsCacheStored()
        } else {
            song = songLocalStorage.getSongByTerm(term)
            if (song != null) {
                song.setSongIsLocallyStored()
                songCache.insertSong(term, song)
            } else {
                song = songBroker.getSongByTerm(term)
                if (song != null) {
                    songLocalStorage.insertSong(term, song)
                    songCache.insertSong(term, song)
                }
            }

        }
        return song ?: EmptySong
    }


    private fun SpotifySong.setSongIsCacheStored(){
        this.isCacheStored = true

    }

    private fun SpotifySong.setSongIsLocallyStored(){
        this.isLocallyStored = true

    }
}