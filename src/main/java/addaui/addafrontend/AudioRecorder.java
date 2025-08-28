package addaui.addafrontend;
import javax.sound.sampled.*;
import java.io.*;
public class AudioRecorder {
    private TargetDataLine line;
    private File audioFile;
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    public void startRecording(String filename) {
        try {
            AudioFormat format = new AudioFormat(16000, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                return;
            }

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            audioFile = new File(filename);
            Thread recordingThread = new Thread(() -> {
                try (AudioInputStream ais = new AudioInputStream(line)) {
                    AudioSystem.write(ais, fileType, audioFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            recordingThread.start();

            System.out.println("Recording started...");

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void stopRecording() {
        line.stop();
        line.close();
        System.out.println("Recording stopped, saved to " + audioFile.getAbsolutePath());
    }

    public File getAudioFile() {
        return audioFile;
    }
}
