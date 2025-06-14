package ayds.apolo.songinfo.home.model.repository

import ayds.apolo.songinfo.home.model.entities.SpotifySong
import ayds.apolo.songinfo.home.model.repository.external.spotify.SongBroker
import ayds.apolo.songinfo.home.model.repository.external.spotify.SpotifyTrackService
import ayds.apolo.songinfo.home.model.repository.local.spotify.SpotifyLocalStorage
import ayds.apolo.songinfo.home.model.repository.local.spotify.cache.SongCache
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test


class SongRepositoryTest {
    val songCache= mockk<SongCache>()
    val songLocalStorage= mockk<SpotifyLocalStorage>()
    val songBroker= mockk<SongBroker>()
    val songRepository= SongRepository(songLocalStorage,songCache,songBroker)



    @Test
    fun `on cache song should return song in cache`() {
        val songTest: SpotifySong = SpotifySong( "id", "title", "artist", "album", "url", "image", "imageUrl", true,true)
        every { songCache.getSongByTerm("title") } returns songTest
        val result = songRepository.getSongByTerm("title")
        Assert.assertEquals(songTest, result)
        verify { songCache.getSongByTerm("title") }
        verify(inverse=true) { songLocalStorage.getSongByTerm("title")  }
        verify(inverse=true){ songBroker.getSongByTerm("title")  }
    }

    @Test
    fun `on Locally stored song should return a locally stored song`() {
        val songTest: SpotifySong = SpotifySong( "id", "title", "artist", "album", "url", "image", "imageUrl", true,true)
        every { songCache.getSongByTerm("title") } returns null
        every { songLocalStorage.getSongByTerm("title") } returns songTest
        val result = songRepository.getSongByTerm("title")
        Assert.assertEquals(songTest, result)
        verify { songCache.getSongByTerm("title") }
        verify { songLocalStorage.getSongByTerm("title") }
        verify(inverse=true){ songBroker.getSongByTerm("title")  }

    }
    @Test
    fun `on broker song should return a broker song`() {
        val songTest: SpotifySong = SpotifySong( "id", "title", "artist", "album", "url", "image", "imageUrl", true,true)
        every { songCache.getSongByTerm("title") } returns null
        every { songLocalStorage.getSongByTerm("title") } returns null
        every { songBroker.getSongByTerm("title") } returns songTest
        val result = songRepository.getSongByTerm("title")
        Assert.assertEquals(songTest, result)
        verify { songCache.getSongByTerm("title") }
        verify { songLocalStorage.getSongByTerm("title") }
        verify { songBroker.getSongByTerm("title") }
    }



}