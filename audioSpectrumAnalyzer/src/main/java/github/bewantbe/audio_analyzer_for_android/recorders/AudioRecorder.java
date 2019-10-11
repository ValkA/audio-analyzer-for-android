package github.bewantbe.audio_analyzer_for_android.recorders;

import android.media.AudioRecord;

public class AudioRecorder implements IRecorder {
    AudioRecord record;

    public AudioRecorder(AudioRecord record){
        this.record = record;
    }

    @Override
    public void stop() {
        record.stop();
        record.release();
    }

    @Override
    public int read(short[] audioData, int offsetInShorts, int sizeInShorts) {
        return record.read(audioData, offsetInShorts, sizeInShorts);
    }
}
