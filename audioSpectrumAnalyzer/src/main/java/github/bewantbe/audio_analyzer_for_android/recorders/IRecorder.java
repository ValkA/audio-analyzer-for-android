package github.bewantbe.audio_analyzer_for_android.recorders;

public interface IRecorder {
    void stop();
    int read(short[] audioData, int offsetInShorts, int sizeInShorts);
}
