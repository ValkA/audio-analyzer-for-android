package github.bewantbe.audio_analyzer_for_android.recorders;

import java.io.IOException;

public interface IRecorder {
    void stop();
    int read(short[] audioData, int offsetInShorts, int sizeInShorts) throws IOException;
}
