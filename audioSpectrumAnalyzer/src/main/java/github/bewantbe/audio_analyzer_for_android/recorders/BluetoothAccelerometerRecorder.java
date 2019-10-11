package github.bewantbe.audio_analyzer_for_android.recorders;

import android.bluetooth.BluetoothSocket;
import android.media.AudioRecord;
import android.util.Log;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BluetoothAccelerometerRecorder implements IRecorder {
    private static final Pattern responseSeparatorPattern = Pattern.compile("[\\r][\\n]");
    private static final Pattern responseSeparatorPattern2 = Pattern.compile("[\\r][\\n]");

    BluetoothSocket socket;
    byte[] buffer = new byte[4096];
    StringBuilder accumulatedBuffer = new StringBuilder();

    public BluetoothAccelerometerRecorder(BluetoothSocket socket){
        this.socket = socket;
//        try {
//            socket.getInputStream().skip(socket.getInputStream().available());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void stop() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read(short[] audioData, int offsetInShorts, int sizeInShorts) {
        int ret = 0;
        try {
            int length;
            while (ret<sizeInShorts && (length = socket.getInputStream().read(buffer)) != -1){
                String cleanString = new String(buffer, 0, length).replace("\0", "");
                accumulatedBuffer.append(cleanString);
                Matcher m = responseSeparatorPattern.matcher(accumulatedBuffer);
                while (ret<sizeInShorts && m.find()) {
                    Log.i("TAG", accumulatedBuffer.toString());
                    String message = responseSeparatorPattern2.matcher(accumulatedBuffer.substring(0, m.end())).replaceAll("").trim();
//                    Log.i("BT", message);
                    String[] xyz = message.split("\t");
                    accumulatedBuffer.delete(0, m.end());
                    if(xyz.length!=3) continue;
                    try{
                        audioData[offsetInShorts+ret] = Short.parseShort(xyz[2]);//read Z value of vibrometer
                        ++ret;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    m = responseSeparatorPattern.matcher(accumulatedBuffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ret == 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
