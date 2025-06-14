package ayds.apolo.songinfo.home.model

import ayds.apolo.songinfo.home.model.repository.SongRepository
import ayds.apolo.songinfo.home.model.repository.external.spotify.SongBrokerImpl
import ayds.apolo.songinfo.home.model.repository.external.spotify.SpotifyModule
import ayds.apolo.songinfo.home.model.repository.external.spotify.wikiResolver
import ayds.apolo.songinfo.home.model.repository.external.spotify.wikiResolverImpl
import ayds.apolo.songinfo.home.model.repository.local.spotify.cache.SongCacheImpl
import ayds.apolo.songinfo.home.model.repository.local.spotify.sqldb.ResultSetToSpotifySongMapperImpl
import ayds.apolo.songinfo.home.model.repository.local.spotify.sqldb.SpotifySqlDBImpl
import ayds.apolo.songinfo.home.model.repository.local.spotify.sqldb.SpotifySqlQueriesImpl

object HomeModelInjector {
  private val wikiResolver: wikiResolver = wikiResolverImpl()
  private val songLocalStorage=SpotifySqlDBImpl(SpotifySqlQueriesImpl(),ResultSetToSpotifySongMapperImpl())
  private val songBroker= SongBrokerImpl(SpotifyModule.spotifyTrackService, wikiResolver)
  private val songCache=SongCacheImpl()
  private val repository:SongRepository= SongRepository(songLocalStorage,songCache,songBroker)

  val homeModel: HomeModel = HomeModelImpl(repository)
}