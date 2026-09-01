package com.automationexercices.media;

// Imports بتاعت تسجيل الشاشة
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;

// Imports بتاعت المشروع بتاعك
import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;

// Imports بتاعت تحويل الفيديو (المسارات الصح اللي كانت ضاربة)
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

import static com.automation.remarks.video.RecorderFactory.getRecorder;

public class ScreenRecordManager {

    private static ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();
    public static final String RECORDINGS_PATH = System.getProperty("user.dir") + "/test-output/recordings/";

    /**
     * Starts screen recording.
     */
    public static void startRecording() {
        if (PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                // Ensure the recordings directory exists
                File recordingsDir = new File(RECORDINGS_PATH);
                if (!recordingsDir.exists()) {
                    recordingsDir.mkdirs();
                }

                // Configure the recorder to use the custom directory and file name
                if (PropertyReader.getProperty("executionType").equalsIgnoreCase("local")) {
                    recorder.set(getRecorder(VideoRecorder.conf().recorderType()));
                    // Start recording
                    recorder.get().start();
                    LogsManager.info("Recording Started");
                }

            } catch (Exception e) {
                LogsManager.error("Failed to start recording: " + e.getMessage());
            }
        }
    }

    /**
     * Stops screen recording and returns the video as an InputStream.
     */
    public static void stopRecording(String testMethodName) {
        try {
            if (recorder.get() != null) {
                // Stop the recorder and get the video file
                String videoFilePath = String.valueOf(recorder.get().stopAndSave(testMethodName));
                File videoFile = new File(videoFilePath);

                // Log the file path for debugging
                LogsManager.info("Video file saved at: " + videoFile.getAbsolutePath());

                // Convert the video to .mp4 format
                File mp4File = encodeRecording(videoFile);
                LogsManager.info("Recording Stopped and Converted to MP4: " + mp4File.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts .avi video to .mp4 format
     */
    public static File encodeRecording(File aviFile) {
        File mp4File = new File(aviFile.getAbsolutePath().replace(".avi", ".mp4"));
        try {
            // استخدام الـ Classes الصح من باكدج encode
            VideoAttributes video = new VideoAttributes();
            video.setCodec("h264");
            video.setBitRate(3200000);
            video.setFrameRate(30);

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
            attrs.setVideoAttributes(video);
            attrs.setAudioAttributes(audio);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(aviFile), mp4File, attrs);

            if(aviFile.exists()) {
                aviFile.delete();
            }
        } catch (Exception e) {
            LogsManager.error("Failed to convert video to MP4: " + e.getMessage());
        }
        return mp4File;
    }
}