
package com.pugh.sockso.music;

import com.pugh.sockso.tests.SocksoTestCase;
import com.pugh.sockso.tests.TestDatabase;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class ArtistTest extends SocksoTestCase {
    
    private TestDatabase db;
    
    @Override
    protected void setUp() {
        db = new TestDatabase();
    }

    public void testGetters() {

        final int id = 123, albumCount = 456, trackCount = 789, playCount = 159;
        final String name = "some name";
        final Date dateAdded = new Date();
        final Artist artist = new Artist.Builder()
                .id(id)
                .name(name)
                .dateAdded(dateAdded)
                .albumCount(albumCount)
                .trackCount(trackCount)
                .playCount(playCount)
                .build();

        assertEquals( dateAdded, artist.getDateAdded() );
        assertEquals( trackCount, artist.getTrackCount() );
        assertEquals( albumCount, artist.getAlbumCount() );
        assertEquals( playCount, artist.getPlayCount() );
        
    }

    public void testFindReturnsArtistById() throws Exception {
        db.fixture( "singleTrack" );
        Artist artist = Artist.find( db, 1 );
        assertEquals( 1, artist.getId() );
        assertEquals( "My Artist", artist.getName() );
    }
    
    public void testFindReturnsNullForInvalidArtistId() throws Exception {
        assertNull( Artist.find(new TestDatabase(),123) );
    }
    
    public void testFindallReturnsAllArtists() throws Exception {
        db.fixture( "artists" );
        List<Artist> artists = Artist.findAll( db, 3, 0 );
        assertEquals( 3, artists.size() );
    }
    
    public void testFindallCanBeLimited() throws Exception {
        db.fixture( "artists" );
        List<Artist> artists = Artist.findAll( db, 2, 0 );
        assertEquals( 2, artists.size() );
    }
    
    public void testFindallCanBeOffset() throws Exception {
        db.fixture( "artists" );
        List<Artist> artists = Artist.findAll( db, 3, 1 );
        assertEquals( 2, artists.size() );
    }
    
    public void testFindallLimitOfMinusOneMeansNoLimit() throws Exception {
        db.fixture( "artists" );
        List<Artist> artists = Artist.findAll( db, -1, 0 );
        assertEquals( 3, artists.size() );
    }
    
    public void testFindallReturnsArtistsAlphabetically() throws Exception {
        db.fixture( "artists" );
        List<Artist> artists = Artist.findAll( db, -1, 0 );
        assertEquals( "A Artist", artists.get(0).getName() );
        assertEquals( "Xylophone", artists.get(2).getName() );
    }
    
    public void testFindallReturnsArtistsWithTheirDateAddedSet() throws Exception {
        db.fixture( "artists" );
        List<Artist> artists = Artist.findAll( db, -1, 0 );

        final Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-02-03 01:02:03");
        
        assertEquals(date, artists.get(0).getDateAdded());
    }
    
}
