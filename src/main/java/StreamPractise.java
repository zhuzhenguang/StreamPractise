import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamPractise {
    public Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.tracks()) {
                if (track.length() > 60) {
                    String name = track.name();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    public Set<String> findLongTrackByStream(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.tracks().stream())
                .filter(track -> track.length() > 60)
                .map(Track::name)
                .collect(Collectors.toSet());
    }

    public static void printTrackLengthStatistics(Album album) {
        LongSummaryStatistics longSummaryStatistics = album.tracks().stream()
                .mapToLong(Track::length)
                .summaryStatistics();
    }
}

record Track(long length, String name) {
};

record Album(String name, List<Track> tracks, List<Artist> musicians) {
};

record Artist(String name, Stream<Artist> members, String origin) {
}

class Logger {
    public boolean isDebugEnable() {
        return true;
    }

    public void debug(Supplier<String> message) {
        if (isDebugEnable()) {
            debug(message.get());
        }
    }

    public void debug(String message) {
        System.out.println(message);
    }
}

interface Performance {
    String getName();

    Stream<Artist> getMusicians();

    default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(musician -> Stream.concat(Stream.of(musician), musician.members()));
    }
}

class Artists {
    private List<Artist> artists;

    Artists(List<Artist> artists) {
        this.artists = artists;
    }

    Artist getArtist(int index) {
        if (index < 0 || index >= artists.size()) {
            indexException(index);
        }
        return artists.get(index);
    }

    private void indexException(int index) {
        throw new IllegalArgumentException(index + "doesn't correspond to an Artist");
    }

    String getArtistName(int index) {
        try {
            Artist artist = getArtist(index);
            return artist.name();
        } catch (IllegalArgumentException e) {
            return "unknown";
        }
    }

    Optional<Artist> getArtist2(int index) {
        return index < 0 || index >= artists.size() ?
                Optional.empty() :
                Optional.of(artists.get(index));

    }

    String getArtistName2(int index) {
        return getArtist2(index).map(Artist::name).orElse("unknown");
    }
}
