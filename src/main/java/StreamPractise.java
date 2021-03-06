import java.util.HashSet;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StreamPractise {
    public Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.trackList()) {
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
                .flatMap(album -> album.trackList().stream())
                .filter(track -> track.length() > 60)
                .map(Track::name)
                .collect(Collectors.toSet());
    }

    public static void printTrackLengthStatistics(Album album) {
        LongSummaryStatistics longSummaryStatistics = album.trackList().stream()
                .mapToLong(Track::length)
                .summaryStatistics();
    }
}

record Track(long length, String name) {
};

record Album(List<Track> trackList) {
};

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
